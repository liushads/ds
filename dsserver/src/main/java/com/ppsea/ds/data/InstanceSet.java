package com.ppsea.ds.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.task.InstanceTask;

/**
 * ClassName:InstanceSet
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 *
 * @see 	 
 */
public class InstanceSet {
	private static final Logger log = Logger.getLogger(InstanceTask.class);
	// MissionId, Teams<OwnerID, members>
	private static Map<Integer, Map<Integer, List<Player>>> applyTeam = new ConcurrentHashMap<Integer, Map<Integer,List<Player>>>();
	private static long lastSwitchTime ;
	private static long SWICH_INTERVAL = 1000*60*5;

	/**
	 * 增加到申请队伍
	 * 
	 * @param mission
	 * @param player
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	public static boolean addPlayer_old(Mission mission, Integer ownerId, Player player){
		Map<Integer, List<Player>> map = applyTeam.get(mission.getId());
		
		if(map == null)	map = new HashMap<Integer, List<Player>>();
		
		List<Player> list = map.get(ownerId);
		if(list == null) list = new ArrayList<Player>();
		
		// 人数超过限制
		if(list.size() >= mission.getConditionTeamSize()) return false;
		
		// 已经在申请队伍中
		if(list.contains(player)) return false;
		
		// 加入申请队伍
		list.add(player);
		map.put(ownerId, list);
		
		applyTeam.put(mission.getId(), map);
		
		return true;
	}
	
	public static boolean addPlayer(Mission mission, Integer ownerId, Player player){
		Map<Integer, List<Player>> map = applyTeam.get(mission.getId());
		
		if(map == null) {
			map = new ConcurrentHashMap<Integer, List<Player>>();	
			applyTeam.put(mission.getId(), map);
		}	
		
		List<Player> list = map.get(ownerId);
		if(list == null) {
			list = new ArrayList<Player>();	
			map.put(ownerId, list);
		} 
		
		// 人数超过限制
		if(list.size() >= mission.getConditionTeamSize()) {
			return false;			
		} 
		
		// 已经在申请队伍中
		if(list.contains(player)) {
			return false;			
		}
		
		// 加入申请队伍
		list.add(player);
		return true;
	}
	
	/**
	 * 申请的队伍
	 * 
	 * @param missionId
	 * @return
	 */
	public static Map<Integer, List<Player>> getApplyTeamByMission(Integer missionId){
		
		if(System.currentTimeMillis() - lastSwitchTime > SWICH_INTERVAL){
			swichMap();
			lastSwitchTime = System.currentTimeMillis();
		}
		
		return applyTeam.get(missionId);
	}
	
	/**
	 * 移除申请队伍
	 * 
	 * @param missionId
	 * @param ownerId
	 */
	public static void removeTeam(Integer missionId, Integer ownerId){
		try{
			Map<Integer, List<Player>> map = getApplyTeamByMission(missionId);
			if(map != null)map.remove(ownerId);
			applyTeam.put(missionId, map);
		}
		catch (Exception e) {
			log.error("exception", e);
		}
	}
	
	private static void swichMap(){
		Map<Integer, Map<Integer, List<Player>>> tmp = new ConcurrentHashMap<Integer, Map<Integer,List<Player>>>();
		for(Integer key:applyTeam.keySet()){
			Map<Integer, List<Player>> teams = new ConcurrentHashMap<Integer, List<Player>>();
			Map<Integer, List<Player>> teamsOld = applyTeam.get(key);
			
			if(teamsOld != null){
				for(Integer key1:teamsOld.keySet()){
					List<Player> players = new ArrayList<Player>();
					List<Player> playersOld = teamsOld.get(key1);
					
					if(playersOld != null){
						for(Player player : playersOld){
							players.add(player);
						}
						
						teams.put(key1, players);
					}
				}
				
				tmp.put(key, teams);
			}
		}
		if (tmp.size() > 0) {
			applyTeam = tmp;			
		}
	}
	
	public static boolean checkExistedInstanceMission(Player player) {
		if (applyTeam != null && applyTeam.size() > 0) {
			for (Map.Entry<Integer, Map<Integer, List<Player>>> map : applyTeam.entrySet()) {
				Map<Integer, List<Player>> team = map.getValue();
				for (Map.Entry<Integer, List<Player>> entry  : team.entrySet()) {
					List<Player> list = entry.getValue();
					for (Player p : list) {
						if (player.getId().equals(p.getId())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void removeFromApplyTeam(Player player) {
		try {
			if (applyTeam != null && applyTeam.size() > 0) {
				for (Map.Entry<Integer, Map<Integer, List<Player>>> map : applyTeam.entrySet()) {
					Map<Integer, List<Player>> team = map.getValue();
					for (Map.Entry<Integer, List<Player>> entry  : team.entrySet()) {
						try {
							List<Player> list = entry.getValue();
							Integer key = entry.getKey();
							for (Player p : list) {
								if (player.getId().equals(p.getId())) {
									try {
										list.remove(p);
										break;
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
							if (list != null && list.size() == 0) {
								team.remove(key);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
