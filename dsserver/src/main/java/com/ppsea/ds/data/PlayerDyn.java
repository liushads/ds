package com.ppsea.ds.data;

import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.SpecBuff;

/**
 * player 动态属性
 * @author IBM
 *
 */
public class PlayerDyn extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	
	
	private int speed; 			//城市间移动速度
	
	//基础属性
	private int maxHp;				//最大体力
	private int maxHp_pre;				//最大体力
	private int maxRoom;			//最大负重
	private int maxExp;				//本级最大经验值
	
	private int attackMin ;			//最小攻击	
	private int attackMin_pre ;			//最小攻击	
	private int	attackMax ;			//最大攻击	
	private int	attackMax_pre ;			//最大攻击	
	private int	defence ; 			//防御
	private int	defence_pre; 			//防御
	private int legacy;      		//增加掉落
	private int legacy_pre;      		//增加掉落
//	private int	morale;  			//暴击几率 万分比
	private int parry;				//格挡概率 万分比
	private int parry_pre;				//格挡概率 万分比
	private int crit; 				//暴击几率 万分比
	private int crit_pre; 				//暴击几率 万分比
	
	private int constitution;		//体质1点=10点生命
	private int constitution_pre;		//体质1点=10点生命
	private int forces;				//力量1点=1点防御
	private int forces_pre;				//力量1点=1点防御
	private int agility;			//敏捷1点=1点攻击+0.2暴击概率
	private int agility_pre;			//敏捷1点=1点攻击+0.2暴击概率
	private int intelligence;		//智力1点=1格挡概率
	private int intelligence_pre;		//智力1点=1格挡概率
	private int fame;				//疲劳值
//	private int fame_pre;				//疲劳值
		
	private int lucky;				//幸运
	private int lucky_pre;				//幸运
	
	public PlayerDyn(){
		maxHp = 0;
		attackMax = 0;
		attackMin = 0;
		defence = 0;
	}
	
	public void init(){
		//基础属性
		speed = 1;
		maxRoom =0 ;			//最大负重
		maxExp =0;				//本级最大经验值
		
		maxHp =0;				//最大体力
		attackMin =0 ;			//最小攻击	
		attackMax =0 ;			//最大攻击	
		defence =0 ; 			//防御
		crit = 0;				//暴击几率 万分比
		parry = 0;				//格挡概率 万分比

		constitution = 0;//体质
		forces = 0;//力量
		agility = 0;//敏捷
		intelligence = 0;//智力
		legacy =0;      		//增加掉落
		lucky = 0;	
		fame = 0;

		
		
		maxHp_pre =0;				//最大体力
		attackMin_pre =0 ;			//最小攻击	
		attackMax_pre =0 ;			//最大攻击	
		defence_pre =0 ; 			//防御
		crit_pre = 0;				//暴击几率 万分比
		parry_pre = 0;				//格挡概率 万分比

		constitution_pre = 0;
		forces_pre = 0;
		agility_pre = 0;
		intelligence_pre = 0;
		
		legacy_pre =0;      		//增加掉落
		lucky_pre = 0;	
//		fame_pre = 0;
	}
	
	public void changeMaxHp(int n){
		maxHp += n;
	}
	public void changeAttackMin(int n){
		attackMin += n;
	}
	public void changeAttackMax(int n){
		attackMax += n;
	}
	public void changeAttack(int n){
		attackMin += n;
		attackMax += n;
	}
	public void changeDefence(int n){
		defence += n;
	}
	public void changeCrit(int n){
		crit += n;
	}
	
	public void changeParry(int n){
		parry += n;
	}
	public void changeConstitution(int n){
		constitution += n;
	}
	public void changeForces(int n){
		forces += n;
	}
	public void changeAgility(int n){
		agility += n;
	}
	public void changeIntelligence(int n){
		intelligence += n;
	}
	
	public void changeFame(int n){
		fame += n;
	}
	public void changeLegacy(int n){
		legacy += n;
	}
	public void changeLucky(int n){
		lucky += n;
	}
	public void changeMaxRoom(int n){
		maxRoom += n;
	}
	public void changeSpeed(int n){
		speed += n;
	}
	
	
	
	
	//--------------------------------------
	public void changeMaxHpPre(int n){
		maxHp_pre += n;
	}
	public void changeAttackMinPre(int n){
		attackMin_pre += n;
	}
	public void changeAttackMaxPre(int n){
		attackMax_pre += n;
	}
	public void changeAttackPre(int n){
		attackMin_pre += n;
		attackMax_pre += n;
	}
	public void changeDefencePre(int n){
		defence_pre += n;
	}
	public void changeCritPre(int n){
		crit_pre += n;
	}
	
	public void changeParryPre(int n){
		parry_pre += n;
	}
	public void changeConstitutionPre(int n){
		constitution_pre += n;
	}
	public void changeForcesPre(int n){
		forces_pre += n;
	}
	public void changeAgilityPre(int n){
		agility_pre += n;
	}
	public void changeIntelligencePre(int n){
		intelligence_pre += n;
	}
	
