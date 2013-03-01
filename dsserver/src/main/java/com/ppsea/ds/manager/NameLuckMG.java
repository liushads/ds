/*
 * 
 */
package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.data.model.NameLuck;

/**
 * @author Administrator
 * @date 2010-7-15
 */
public class NameLuckMG {
	
	public static NameLuckMG instance = new NameLuckMG();
	
	/**
	 */
	private Map<Integer, List<NameLuck>> nameLuckData = new HashMap<Integer, List<NameLuck>>();
	
	private Random rand = new Random();
	
	private NameLuckMG() {
		
	}
	
	public void init() {
		
		List<NameLuck> nameLuckList = DBManager.queryNameLuck();
		for (NameLuck luck : nameLuckList) {
			int type = luck.getIsomerism();
			List<NameLuck> luckList = nameLuckData.get(type);
			if (luckList == null) {
				luckList = new ArrayList<NameLuck>();
				nameLuckData.put(type, luckList);
			}
			luckList.add(luck);
		}
	}

	public NameLuck getNameRandLuck(Integer type) {
		List<NameLuck> nameLuckList = this.nameLuckData.get(type);
		if (nameLuckList != null) {
			int luckId = rand.nextInt(nameLuckList.size());
			return nameLuckList.get(luckId);
		}
		return null;
	}
	
	public NameLuck getNameLuckById(Integer luckId) {
		for (Map.Entry<Integer, List<NameLuck>> entry : nameLuckData.entrySet()) {
			List<NameLuck> list = entry.getValue();
			for (NameLuck luck : list) {
				if (luck.getId().equals(luckId)) {
					return luck;
				}
			}
		}
		return null;
	}
}
