package com.ppsea.ds.command.mission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.RawMission;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;

/**
 * ClassName:MissionListCmd 玩家任务列表和详情
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Dec 14, 2008		1:57:02 PM
 *
 * @see 	 
 */
public class PlayerMissionCmd extends BaseCmd {
	
	
	private static final String MISSION_TYPE_MAIN = "main";
	
	private static final String MISSION_TYPE_PLAYER = "playerType";
	
	//private static final String MISSION_TYPE_REPEAT = "repeat";
	
	private static final String MISSION_TYPE_FB = "fb";
	
	private static final String MISSION_TYPE_YABIAO = "yabiao";
	
	private static final String MISSION_TYPE_DAILY = "daily";
	
	/**
	 * ps[0] = target
	 * ps[1] = type/missionId
	 * (non-Javadoc)
	 */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult();
		
		String target = ps[0];
		
		if(target.equals("type")){
			Map<String, String> counter = new LinkedHashMap<String, String>();
			counter.put(MISSION_TYPE_MAIN, 0+"");
			counter.put(MISSION_TYPE_PLAYER, 0+"");
			//counter.put(MISSION_TYPE_REPEAT, 0+"");
			counter.put(MISSION_TYPE_FB, 0+"");
			counter.put(MISSION_TYPE_YABIAO, 0+"");
			counter.put(MISSION_TYPE_DAILY, 0+"");

			for(PlayerMission pm : player.getOnGoingMissions().values()){
				if(pm.isComplete()) {
					continue;					
				}
				
				MissionType type = MissionMG.instance.getMissionTypes().get(pm.getMission().getType());
				if ("1".equals(type.getId().toString())) {
					//主线任务.
					
					int count = Integer.parseInt(counter.get(MISSION_TYPE_MAIN));
					counter.put(MISSION_TYPE_MAIN, String.valueOf(++count));
					
				} else if ("3".equals(type.getId().toString())) {
					//斗神任务
					int count = Integer.parseInt(counter.get(MISSION_TYPE_PLAYER));
					counter.put(MISSION_TYPE_PLAYER, String.valueOf(++count));
				} else if ("4".equals(type.getId().toString())) {
					//副本任务
					int count = Integer.parseInt(counter.get(MISSION_TYPE_FB));
					counter.put(MISSION_TYPE_FB, String.valueOf(++count));
				} else if ("8".equals(type.getId().toString())) {
					//每日任务
					
					int count = Integer.parseInt(counter.get(MISSION_TYPE_DAILY));
					counter.put(MISSION_TYPE_DAILY, String.valueOf(++count));
					
				}  else if ("6".equals(type.getId().toString())) {
					//押镖任务
				
					int count = Integer.parseInt(counter.get(MISSION_TYPE_YABIAO));
					counter.put(MISSION_TYPE_YABIAO, String.valueOf(++count));
					
				} else {
					continue;
				}
				
//				else if ("5".equals(type.getId().toString()) || "2".equals(type.getId().toString())) {
//					//循环任务
//					int count = Integer.parseInt(counter.get(MISSION_TYPE_REPEAT));
//					counter.put(MISSION_TYPE_REPEAT, String.valueOf(++count));
//				}
			}
			
			int count = Integer.parseInt(counter.get(MISSION_TYPE_PLAYER));
			counter.put(MISSION_TYPE_PLAYER, String.valueOf(count+MissionMG.instance.getPlayerEnemyMission(player.getId()).size()));
			
			for (Map.Entry<String, String> entry : counter.entrySet()) {
				result.setVO(entry.getKey(), entry.getValue());
			}
			result.setStatus(STATUS_TYPE);
		}else if(target.equals("list")){
			String type = ps[1];
			
			List<Integer> missionType = new ArrayList<Integer>();
			Map<String, PlayerMission> missions = new HashMap<String, PlayerMission>();
			if (MISSION_TYPE_MAIN.equals(type) || "1".equals(type)) {
				int ms=1;
				result.setVO(TAG_TIQIU_DIANQIU, ms);
				missionType.add(Integer.valueOf(1));
				result.setVO("missionType", type);
			} else if (MISSION_TYPE_PLAYER.equals(type) || "3".equals(type)) {
				
//				List<PlayerMission> mlist = HunterService.instance.listHunterMissions(player);
//				for (PlayerMission pm : mlist) {
//					missions.put(pm.getId().toString(), pm);
//				}
//				result.setVO("jisha", mlist.size());
//				missionType.add(Integer.valueOf(3));
//				result.setVO("missionType", MISSION_TYPE_PLAYER);
			} else if (MISSION_TYPE_FB.equals(type) || "4".equals(type)) {
				
				missionType.add(Integer.valueOf(4));
				result.setVO("missionType", type);
			} else if (MISSION_TYPE_DAILY.equals(type) || "8".equals(type)) {
				int ms=2;
				result.setVO(TAG_TIQIU_DIANQIU, ms);
				missionType.add(Integer.valueOf(8));
				result.setVO("missionType", type);
			} else if (MISSION_TYPE_YABIAO.equals(type) || "6".equals(type)) {
				int ms=3;
				result.setVO(TAG_TIQIU_DIANQIU, ms);
				missionType.add(Integer.valueOf(6));
				result.setVO("missionType", type);
			} 
			
//			else if (MISSION_TYPE_REPEAT.equals(type) || "2".equals(type) || "5".equals(type)|| "6".equals(type) || "8".equals(type)) {
//				
//				if (!MISSION_TYPE_REPEAT.equals(type)) {
//					missionType.add(Integer.valueOf(type));
//				} else {
//					result.setVO("yabiao", listMissions(player, Integer.valueOf(6)).size());
//					result.setVO("daily", listMissions(player, Integer.valueOf(8)).size());
//					result.setVO("tong", listMissions(player, Integer.valueOf(5)).size());
//					result.setVO("sect", listMissions(player, Integer.valueOf(2)).size());
//				}
//				result.setVO("missionType", type);
////				result.setVO("missionType", type);
////				result.setStatus(STATUS_LIST);
////				return result;
//			} 
			for (Integer stype : missionType) {
				missions.putAll(listMissions(player, stype));
			}
			result.setVO(TAG_MISSIONS, missions);
			
			result.setStatus(STATUS_LIST);
		}else if(target.equals("cancel")){
			String missionId = ps[1];
			
			PlayerMission pm = player.getMission(missionId);
			if(pm == null || 
					(pm.getMission().getType() != MissionType.TYPE_REWARD &&
							pm.getMission().getType() != MissionType.TYPE_DAILY)){
				result.setStatus(STATUS_FAIL);
				return result;
			}
			
			// 放弃
			pm.getMission().cancelMission(player, pm);
			MissionService.removeMission(player, pm);
			if(pm.getMission().getType() == MissionType.TYPE_DAILY)
				MissionService.removeSerialMissions(player, pm);
			/**
			if(pm.getMission().getType() == MissionType.TYPE_DAILY) {
				player.dropItem(RewardService.OCT_ITEM_A,1);
				player.dropItem(RewardService.OCT_ITEM_B,1);
			}
			*/
			Item item = null;
			if (pm.getMission().getName().contains("师门")) {
				//int itemId = 10374;师门令
				item = ItemMG.instance.getItem(10374);
			} else if (pm.getMission().getName().contains("每日")) {
				//10358斗神令
				//item = ItemMG.instance.getItem(10358);
			}
			if (item != null) {
				ItemService.releasePlayerItem(player, item, true);				
			}
			result.setVO(TAG_MISSIONS, listMissions(player, pm.getMission().getType()));
			result.setStatus(STATUS_LIST);
		}else if(target.equals("info")){
			String missionId = ps[1];
//			String type = ps[2];
			PlayerMission playerMission = player.getMission(missionId);
			
			RawMission raw = null;
			try {
				raw = MissionMG.instance.getRawMission(Integer
				        .parseInt(missionId));
				if (raw != null) {
					City city = MapMG.instance.getCity(raw.getCityId());
					
					Area area = MapMG.instance.getArea(city.getAreaId());
					CityFacility cf = MapMG.instance.getCityFacility(raw
					        .getCityfacilityId());
					result.setVO("targetCity", city);
					result.setVO("targetArea", area);
					result.setVO("targetFacility", cf.getFacility());
					Npc npce = NpcMG.instance.getNpc(raw.getNpcEnd());
					result.setVO("targetNpc", npce);
				}
			} catch (Exception e) {

			}
			
			PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.instance.getItem(Constants.ITEM_MISSION_GUIDE));
			
