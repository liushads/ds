package com.ppsea.ds.command.resell;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.SellPriceVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.data.model.ResellPrice;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ResellPriceMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerResellService;


/**
 * 购买货物
 * 以player为基准，同时更新DB  
 * 一次买一个；一次买多个，进行遍历添加
 * @author {xl}
 *
 */
public class BuyGoodsCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();//倒卖对象
		List<BoughtPriceVo> boughtList = player.getBoughtGoods();  //已购买对象
		if(ps.length !=2){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!"));
			return result;
		}
		//参数：1.货物ID、2.数量
		int goodsId = Integer.valueOf(ps[0]);
		ResellPrice resellPrice = ResellPriceMG.instance.getResellPrice(goodsId);
		if(resellPrice == null){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "货物不存在!"));
			return result;
		}
		int goodsNum = Integer.valueOf(ps[1]);
		//需要消耗的倒卖币
		SellPriceVo currPriceVo = player.getPriceById(goodsId);
		int needGold = currPriceVo.getPrice()*goodsNum;
		
		if (playerResell.getResellGold()<=needGold) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_RESELL_NO_RESELL_GOLD, "你的倒卖币不够,努力赚钱再来吧"));
			return result;
		}
		//可买数量上限为10
		if((boughtList.size()+goodsNum) > PlayerResellService.BUYABLE_GOODS_MAX_NUM){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_RESELL_GOODS_FULL, "你可购买货物已经达到上限，先去卖货物吧"));
			return result;
		}
		//扣除倒卖币
		playerResell.setResellGold(playerResell.getResellGold()-needGold);
		//添加已购买货物到内存  BoughtPriceVo  id name price 多个则遍历
		for(int i=1;i<=goodsNum;i++){
			BoughtPriceVo priceVo = new BoughtPriceVo(goodsId, "", currPriceVo.getPrice(),PlayerResellService.getRandomCode());
			boughtList.add(priceVo);
			player.setBoughtGoods(boughtList);
		}
		//更新倒卖对象至DB
		DBService.commit(playerResell);
		result.setVO("goods_name", resellPrice.getItemName());
		
		return result;
	}

}
