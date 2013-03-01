package com.ppsea.ds.data.model;

import java.io.Serializable;

public class PlayerDartVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int playerId;
	
	private String playerName;
	
	private int playerLevel;
	
	private String dartName;
	
	private int isRob = 0;


	public int getIsRob() {
		return isRob;
	}

	public void setIsRob(int isRob) {
		this.isRob = isRob;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	public String getDartName() {
		return dartName;
	}

	public void setDartName(String dartName) {
		this.dartName = dartName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
