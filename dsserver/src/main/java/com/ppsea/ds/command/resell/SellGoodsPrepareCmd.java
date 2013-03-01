package com.ppsea.ds.command.resell;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.SellPriceVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;

/**
 * @author xl
 *
 */
public class SellGoodsPrepareCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<BoughtPriceVo> boughtList = player.getBoughtGoods();  //已购买对象
		
		
		if(ps.length !=3){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!"));
			return result;
		}
		if(boughtList == null || boughtList.size()==0){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "没有货物可卖,快去买货吧!"));
			return result;
		}
		//参数：1.下标；2.randomCode
		int goodsIndex = Integer.valueOf(ps[1]);
		int randomCode = Integer.valueOf(ps[2]);
		//检查物品是否存在
		BoughtPriceVo currBoughtPriceVo = player.getBoughtPriceVo(goodsIndex);
		if(currBoughtPriceVo == null){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!请不要重复操作"));
			return result;
		}
		//校验随机码
		if(currBoughtPriceVo.getRandomCode() != randomCode){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "请不要重复操作"));
			return result;
		}
		
		SellPriceVo spv = player.getPriceById(currBoughtPriceVo.getId()); //当前此货物的价格  对象
		int currSellPrice =    spv.getPrice();//卖价
		int profit = currSellPrice-currBoughtPriceVo.getPrice();//利润
		//卖价、名字、单利润 、出售全部总利润
		int goodsTotal = 0;
		for (BoughtPriceVo boughtPriceVo : boughtList) {
			if(boughtPriceVo.getId() == currBoughtPriceVo.getId()){
				goodsTotal ++;
			}
		}
		result.setVO(TAG_SELL_PRICE_VO, spv);
		result.setVO("profit", profit);
		result.setVO("profit_total", profit*goodsTotal);
		//出售单个：类型1|下标|randomCode      出售全部：类型2|goodsId|0
		result.setVO("goods_index", goodsIndex);
		result.setVO("random_code", randomCode);
		result.setVO("goods_id", currBoughtPriceVo.getId());
		
		return result;
	}

}
