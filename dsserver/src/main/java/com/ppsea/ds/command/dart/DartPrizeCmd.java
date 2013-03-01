package com.ppsea.ds.command.dart;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DartService;
import com.ppsea.ds.service.ErrorCode;

public class DartPrizeCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		ErrorMsg msg = DartService.getDartPrize(player);
		if (msg.code != ErrorCode.SUCC) {
			setResult(result, STATUS_FAIL, msg);
			return result;
		}
		CommandRequest newCmd = new CommandRequest();
		newCmd.setCmd("d_SD");
		newCmd.setPid(player.getId().toString());
		newCmd.setPs(new String[] { "in" });
		result = callOtherCmd(newCmd);
		result.setVO("label_desc", msg.text);
		return result;
	}

}
