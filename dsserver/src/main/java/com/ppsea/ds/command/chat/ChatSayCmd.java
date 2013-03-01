package com.ppsea.ds.command.chat;

import java.util.Calendar;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.WordFilter;

/**
 * ClassName:ChatAllSay
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Nov 30, 2009		11:32:29 AM
 *
 * @see 	 
 */
public class ChatSayCmd extends BaseCmd {

	/**
	 * (non-Javadoc)
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.command.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);

		/*int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (!(hour >= 8 && hour < 23)) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.COMMON_ERR_BASE, "宵禁时间不允许发言，聊天开放时间从8点至23点。"));
			return result;
		}*/
		
//		if (player.getLevel() < 2) {
//			result.setText(TAG_DESC, "等级2级及以上才能公聊。");
//			result.setStatus(STATUS_FAIL);
//			return result;
//		}
//
//		if (player.getStatus() == 1) {
//			result.setText(TAG_DESC, "你被禁言了！");
//			result.setStatus(STATUS_FAIL);
//			return result;
//		}

		if (System.currentTimeMillis() - player.getLastChatTime() <= Constants.SEND_PUBLIC_INTERVAL * 1000) {
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.COMMON_ERR_BASE, "请不要刷屏！"));
			result.setStatus(STATUS_FAIL);
			return result;
		}

		String content = ps[0];
		String key = ps[1];
		boolean flag = ChatService.isEnableChat(player, key);
		if (!flag) {
			result.setStatus(Command.STATUS_FAIL);
			if (player.getLevel() < 10) {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "您还没有发言的权限，10级后才可以发言!"));
			} else {
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "您被禁言了，暂时不能发言，需要等解禁后才可以发言 ."));				
			}
			return result;
		}
		if (ChatService.CHAT_WORLD.equals(key)) { 
			if (System.currentTimeMillis() - player.getLastChatTime() < Constants.PUBLIC_INTERVAL*1000) {
				result.setStatus(Command.STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "您暂时不能发言，公聊的间隔发送时间为2分钟!"));
				return result;
			}
		}
		/*if (key.equals(ChatService.CHAT_GANG) && player.getTongId() == 0) {
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.COMMON_ERR_BASE, "你没有加入帮会，不能发言！"));
			result.setStatus(STATUS_FAIL);
			return result;
		}else if (key.equals(ChatService.CHAT_TEAM) && player.getTeamId() == 0) {
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.COMMON_ERR_BASE, "你没有加入队伍，不能发言！"));
			result.setStatus(STATUS_FAIL);
			return result;
		}*/

		player.setLastChatTime(System.currentTimeMillis());

		if (content.length() > 0) {
			if (content.length() > 30)
				content = content.substring(0, 30);
			
			content = WordFilter.filterDirtyWord(content);

			ChatService.sayAll(player, content, key);
		}

		result.setVO(TAG_PAGE, 0);
		result.setVO(TAG_MESSAGE_TYPE, key);
		result.setVO(TAG_PAGE_OBJS, ChatService.queryMessages(player, key, 0, Constants.PAGE_SIZE));

		return result;
	}

}
