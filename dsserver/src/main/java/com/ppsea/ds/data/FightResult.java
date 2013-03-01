/**
 * 
 */
package com.ppsea.ds.data;

import com.ppsea.ds.data.model.Player;


public class FightResult implements Cloneable 
{
	private Player src;
	private Player dst;
	private int decHp = 0;
	private int decMp = 0;
	private int autoAddHp = 0;
	private int autoAddMp = 0;
	private int autoAddState = 0;
	private int autoAddEndure = 0;
	
	private boolean didSunder;		//破甲
	private boolean didSeal;		//封印
	private boolean didBrokensoul;  //破魂
	private boolean didPoison;		//中毒
	private boolean didSlow;		//缓慢
	private boolean didRebound;		//反震
	private boolean didAgility;		//躲避
	private boolean didMorale;		//暴击
	
	private boolean isKiller;
	private String nameJx = null;	//绝学名称
	private String failDesc = "";   //失败输出描述
	//------------------------------------------------------------
	public FightResult(Player s, Player d) { src = s; dst = d; }
	public FightResult() {}
	/**
	 * 合并两次战斗结果
	 * @param fr
	 */
	public void merge(FightResult fr)
	{
		this.decHp += fr.decHp;
		this.autoAddHp += fr.autoAddHp;
		this.autoAddState += fr.autoAddState;
		
		this.didSunder = (this.didSunder || fr.didSunder);
		this.didSeal = (this.didSeal || fr.didSeal);
		this.didBrokensoul = (this.didBrokensoul || fr.didBrokensoul);
		this.didPoison = (this.didPoison || fr.didPoison);
		this.didSlow = (this.didSlow || fr.didSlow);
		this.didRebound = (this.didRebound || fr.didRebound);
		
		this.didAgility = (this.didAgility || fr.didAgility);
		this.didMorale = (this.didMorale || fr.didMorale);
		this.isKiller = (this.isKiller || fr.isKiller);
	}
	//////////////////////////////////////////////////////////////
	
	public Player getSrc() {
		return src;
	}
	
	public void setSrc(Player src) {
		this.src = src;
	}
	public Player getDst() {
		return dst;
	}
	public void setDst(Player dst) {
		this.dst = dst;
	}
	public int getDecHp() {
		return decHp;
	}
	public void setDecHp(int decHp) {
		this.decHp = decHp;
	}
	
	public boolean isKiller() {
		return isKiller;
	}
	public void setKiller(boolean isKiller) {
		this.isKiller = isKiller;
	}
	public int getAutoAddHp() {
		return autoAddHp;
	}
	public void setAutoAddHp(int autoAddHp) {
		this.autoAddHp = autoAddHp;
	}
	public int getAutoAddState() {
		return autoAddState;
	}
	public void setAutoAddState(int autoAddState) {
		this.autoAddState = autoAddState;
	}
	public boolean isDidSunder() {
		return didSunder;
	}
	public void setDidSunder(boolean didSunder) {
		this.didSunder = didSunder;
	}
	public boolean isDidSeal() {
		return didSeal;
	}
	public void setDidSeal(boolean didSeal) {
		this.didSeal = didSeal;
	}
	public boolean isDidBrokensoul() {
		return didBrokensoul;
	}
	public void setDidBrokensoul(boolean didBrokensoul) {
		this.didBrokensoul = didBrokensoul;
	}
	public boolean isDidPoison() {
		return didPoison;
	}
	public void setDidPoison(boolean didPoison) {
		this.didPoison = didPoison;
	}
	public boolean isDidSlow() {
		return didSlow;
	}
	public void setDidSlow(boolean didSlow) {
		this.didSlow = didSlow;
	}
	public boolean isDidRebound() {
		return didRebound;
	}
	public void setDidRebound(boolean didRebound) {
		this.didRebound = didRebound;
	}
	public boolean isDidAgility() {
		return didAgility;
	}
	public void setDidAgility(boolean didAgility) {
		this.didAgility = didAgility;
	}
	public int getDecMp() {
		return decMp;
	}
	public void setDecMp(int decMp) {
		this.decMp = decMp;
	}
	public boolean isDidMorale() {
		return didMorale;
	}
	public void setDidMorale(boolean didMorale) {
		this.didMorale = didMorale;
	}
	public int getAutoAddMp() {
		return autoAddMp;
	}
	public void setAutoAddMp(int autoAddMp) {
		this.autoAddMp = autoAddMp;
	}
	public String getNameJx() {
		return nameJx;
	}
	public void setNameJx(String nameJx) {
		this.nameJx = nameJx;
	}
	public String getFailDesc() {
		return failDesc;
	}
	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}
	/**
     * @return the autoAddEndure
     */
    public int getAutoAddEndure() {
    	return autoAddEndure;
    }
	/**
     * @param autoAddEndure the autoAddEndure to set
     */
    public void setAutoAddEndure(int autoAddEndure) {
    	this.autoAddEndure = autoAddEndure;
    }
}
