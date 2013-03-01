package com.ppsea.ds.command.resell;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.PlayerResellService;

/**
 * 游戏币兑换  准备页面
 * @author xl
 *
 */
public class ExchangeGameGoldPrepareCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();//倒卖对象
		//现在拥有的倒卖币
		result.setVO(TAG_RESELL_GOLD, playerResell.getResellGold());
		//今日还可以兑换
		result.setVO(TAG_EXCHANGE_REMAIN_GOLD, PlayerResellService.EXCHANGE_GOLD_MAX-playerResell.getExchangeGold());
		
		return result;
	}

}
