/*
 * 
 */
package com.ppsea.ds.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerFishing;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2010-11-5
 */
public class LakeWatcherService {

	
	private static Logger logger = Logger.getLogger("Reward");
	
	public final static String ACTIVE_NAME_WACHER = "lake_watcher";
	
	public final static int ACTIVE_TYPE_GIVE = 1;
	public final static int ACTIVE_TYPE_FISH = 2;
	
	/**
	 * daily giving include:2个许愿瓶，1个鱼竿，2个普通鱼饵, 2传话筒.
	 */
	public static ErrorMsg getFishingGivings(Player player) {
		String activeKey = player.getId().toString() + "_"+ACTIVE_NAME_WACHER + "_" + ACTIVE_TYPE_GIVE;
		PlayerActive pa = player.getPlayerActive().get(activeKey);
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		ErrorMsg msg = null;
		if (pa == null) {
			// has not give.
			pa = PlayerMG.instance.createPlayerActive(player, ACTIVE_NAME_WACHER, ACTIVE_TYPE_GIVE);
			msg = dailyGiving(player);
			player.getPlayerActive().put(activeKey, pa);
			DBService.commit(pa);
			return msg;
		} else {
			Calendar lastCal = pa.getLastCal();
			int lastDay = lastCal.get(Calendar.DAY_OF_MONTH);
			if (dayOfMonth != lastDay) {
				// today has not give.
				msg = dailyGiving(player);
				pa.setLastCal(cal);
				pa.setTime(pa.getTime() + 1);
				player.getPlayerActive().put(activeKey, pa);
				DBService.commit(pa);
				return msg;
			} else {
				//can be gave.
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "你今天的钓鱼福利已经领完，请明天再来");
			}
			
		}
	}
	
	private static ErrorMsg dailyGiving(Player player) {
		String msg = "系统赠送你：";
		String tp = "";
		//10472-许愿瓶
		ItemService.addItem(player, 10472, 2, false);
		msg += "2x"+ItemMG.instance.getItem(10472).getName()+",";
		tp += "|2|10472";
		//10473-鱼竿
		PlayerItem pi = ItemService.findPlayerItem(player, 10473);
		if (pi == null) {
			ItemService.addItem(player, 10473, 1, false);
			msg += "1x"+ItemMG.instance.getItem(10473).getName()+",";
			tp += "|1|10473";
		}
		//10474-普通鱼饵
		ItemService.addItem(player, 10474, 2, false);
		msg += "2x"+ItemMG.instance.getItem(10474).getName()+",";
		tp += "|2|10474";
		//10471-传话筒
		ItemService.addItem(player, 10471, 2, false);
		msg += "2x"+ItemMG.instance.getItem(10471).getName()+"";
		tp += "|2|10471";
		ChatService.sendMessageSystem(player.getId(), msg);
		logger.info(LoggerHelper.ZONE_ID+"|lake_watcher|"+ACTIVE_TYPE_GIVE+"|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+tp);
		return new ErrorMsg(ErrorCode.SUCC,msg);
	}
	
	public static ErrorMsg setFishingLure(Player player, int lureId) {
		PlayerItem pi = ItemService.findPlayerItem(player, lureId);
		
		if (pi == null || pi.getAmount() == 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST,"物品不存在");
		}
		
		ItemService.releasePlayerItem(player, pi, 1, true);
		player.setLastLureId(lureId);
		
		return new ErrorMsg(ErrorCode.SUCC, "上鱼饵成功，你可以放长线钓大鱼了");
	}
	
	private static String[] fish_notes = {
		"你看到鱼漂似乎轻轻的动了一下，不过瞬间又恢复了平静；你看到鱼漂一动不动，你都怀疑是不是时间停止了，你的眼都花了",
		"你看到鱼线飘荡在海水中，鱼漂突然有力的抖动了一下",
		"你看到鱼漂露一目，但不是短促下沉。而后又渐渐回升到二目，真是让人困惑",
		"你看到鱼漂一点点上升，慢慢的直至平躺在了水面上",
		"你感觉鱼线绷紧起来，鱼漂剧烈的抖动，似乎有鱼上钩了",
		"你看到鱼漂慢慢上升至三目，突然停顿了一下，但还未有短促有力下沉的现象",
		"你发现鱼漂徐徐上升一目左右，然后短促有力的下沉了一下",
		"鱼线都把你的手勒疼了，浮漂已经彻底沉入海底，快起钩，上鱼了",
		"你看到鱼漂突然在海面上消失了，应该是条大鱼"};
	
	public static String gonaWatch(Player player) {
		int lastNoteId = player.getLastFishNoteId();
		if (lastNoteId >= fish_notes.length) {
			lastNoteId = 0;
			player.setLastFishNoteId(lastNoteId);
		}
		if (player.getLastLureId() <= 0) {
			return "鱼钩上没有鱼饵了，没有鱼饵怎么会有鱼上钩呢，赶紧去换吧";
		}
		String note = fish_notes[lastNoteId];
		player.setLastFishNoteId(player.getLastFishNoteId() + 1);
		return note;
	}
	
	//精制白饵概率分布.
	private static int[][] jingzhibaier = {{10462,9}, {10369,9}, {10631,9},{10529,9},{10511,9},{36,9},{10500,2}, {10501, 2}, {10502, 2}, {10389, 9}, {10469, 2}, {10475, 9}, {70, 9}, {10414, 9}, {10498, 2}};
	private static int[] jingzhibaierPool = new int[100];
	private static Random rand = new Random();
	
	//大藻鲤饵
	private static int[][] dazaolier = {{10503,5}, {10492,4}, {10497,4}, {10493,5}, {10494, 4}, {10490, 4}, {37, 10}, {31, 10},{10403, 10},{60, 4}, {10347, 10}, {10314, 1}, {10495, 4}, {10530, 10}, {10476, 10}, {10499, 5}};
	private static int[] dazaolierPool = new int[100];
	
	//百合鱼饵
	private static int[][] baiheyuer = {{10370,1},  {10465, 5}, {10463, 4},{10466,5}, {10467,5},{10464,5},{36, 1}, {37, 2}, {31, 2}, {10457, 7}, {59, 1},{10405,7}, {10346, 2},{10468, 5},{10491, 4},{10496, 5},{10319,1},{10324,1}, {10470, 5},{10467,4},{10531, 7},{10534,7},{10536,7},{10477,7}};
	private static int[] baihePool = new int[100];
	
	public static void init() {
		try {

			//精制白饵
			int pos = 0;
			for (int i = 0; i < jingzhibaier.length; i++) {
				int itemId = jingzhibaier[i][0];
				int amount = jingzhibaier[i][1];
				for (int j = 0; j < amount; j++) {
					jingzhibaierPool[pos] = itemId;
					pos ++;
				}
			}
			pos = 0;
			//大藻鲤饵
			for (int i = 0; i < dazaolier.length; i++) {
				int itemId = dazaolier[i][0];
				int amount = dazaolier[i][1];
				for (int j = 0; j < amount; j++) {
					dazaolierPool[pos] = itemId;
					pos ++;
				}
			}
			pos = 0;
			//百合鱼饵
			for (int i = 0; i <baiheyuer.length; i++ ) {
				int itemId = baiheyuer[i][0];
				int amount = baiheyuer[i][1];
				for (int j = 0; j < amount; j++) {
					baihePool[pos] = itemId;
					pos ++;
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ErrorMsg liftUp(Player player, CommandResult result) {
		int lastLureId = player.getLastLureId();
		player.setLastLureId(0);
		player.setLastFishNoteId(0);
		int itemId = 0;
		int amount = 0;
		switch(lastLureId) {
		case 10474:
			//普通鱼饵只能调到许愿瓶
			itemId = 10472;
			amount = 1;
			PlayerFishing pf = pickUpFishingWish(player);
			if (pf != null) {
				result.setVO("wishId",pf.getId());
				return new ErrorMsg(ErrorCode.SUCC, amount+"x"+ItemMG.instance.getItem(itemId).getName(), itemId);
			} else {
				return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "什么都不钓到");
			}
		case 10475:
			//精制白饵
			int index = rand.nextInt(jingzhibaierPool.length);
			itemId = jingzhibaierPool[index];
			amount = 1;
			if (itemId == 10405) {
				amount = 10;
			}
			break;
		case 10476:
			//大藻鲤饵
			int i = rand.nextInt(dazaolierPool.length);
			itemId = dazaolierPool[i];
			amount = 1;
			if (itemId == 10405) {
				//鉴定卷轴
				amount = 20;
			}
			break;
		case 10477:
			//百合鱼饵
			int j = rand.nextInt(baihePool.length);
			itemId = baihePool[j];
			amount = 1;
			if (itemId == 10405) {
				amount = 30;//鉴定卷轴
			}
			break;
			default:
				//没有鱼饵，什么都没掉到
				return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有鱼饵，什么都没钓到");
				
		}
		ItemService.addItem(player, itemId, amount, false);	
		PlayerFishing pf = pickUpFishingWish(player);
		String str = amount+"x"+ItemMG.instance.getItem(itemId).getName();
		if (pf != null) {
			str += ",1x许愿瓶";
			result.setVO("wishId",pf.getId());
			//result.setVO("itemId",10472);
		}
		//ItemService.addItem(player, 10472, 1, false);//许愿瓶
		logger.info(LoggerHelper.ZONE_ID+"|lake_watcher|"+ACTIVE_TYPE_FISH+"|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+amount+"|"+itemId+"|1|10472");
		//System.out.println("itemId="+itemId+", "+ItemMG.instance.getItem(itemId).getName());
		return new ErrorMsg(ErrorCode.SUCC, str, itemId);
	}
	
	//未被吊起的许愿瓶
	private static Map<Integer, Map<Integer, PlayerFishing>> unFishingWishes = new ConcurrentHashMap<Integer, Map<Integer, PlayerFishing>>();
	
	private static List<PlayerFishing> wishesList = new LinkedList<PlayerFishing>();
	private static long lastUpdateTime = 0;
	
	private static Map<Integer, Map<Integer, PlayerFishing>> getUnFishingWishes() {
		return unFishingWishes;
	}
	
	public static void removeFishWishes(Player player) {
		getUnFishingWishes().remove(player.getId());
		updateFishingList(true);
	}
	
	public static void addFishWishes(Player player, PlayerFishing pf) {
		Map<Integer, PlayerFishing> ulist = getUnFishingWishes().get(player.getId());
		if (ulist == null) {
			ulist = new HashMap<Integer, PlayerFishing>();
			getUnFishingWishes().put(player.getId(), ulist);
		}
		ulist.put(pf.getId(), pf);
		updateFishingList(true);
	}
	
	public static synchronized void updateFishingList(boolean isForce) {
		if (!isForce) {
			if (System.currentTimeMillis() - lastUpdateTime < 60 * 1000) {
				return ;
			}
		}
		wishesList.clear();
		Map<Integer, Map<Integer, PlayerFishing>> wishes = getUnFishingWishes();
		for (Map.Entry<Integer, Map<Integer, PlayerFishing>> entry : wishes.entrySet()) {
			wishesList.addAll(entry.getValue().values());
		}
		lastUpdateTime = System.currentTimeMillis();
	}
	
	public static PlayerFishing pickUpFishingWish(Player player) {
		updateFishingList(false);
		int i = 0;
		PlayerFishing pf = null;
		try {
			while (i < 3) {
				int index = rand.nextInt(wishesList.size());
				pf = wishesList.get(index);
				if (pf != null) {
					if (pf.getPlayerId() == player.getId().intValue() || (pf.getReceiverId() != null && pf.getReceiverId() > 0)) {
						i++;
						pf = null;
						continue;
					} else {
						break;
					}
				}
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (pf != null) {
			Map<Integer, PlayerFishing> map = getUnFishingWishes().get(pf.getPlayerId());
			if (map != null) {
				map.remove(pf.getId());
				if (map.size() == 0) {
					getUnFishingWishes().remove(pf.getPlayerId());
				}
				updateFishingList(true);
			}
			pf.setReceiverId(player.getId());
			DBService.commit(pf);
			player.getFishWishes().put(pf.getId(),pf);
		}
		return pf;
	}
	
	private static PlayerFishing createPlayerFishing(Player player, String content) {
		PlayerFishing pf = new PlayerFishing();
		pf.setId(GlobalGenerator.instance.getIdForNewObj(pf));
		pf.setPlayerId(player.getId());
		pf.setContent(content);
		pf.setCreateDate(new Date());
		pf.setLastOperateId(player.getId());
		pf.setLastUpdateDate(new Date());
		return pf;
	}
	
	public static ErrorMsg throwFishWishes(Player player, String content) {
		PlayerItem pi = ItemService.findPlayerItem(player, 10472);//许愿瓶
		if (pi == null || pi.getAmount() == 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有许愿瓶，不能许愿");
		}
		ItemService.releasePlayerItem(player, pi, 1, true);
		PlayerFishing pf = createPlayerFishing(player, content);
		DBService.commit(pf);
		addFishWishes(player, pf);
		return Constants.SUCC;
	}
	
	public static ErrorMsg replyFishWishes(Player player, int wishId, String content) {
		PlayerFishing pf = player.getFishWishes().get(wishId);
		if (pf == null) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统数据异常");
		} else {
			
			int lastOpId = pf.getLastOperateId();
			Player p = PlayerMG.instance.getOnlinePlayer(lastOpId);
			if (p == null) {
				return new ErrorMsg(ErrorCode.ERR_NOT_ONLINE, "对方不在线 ，不可回复");
			}
			PlayerItem pi = ItemService.findPlayerItem(player, 10471);//传话筒
			if (pi == null || pi.getAmount() == 0) {
				return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有传话筒，不能回复");
			}
			ItemService.releasePlayerItem(player, pi, 1, true);
			pf.setContent(content);
			pf.setLastOperateId(player.getId());
			pf.setLastUpdateDate(new Date());
			DBService.commit(pf);
			player.getFishWishes().remove(pf.getId());
			p.getFishWishes().put(pf.getId(), pf);
		}
		return Constants.SUCC;
	}
}
