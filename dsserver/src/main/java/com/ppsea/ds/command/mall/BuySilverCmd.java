package com.ppsea.ds.command.mall;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

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

/**
 * 输入买入个数
 * */
public class BuySilverCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(BuySilverCmd.class);
	
	/**
	 * @param ps ps[0] = item_id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int num = Integer.parseInt(ps[0]);
		if(num<0 || num>1000){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_INPUT_NUM, "输入错误，请输入1~1000的整数!");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}

		//扣金贝
		if( !(player.consumeInGlod( num*100 ) || player.consumeInAdvGlod( num*100 ))){
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_INPUT_NUM, "输入错误，请输入1~1000的整数!");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		
		//加银贝
		int amount = num*Constants.getIntValue("mall.today.sliver.price");

		player.addCopper(amount*1000);
		
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_AMOUNT, amount);
		result.setVO(TAG_GOLD, num);
		
		
		// 交易日志
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(0).append("|").append(num).append("|");
		result.setExtMsg(stringBuffer);
		return result;
	}
}
