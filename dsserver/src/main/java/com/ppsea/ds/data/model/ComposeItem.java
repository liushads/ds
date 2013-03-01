package com.ppsea.ds.data.model;

import java.util.ArrayList;
import java.util.List;

import  com.ppsea.ds.data.BaseObject;

public class ComposeItem extends BaseObject {
    /**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column compose_item.id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private Integer id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column compose_item.item_id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private Integer itemId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column compose_item.need_items
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private String needItems;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column compose_item.need_copper
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private Integer needCopper;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column compose_item.need_gold
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	private Integer needGold;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column compose_item.id
	 * @return  the value of compose_item.id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column compose_item.id
	 * @param id  the value for compose_item.id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column compose_item.item_id
	 * @return  the value of compose_item.item_id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer getItemId() {
		return itemId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column compose_item.item_id
	 * @param itemId  the value for compose_item.item_id
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column compose_item.need_items
	 * @return  the value of compose_item.need_items
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public String getNeedItems() {
		return needItems;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column compose_item.need_items
	 * @param needItems  the value for compose_item.need_items
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public void setNeedItems(String needItems) {
		this.needItems = needItems;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column compose_item.need_copper
	 * @return  the value of compose_item.need_copper
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer getNeedCopper() {
		return needCopper;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column compose_item.need_copper
	 * @param needCopper  the value for compose_item.need_copper
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public void setNeedCopper(Integer needCopper) {
		this.needCopper = needCopper;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column compose_item.need_gold
	 * @return  the value of compose_item.need_gold
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public Integer getNeedGold() {
		return needGold;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column compose_item.need_gold
	 * @param needGold  the value for compose_item.need_gold
	 * @ibatorgenerated  Tue Jan 15 22:06:05 CST 2013
	 */
	public void setNeedGold(Integer needGold) {
		this.needGold = needGold;
	}

	private String itemName;

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	private List<Compose> compose = new ArrayList<Compose>();

	public List<Compose> getCompose() {
		return compose;
	}

	public void setCompose(List<Compose> compose) {
		this.compose = compose;
	}

}