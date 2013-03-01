/*
 * 
 */
package com.ppsea.ds.command.move;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

/**
 * @author Administrator
 * @date 2010-7-20
 */
public class AutoTravelCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String flag = ps[0];
		if ("1".equals(flag)) {
			//开启自动行走
			player.setAutoSail(true);
		} else {
			player.setAutoSail(false);
		}
		result = callOtherCmd("mv_G", player, ps);
		return result;
	}

}
