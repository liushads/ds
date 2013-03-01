/*
 * 
 */
package com.ppsea.ds.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.dao.ItemGroupDAO;
import com.ppsea.ds.data.dao.ItemGroupDAOImpl;
import com.ppsea.ds.data.model.ItemGroup;

/**
 * @author Administrator
 * @date 2010-5-15
 */
public class ItemGroupMG {

	/** logger writer. */
	private static Logger logger = Logger.getLogger(ItemGroupMG.class);

	/** instance of ItemGroupMG. */
	public static ItemGroupMG instance = new ItemGroupMG();

	/** key-value collection to put item id with item group object. */
	private Map<Integer, ItemGroup> itemGroupMap = new HashMap<Integer, ItemGroup>();

	/**
	 * private constructor.
	 */
	private ItemGroupMG() {

	}

	public void loadItemGroup() {
		logger.info("begin to load item group");
		List<ItemGroup> itemGroupList = queryAllItemGroup();
		for (ItemGroup itemGroup : itemGroupList) {
			itemGroupMap.put(itemGroup.getItemid(), itemGroup);
		}
		logger.info("load item group end, loaded record: "+itemGroupList.size());
	}

	/**
	 * query all item group from DB.
	 */
	@SuppressWarnings("unchecked")
	public List<ItemGroup> queryAllItemGroup() {
		ItemGroupDAO itemGroupDao = (ItemGroupDAO) DBManager.getDataDao(ItemGroupDAOImpl.class);
		try {
			return itemGroupDao.selectByExample(null);
		} catch (SQLException e) {
			logger.error("query all item group failed.", e);
		}
		 return new ArrayList<ItemGroup>();
	}
	
	/**
	 * retrieve item group from DB.
	 * @param itemId
	 * @return
	 */
	public ItemGroup getItemGroupByItemId(Integer itemId) {
		return this.itemGroupMap.get(itemId);
	}
	
	/**
	 * retrieve all item id in the same group by group id.
	 * @param groupId
	 * @return
	 */
	public List<Integer> getGroupedItemId(Integer groupId) {
		List<Integer> itemIdList = new ArrayList<Integer>();
		for (Map.Entry<Integer, ItemGroup> entry : itemGroupMap.entrySet()) {
			try {
				if (groupId.equals(entry.getValue().getGroupid())) {
					itemIdList.add(entry.getKey());
				}		
			} catch (Exception e) {
				
			}
		}
		return itemIdList;
	}
	
}
