/*
 * 
 */
package com.ppsea.ds.command.player;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerEnemy;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;

/**
 * @author Administrator
 * @date 2010-5-20
 */
public class ListEnemyCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		List<PlayerEnemy>  playerEnemyList = PlayerMG.instance.getPlayerEnemyList(player);
		
		return null;
	}

}
