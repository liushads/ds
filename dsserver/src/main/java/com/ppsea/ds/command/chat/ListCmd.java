package com.ppsea.ds.command.chat;

import java.util.Calendar;

import org.apache.commons.lang.ArrayUtils;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ChatService;

public class ListCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException{
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		Calendar c = Calendar.getInstance();
		result.setVO("month", c.get(Calendar.MONTH));
		
		int page = Integer.parseInt(ps[0]);
		String key = ChatService.CHAT_WORLD;
		if (ArrayUtils.getLength(ps) > 1) {
			key = ps[1];
		}

		/*int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (!(hour >= 8 && hour < 23) || "-1".equals(key)) {//宵禁时间
			result.setVO(TAG_CHAT_STATUS, true);
		}*/

		int start = page * Constants.PAGE_SIZE;
		int end = start + Constants.PAGE_SIZE;
		if (start < 0) {
			start = 0;
		}

		result.setVO(TAG_PAGE, page);
		result.setVO(TAG_MESSAGE_TYPE, key);
		
		if(key.equals(ChatService.CHAT_PRIVATE)){
			result.setVO(TAG_PAGE_OBJS, ChatService.queryPrivateMessages(player, page));

			// 更新读消息时间
			player.setReadMessageTime(System.currentTimeMillis() / 1000);
		}else{
			result.setVO(TAG_PAGE_OBJS, ChatService.queryMessages(player, key, start, end));
		}

		return result;
	}
}
