package com.ppsea.ds.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.BuffType;
import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.Dart;
import com.ppsea.ds.data.model.FarmPre;
import com.ppsea.ds.data.model.ItemDecompose;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.ItemType;
import com.ppsea.ds.data.model.Skill;
import com.ppsea.ds.data.model.SpecBuff;
import com.ppsea.ds.manager.DBManager;

/**
 * 新加资源存放处，原有资源不处理
 * @author ygxhj
 *
 */
public class ResourceCache {
	
	public static final ResourceCache instance = new ResourceCache();
	public Random random = new Random();
	private ResourceCache() {
	}
	public static ResourceCache init() {
		return instance;
	}
	
	/**
	 * 特殊附加属性资源
	 */
	private Map<String, SpecBuff> specBuffMap = new HashMap<String, SpecBuff>();
	
	/**
	 * 装备类型资源
	 */
	private List<ItemType> itemTypes = new ArrayList<ItemType>();
	
	/**
	 * 附加属性资源
	 */
	private Map<Integer, TreeMap<Integer, BuffRand>> buffRandMap = new HashMap<Integer, TreeMap<Integer,BuffRand>>();
	
	/**
	 * 附加属性类型
	 */
	private Map<Integer, BuffType> buffTypeMap = new HashMap<Integer, BuffType>();
	
	/**
	 * 合成装备资源
	 */
	private Map<Integer, ComposeItem> composeItem = new HashMap<Integer, ComposeItem>();
	/**
	 * 套装资源
	 */
	private Map<Integer, ItemSuit> itemSuits = new HashMap<Integer, ItemSuit>();
	
	/**
	 * 分解装备资源，根据强化等级分
	 */
	private Map<Integer, List<ItemDecompose>> itemDecomposeMap = new HashMap<Integer, List<ItemDecompose>>();
	
	/**
	 * 装备强化资源库
	 */
	private Map<Integer, ItemImprove> improves = new HashMap<Integer, ItemImprove>();
	
	/**
	 * 押镖资源
	 */
	private Map<Integer, Dart> darts = new HashMap<Integer, Dart>();
	
	/**
	 * 技能资源库，<skill_type,<id,skill>>
	 */
	private Map<Integer, Map<Integer, Skill>> skills = new HashMap<Integer, Map<Integer,Skill>>();
	
	/**
	 * 宝树成长随机事件
	 */
	private Map<Integer, Map<Integer, FarmPre>> farmPars = new HashMap<Integer, Map<Integer,FarmPre>>();
	
	public Map<Integer, Map<Integer, FarmPre>> getFarmPars() {
		return farmPars;
	}
	public Map<Integer, Map<Integer, Skill>> getSkills() {
		return skills;
	}
	public List<ItemType> getItemTypes() {
		return itemTypes;
	}
	public Map<String, SpecBuff> getSpecBuffMap() {
		return specBuffMap;
	}
	
	public void loadSpecBuff(){
		DBManager.loadSpecBuff(specBuffMap);
	}
	
	public void loadBuffRand(){
		DBManager.loadBuffRand(buffRandMap, buffTypeMap);
	}
	
	public void loadComposeItem(){
		DBManager.loadComposeItem(composeItem);
	}
	public void loadItemSuit(){
		DBManager.loadItemSuit(itemSuits);
	}
	public void loadItemDecompose(){
		DBManager.loadItemDecomposes(itemDecomposeMap);
	}
	public void loadItemType(){
		itemTypes = DBManager.loadItemTpye();
	}
	public void loadItemImprove(){
		DBManager.loadItemImprove(improves);
	}
	public Map<Integer, TreeMap<Integer, BuffRand>> getBuffRandMap() {
		return buffRandMap;
	}
	public Map<Integer, BuffType> getBuffTypeMap() {
		return buffTypeMap;
	}
	public Map<Integer, ComposeItem> getComposeItem() {
		return composeItem;
	}
	public Map<Integer, ItemSuit> getItemSuits() {
		return itemSuits;
	}
	public Map<Integer, List<ItemDecompose>> getItemDecomposeMap() {
		return itemDecomposeMap;
	}
	public Map<Integer, ItemImprove> getImproves() {
		return improves;
	}
	public Map<Integer, Dart> getDarts() {
		return darts;
	}
	public void loadDarts(){
		DBManager.loadDart(darts);
	}
	
	public void loadSkills(){
		DBManager.loadSkill(skills);
	}
	
	public void loadFarmPre(){
		DBManager.loadFarmPre(farmPars);
	}

}
