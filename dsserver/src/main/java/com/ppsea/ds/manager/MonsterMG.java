package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.CityFacilityMonster;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.MonsterPropConf;
import com.ppsea.ds.data.model.NpcInteraction;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;

public class MonsterMG {
	private static final Logger log = Logger.getLogger(MonsterMG.class);
	private static Random RAND = new Random();
	public static MonsterMG instance = new MonsterMG();
	//Map<monster-id,Monster>
	private static Map<Integer, Monster> monster = new ConcurrentHashMap<Integer, Monster>();
	//Map<level,list<Monster>>
	private static Map<Integer, LinkedList<Monster>> 
		instanceMonster = new ConcurrentHashMap<Integer, LinkedList<Monster>>();
	//Map<city-facility-id,list<Monster>>
	private static Map<Integer, LinkedList<CityFacilityMonster>> 
		cityFacilityMonster = new HashMap<Integer, LinkedList<CityFacilityMonster>>();

	/**
	 * 特殊怪的数据配置.<type,<key,value>>.
	 */
	private static Map<Integer,List<MonsterPropConf>> monsterPropConf = new HashMap<Integer,List<MonsterPropConf>>();
	public final static int MONSTER_TYPE_AUTO_RESTORE_HP = 1;
	
	
	
	/**
	 * 
	 */
	public MonsterMG(){
	}
	/**
	 * 加载monster和cityfacility-monster
	 */
	public void init() 
	{
		cityFacilityMonster.clear();
		monster.clear();
		instanceMonster.clear();
		List<CityFacilityMonster> lcfm = DBManager.loadCityFacilityMonster();
		for (CityFacilityMonster c : lcfm) {
			Integer cfid = c.getCityFacilityId();
			if (cityFacilityMonster.containsKey(cfid) == false) {
				cityFacilityMonster.put(cfid, new LinkedList<CityFacilityMonster>());
			}
			cityFacilityMonster.get(cfid).add(c);
		}
		List<Monster> lm = DBManager.loadMonster();
		for (Monster m : lm) 
		{
			m.initDropItemProp();
			monster.put(m.getId(), m);
			if (m.getIsInstance().intValue() == 1) {
				if (!instanceMonster.containsKey(m.getLevel())) {
					instanceMonster.put(m.getLevel(), new LinkedList<Monster>());
				}
				instanceMonster.get(m.getLevel()).add(m);
			}
		}
		loadMonsterPropConf();
	}

	/**
	 * 生成练宠怪
	 * @param p
	 * @return
	 */
	public Monster getMonsterForPet(Player p) {
		int level = p.getLevel() + (new Random()).nextInt(10);
		if(level>200){
			level = 200;
		}
		LinkedList<Monster> lm = instanceMonster.get(level);
		if (lm != null && lm.size() > 0) {
			return lm.get(new Random().nextInt(lm.size()));
		}
		return null;
	}
	/**
	 * 根据等级获取副本怪
	 * @param level
	 * @return
	 */
	public LinkedList<Monster> getMonsterByLevel(int level) {
		return instanceMonster.get(level);
	}
	/**
	 * 根据monsterid获取monster实例
	 * @param monsterId
	 * @return
	 */
	public Monster getMonsterById(int monsterId) {
		return monster.get(monsterId);
	}
	
