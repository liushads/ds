package com.ppsea.ds.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.dao.ItemMissionDAO;
import com.ppsea.ds.data.dao.ItemMissionDAOImpl;
import com.ppsea.ds.data.dao.MissionPreDAO;
import com.ppsea.ds.data.dao.MissionPreDAOImpl;
import com.ppsea.ds.data.dao.MonsterMissionDAO;
import com.ppsea.ds.data.dao.MonsterMissionDAOImpl;
import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.ItemMission;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionCondition;
import com.ppsea.ds.data.model.MissionPre;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.MonsterMission;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerEnemy;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.Question;
import com.ppsea.ds.data.model.RawMission;
import com.ppsea.ds.exception.PpseaException;

/**
 * 任务加载器
 * 
 * */

public class MissionMG {
	private static final Logger log = Logger.getLogger(MissionMG.class);
	public static MissionMG instance = new MissionMG();
	
	private Map<Integer, Action> actions = new HashMap<Integer, Action>();
	private Map<Integer, Question> questions = new HashMap<Integer, Question>();
	private Map<Integer, Mission> missions = new HashMap<Integer, Mission>();
	private Map<Integer, MissionType> missionTypes = new HashMap<Integer, MissionType>();
	private Map<Integer, List<MissionPre>> preMissions = new HashMap<Integer, List<MissionPre>>();
	private Map<Integer, Integer> postMissions = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> preMissionIds = new HashMap<Integer, Integer>();
	private Map<Integer, List<Integer>> monsterMission = new HashMap<Integer, List<Integer>>();
	private Map<Integer, List<Integer>> itemMission = new HashMap<Integer, List<Integer>>();
	
	//added by Alex.
	/** key=player id.inner key=mission condition id, 玩家发布的通缉任务，同一时间可能会发布多条通缉令.*/
	private Map<Integer, Map<Integer,MissionCondition>> missionConditionMap = new ConcurrentHashMap<Integer, Map<Integer,MissionCondition>>();
	
	/** key=player id, inner key=player mission id当前玩家的任务关系，一个任务一条记录，同一时间只能有一个击杀任务. */
	private Map<Integer, Map<Integer,PlayerMission>> playerMissionMap = new ConcurrentHashMap<Integer, Map<Integer,PlayerMission>>();
	
	private Map<Integer, RawMission> rawMissionMap = new ConcurrentHashMap<Integer, RawMission>();
	
	// 同时进行的任务数量
	public static final int CONCURRENT_MISSIONS = 200;
	
	public void init() throws PpseaException{
		loadMissions();
		loadActions();
		loadQuestions();
		loadMissionTypes();
		loadMonsterMission();
		loadItemMission();
//		loadMissionCondition();
		loadRawMission();
		//loadPlayerMission();
	}

	
	public void loadMissions() {
		missions = DBManager.queryAllMissions();
		
		try {
			log.info("init: 任务 -- 前一任务");
			MissionPreDAO dao = (MissionPreDAO) DBManager.getDataDao(MissionPreDAOImpl.class);
			Collection<MissionPre> c = dao.selectByExample(null);

			for (MissionPre nq : c) {
				List<MissionPre> list = preMissions.get(nq.getFromMissionId());
				if (list == null)
					list = new ArrayList<MissionPre>();
				list.add(nq);

				preMissions.put(nq.getFromMissionId(), list);
				postMissions.put(nq.getToMissionId(), nq.getFromMissionId());
				preMissionIds.put(nq.getFromMissionId(), nq.getToMissionId());
			}
		} catch (Exception e) {
			log.error("exception", e);
		}
		
	}

	public void loadRawMission() {
		try {

			List<RawMission> list = DBManager.queryRawMission();
			for (RawMission rw : list) {
				rawMissionMap.put(rw.getMissionId(), rw);
			}
		
		} catch (Exception e) {
			log.error("exception", e);
		}
	}
	
	public RawMission getRawMission(int missionId) {
		return rawMissionMap.get(missionId);
	}
	
