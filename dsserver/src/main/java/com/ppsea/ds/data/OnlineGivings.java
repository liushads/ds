/*
 * 
 */
package com.ppsea.ds.data;

import com.ppsea.ds.data.model.Item;

/**
 * @author Administrator
 * @date 2010-6-8
 */
public class OnlineGivings extends BaseObject {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private Item item;
    
    private int itemNum;
    
    private int onlineTime;

	/**
     * @param item
     * @param itemNum
     * @param onlineTime
     */
    public OnlineGivings(Item item, int itemNum, int onlineTime) {
	    super();
	    this.item = item;
	    this.itemNum = itemNum;
	    this.onlineTime = onlineTime;
    }

	/**
     * @return the item
     */
    public Item getItem() {
    	return item;
    }

	/**
     * @param item the item to set
     */
    public void setItem(Item item) {
    	this.item = item;
    }

	/**
     * @return the itemNum
     */
    public int getItemNum() {
    	return itemNum;
    }

	/**
     * @param itemNum the itemNum to set
     */
    public void setItemNum(int itemNum) {
    	this.itemNum = itemNum;
    }

	/**
     * @return the onlineTime
     */
    public int getOnlineTime() {
    	return onlineTime;
    }

	/**
     * @param onlineTime the onlineTime to set
     */
    public void setOnlineTime(int onlineTime) {
    	this.onlineTime = onlineTime;
    }

}
