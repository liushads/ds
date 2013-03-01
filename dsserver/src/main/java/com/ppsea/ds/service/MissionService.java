package com.ppsea.ds.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.manager.MissionMG;

/**
 * ClassName:MissionService
 *
 * @author   Daniel Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Feb 16, 2009		3:24:14 PM
 *
 * @see 	 
 */
public class MissionService {
	
	private static final DateFormat DF = new SimpleDateFormat("yyyyMMdd");
	
	/**
	 * 打怪进行中的状态描述
	 * 
	 * @param monsterId
	 * @param player
	 * @return    
	 * @return String    
	 * @throws
	 */
	public static String getMissionStatusByMonster(Integer monsterId, Player player){
		List<Integer> list = MissionMG.instance.getMissionIdByMonster(monsterId);
		
		if(list == null)return "";
		
		PlayerMission playerMission = null;
		
		for(Integer i : list){
			playerMission = player.getMissionOnGoing(String.valueOf(i));
			if(playerMission != null) break;			
		}
		
		if(playerMission == null) return "";
		
		if(player.getInteractions().get(Constants.PREFIX_MONSTER + monsterId) == null)
			player.increInteraction(Constants.PREFIX_MONSTER + monsterId);
		
		String s = getMissionStatusByMission(playerMission.getMission(), player);
		
		if(!s.equals(""))return "【" + playerMission.getMission().getName() + "】" + s;
		else return s;
	}
	
	/**
	 * 捡起物品时的任务状态描述
	 * 
	 * @param itemId
	 * @param player
	 * @return    
	 * @return String    
	 * @throws
	 */
	public static String getMissionStatusByItem(Integer itemId, Player player){
		List<Integer> list = MissionMG.instance.getMissionIdByItem(itemId);
		
		if(list == null) return "";
		
		PlayerMission playerMission = null;
		
		for(Integer i : list){
			playerMission = player.getMissionOnGoing(String.valueOf(i));
			if(playerMission != null) break;			
		}
		
		if(playerMission == null) return "";
		
		String s = getMissionStatusByMission(playerMission.getMission(), player);
		
		if(!s.equals(""))return "【" + playerMission.getMission().getName() + "】" + s;
		else return s;
	}

	/**
	 * 查看任务时的进行状态
	 * 
	 * @param mission
	 * @param player
	 * @return    
	 * @return String    
	 * @throws
	 */
	public static String getMissionStatusByMission(Mission mission, Player player){
		DialogAction da = mission.ongoingMission(player, null, null, null);
		
		return da == null?"":da.getDialog();
	}
	
	/**
	 * 是否有押镖任务
	 * 
	 * @param player
	 * @return
	 */
	public static boolean hasRewardMission(Player player){
		Collection<PlayerMission> c = player.getOnGoingMissions().values();
		for(PlayerMission pm : c){
			if(!pm.isComplete() && pm.getMission().getType() == MissionType.TYPE_REWARD)
				return true;
		}
		
		return false;
	}
	
	/**
	 * 放弃任务
	 * 
	 * @param player
	 * @param pm
	 * @param key
	 */
	public static void removeMission(Player player, PlayerMission pm){
		String key = String.valueOf(pm.getMission().getId());
		player.getMissions().remove(key);
		player.getOnGoingMissions().remove(key);
		
		DBService.commitDelete(pm);
	}
	
	/**
	 * 根据类型删除任务，主要用于押镖任务.
	 * @param player
	 * @param missionType
	 */
	public static void deleteMissionByType(Player player, int missionType) {
		
		Map<String, PlayerMission> onGoingMissions = player.getOnGoingMissions();
		for (Map.Entry<String, PlayerMission> entry : onGoingMissions.entrySet()) {
			try {
				PlayerMission pm = entry.getValue();
				if ((missionType == pm.getMission().getType())) {
					pm.getMission().cancelMission(player, pm);
					removeMission(player, pm);
					//onGoingMissions = player.getOnGoingMissions();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 删除系列的已完成的每日任务
	 * 
	 */
	public static void removeSerialMissions(Player player, PlayerMission pm){
		PlayerMission playerMission = null;
		while((playerMission = pm.getPrePlayerMission()) != null){
			player.getMissions().remove(String.valueOf(playerMission.getMission().getId()));
			
			DBService.commitDelete(playerMission);
			
			pm = playerMission;
		}
	}
	
	/**
	 * 一天内已经做过的次数是否超过timeRange
	 * 
	 * @param player
	 * @param missionId
	 * @param timeRange
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	public static boolean hasDoneMission(Player player, Integer missionId, int timeRange) {
		PlayerMission pm = player.getMission(missionId.toString());

		if (pm == null)
			return false;

		if (pm.getTimesBeDone() < timeRange)
			return false;
		else {
			if(!DF.format(new Date(pm.getStart() * Command.ONE_MINUTE)).equals(DF.format(new Date()))){
				pm.setTimesBeDone(0);
				return false;
			}
			
			pm.setComplete(Boolean.TRUE);

			DBService.commit(pm);

			return true;
		}
	}
	
	public static int lessThan10Minutes(Player player, Integer missionId){
		PlayerMission pm = player.getMission(missionId.toString());

		if (pm == null)
			return 0;
		
		return 10 - ((int) (new Date().getTime() / Command.ONE_MINUTE) - pm.getStart());
	}
}
