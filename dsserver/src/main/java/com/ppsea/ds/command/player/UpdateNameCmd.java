package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

public class UpdateNameCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result =new CommandResult(Command.STATUS_SUCC);
		result.setVO(TAG_PLAYER, player);
		return result;
	}

}
