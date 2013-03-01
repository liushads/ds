package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemType;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;

public class ListCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListCmd.class);
	
	/**
	 * @param ps[0]=type
	 * @param ps[1]=pageId
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String type = ps[0];

		List<PlayerItem> objs = new LinkedList<PlayerItem>();
		//装备类
		if (type.equals(Item.ARM_TYPE_STR)) {
			for (List<PlayerItem> pis : player.getArms().values()) {
				if (pis != null) {
					objs.addAll(pis);
				}
			}
		} else {
			Map<String, PlayerItem> map = player.getNonArms().get(ps[0]);
			if (map != null) {
				for (PlayerItem pi : map.values()) {
					objs.add(pi);
				}
			}
		}
		
		//排序
		ItemService.sortByLevelDesc(objs);
		int pageId = 0;
		try {
			if (ps[1].startsWith("page_")) {
				pageId = Integer.parseInt(ps[1].split("_")[1]);
				pageId -= 1;
			} else {
				pageId = Integer.parseInt(ps[1]);
			}

			if (pageId < 0) {
				pageId = 0;
			}
			int totalPage = Util.totalPage(objs.size()) - 1;
			if (pageId > totalPage) {
				pageId = totalPage;
			}
		} catch (Exception e) {
		}
		Util.page(objs, pageId, result);
		result.setVO(TAG_RETURN, ps[0]);
		result.setVO(TAG_PLAYER, player);
		List<ItemType> list = new ArrayList<ItemType>();
		for (ItemType it : ResourceCache.instance.getItemTypes()) {
			if (it.getId() > 0 && it.getId() <= 5) {
				list.add(it);
			}
		}
		result.setVO("item_type", list);
		return result;
	}
}
