package com.ppsea.ds.command.message;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;

/**
 * ClassName:ChatInputCmd 非CARD的支持
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Jun 30, 2009		9:01:11 AM
 *
 * @see 	 
 */
public class MsgInputCmd extends BaseCmd {

	/**
	 * (non-Javadoc)
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.core.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps) {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		if(player.getLatestMessage() != null && player.getLatestMessage().getFromPlayerName() != null)player.setLatestMessage(null);
		boolean flag = ChatService.isEnableChat(player, ChatService.CHAT_PRIVATE);
		if (!flag) {
			result.setStatus(Command.STATUS_FAIL);
			if (player.getLevel() < 10) {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "您还没有发言的权限，10级后才可以发言!"));
			} else {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "您被禁言了，暂时不能发言，需要等解禁后才可以发言 ."));				
			}
			return result;
		}
		result.setText(TAG_RECEIVER, ps[0]);
		return result;
	}

}
