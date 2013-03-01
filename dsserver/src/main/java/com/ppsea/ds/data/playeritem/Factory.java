package com.ppsea.ds.data.playeritem;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.BuffRandVo;
import com.ppsea.ds.data.model.BuffType;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.util.Util;


public class Factory {
	
	public static final String SPLIT_KEY = "_";
	public static final String SPLIT_KEY_2 = "|";
	/**
	 * 根据item来创建playeritem
	 * */
	public static PlayerItem create(Player player, Item item){
		PlayerItem playerItem = new PlayerItem();
		playerItem.setId(GlobalGenerator.instance.getIdForNewObj(playerItem));
		playerItem.setPlayerId(player.getId());
		playerItem.setAmount(1);	
		playerItem.setIsUse(0);
		playerItem.setItemId(item.getId());
		playerItem.setItem(item);
		playerItem.setBindId(0);
		playerItem.setImproveLevel(0);
		playerItem.setIsExchange(item.getIsExchange());
		playerItem.setInExchange(false);
		playerItem.setExchangeAmount(0);
		playerItem.setExchangePrice(0);
		playerItem.setCurrHoleAmount(0);
		playerItem.setStarLevel(0);
		Date now = new Date();
		playerItem.setUpdatedTime(now);
		playerItem.setCreatedTime(now);
		playerItem.setBuffrandids(getItemBuffRand(item));
		setPlayerItemBuff(playerItem);
		setCurrHp(playerItem);
		return mountHandler(playerItem);
	}
	
	/**
	 * 根据类型设置handler
	 * */
	public static PlayerItem mountHandler(PlayerItem playerItem){
		if(playerItem.getItem().getType() == Item.ARM_TYPE){
			playerItem.setHandler(ArmHandler.getInstance());
		}
		if(playerItem.getItem().getType() == Item.REMEDIES_TYPE){
			playerItem.setHandler(RemedyHandler.getInstance());
		}
		if (Item.LIFE_TYPE == playerItem.item.getType()) {
			playerItem.setHandler(LifeHandler.getInstance());
		}
		return playerItem;
	}
	
	/**
	 * 根据item生成相应的附加属性
	 * 产生玩家道具、地上临时道具时时调用
	 * @param item
	 * @return
	 */
	private static String getItemBuffRand(Item item){
		List<BuffRand> brs = getBuffRand(null,item.getBuffNum());
		if (brs == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (BuffRand br : brs) {
			if (sb.length() > 0) {
				sb.append(SPLIT_KEY_2);
			}
			sb.append(br.getIdStr());
			sb.append(SPLIT_KEY);
			sb.append(0);
		}
		return sb.toString();
	}
	
	/**
	 * 根据数量产生相应的随机项，产生的随机项不能重复
	 * @param num
	 * @return
	 */
	public static List<BuffRand> getBuffRand(TreeMap<Integer, BuffRandVo> buffs,int num){
		
		List<BuffRand> buffRands = null;
		if (num <= 0) {
			return buffRands;
		}
		buffRands = new ArrayList<BuffRand>();
		//1、产生随机出现的类型
		Map<String, Integer> keyChanceMap = new HashMap<String, Integer>();
		keyChanceMap.clear();
		for(BuffType bt : ResourceCache.instance.getBuffTypeMap().values()){
			keyChanceMap.put(bt.getIdStr(), bt.getPre());
		}
		
		Set<Integer> set = new HashSet<Integer>();
		int key = 0;
		boolean tmp = false;
		while (set.size() < num) {
			tmp = false;
			key = Integer.parseInt(Util.getPreKey(keyChanceMap));
			if (buffs != null) {
				for (BuffRandVo b : buffs.values()) {
					if (b.getBuffRand().getBuffTypeId().intValue() == key) {
						tmp = true;
						break;
					}
				}
			}
			if (tmp) {
				continue;
			}
			set.add(key);
		}
		//2、在每个随机项中产生具体随机加成
		TreeMap<Integer, BuffRand> buffRand = null;
		Iterator<Integer> iterator = set.iterator();
		int type = 0;
		int buffRandId = 0;
		BuffRandVo brv = null;
		while (iterator.hasNext()) {
			keyChanceMap.clear();
			type = iterator.next();
			buffRand = ResourceCache.instance.getBuffRandMap().get(type);
			for(BuffRand br : buffRand.values()){
				keyChanceMap.put(br.getIdStr(), br.getPre());
			}
			String tmpString = Util.getPreKey(keyChanceMap);
			buffRandId = Integer.parseInt(tmpString); 
			buffRands.add(buffRand.get(buffRandId));
			if (buffs == null) {
				buffs = new TreeMap<Integer, BuffRandVo>();
			}
			brv = new BuffRandVo();
			brv.setBuffRand(buffRand.get(buffRandId));
			brv.setLock("0");
			buffs.put(buffRandId, brv);
		}
		return buffRands;
	}
	
	public static void setPlayerItemBuff(PlayerItem playerItem){
		if (playerItem == null 
				|| playerItem.getBuffrandids() == null 
				|| playerItem.getBuffrandids().length() <= 0) {
			return;
		}
		
		TreeMap<Integer, BuffRandVo> buffRands = null;
		BuffRand br = null;
		String ids_[] = playerItem.getBuffrandids().split("\\|");
		String ids[] = null;
		BuffRandVo brv = null;
		for (int i = 0; i < ids_.length; i++) {
			ids = ids_[i].split(SPLIT_KEY);
			for (int key : ResourceCache.instance.getBuffTypeMap().keySet()) {
				if (ResourceCache.instance.getBuffRandMap().get(key).containsKey(Integer.parseInt(ids[0]))) {
					br = ResourceCache.instance.getBuffRandMap().get(key).get(Integer.parseInt(ids[0]));
					break;
				}
			}
			if (buffRands == null) {
				buffRands = new TreeMap<Integer, BuffRandVo>();
			}
			brv = new BuffRandVo();
			brv.setBuffRand(br);
			brv.setLock(ids[1]);
			buffRands.put(br.getId(), brv);
		}
		if (buffRands != null) {
			playerItem.setBuffRands(buffRands);
		}
	}
	
	private static void setCurrHp(PlayerItem playerItem){
		if (playerItem.getItem().getType() == Item.ARM_TYPE && playerItem.getItem().getPosition() == Item.POS_HP) {
			playerItem.setCurrHp(playerItem.getItem().getHp());
		}
	}
}
