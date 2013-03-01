/*
 * 
 */
package com.ppsea.ds.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.ActiveItems;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2011-2-21
 */
public class ActiveService {

	private static Logger logger = Logger.getLogger("Reward");
	
	private static Map<Integer, Map<Integer, ActiveItems>> activeItemMap = new ConcurrentHashMap<Integer, Map<Integer, ActiveItems>>();
	
//	public static void loadActiveItems() {
//		List<ActiveItems> list = DBManager.queryActiveItems();
//		if (list != null && list.size() > 0) {
//			for  (ActiveItems ai : list) {
//				Map<Integer, ActiveItems> map = activeItemMap.get(ai.getType());
//				if (map == null) {
//					map = new HashMap<Integer, ActiveItems>();
//					activeItemMap.put(ai.getType(), map);
//				}
//				map.put(ai.getItemId(), ai);
//			}
//		}
//	}
	
	public static Map<Integer, ActiveItems> getActiveItemsByType(int type) {
		return activeItemMap.get(type);
	}
	
	public static ActiveItems getActiveItems(int type, int itemId) {
		Map<Integer, ActiveItems> map = activeItemMap.get(type);
		if (map != null) {
			return map.get(itemId);
		}
		return null;
	}
	
	public static ErrorMsg openActivePackages(Player player, int itemId, int type) {
		ActiveItems ai = getActiveItems(type, itemId);
		if (ai == null) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "类型错误");
		}
		PlayerItem pi = ItemService.findPlayerItem(player, itemId);
		if (pi == null || pi.getAmount() < ai.getItemAmount()) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "物品不存在或者数量不足");
		}
		ErrorMsg msg = ItemService.releasePlayerItem(player, pi, ai.getItemAmount(), true);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		String str = "恭喜你获得：";
		int logid = 0;
		if (ai.getType() == 1) {
			ai.parseActiveItems();
			Map<Integer, Integer> itemMaps = ai.getItemMaps();
			
			if (itemMaps != null && itemMaps.size() > 0) {
				for (Map.Entry<Integer, Integer> entry : itemMaps.entrySet()) {
					int id = entry.getKey();
					int amount = entry.getValue();
					ItemService.addItem(player, id, amount, false);
					str += ItemMG.instance.getItem(id).getName();
					str += "x"+amount;
					str += ",";
				}
			}
		} else {
			//概率.
			ai.parseActiveProbabilityItems();
			int[] items = ai.getProbabilityItem();
			if (items != null && items.length > 0) {
				int item2giveId = items[0];
				logid= item2giveId;
				int item2giveAmount = items[1];
				ItemService.addItem(player, item2giveId, item2giveAmount, false);
				str += ItemMG.instance.getItem(item2giveId).getName();
				str += "x"+item2giveAmount;
				str += ",";
			} else {
				//扯淡，什么都没得到.
			}
		}
		if (ai.getCopper() != null && ai.getCopper() > 0) {
			player.addCopper(ai.getCopper());
			str += ai.getCopper() +"铜";
		}
		if (ai.getGold() != null && ai.getGold() > 0) {
			player.addGold(ai.getGold());
			str += ",";
			str += ai.getGold()+"金";
		}
		DBService.commit(player);
		logger.info(LoggerHelper.ZONE_ID+"|opaps|E|"+type+"|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+itemId+"|"+logid);
		return new ErrorMsg(ErrorCode.SUCC, str);
	}
	
}
