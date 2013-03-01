/*
 * 
 */
package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;

/**
 * @author Administrator
 * @date 2011-3-29
 */
public class EnterWithPasswordCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player,
	 *      java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);

		String password = "";
		if (ps != null && ps.length > 0) {
			password = ps[0];
		}
		if (!player.getPasswd().equals(password)) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD,
			        "书签密码错"));
			return result;
		}
		CommandRequest newCmd = new CommandRequest();
		newCmd.setCmd("p_E");
		newCmd.setPid(player.getId().toString());
		newCmd.setPs(new String[]{});
		//newCmd.setUid(player.getUserId().toString());

		return callOtherCmd(newCmd);
	}

}
