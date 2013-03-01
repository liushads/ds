/*
 * 
 */
package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ActiveService;
import com.ppsea.ds.service.ErrorCode;

/**
 * @author Administrator
 * @date 2011-2-21
 */
public class OpenActivePackagesCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		int itemId = Integer.parseInt(ps[0]);
		int type = Integer.parseInt(ps[1]);
		ErrorMsg msg = ActiveService.openActivePackages(player, itemId, type);
		
		if (msg.code != ErrorCode.SUCC) {
			setResult(result, STATUS_FAIL, msg);
			return result;
		}
		result.setVO("info", msg.getText());
		result.setStatus("info");
		return result;
	}

}
