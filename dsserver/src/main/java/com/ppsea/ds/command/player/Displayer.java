/*
 * 
 */
package com.ppsea.ds.command.player;

import java.util.Date;

import com.ppsea.ds.data.BaseObject;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;

/**
 * @author Administrator
 * @date 2011-1-11
 */
@SuppressWarnings("serial")
public class Displayer extends BaseObject{
	/**
	 * 首页置顶1，公聊2.
	 */
	int displayType;
	
	/**
	 * 装备展示1，结婚炫耀2，3，许愿等.
	 */
	int serverType;
	
	Date createTime;
	
	/**
	 * 开始展示的时间.
	 */
	long displayTime;
	
	Player player;
	
	Player matePlayer;
	
	PlayerItem playerItem;
	String seller;
	
	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	/**
	 * 许愿内容.
	 */
	String wishes;
	
	/**
     * @return the wishes
     */
    public String getWishes() {
    	return wishes;
    }

	/**
     * @param wishes the wishes to set
     */
    public void setWishes(String wishes) {
    	this.wishes = wishes;
    }

	/**
     * @return the displayType
     */
    public int getDisplayType() {
    	return displayType;
    }

	/**
     * @param displayType the displayType to set
     */
    public void setDisplayType(int displayType) {
    	this.displayType = displayType;
    }

	/**
     * @return the serverType
     */
    public int getServerType() {
    	return serverType;
    }

	/**
     * @param serverType the serverType to set
     */
    public void setServerType(int serverType) {
    	this.serverType = serverType;
    }

	/**
     * @return the createTime
     */
    public Date getCreateTime() {
    	return createTime;
    }

	/**
     * @param createTime the createTime to set
     */
    public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }

	/**
     * @return the player
     */
    public Player getPlayer() {
    	return player;
    }

	/**
     * @return the displayTime
     */
    public long getDisplayTime() {
    	return displayTime;
    }

	/**
     * @param displayTime the displayTime to set
     */
    public void setDisplayTime(long displayTime) {
    	this.displayTime = displayTime;
    }

	/**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
    	this.player = player;
    }

	/**
     * @return the matePlayer
     */
    public Player getMatePlayer() {
    	return matePlayer;
    }

	/**
     * @param matePlayer the matePlayer to set
     */
    public void setMatePlayer(Player matePlayer) {
    	this.matePlayer = matePlayer;
    }

	/**
     * @return the playerItem
     */
    public PlayerItem getPlayerItem() {
    	return playerItem;
    }

	/**
     * @param playerItem the playerItem to set
     */
    public void setPlayerItem(PlayerItem playerItem) {
    	this.playerItem = playerItem;
    }

}
