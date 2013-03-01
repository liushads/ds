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
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * @author Administrator
 * @date 2010-10-25
 */
public class ArmRepairCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
//		
//		ErrorMsg msg = ItemService.autoRepairArm(player, false);
//		if (msg.code != ErrorCode.SUCC) {
//			result.setStatus(STATUS_FAIL);
//			result.setVO(TAG_ERR_MSG, msg);
//			return result;
//		}
//		result.setStatus("reward");
//		int amount = 0;
//		try {
//			amount = Integer.parseInt(msg.getObj().toString());
//		} catch (Exception e) {}
//		String str = "装备恢复耐久值为"+amount;
//		result.setVO("rewards", str);
		return result;
	}

}
