package com.ppsea.ds.command.mall;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Goods;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
import com.ppsea.ds.manager.GoodsMG;
import com.ppsea.ds.data.Constants;

public class GoldBuyGoodsCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(GoldBuyGoodsCmd.class);
	
	/**
	 * @param ps ps[0] = item_id, ps[1]=数量
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//判断道具是否存在
		if(ps == null || ps.length<3){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_NO_ITEM, "购买的道具不存在!");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		int goodsId = Integer.parseInt(ps[0]);
		int num = Integer.parseInt(ps[1]);
		int buyType = Integer.parseInt(ps[2]);
		
		Goods goods = GoodsMG.instance.getGoods(goodsId);
		//判断道具是否存在
		if(goods == null){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_NO_ITEM, "购买的道具不存在!");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		
		ErrorMsg errMsg = null;
		//用户选择金贝购买，且该道具允许金贝购买
		if( buyType == 0 && goods.getBuyType() == 0){
			errMsg = ItemService.buyPackageInGold(player, goods, num);
		}
		else{
			errMsg = ItemService.buyPackageInAdvGold(player, goods, num);
		}
		
		if( errMsg.getCode() != ErrorCode.SUCC){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		
		result.setVO(TAG_NUM, num);
		result.setVO(TAG_GOODS, goods);
		result.setVO(TAG_PLAYER, player);
		result.setVO("needGold", errMsg.getText());
		result.setVO("discount", Integer.parseInt(errMsg.getObj().toString()));

		// 交易日志
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(goods.getId()).append("|").append(num).append("|");
		result.setExtMsg(stringBuffer);
		
		return result;
	}
}
