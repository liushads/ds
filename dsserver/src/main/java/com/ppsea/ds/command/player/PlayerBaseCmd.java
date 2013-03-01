package com.ppsea.ds.command.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Message;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionCondition;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;
import com.ppsea.ds.util.LoggerHelper;

/**
 * ClassName:PlayerCmd
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Dec 21, 2008		4:47:12 PM
 *
 * @see 	 
 */
public abstract class PlayerBaseCmd extends BaseCmd {
	
	// 消息缓存大小
	public static final int MESSAGES_SIZE = 50;
	
	// 缓存数组 - 0:公告消息
	public static Message[] messages = new Message[MESSAGES_SIZE];
	
	// 缓存数值 - 2:队伍消息 <tongId, messages>
	public static Map<Integer, List<Message>> tongMessages = new ConcurrentHashMap<Integer, List<Message>>();

	// 缓存数值 - 3:队伍消息 <teamId, messages>
	public static Map<Integer, List<Message>> teamMessages = new ConcurrentHashMap<Integer, List<Message>>();
	
	// 当前保存位置
	public static int msgIndex = 0;

	// 发公共消息收费
//	public static final int SEND_PUBLIC_FEE = Integer.parseInt(ResourceCache.instance.getValue("chat.public.fee", "100"));

	protected static final String DIALOG_FAILURE = "";
	
	public static final int STATE_COMPLETE = 0;
	public static final int STATE_NOT_COMPLETE = 1;
	public static final int STATE_NO_CHECK = 2;
	
	private static final Random RND = new Random();
	
