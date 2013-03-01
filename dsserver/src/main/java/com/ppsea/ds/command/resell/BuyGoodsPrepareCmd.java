package com.ppsea.ds.command.resell;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.SellPriceVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerResellService;

/**
 * @author xl
 *
 */
public class BuyGoodsPrepareCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();//倒卖对象
		List<BoughtPriceVo> boughtList = player.getBoughtGoods();  //已购买对象
		if(ps.length !=1){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!"));
			return result;
		}
		
		int goodsId = Integer.valueOf(ps[0]);
		
		//可买数量上限为10
		if(boughtList.size() >= PlayerResellService.BUYABLE_GOODS_MAX_NUM){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_RESELL_GOODS_FULL, "你可购买货物已经达到上限，先去卖货物吧"));
			return result;
		}
		// 价格、名字、ID、剩余可买份数
		int remainNumber = PlayerResellService.BUYABLE_GOODS_MAX_NUM-boughtList.size();
		SellPriceVo spv = player.getPriceById(goodsId);
		result.setVO(TAG_SELL_PRICE_VO, spv);
		result.setVO("remain_number", remainNumber);
		return result;
	}

}
