package com.ppsea.ds.data.model;

import java.io.Serializable;

public class Compose implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int itemId;
	private String name;
	private int needNum;
	private int amount;
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNeedNum() {
		return needNum;
	}

	public void setNeedNum(int needNum) {
		this.needNum = needNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
