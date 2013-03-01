package com.ppsea.ds.data.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComposeVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Compose> compose = null;
	private int gold;
	private int copper;
	public List<Compose> getCompose() {
		return compose;
	}
	public void setCompose(List<Compose> compose) {
		this.compose = compose;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int getCopper() {
		return copper;
	}
	public void setCopper(int copper) {
		this.copper = copper;
	}

}
