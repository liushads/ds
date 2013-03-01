package com.ppsea.ds.service;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;

public class WalkService {

	/**
	 * 检查玩家战斗状态
	 * @param player
	 * @param result
	 * @return
	 */
	public static boolean checkFight(Player player, CommandResult result) {
		if(checkFightCity(player, result)){
			return true;
		}
		if (checkFightInstal(player, result)) {
			return true;
		}
		if (checkFightStatus(player, result)) {
			return true;
		}
		return false;
	}

	private static boolean checkFightCity(Player player, CommandResult result) {

		return false;
	}

	private static boolean checkFightInstal(Player player, CommandResult result) {
		return false;
	}

	private static boolean checkFightStatus(Player player, CommandResult result) {
		if (player.getLastEnemy() != null) {
			FightService.gotoFightStat(player, player.getLastEnemy());
			return true;
		}
		return false;
	}
}
