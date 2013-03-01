package com.ppsea.ds.command.resell;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerResellService;

/**
 * 游戏币兑换
 * @author xl
 *
 */
public class ExchangeGameGoldCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();//倒卖对象
		int targetGameGold = Integer.valueOf(ps[0])*10000;//传入金额以W为单位
		if(playerResell.getExchangeGold()>=PlayerResellService.EXCHANGE_GOLD_MAX){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "今日已达最大限制,明天再来吧!"));
			return result;
		}
		//计算即将达到累计兑换的游戏币
		int totalExGold = playerResell.getExchangeGold()+targetGameGold;
		if(totalExGold>PlayerResellService.EXCHANGE_GOLD_MAX){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "累计已达最大兑换限制,请重新输入!"));
			return result;
		}
		if(targetGameGold<PlayerResellService.EXCHANGE_GOLD_START){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "最低"+PlayerResellService.EXCHANGE_GOLD_START+"游戏币起兑,请重新输入!"));
			return result;
		}
		if(playerResell.getResellGold()<targetGameGold*PlayerResellService.EXCHANGE_RATE){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "你的倒卖币不足,努力赚钱再来吧!"));
			return result;
		}
		//兑换
		playerResell.setResellGold(playerResell.getResellGold()-targetGameGold*PlayerResellService.EXCHANGE_RATE);
		player.addCopper(targetGameGold);
		result.setVO(TAG_COPPER, targetGameGold);
		
		//更新回今日兑换的游戏币字段
		playerResell.setExchangeGold(totalExGold);
		//更新至DB
		DBService.commit(playerResell);
		DBService.commit(player);
		return result;
	}

}