	/**
	 * 检查是否满足前面的任务限制条件
	 * 
	 * @param player
	 * @param mission
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	protected boolean satisfyPreMission(Player player, Mission mission){
		if(mission.getPreMissions() == null || mission.getPreMissions().size() == 0) return true;
		else{
			for(Mission m : mission.getPreMissions()){
				PlayerMission pm = player.getMission(String.valueOf(m.getId()));
				
				if((pm != null &&  
					((mission.getPreMissionState() == STATE_COMPLETE && pm.isComplete()) ||
					(mission.getPreMissionState() == STATE_NOT_COMPLETE && !pm.isComplete()) ||
					(mission.getPreMissionState() == STATE_NO_CHECK))) ||
					player.inPastMission(m.getId()))
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 检查玩家已有的任务
	 * 
	 * @param player
	 * @param mission
	 * @param result
	 * @param npc
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	protected boolean checkUserMission(Player player, PlayerMission playerMission, CommandResult result, Npc npc){
		// 检查是否有前一个任务的限制条件
//		if(!satisfyPreMission(player, playerMission.getMission())) return false;
		
		// 可结束
		if(canEndMission(playerMission, player, npc, result)) return true;
		
		// 进行中
		if (playerMission.getMission().isOngoingMission(player, npc, playerMission)) {
			DialogAction nda = playerMission.getMission().ongoingMission(player, npc,
					playerMission, null);

			if (nda != null){
				addFastMissionAction(player, playerMission.getMission(), nda);
				result.setVO(TAG_DIALOG, nda);
			}else
				result.setVO(TAG_DIALOG, new DialogAction(DIALOG_FAILURE,
						null));

			return true;
		}
		
		return false;
	}
	
	/**
	 * 检查当前可获取的任务
	 * 
	 * @param player
	 * @param mission
	 * @param result
	 * @param npc
	 * @param condition
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	protected boolean checkNewMission(Player player, Mission mission, CommandResult result, Npc npc, MissionCondition condition){
		PlayerMission playerMission = player.getMission(String.valueOf(mission.getId()));
		
		if(player.getLevel() < mission.getConditionLevel() ||
				player.getFame() < mission.getConditionFame() ||
				(mission.getConditionLevelEnd() > 0 && player.getLevel() >= mission.getConditionLevelEnd()))
			return false;
		
		if(mission.getType() == MissionType.TYPE_DAILY && mission.getPreMissions().size() == 0){
			// 必须未接这个NPC每日任务
			Collection<PlayerMission> c = player.getOnGoingMissions().values();
			for(PlayerMission pm : c){
				if(npc.getMissions().containsValue(pm.getMission()) && 
						pm.getMission().getType() == MissionType.TYPE_DAILY) return false;
			}
			
			// 如果是每日任务并且是开始任务，则随机，防止每次都接同一系列
			mission = npc.getStartMissions().get(RND.nextInt(npc.getStartMissions().size()));
		}
		
		clearExistedDailyMission(player, mission.getId().toString());
		
		// 任务不能够重复并且玩家已经有该任务
		if (player.inPastMission(mission.getId()) 
				||!satisfyPreMission(player, mission)
				|| (	playerMission != null 
						&& playerMission.getMission().getId() == mission.getId() 
						&& (mission.getRepeatTimes() != -1 
								&& (mission.getRepeatTimes() == 0 
										|| playerMission.getTimesBeDone() > mission.getRepeatTimes()))))
			return false;
		
		if (mission.canStartMission(player, npc, condition)) {
			// 直接接受任务
			playerMission = takeMission(player, npc, mission);
			
			LoggerHelper.Mission.logForAcceptMission(player, playerMission);
			// 开始任务
			DialogAction da = mission.getMission(player, npc, null);
			if(!da.getDialog().equals("") || da.getActions().size() > 0
					|| da.getQuestion() != null){
				
				// 快速进行任务
				addFastMissionAction(player, mission, da);
				
				result.setVO(TAG_DIALOG, da);
			}
			
			// 是否已经完成
			if(canEndMission(playerMission, player, npc, result)) return true;
			
			result.setVO(TAG_MISSION, mission);

			return true;
		}else{
			if(condition.getDialog() != null && !condition.getDialog().equals("")){
				result.setVO(TAG_DIALOG, new DialogAction(condition.getDialog(), null));
			}
		}
		
		return false;
	}
	
	/**
	 * 任务是否可以结束
	 * 
	 * @param playerMission
	 * @param player
	 * @param npc
	 * @param result
	 * @return    
	 * @return boolean    
	 * @throws
	 */
	private boolean canEndMission(PlayerMission playerMission, Player player, Npc npc, CommandResult result){
		Mission mission = playerMission.getMission();
		if (mission.canEndMission(player, npc, playerMission)) {
			
			playerMission.setEnd((int) (new Date().getTime()	/ ONE_MINUTE));

			DialogAction nda = mission.endMission(player, npc,
					playerMission, null);

			if (nda != null) {
				playerMission.setComplete(true);
				playerMission.addTimes();
				
				result.setText(TAG_WIN_UPGRADE, player.getLevel());
				result.setVO(TAG_DIALOG, nda);

				// 计数
//				mission.decreaseTotalOngoing();

				// Commit
				if(mission.getType() != MissionType.TYPE_INSTANCE &&
						mission.getType() != MissionType.TYPE_DAILY &&
						mission.getRepeatTimes() != -1 && 	
					(mission.getRepeatTimes() == 0 ||
					mission.getRepeatTimes() < playerMission.getTimesBeDone())){

					player.addPastMission(mission.getId());
					
					if(player.getMissionPast().getPlayerId() == null)
						player.getMissionPast().setPlayerId(player.getId());
					
					DBService.commit(player.getMissionPast());
					DBService.commitDelete(playerMission);
					
				}else if(mission.getRepeatTimes() == -1){
					MissionService.removeMission(player, playerMission);
				}else if(mission.getType() == MissionType.TYPE_DAILY){
					Integer id = MissionMG.instance.getPostMissions().get(mission.getId());
					if(id == null){
						// 如果是最后一个任务，则删除当前任务
						MissionService.removeMission(player, playerMission);
						
						// 删除系列任务的记录，可以重接
						MissionService.removeSerialMissions(player, playerMission);
					}else
						DBService.commit(playerMission);
				}else	
					DBService.commit(playerMission);
			} else {
				result.setVO(TAG_DIALOG, new DialogAction(DIALOG_FAILURE,
						null));
			}
			LoggerHelper.Mission.logForCompleteMission(player, playerMission);
			return true;
		}
		
		return false;
	}
	
