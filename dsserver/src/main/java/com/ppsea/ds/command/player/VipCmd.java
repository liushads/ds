package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

public class VipCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if (ps==null||ps.length==0) {
			result.setStatus("index");
			return result;
		}
		String action=ps[0];
		return result;
	}

}