	public Mission getMission(Integer id) {
		return missions.get(id);
	}

	public Map<Integer, Mission> getMissions() {
		return this.missions;
	}
	
	public void loadMissionTypes(){
		missionTypes = DBManager.queryAllMissionType();
	}
	
	public Map<Integer, MissionType> getMissionTypes(){
		return this.missionTypes;
	}

	public void loadActions(){
		List<Action> actionList = DBManager.queryAllActions();
		for(Action action : actionList){
			actions.put(action.getId(), action);
		}		
	}
	
	public Action getAction(int actionId){
		return actions.get(actionId);
	}

	public void loadQuestions() {
		questions = DBManager.queryAllQuestions();
	}

	public Question getQuestion(Integer id) {
		return questions.get(id);
	}

	public Map<Integer, List<MissionPre>> getPreMissions() {
		return preMissions;
	}

	public Map<Integer, Integer> getPostMissions() {
		return postMissions;
	}

	public Map<Integer, Integer> getPreMissionIds() {
		return preMissionIds;
	}
	
	private void loadMonsterMission() {
		try {
			MonsterMissionDAO dao = (MonsterMissionDAO) DBManager.getDataDao(MonsterMissionDAOImpl.class);
			List<MonsterMission> list = dao.selectByExample(null);
			for (MonsterMission mm : list) {
				List<Integer> ll = monsterMission.get(mm.getMonsterId());
				if (ll == null)
					ll = new ArrayList<Integer>();

				ll.add(mm.getMissionId());
				monsterMission.put(mm.getMonsterId(), ll);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}

	public List<Integer> getMissionIdByMonster(Integer monsterId) {
		return monsterMission.get(monsterId);
	}
	
	private void loadItemMission() {
		try {
			ItemMissionDAO dao = (ItemMissionDAO) DBManager.getDataDao(ItemMissionDAOImpl.class);
			List<ItemMission> list = dao.selectByExample(null);
			for (ItemMission mm : list) {
				List<Integer> ll = itemMission.get(mm.getItemId());
				if (ll == null)
					ll = new ArrayList<Integer>();

				ll.add(mm.getMissionId());
				itemMission.put(mm.getItemId(), ll);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}

	public List<Integer> getMissionIdByItem(Integer itemId) {
		return itemMission.get(itemId);
	}

	
	
	/**
	 * @return the missionConditionMap
	 */
	public Map<Integer, Map<Integer,MissionCondition>> getMissionConditionMap() {
		return missionConditionMap;
	}


	/**
	 * @return the cityMissionMap
	 */
	public Map<Integer, Map<Integer,PlayerMission>> getPlayerMissionMap() {
		return playerMissionMap;
	}
	
	public void loadMissionCondition() {
		List<MissionCondition> missionConditionList = DBManager.queryMissionCondition();
		for (MissionCondition mc : missionConditionList) {
			Map<Integer,MissionCondition> missionMap = missionConditionMap.get(mc.getIssuerId());
			if (missionMap == null) {
				missionMap = new HashMap<Integer,MissionCondition>();
				missionConditionMap.put(mc.getIssuerId(), missionMap);
			}
			missionMap.put(mc.getId(), mc);
		}
	}
	
	public void loadPlayerMission(Player player) {
		 List<PlayerMission> playerMissionList = DBManager.queryPlayerMission(player);
		 for (PlayerMission pm : playerMissionList) {
			 Map<Integer,PlayerMission> playerMission = playerMissionMap.get(pm.getPlayerId());
			 if (playerMission == null) {
				 playerMission = new HashMap<Integer,PlayerMission>();
				 playerMissionMap.put(pm.getPlayerId(), playerMission);
			 }
			 playerMission.put(pm.getId(), pm);
			 Integer mcid = pm.getConditionId();
			 if (mcid != null) {
				 //load condition.
				 MissionCondition mc = getMissionConditionById(mcid);
				 pm.setCondition(mc);
			 }
//			 pm.setMission(missions.get(Constants.MISSION_HUNT_ID));
		 }
	}

	
	/**
	 * 根据mission condition id 返回对应的 condition对象. 
	 * @param missionConditionId
	 * @return
	 */
	public MissionCondition getMissionConditionById(Integer missionConditionId) {

		Map<Integer, MissionCondition> allMisc = getAllMissionConditions();
		for (Map.Entry<Integer, MissionCondition> entry : allMisc.entrySet()) {
			Integer tempMisId = entry.getKey();
			if (missionConditionId.equals(tempMisId)) {
				return entry.getValue();
			}
		}

		return null;
	}
	
	/**
	 *   返回当前所有的已发布的击杀任务. 
	 * @return
	 */
	public Map<Integer, MissionCondition> getAllMissionConditions() {
		Collection<Map<Integer, MissionCondition>> values = this.missionConditionMap.values();
		Iterator<Map<Integer, MissionCondition>> ites = values.iterator();
		Map<Integer, MissionCondition> allMisc = new HashMap<Integer, MissionCondition>();
		while (ites.hasNext()) {
			Map<Integer, MissionCondition> missionConditionMap =  ites.next();
			allMisc.putAll(missionConditionMap);
		}
		return allMisc;
	}
	
	/**
	 *  返回某个玩家发布的击杀任务.
	 * @param playerId
	 * @return
	 */
	public Map<Integer,MissionCondition> getPlayerMissionConditions(Integer playerId) {
		Map<Integer,MissionCondition> missionConditions = this.missionConditionMap.get(playerId);
		if (missionConditions == null) {
			missionConditions = new HashMap<Integer, MissionCondition>();
			missionConditionMap.put(playerId, missionConditions);
		}
		return missionConditions;
	}
	
	public List<MissionCondition> getPlayerMissionConditionList(Integer playerId) {
		 Map<Integer,MissionCondition> allMis = getPlayerMissionConditions(playerId);
		 List<MissionCondition> list = new ArrayList<MissionCondition>();
		 for (Map.Entry<Integer, MissionCondition> entry : allMis.entrySet()) {
			 list.add(entry.getValue());
		 }
		 return list;
	}
	
	/**
	 * 返回玩家的所有击杀任务.
	 * @param playerId
	 * @return
	 */
	public Map<Integer,PlayerMission> getPlayerEnemyMission(Integer playerId) {
		Map<Integer,PlayerMission> allPlms = this.getPlayerMissions(playerId);
		Map<Integer,PlayerMission> temp = new HashMap<Integer,PlayerMission>();
		for (Map.Entry<Integer, PlayerMission> entry : allPlms.entrySet()) {
			PlayerMission pm = entry.getValue();
			if ((pm.getConditionId() != null) && (pm.getCondition() != null)) {
				temp.put(pm.getId(), pm);
			}
		}
		return temp;
	}
	
	public  Map<Integer,PlayerMission> getPlayerEnemyMissionNotComplemeted(Integer playerId) {
		Map<Integer,PlayerMission> allPlms = getPlayerEnemyMission(playerId);
		Map<Integer,PlayerMission> temp = new HashMap<Integer,PlayerMission>();
		for (Map.Entry<Integer, PlayerMission> entry : allPlms.entrySet()) {
			PlayerMission pm = entry.getValue();
			if ((pm.getConditionId() != null) && (pm.getCondition() != null) && (!pm.isComplete())) {
				temp.put(pm.getId(), pm);
			}
		}
		return temp;
	}
	
	/**
	 * 返回所有玩家的任务列表.
	 * @return
	 */
	public Map<Integer, PlayerMission> getAllPlayerMissions() {
		Collection<Map<Integer, PlayerMission>> values = this.playerMissionMap.values();
		Iterator<Map<Integer, PlayerMission>> ites = values.iterator();
		Map<Integer, PlayerMission> allPms = new HashMap<Integer, PlayerMission>();
		while (ites.hasNext()) {
			Map<Integer, PlayerMission> map = ites.next();
			allPms.putAll(map);
		}
		return allPms;
	}
	
	/**
	 * 返回某个击杀任务的所有承接者列表.
	 * @param conditionId
	 * @return
	 */
	public List<PlayerMission> getTakedMissionConditions(Integer conditionId) {
		Map<Integer, PlayerMission> allPms = getAllPlayerMissions();
		List<PlayerMission> pmList = new ArrayList<PlayerMission>();
		for (Map.Entry<Integer, PlayerMission> entry : allPms.entrySet()) {
			PlayerMission pm = entry.getValue();
			if (pm.getConditionId() != null && (pm.getConditionId().equals(conditionId))) {
				pmList.add(pm);
			}
		}
		return pmList;
	}
	
	/**
	 * 返回玩家的任务列表.
	 * @param playerId
	 * @return
	 */
	public Map<Integer,PlayerMission> getPlayerMissions(Integer playerId) {
		 Map<Integer,PlayerMission> playerMiss = this.playerMissionMap.get(playerId);
		 if (playerMiss == null) {
			 playerMiss = new HashMap<Integer, PlayerMission>();
			 playerMissionMap.put(playerId, playerMiss);
		 }
		 return playerMiss;
	}
	/**
	 * 返回玩家的任务列表.
	 * @param playerId
	 * @return
	 */
	public List<PlayerMission> getPlayerMissionList(Integer playerId) {
		Map<Integer,PlayerMission> pmm = getPlayerMissions(playerId);
		List<PlayerMission> pmList = new ArrayList<PlayerMission>();
		 for (Map.Entry<Integer, PlayerMission> entry : pmm.entrySet()) {
			 PlayerMission pm = entry.getValue();
			 pmList.add(pm);
		}
		 return pmList;
	}
	
	/**
	 * 返回某个通缉任务的所有承接者列表.
	 * @param conditionId
	 * @return
	 */
	public List<PlayerMission> getPlayerMissionListByMisId(Integer conditionId) {
		Map<Integer, PlayerMission> allTmps = this.getAllPlayerMissions();
		List<PlayerMission> pms = new ArrayList<PlayerMission>();
		for (Map.Entry<Integer, PlayerMission> entry : allTmps.entrySet()) {
			PlayerMission pm = entry.getValue();
			if (pm.getConditionId() != null && (pm.getConditionId().equals(conditionId))) {
				pms.add(pm);
			}
		}
		return pms;
	}
	
	public PlayerEnemy createPlayerEnemy(Integer playerId, Integer enemyId, String enemyName) {
		PlayerEnemy playerEnemy = new PlayerEnemy();
		playerEnemy.setId(GlobalGenerator.instance.getIdForNewObj(playerEnemy));
		playerEnemy.setPlayerId(playerId);
		playerEnemy.setEnemyId(enemyId);
		playerEnemy.setEnemyName(enemyName);
		return playerEnemy;
	}
	
	public PlayerMission createPlayerMission(Player player) {
		PlayerMission playerMission = new PlayerMission();
		playerMission.setId(GlobalGenerator.instance.getIdForNewObj(playerMission));
		playerMission.setCity(player.getCityFacility().getCity());
		playerMission.setCityId(player.getCityFacility().getCity().getId());
		playerMission.setComplete(Boolean.FALSE);
		playerMission.setStart((int)(new Date().getTime()/Command.ONE_MINUTE));
		playerMission.setPlayerId(player.getId());
		playerMission.setEnd(0);
		return playerMission;
	}
	
	public MissionCondition createMissionCondition(Player player, Player targetPlayer) {
		MissionCondition condition = new MissionCondition();
		condition.setId(GlobalGenerator.instance.getIdForNewObj(condition));
		condition.setTargetId(targetPlayer.getId());
		condition.setIssuerId(player.getId());
		condition.setStart((int)(new Date().getTime()/Command.ONE_MINUTE));
		condition.setIssuerName(player.getName());
		condition.setTargetName(targetPlayer.getName());
		condition.setCityId(player.getCityId());
		return condition;
	}
}