//	public void changeFamePre(int n){
//		fame_pre += n;
//	}
	public void changeLegacyPre(int n){
		legacy_pre += n;
	}
	public void changeLuckyPre(int n){
		lucky_pre += n;
	}
	

	
	public int getSpeed() {
		return speed;
	}

	public String print() {
		StringBuffer sb = new StringBuffer();
		sb.append("maxHp=" + maxHp + ",")
		.append(",attackMin="+ attackMin + ",")
		.append(",attackMax="+ attackMax + ",")
		.append(",defence="+ defence + ",");
		return sb.toString();
	}	
	
	/**
	 * 根据道具增加用户属性
	 * @param player 玩家
	 * @param playerItem 道具
	 * @param flag = 1 is add, flag = -1 is sub
	 * */
	public void changeProperty(Player player,PlayerItem playerItem, int flag){
		if(playerItem == null || playerItem.getIsUse() != 1){
			return;
		}				
		//附加
		maxHp = maxHp + flag*playerItem.getItem().getHp();
		attackMin = attackMin + flag*playerItem.getItem().getAttackMin();		
		attackMax = attackMax + flag*playerItem.getItem().getAttackMax();	
		defence = defence +flag*playerItem.getItem().getDefence();
		
		//检查
		checkProperty();				
	}
	
	/**
	 * 时效道具改变玩家基础属性.
	 * @param playerItem
	 * @param flag
	 */
	public void changeProperty(Item item, int flag) {
		if(item == null){
			return;
		}		
		//改变基础属性
		changeStandardProperty(item,flag);
		
		//重检查.
		checkProperty();
	}
	

	public int getMaxHp() {
		return maxHp;
	}


	public int getAttackMin() {
		return attackMin;
	}


	public int getAttackMax() {
		return attackMax;
	}


	public int getDefence() {
		return defence;
	}


//	public int getAgility() {
//		return agility;
//	}
//
//	public Agility(int agility) {
//		this.agility = agility;
//	}

	public int getLegacy() {
		return legacy;
	}



	public int getMaxRoom() {
		return maxRoom;
	}


	public int getMaxExp() {
		return maxExp;
	}


	// ----------------------------------------------------------------- 私有方法

	/**
	 * 改变基础属性值
	 * @param item 道具
	 * @param flag 标记
	 **/
	private void changeStandardProperty (Item item, int flag){
		//计算基础属性
		maxHp = maxHp + flag*item.getHp();
		attackMin = attackMin + flag*item.getAttackMin();		
		attackMax = attackMax + flag*item.getAttackMax();		
		defence = defence +flag*item.getDefence();
	}	
	/**
	 * 检查属性是否有为负数的,如果有为负数的则设置为0 
	 **/
	private void checkProperty(){
		if(attackMin < 0) attackMin =0;
		if(attackMax < 0) attackMax =0;
		if(defence < 0) defence = 0;
	}
	public int getLucky() {
		return lucky;
	}


	public int getCrit() {
		return crit;
	}




	public int getConstitution() {
		return constitution;
	}




	public int getAgility() {
		return agility;
	}




	public int getIntelligence() {
		return intelligence;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getForces() {
		return forces;
	}


	public int getFame() {
		return fame;
	}

	public int getParry() {
		return parry;
	}
	
	public int getMaxHp_pre() {
		return maxHp_pre;
	}

	public int getAttackMin_pre() {
		return attackMin_pre;
	}

	public int getAttackMax_pre() {
		return attackMax_pre;
	}

	public int getDefence_pre() {
		return defence_pre;
	}

	public int getLegacy_pre() {
		return legacy_pre;
	}

	public int getParry_pre() {
		return parry_pre;
	}

	public int getCrit_pre() {
		return crit_pre;
	}

	public int getConstitution_pre() {
		return constitution_pre;
	}

	public int getForces_pre() {
		return forces_pre;
	}

	public int getAgility_pre() {
		return agility_pre;
	}

	public int getIntelligence_pre() {
		return intelligence_pre;
	}

//	public int getFame_pre() {
//		return fame_pre;
//	}

	public void setMaxExp(int maxExp) {
		this.maxExp = maxExp;
	}

	public int getLucky_pre() {
		return lucky_pre;
	}


}
