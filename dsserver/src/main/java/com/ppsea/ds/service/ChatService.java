package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Message;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.WordFilter;
import com.ppsea.ds.util.cache.LRUMap;
import com.ppsea.ds.util.serial.LoopQ;

/**
 * ClassName:ChatService
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Nov 30, 2009		10:33:25 AM
 *
 * @see 	 
 */
public class ChatService {
	public static final String CHAT_WORLD = "0";
	public static final String CHAT_PRIVATE = "1";
	public static final String CHAT_GANG = "2";
	public static final String CHAT_TEAM = "3";
	public static final String CHAT_SECT = "4";
	public static final String CHAT_ALL = "5";//禁言(私聊和公聊)(-1全开)
	
	public static final int MESSAEG_COUNT = 49;
	
	// 全服的聊天消息，格式<MainType-SubType, Messages>
	private static Map<String, LoopQ<Message>> chatAllMessages = new LRUMap<String, LoopQ<Message>>();
	
	// 帮聊，<帮ID，消息列表>
	private static Map<String, LoopQ<Message>> chatGangMessages = new LRUMap<String, LoopQ<Message>>();
	
	// 队聊，<队ID，消息列表>
	private static Map<String, LoopQ<Message>> chatTeamMessages = new LRUMap<String, LoopQ<Message>>();
	
	/**
	 * 全服的聊天
	 */
	public static void sayAll(Player player, String content, String key){
		Integer pid = (player==null?0:player.getId());
		String nick = (player==null?null:player.getName());
		
		String ncontent = WordFilter.filterDirtyWord(content);
		Map<String, LoopQ<Message>> map = chatAllMessages;
		
		/*if(key.equals(CHAT_GANG)){
			map = chatGangMessages;
			key = String.valueOf(player.getTongId());
		}
		else */if(key.equals(CHAT_SECT)){
			key = CHAT_WORLD + player.getSectId();
		}
		
		say(pid, nick, ncontent, key, map);
	}
	
	/**
	 * 小Q全服公聊
	 */
	public static void sayXiaoQ(String content){
		sayAll(null, content, CHAT_WORLD);
	}
	
	/**
	 * 队聊
	 */
	public static void sayTeam(Player player, String content){
		sayAll(player, content, CHAT_TEAM);
	}
	
	public static void sayTong(String tongId, String content){
		Map<String, LoopQ<Message>> map = chatAllMessages;
		map = chatGangMessages;
		say(null, null, content, tongId, map);
	}
	
	
	/**
	 * 发言
	 * 
	 * @param player
	 * @param content
	 * @param key    
	 * @return void    
	 * @throws
	 */
	private static void say(Integer pid, String nickName, String content, String key, Map<String, LoopQ<Message>> map){
		LoopQ<Message> q = map.get(key);
		
		if(q == null){
			List<Message> list = Collections.synchronizedList(new ArrayList<Message>());
			q = new LoopQ<Message>(list, MESSAEG_COUNT);
			
			map.put(key, q);
		}
		
		Message message = null;
		
		if(q.size() > 0) message = q.get(0);
		
		if(message != null && message.getContent().equals(content))
			return;
		
		message = new Message();
		message.setId(GlobalGenerator.instance.getIdForNewObj(message));
		message.setTypeId(Message.MSG_PUBLIC);
		message.setFromPlayerId(pid);
		message.setFromPlayerName(nickName);
		message.setContent(content);
		message.setCreateTime(new Date());
		
		q.add(message);
	}	
	
	/**
	 * 发言的列表
	 */
	public static List<Message> queryMessages(Player player, String key, int start, int end){
		Map<String, LoopQ<Message>> map = chatAllMessages;
		
		/*if(key.equals(CHAT_GANG)){
			map = chatGangMessages;
			key = String.valueOf(player.getTongId());
		}
		else */if(key.equals(CHAT_SECT)){
			key = CHAT_WORLD + player.getSectId();
		}

		LoopQ<Message> q = map.get(key);
		
		ArrayList<Message> list = new ArrayList<Message>(Constants.PAGE_SIZE);
		
		if(q != null){
			for(int i = start; i < end; i++){
				if(q.size() > i) list.add(q.get(i));
			}
		}
		
		return list;
	}

