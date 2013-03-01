package com.ppsea.ds.command.message;


import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
/**
 * ClassName:SendMsgCmd
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Oct 23, 2009		4:29:44 PM
 *
 * @see 	 
 */
public class SendMsgCmd extends BaseCmd {

	/**
	 * (non-Javadoc)
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.command.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps) {
		CommandResult result = new CommandResult();
		String content = ps[0];
		Integer toPlayerId = Integer.valueOf(ps[1]);
		
		if (content != null) {
			content = content.trim();
			if ( content.length() >=100) {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_OFFLINE,"您输入的消息过长，请重新输入"));
				result.setStatus(STATUS_FAIL);
				return result;
			}
			if ("".equals(content)) {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_OFFLINE,"您输入的消息为空"));
				result.setStatus(STATUS_FAIL);
				return result;
			}
		}
		ChatService.sendMessagePrivate(toPlayerId, player.getId(), player.getName(), content);
		result.setText(TAG_DESC, "消息发送成功！");
		
		result.setVO(TAG_RECEIVER, toPlayerId);
		result.setStatus(STATUS_SUCC);
		return result;
	}

}
