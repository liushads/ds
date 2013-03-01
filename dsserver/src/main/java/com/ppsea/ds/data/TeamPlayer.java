package com.ppsea.ds.data;

import com.ppsea.ds.data.model.Player;

public class TeamPlayer extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int playerId;
	private String name;
	private int sectId;
	private int online = 0;
	
	public TeamPlayer(Player player){
		this.playerId = player.getId();
		this.name = player.getName();
		this.sectId = player.getSectId();
		
	}
	

	/**
     * @return the online
     */
    public int getOnline() {
    	return online;
    }




	/**
     * @param online the online to set
     */
    public void setOnline(int online) {
    	this.online = online;
    }

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public int getSectId() {
		return sectId;
	}

	public void setSectId(int sectId) {
		this.sectId = sectId;
	}
	
	
}
