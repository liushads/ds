package com.ppsea.ds.command.resell;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerResellService;

/**
 * 物价刷新
 * 每次刷新花费1元人民币，每天最多刷新50次
 * @author xl
 *
 */
public class RefreshPriceCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();
		if(playerResell.getFlushTimes()>=PlayerResellService.REFRESH_COST_GOLD_MAX_TIMES){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_RESELL_FLUSH_MAX_TIMES, "已达到今日最大刷新次数,明天再来吧"));
			return result;
		}
		
		if (player.getGold()  < PlayerResellService.REFRESH_PRICE_COST_GOLD) {
			throw new NoMoneyException("余额不足");
		}
		// 扣金锭
		player.setGold(player.getGold() - PlayerResellService.REFRESH_PRICE_COST_GOLD);
		DBService.commit(player);
		//执行刷新
		PlayerResellService.refreshPrice(player,true);
		
		
		callOtherCmd("rs_M", player, ps);
		return null;
	}

}
