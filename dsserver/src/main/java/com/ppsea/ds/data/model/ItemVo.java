package com.ppsea.ds.data.model;

import java.io.Serializable;

public class ItemVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private String name;
	
	private String desception;

	private int num;
	
	/**
	 * 玩家有该道具的数量
	 */
	private int playerNum;
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getDesception() {
		return desception;
	}

	public void setDesception(String desception) {
		this.desception = desception;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
