/*
 * 
 */
package com.ppsea.ds.command.npc;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.PlayerService;

/**
 * @author Administrator
 * @date 2011-5-11
 */
public class NpcLeaveCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		if (ps == null || ps.length == 0) {
			result.setStatus("main");
			return result;
		}
		Integer id = PlayerService.getCenterId(player);
		player.setSpecialCity(true);
		result = callOtherCmd("mv_W", player, new String[]{id.toString()});
		player.setSpecialCity(false);
		return result;
	}

}
