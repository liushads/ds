/*
 * 
 */
package com.ppsea.ds.data;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.GamblerService;

/**
 * @author Administrator
 * @date 2011-1-27
 */
@SuppressWarnings("serial")
public class GambleEntity extends BaseObject {

	private Player player;
	
	private int[] maxRoundPoints;
	
	private int[] currentRoundPoints;

	/**
     * @return the player
     */
    public Player getPlayer() {
    	return player;
    }

	/**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
    	this.player = player;
    }

	/**
     * @return the maxRoundPoints
     */
    public int[] getMaxRoundPoints() {
    	return maxRoundPoints;
    }

	/**
     * @param maxRoundPoints the maxRoundPoints to set
     */
    public void setMaxRoundPoints(int[] maxRoundPoints) {
    	this.maxRoundPoints = maxRoundPoints;
    }

	/**
     * @return the currentRoundPoints
     */
    public int[] getCurrentRoundPoints() {
    	return currentRoundPoints;
    }

	/**
     * @param currentRoundPoints the currentRoundPoints to set
     */
    public void setCurrentRoundPoints(int[] currentRoundPoints) {
    	this.currentRoundPoints = currentRoundPoints;
    }
	
	public String convert2Str(int[] arry) {
		if (arry == null || arry.length == 0) {
			return null;
		}
		String str = "";
		for (int i = 0; i < arry.length; i++) {
			str += ""+arry[i]+",";
		}
		if (str.length() > 0) {
			str.substring(0, str.length() - 1);
		}
		return str;
	}
	
	public int getMaxPoints() {
		return GamblerService.calResult(this.maxRoundPoints);
	}
	
}
