/**
 * 
 */
package com.ppsea.ds.data;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.data.model.PlayerItem;

public class FightLostItem implements Cloneable {
	public List<PlayerItem> lostItems = new LinkedList<PlayerItem>();
	//public List<ShipType> lostShips = new LinkedList<ShipType>();
	public int lostCopper = 0;
	public int lostSupply = 0;
	public int lostMorale = 0;
	public void clear() {
		lostItems.clear();
		///lostShips.clear();
		lostCopper = 0;
		lostSupply = 0;
		lostMorale = 0;
	}
	
	public int getLostMorale() {
		return lostMorale;
	}

	public void setLostMorale(int lostMorale) {
		this.lostMorale = lostMorale;
	}

	/**
	 * 
	 * @return
	 */
	public int getLostSupply() {
		return lostSupply;
	}
	/**
	 * 
	 * @param lostSupply
	 */
	public void setLostSupply(int lostSupply) {
		this.lostSupply = lostSupply;
	}
	/**
	 * @return the lostItems
	 */
	public List<PlayerItem> getLostItems() {
		return lostItems;
	}
	/**
	 * @param lostItems the lostItems to set
	 */
	public void setLostItems(List<PlayerItem> lostItems) {
		this.lostItems = lostItems;
	}

	/**
	 * @return the lostCopper
	 */
	public int getLostCopper() {
		return lostCopper;
	}
	/**
	 * @param lostCopper the lostCopper to set
	 */
	public void setLostCopper(int lostCopper) {
		this.lostCopper = lostCopper;
	}
}
