package com.ppsea.ds.data;
/**
 * Pet 动态属性
 */
public class PetDyn extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	
	//基础属性
	private int maxExp;				//本级最大经验值
	
	private int attack ;			//攻击	
	private int	defence ; 			//防御
	private int	agility ; 			//敏捷

		
	//特殊属性,宠物暂时没有特殊属性影响
	private int sunder;        		//破甲
	private int seal;       		//封印
	private int poison;       		//中毒
	private int slow;         		//迟缓
	private int brokensoul;         //破魂
	private int rebound;      		//反震
	
	private int antiSunder;    		//反破甲
	private int antiSeal;   		//反封印
	private int antiPoison;   		//反中毒
	private int antiSlow;     		//反迟缓
	private int antiBrokensoul;     //反破魂
	private int antiRebound;  		//反反震	
	
	public int getMaxExp() {
		return maxExp;
	}
	
	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public void setAttack(int attack) {
		this.attack = attack;
	}
	
	public int getDefence() {
		return defence;
	}
	
	public void setDefence(int defence) {
		this.defence = defence;
	}
	
	public int getAgility() {
		return agility;
	}
	
	public void setAgility(int agility) {
		this.agility = agility;
	}
	
	public int getSunder() {
		return sunder;
	}
	
	public void setSunder(int sunder) {
		this.sunder = sunder;
	}
	
	public int getSeal() {
		return seal;
	}
	
	public void setSeal(int seal) {
		this.seal = seal;
	}
	
	public int getPoison() {
		return poison;
	}
	public void setPoison(int poison) {
		this.poison = poison;
	}
	
	public int getSlow() {
		return slow;
	}
	
	public void setSlow(int slow) {
		this.slow = slow;
	}
	
	public int getBrokensoul() {
		return brokensoul;
	}
	public void setBrokensoul(int brokensoul) {
		this.brokensoul = brokensoul;
	}
	
	public int getRebound() {
		return rebound;
	}
	
	public void setRebound(int rebound) {
		this.rebound = rebound;
	}
	
	public int getAntiSunder() {
		return antiSunder;
	}
	
	public void setAntiSunder(int antiSunder) {
		this.antiSunder = antiSunder;
	}
	
	public int getAntiSeal() {
		return antiSeal;
	}
	
	public void setAntiSeal(int antiSeal) {
		this.antiSeal = antiSeal;
	}
	
	public int getAntiPoison() {
		return antiPoison;
	}
	public void setAntiPoison(int antiPoison) {
		this.antiPoison = antiPoison;
	}
	
	public int getAntiSlow() {
		return antiSlow;
	}
	
	public void setAntiSlow(int antiSlow) {
		this.antiSlow = antiSlow;
	}
	
	public int getAntiBrokensoul() {
		return antiBrokensoul;
	}
	
	public void setAntiBrokensoul(int antiBrokensoul) {
		this.antiBrokensoul = antiBrokensoul;
	}
	
	public int getAntiRebound() {
		return antiRebound;
	}
	
	public void setAntiRebound(int antiRebound) {
		this.antiRebound = antiRebound;
	}
	
}