	private Item getOneDropItem(int itemId, int itemProp, int addLegacy) {
		Item it = ItemMG.instance.getItem(itemId);
		if (it != null && it.getId().intValue() > 0) 
		{
			itemProp += itemProp*addLegacy/100;
			//调整掉落概率到万分之一,gz,20100811
			if (RAND.nextInt(10000) < itemProp) return it;
			//if (RAND.nextInt(100) < itemProp) return it;
		}
		return null;
	}
	/**
	 * 获取怪物掉落装备
	 * @param m
	 * @return
	 */
	public List<Item> getDropItemsFromMonster(Player p, Monster m) {
		List<Item> all = new LinkedList<Item>();
		//playerLegacy = 0; add by gz,20100811,取消玩家爆率增加功能
		int playerLegacy = 0; //p.getDyn().getLegacy(); //玩家属性能增加的掉落概率
		if (playerLegacy > 100) playerLegacy = 100;//保护，最多增加1倍爆率
		
		Item it = getOneDropItem(m.getItemId1(), m.getItemProp1(), playerLegacy);
		if (it != null) all.add(it);
		
		it = getOneDropItem(m.getItemId2(), m.getItemProp2(), playerLegacy);
		if (it != null) all.add(it);
		
		it = getOneDropItem(m.getItemId3(), m.getItemProp3(), playerLegacy);
		if (it != null) all.add(it);

		it = getOneDropItem(m.getItemId4(), m.getItemProp4(), playerLegacy);
		if (it != null) all.add(it);
		
		it = getOneDropItem(m.getItemId5(), m.getItemProp5(), playerLegacy);
		if (it != null) all.add(it);
		
		//随机掉的装备数量
		int randomCount = m.getDropItemCountProp()[(new Random()).nextInt(100)];
		
		//只对数量进行爆率加成(可以优化?)
		randomCount += randomCount*playerLegacy/100;
		for (int i = 0; i < randomCount; ++i) 
		{
			int randomLevel = m.getDropItemLevelProp()[(new Random()).nextInt(100)];
			ArrayList<Integer> allItems = ItemMG.instance.getAllLegacyItemByLevel(randomLevel);
			if (allItems != null && allItems.size() > 0) {
				int randomIndex = (new Random()).nextInt(allItems.size());
				Integer itemId = allItems.get(randomIndex);
				if (itemId.intValue() > 0) {
					it = ItemMG.instance.getItem(itemId);
					if (it != null && it.getLegacy()) {
						if (it.getType().intValue() == Item.ARM_TYPE) {
							int prop = it.getLevel().intValue() * 100 /p.getLevel().intValue();
							if (prop > 0 && prop < 100 && (new Random().nextInt(100)) > prop) {
								continue;
							}
						}
						all.add(it);
					}
				}
			}
		}
		return all;
	}
	/**
	 * 
	 * @param showProp
	 * @return
	 */
	public boolean isAttack(int attackProp) {
		if (attackProp > 100) attackProp = 100;
		int b = (new Random()).nextInt(100) + 1;
		return (b < attackProp);
	}
	/**
	 * 
	 * @param showProp
	 * @return
	 */
	public boolean isShow(int showProp) {
		if (showProp > 100) showProp = 100;
		int b = (new Random()).nextInt(100) + 1;
		return (b < showProp);
	}
	/**
	 * 获得一个设施的怪物列表
	 * @param cid
	 * @param fid
	 * @return
	 */
	public List<CityFacilityMonster> getMonsterIn(int cityFacilityId) {
		LinkedList<CityFacilityMonster> lm = cityFacilityMonster.get(cityFacilityId);
		if (lm != null) return lm;
		return new LinkedList<CityFacilityMonster>();
	}
	/**
	 * 为玩家生成当前设施的怪
	 * @param player
	 * @return
	 */
	public void getMonsterToPlayer(Player player) {
		List<CityFacilityMonster> ls = getMonsterIn(player.getCityFacilityId());
		List<Monster> all = new LinkedList<Monster>();
		//在普通城市
		for (CityFacilityMonster cm : ls) 
		{
			for (int i = 0; i < cm.getMaxCount(); ++i) 
			{
				if (isShow(cm.getShowupProb())) 
				{
					Monster m = getMonsterById(cm.getMonsterId());
					if (m != null) 
					{
						// 如果只是有任务才显示，那么检查是否已经接受该任务
						// 判断规则："monster" + monster.id已经存在并且为0，表示玩家首次碰到
						// 只能针对一个BOSS的情况，多个怪无法配置“有任务才出现”
						if(m.getShowForMission().intValue() != 0)
						{
							List<Integer> list = MissionMG.instance.getMissionIdByMonster(cm.getMonsterId());
							if(list == null) continue;
							else{
								// 用户是否有怪相关的未完成的任务
								boolean hasMission = false;
								for(Integer id : list){
									PlayerMission playerMission = player.getMission(String.valueOf(id));
									if(playerMission != null && !playerMission.isComplete()){
										hasMission = true;
										break;
									}
								}
								if(!hasMission) continue;
							}
							// 如果未设置
							NpcInteraction ni = player.getInteractions().get(Constants.PREFIX_MONSTER + cm.getMonsterId());
							if(ni == null) player.setInteraction(Constants.PREFIX_MONSTER + cm.getMonsterId(), 0);
							else if(ni.getValue() >= 1) continue;
						}
						Integer identify = -1 - all.size();
						Monster fm = new Monster();
						fm.init(cm, m, identify);
						player.getMonster().put(identify, fm);
						all.add(fm);
					}
				}
			}
		}
		return;
	}

	private void loadMonsterPropConf() {
		try {
			List<MonsterPropConf> confList = DBManager.queryMonsterPropConf();
			for (MonsterPropConf mpc : confList) {
				Integer type = mpc.getMtype();
				List<MonsterPropConf> conf = monsterPropConf.get(type);
				if (conf == null) {
					conf = new ArrayList<MonsterPropConf>();
					monsterPropConf.put(type, conf);
				}
				conf.add(mpc);
			}
		} catch (Exception e) {
			log.error("loadMonsterPropConf", e);
		}
	}
	
	public List<MonsterPropConf> getMonsterPropConfList(Integer type) {
		return monsterPropConf.get(type);
	}
	
	private void initAutoRestoreHpConfPool() {
	/**
	 * 	try {
			List<MonsterPropConf> propList = getMonsterPropConfList(MONSTER_PROP_AUTO_RESTORE_HP);
			if (propList != null) {
				int index = 0;
				for (MonsterPropConf conf : propList) {
					int key = conf.getMkey();
					int value = conf.getMvalue();
					for (int i = 0; i < key; i++) {
						autoRestoerHpConfPool[index] = value;
						index++;
					}
				}
			}
		} catch (Exception e) {
			log.error("initAutoRestoreHpConfPool", e);
		}
	 */
	}
	
	public int[] getAutoRestoreHpConfPool(Integer monsterType) {
		int[] autoRestoerHpConfPool = new int[100];
		try {
			List<MonsterPropConf> propList = getMonsterPropConfList(monsterType);
			if (propList != null) {
				int index = 0;
				for (MonsterPropConf conf : propList) {
					int key = conf.getMkey();
					int value = conf.getMvalue();
					for (int i = 0; i < key; i++) {
						autoRestoerHpConfPool[index] = value;
						index++;
					}
				}
			}
		} catch (Exception e) {
			log.error("initAutoRestoreHpConfPool", e);
		}
		return autoRestoerHpConfPool;
	}
}
