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
 * @date 2011-1-19
 */
public class OutCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		/**
		int cityFacilityId = PlayerService.getCenterId(player);
		
		ErrorMsg msg = PlayerService.move(player, cityFacilityId, false);
		setFailResult(result, msg);
		*/
		return result;
	}

}
