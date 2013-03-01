package com.ppsea.ds.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.util.DateUtil;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Present;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.PresentMG;

/**
 * 玩家送礼 
 **/
public class PresentService {
	
	private static final int DEFAULT_DAYS = 20;

	private static final Logger log = Logger.getLogger(PresentService.class);
	
	
	/**
	 * 今天是否已经给某人送给同样的礼物
	 * @param playerId
	 * @param receiverId
	 * @param itemId
	 * @return
	 */
	public static boolean isSendSamepresent(int playerId, int receiverId, int itemId) {
		int count = DBManager.countTodaySendPresent(playerId, receiverId, itemId);
		return (count > 0) ? true : false;
	}
	
	/**
	 * 最近几天收到的礼物
	 * @param receiver
	 * @param days 负数表示几天前
	 * @return
	 */
	public static List<Present> getTimeReceivePresents(Player receiver, int presentType, int days) {
		return DBManager.getTimeReceivePresents(receiver, presentType, days);
	}
	
	public static long refreshDataTime(Date fromTime, Date endTime) {
		if (null == fromTime) {
			fromTime = Calendar.getInstance().getTime();
		}
		return (endTime.getTime() - fromTime.getTime()) / (1000 * 60 * 30);
	}
	
	public static int countPresents(Player receiver) {
		int receiverId = receiver.getId();
		receiver.setLastCountPresentsTime(Calendar.getInstance().getTime());
		return DBManager.countPresents(receiverId);
	}
	
	/**
	 * 送礼物
	 * @param player
	 * @param receiverId
	 * @param item
	 * @return
	 * @throws PpseaException
	 */
	public static Present sendPresent(Player player, int receiverId, Item item) throws PpseaException {
		return DBManager.sendPresent(player, receiverId, item);
	}
	
	/**
	 * 最近收到的一个礼物
	 * @param receiverId
	 * @return
	 */
	public static Map<String, Object> getRecentReceivePresent(int receiverId, int presentType) {
		Player receiver = new Player();
		receiver.setId(receiverId);
		List<Present> presents = PresentService.getTimeReceivePresents(receiver, presentType, -3);
		Map<String, Object> itemMap = null;
		if (null != presents && !presents.isEmpty()) {
			Item item = PresentMG.instance.getPresent(presents.get(0).getItemId());
			itemMap = createItemMap(presents.get(0), item);
		}
		return itemMap;
	}
	
	public static List<Present> getRecentReceivePresents(int receiverId, int page) {
		Player receiver = new Player();
		receiver.setId(receiverId);
		return getRecentReceivePresents(receiver, page);
	}
	
	/**
	 * 玩家收到的所有礼物
	 * @param receiver
	 * @return
	 */
	public static List<Present> getRecentReceivePresents(Player receiver, int page) {
		return DBManager.getRecentReceivePresents(receiver, page);
	}
	
	public static String getPresentMessages(String name, Player player, Player receiver) {
		String p1Name = player.getName().replaceAll("\\\\", "\\\\\\\\");
		String p2Name = "";
		if(receiver!=null){
			p2Name = receiver.getName().replaceAll("\\\\", "\\\\\\\\");
		}

		return PresentMG.instance.getPresentMessages().get(name).replaceAll("M", p1Name).replaceAll("F", (null == receiver) ? "你" : p2Name);
	}
	
	public static int sumWorth(Player receiver) {
		int receiverId = receiver.getId();
		receiver.setLastsumWorthTime(Calendar.getInstance().getTime());
		return DBManager.sumWorth(receiverId);
	}
	
	public static Map<String, Object> createItemMap(Present present, Item item) {
		Map<String, Object> itemMap = new HashMap<String, Object>();
		itemMap.put("presentPlayer", PlayerMG.instance.getPlayerSimple(present.getPlayerId()));
		itemMap.put("item", item);
		itemMap.put("presentTime", DateUtil.distanceOfTimeInWords(present.getCreatedAt(), Calendar.getInstance().getTime(), DEFAULT_DAYS));
		return itemMap;
	}
}
