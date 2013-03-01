package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

public class ZhshGoCmd  extends BaseCmd{

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		result.setVO("lable_user_id", player.getUserId());
		result.setVO("lable_id", ps[0]);
		
		return result;
	}

}