	/**
	 * 全服发言的最新两条
	 */
	public static List<Message> queryTop2PublicMessages(String key){
		LoopQ<Message> q = chatAllMessages.get(key);
		
		ArrayList<Message> top = new ArrayList<Message>(2);
		
		if(q != null){
			if(q.size() > 0) top.add(q.get(0));
			if(q.size() > 1) top.add(q.get(1));
		}
		
		return top;
	}
	
	/**
	 * 副本队伍的最新两条
	 */
	public static List<Message> queryTop2TeamMessages(String key){
		LoopQ<Message> q = chatTeamMessages.get(key);
		
		ArrayList<Message> top = new ArrayList<Message>(2);
		
		if(q != null){
			if(q.size() > 0){
				Message msg = q.get(0);
				msg.setTypeId(Message.MSG_TEAM);
				top.add(msg);
			}
			if(q.size() > 1){
				Message msg = q.get(1);
				msg.setTypeId(Message.MSG_TEAM);
				top.add(msg);
			}
		}
		
		return top;
	}
	
	/**
	 * 私聊
	 */
	public static void sendMessagePrivate(int pid, int fromPlayerId, String fromPlayerName, String content){
		sendMessage(pid, Message.MSG_PLAYER_PRIVATE, fromPlayerId, fromPlayerName, content, true);
	}
	
	/**
	 * 系统消息
	 */
	public static void sendMessageSystem(int pid, String content, boolean save){
		sendMessage(pid, Message.MSG_SYSTEM, 0, null, content, save);
	}

	public static void sendMessageSystem(int pid, String content){
		sendMessageSystem(pid, content, true);
	}
	
	/**
	 * 查询消息
	 */
	public static List<Message> queryPrivateMessages(Player player, int page){
		List<Message> list = new ArrayList<Message>();
		
		if(page * Constants.PAGE_SIZE >= player.getMessages().size()){
			// 数据库加载
			PlayerMG.instance.loadMessages(player, page);
		}
				
		int i = page * Constants.PAGE_SIZE;
		int end = i + Constants.PAGE_SIZE;
		int last = end > player.getMessages().size()?player.getMessages().size():end;
		
		for(;i < last;i++){
			list.add(player.getMessages().get(i));
		}
		
		// 清空最新消息
		player.setLatestMessage(null);
		
		return list;
	}

	private static void sendMessage(int pid, int type, int fromPlayerId, String fromPlayerName, String content, boolean save) {
		String ncontent = WordFilter.filterDirtyWord(content);
		Message message = new Message();
		message.setId(GlobalGenerator.instance.getIdForNewObj(message));
		message.setPlayerId(pid);
		message.setTypeId(type);
		message.setFromPlayerId(fromPlayerId);
		message.setFromPlayerName(fromPlayerName);
		message.setContent(ncontent);
		message.setCreateTime(new Date());
//		message.setPlain(Byte.parseByte(ps[3]));
		
		if(save) DBService.commit(message);
		
		Player player = PlayerMG.instance.getPlayerFromCache(pid);
		
		if (player != null){
			player.getMessages().add(0, message);
			
			if(message.getCreateTime().getTime() / 1000 > player.getReadMessageTime()) 
				player.setLatestMessage(message);
		}
	}	
	
	public static boolean isEnableChat(Player player, String type) {
		if (!ChatService.CHAT_PRIVATE.equals(type)) {
			if (player.getLevel() < 10) {
				return false;
			}
		}

		if (type.equals(player.getChatStatus()+"")) {
			return false;
		}
		if (CHAT_ALL.equals(""+player.getChatStatus()) && (type.equals(ChatService.CHAT_PRIVATE)  || type.equals(ChatService.CHAT_WORLD))) {
			return false;
		}
		return true;
	}

}