	/**
	 * 接受任务
	 * 
	 * @param player
	 * @param npc
	 * @param missionId    
	 * @return void    
	 * @throws
	 */
	protected PlayerMission takeMission(Player player, Npc npc, Mission mission){
		String missionId = String.valueOf(mission.getId());
		PlayerMission playerMission = player.getMission(missionId);
		
		if(playerMission == null){				
			playerMission = new PlayerMission();
			playerMission.setId(GlobalGenerator.instance.getIdForNewObj(playerMission));
			playerMission.setMission(mission);
			if(npc != null)playerMission.setNpc(npc);
			playerMission.setCity(player.getCityFacility().getCity());
			playerMission.setFacilityName(player.getCityFacility().getFacility().getName());
			if(playerMission.getMission().getPreMissions() != null &&
					playerMission.getMission().getPreMissions().size() > 0){
				for(Mission m : playerMission.getMission().getPreMissions()){
					PlayerMission pm = player.getMission(String.valueOf(m.getId()));
					if(pm != null){
						// 前一个关联任务，结束时同时结束该任务
						playerMission.setPrePlayerMission(pm);
						playerMission.setPrePlayerMissionId(m.getId());
						
						break;
					}
				}
			}

			player.addMission(missionId, playerMission);
		}else
			player.getOnGoingMissions().put(missionId, playerMission);
		
		playerMission.setComplete(false);
		playerMission.setEnd(0);
		playerMission.setStart((int)(new Date().getTime()/ONE_MINUTE));
		playerMission.setPlayerId(player.getId());
		
		DBService.commit(playerMission);		
		
		return playerMission;
	}	
	
	private void clearExistedDailyMission(Player player, String missionId) {
				
		try {
			PlayerMission pm = player.getMission(missionId);
			Mission mission = MissionMG.instance.getMission(Integer.parseInt(missionId));
			if (mission.getPreMissions().size() == 0 && pm != null) {
				if (pm.getMission().getType() == MissionType.TYPE_DAILY || pm.getMission().getType() == 2 || pm.getMission().getType() == 5) {
					Map<Integer, Integer> postMap = MissionMG.instance.getPostMissions();
					Integer postMissionId = postMap.get(Integer.parseInt(missionId));
					List<Integer> postIds = new ArrayList<Integer>();
					while (postMissionId != null) {
						postIds.add(postMissionId);
						postMissionId = postMap.get(postMissionId);	
					}
					
					for (Integer mid : postIds) {
						PlayerMission pmt = player.getMission(mid.toString());
						if (pmt != null) {
							try {
								pmt.getMission().cancelMission(player, pmt);
								MissionService.removeMission(player, pmt);
								MissionService.removeSerialMissions(player, pmt);
							} catch (Exception e){}
						}
					}
				}
				
				//循环师门帮会
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 快速任务
	 * 
	 * @param player
	 * @param mission
	 * @param da    
	 * @return void    
	 * @throws
	 */
	private void addFastMissionAction(Player player, Mission mission, DialogAction da){
		if(mission.getCityfacilityId() == null || mission.getCityfacilityId() == 0) return;
		
		CityFacility cf = MapMG.instance.getCityFacility(mission.getCityfacilityId());
		
		if(cf == null) return;
		
		if (!player.isInInstacneCity() && player.getCityFacility().getCity() == cf.getCity() && player.getCityFacility() != cf) {
			Action action = new Action();
			action.setCommand(COMMAND_MISSION_FAST);
			
			// 数量
			PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.instance.getItem(Constants.ITEM_MISSION_GUIDE));
			action.setName("使用遁地" + "(" + (playerItem==null?0:playerItem.getAmount()) + ")");
			action.setParam(String.valueOf(mission.getCityfacilityId()));
			da.getActions().add(action);
		}
		
		if(player.isInInstacneCity()){
			Action action = new Action();
			action.setId(0);
			action.setName("离开");
			action.setCommand(COMMAND_LEAVE);

			if(player.getInstanceEntrance() > 0){
				action.setParam(String.valueOf(player.getInstanceEntrance()));
			}else{
				Integer areaId = player.getCityFacility().getCity().getAreaId();
				City mainCity = MapMG.instance.getArea(areaId).getMainCity();
				Map<Integer, CityFacility> special = mainCity.getSpecialFacilityMap();
				Integer cityFacilityId = special.get(Constants.CENTER_ID).getId();

				action.setParam(String.valueOf(cityFacilityId));
			}

			da.getActions().add(action);
		}
	}
}
