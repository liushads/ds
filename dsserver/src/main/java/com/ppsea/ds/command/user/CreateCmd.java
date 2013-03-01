package com.ppsea.ds.command.user;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.SectMG;

public class CreateCmd extends BaseCmd{
	public CommandResult execute(CommandRequest cmdReq){
		CommandResult result = new CommandResult(STATUS_SUCC);
		try {
			String uid = cmdReq.getUid();
			int sect_id = Integer.parseInt(cmdReq.getPs()[0]);
			Sect sect = SectMG.instance.getSect(sect_id);
			result.setVO(TAG_UID, uid);
			result.setVO("sect", sect);
			
		} catch (Exception e) {
		}
		return result;
	}
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		// TODO Auto-generated method stub
		return null;
	}

}
