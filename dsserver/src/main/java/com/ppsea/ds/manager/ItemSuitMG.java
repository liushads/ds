/*
 * 
 */
package com.ppsea.ds.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.ItemSuitProperty;
import com.ppsea.ds.data.model.PlayerItem;

/**
 * @author Administrator
 * @date 2010-5-17
 */
public class ItemSuitMG {
	
	/**
	 * key=item suite id.
	 */
	private Map<Integer, ItemSuit> itemSuitMap = new HashMap<Integer, ItemSuit>();

	public static ItemSuitMG instance = new ItemSuitMG();
	
	
	public boolean isSubItemSuit(PlayerItem playerItem) {
		Integer itemSuitId = playerItem.getItem().getItemSuitId();
		if (itemSuitId != null && itemSuitId.intValue() > 0) {
			return true;
		} else {
			// not sub item suit.
			return false;
		}
	}
	
	public ItemSuit getItemSuit(Integer itemSuitId) {
		return itemSuitMap.get(itemSuitId);
	}
	
	
	public void loadAllItemSuit() {
		List<ItemSuit> itemSuitList = DBManager.queryItemSuites();
		for (ItemSuit itemSuit : itemSuitList) {
			itemSuitMap.put(itemSuit.getId(), itemSuit);
			List<ItemSuitProperty> propertyList = DBManager.queryItemSuitProperty(itemSuit.getId());
			for (ItemSuitProperty property : propertyList) {
				itemSuit.getPropertytMap().put(property.getId(), property);
			}
		}
	}
}
