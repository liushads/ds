/*
 * 
 */
package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DisplayerService;
import com.ppsea.ds.service.ErrorCode;

/**
 * @author Administrator
 * @date 2011-1-11
 */
public class DisplayerCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		int displayType = 0; 
		
		int serverType = 0;
		int pitemId = 0;
		int mateId = 0;
		int sid=0;
		ErrorMsg msg = null;
		if (ps != null && ps.length == 2) {
			result.setVO("serverType", ps[0]);
			result.setVO("objId", ps[1]);
			result.setStatus("display");
			return result;
		}
		try {
			displayType = Integer.parseInt(ps[0]);
			serverType = Integer.parseInt(ps[1]);
			if (serverType == 1) {
				//装备
				pitemId = Integer.parseInt(ps[2]);
				msg = DisplayerService.getInstance().commitPlayerItemDisplayer(player, pitemId, displayType);
			} else if (serverType == 2){
				//结婚
				mateId = Integer.parseInt(ps[2]);
				msg = DisplayerService.getInstance().commitMarryDisplayer(player, mateId, displayType);
			}
			else if (serverType==4) {
				sid=Integer.parseInt(ps[2]);
				msg=DisplayerService.getInstance().commitLeiTaiDisplayer(player, sid, displayType);
			}
			else {
				//错误.
				msg = new ErrorMsg(ErrorCode.ERR_SYS, "类型错误");
				
			}
		} catch (NumberFormatException e) {
			msg = new ErrorMsg(ErrorCode.ERR_SYS, "系统错误");
		}
		if (msg.code != ErrorCode.SUCC) {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, msg);
			return result;
		}
		result.setStatus("info");
		result.setVO("serverType", serverType);
		result.setVO("object", msg.getObj());
		return result;
	}

}