			result.setText(TAG_AMOUNT, playerItem == null?0:playerItem.getAmount());
			result.setText(TAG_MISSION_ID, missionId);
			result.setText(TAG_DESC, MissionService.getMissionStatusByMission(playerMission.getMission(), player));
			result.setVO(TAG_MISSION, playerMission);
			if(raw != null && raw.getNpcEnd() != null){
				if(player.getCityFacility() != null && playerMission.getMission().getCityfacilityId() != null && playerMission.getMission().getCityfacilityId() > 0){
					result.setVO(TAG_CITY_FACILITY, MapMG.instance.getCityFacility(playerMission.getMission().getCityfacilityId()));
					result.setVO(TAG_CITY, player.getCityFacility().getCity().getId());
				}
				
			}
			result.setStatus(target);
		}
		
		return result;
	}
	
	private Map<String, PlayerMission> listMissions(Player player, Integer type){
		Map<String, PlayerMission> missions = new LinkedHashMap<String, PlayerMission>();

		for(String key : player.getMissions().keySet()){
			PlayerMission pm = player.getMission(key);
			
			if(!pm.isComplete() && pm.getMission().getType().equals(type)){
				if(type == MissionType.TYPE_INSTANCE){
					if(!player.getCity().getIsInstance()){
						// 有副本任务不在副本中
						// 结束任务
						pm.addTimes();
						pm.setComplete(Boolean.TRUE);
						DBService.commit(pm);
					}else
						missions.put(key, pm);
				}else
					missions.put(key, pm);
			}
		}
		
		return missions;
	}
	
	{
//		Map<MissionType, String> counter = new LinkedHashMap<MissionType, String>();
//		for(MissionType mt : MissionMG.instance.getMissionTypes().values()){
////			if(player.getCity().getIsInstance()){
////				if(mt.getId() != MissionType.TYPE_INSTANCE) continue;
////				counter.put(mt, "0");
////			}else{
////				if(mt.getId() == MissionType.TYPE_INSTANCE) continue;
////				counter.put(mt, "0");
////			}
//			
//			counter.put(mt, "0");
//		}
//		
//		for(PlayerMission pm : player.getOnGoingMissions().values()){
//			if(pm.isComplete()) continue;
//			
//			MissionType type = MissionMG.instance.getMissionTypes().get(pm.getMission().getType());
//			if(counter.get(type) != null){
//				int count = Integer.parseInt(counter.get(type));
//				counter.put(type, String.valueOf(++count));
//			}
//		}
//		
//		for(MissionType type : counter.keySet()){
//			result.setList(TAG_TYPE, type.getName(), String.valueOf(type.getId()), counter.get(type), counter.get(type));
//		}
//		result.setVO("jisha_mission_num", MissionMG.instance.getPlayerEnemyMission(player.getId()).size());
//		result.setStatus(STATUS_TYPE);
	}
}
