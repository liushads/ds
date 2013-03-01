package com.ppsea.ds.command.move;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.fight.ChalLengeCmd;
import com.ppsea.ds.command.fight.SystemSettingCmd;
import com.ppsea.ds.command.player.Displayer;
import com.ppsea.ds.command.player.PlayerBaseCmd;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.InstanceSet;
import com.ppsea.ds.data.model.Action;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.RawMission;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.EventMG;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.MonsterMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.DisplayerService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.EventService;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.TravelService;
import com.ppsea.ds.service.WalkService;

public class WalkCmd extends PlayerBaseCmd{
	private static final Logger log = Logger.getLogger(WalkCmd.class);
	private static Random RND = new Random();

	/**
	 * @param ps 
	 * */
	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		int oldCityFacilityId = player.getCityFacilityId();
		int newCityFacilityId = Integer.valueOf(ps[0]);
		boolean isSameCity = true;
		PlayerService.checkPlayerUpgraded(player, result);
		
		//检查玩家战斗状态
		if (WalkService.checkFight(player, result)) {
			return result;
		}

		if( newCityFacilityId == 0){
			//旅行中进城
			City city = TravelService.getPreCity(player);
			newCityFacilityId = city.getSpecialFacilityMap().get(Constants.STATION_ID).getId();
			isSameCity = false;
		}else if (newCityFacilityId == -1){
			newCityFacilityId = player.getFromCityFacilityId();
			isSameCity = false;
		}
	
		// 进行中的任务数量
		Integer loc = ongoingMissionCount(player, result);
		if(loc > 0){
			newCityFacilityId = loc;
			isSameCity = false;
		}
		if (!SystemSettingCmd.isFlagSet(player.getSettingFlag(),
				SystemSettingCmd.FLAG_MISSION_DESC)) {
		if (player.getMissions()!=null) {
			for(String key : player.getMissions().keySet()){
				PlayerMission pm = player.getMission(key);
				if (pm.getMission().getType()==1&&!pm.getComplete()) {
					result.setVO(TAG_MISSION, pm);
					result.setText("text",MissionService.getMissionStatusByMission(pm.getMission(), player));
					try {
						RawMission raw = MissionMG.instance.getRawMission(pm.getMission().getId());
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
							if(player.getCityFacility() != null && pm.getMission().getCityfacilityId() != null && pm.getMission().getCityfacilityId() > 0){
								result.setVO(TAG_CITY_FACILITY, MapMG.instance.getCityFacility(pm.getMission().getCityfacilityId()));
								result.setVO(TAG_CITY, player.getCityFacility().getCity().getId());
							}
						}
					} catch (Exception e) {

					}
				}
				}
		}
		}
		PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.instance.getItem(Constants.ITEM_MISSION_GUIDE));
		result.setText("shuliang", playerItem == null?0:playerItem.getAmount());
		if (ps.length>=2) {
			if (ps[1].equals("mission")) {
				player.setMissionopen(Integer.valueOf(ps[2]));
				}else if (ps[1].equals("stream")) {
					player.setStreamOpen(Integer.valueOf(ps[2]));
				}else if (ps[1].equals("state")){
					player.setStateOpen(Integer.valueOf(ps[2]));
				}
		}
		
		// 更新玩家位置
		ErrorMsg ret = PlayerService.move(player, newCityFacilityId, isSameCity);		
		if( ret.code != ErrorCode.SUCC ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		result.setVO(TAG_NEWID, newCityFacilityId);
		Map<String, String> map=PlayerMG.instance.getApplemap();
		result.setVO(TAG_ITEMS, map);
		
		result.setVO(TAG_IS_FACILITY, player.getCityFacility().getFacility().getIsCity());
		
		//显示NPC列表
		showNpc(player, result, newCityFacilityId);
		
		//显示怪物列表
		showMonster(player, oldCityFacilityId, newCityFacilityId, result, ps);
		
		//显示掉了的道具
		FightService.checkWinPrize(player, result);
		
		//显示私聊消息.
		PlayerService.showPrivateMsg(player, result);
		
		//最多显示3个同设施在线玩家
		String players = "";
		if(players.length() > 0){
			result.setVO(TAG_PLAYERS, players);
		}
		
		//页面消息
		/*if(player.getCityFacility().getCity().getIsInstance())
			result.setVO(TAG_PAGE_OBJS, ChatService.queryTop2TeamMessages(String.valueOf(player.getTeamId())));
		else */result.setVO(TAG_PAGE_OBJS, ChatService.queryTop2PublicMessages(ChatService.CHAT_WORLD));
		
		// 随机事件
		if(isSameCity){
			DialogAction da = EventService.checkFacilityEvent(player);
			if(da != null){
				if(da.getEvent() != null){
					CommandRequest cmdReq = new CommandRequest();
					cmdReq.setCmd(COMMAND_WALK);
					cmdReq.setPid(String.valueOf(player.getId()));
					cmdReq.setUid(String.valueOf(player.getUserId()));
					
					cmdReq.setPs(new String[]{String.valueOf(da.getEvent().getCityfacilityId())});
					return callOtherCmd(cmdReq);
					
				}else{
					result.setVO(TAG_DIALOG, da);
					result.setStatus(STATUS_INFO);
					return result;
				}
			}
		}
		
		if(player.getCityFacility() != null){
			result.setVO(TAG_FACILITY_DESC, player.getCityFacility().getFacility().getDescription());
			result.setVO(TAG_CITY_FACILITY_ID, player.getCityFacility().getId());
			result.setVO(TAG_FACILITY_ID, player.getCityFacility().getFacility().getId());
		}
		
		// 出口
		//TODO 可以优化为map存储，Map<"东","id:name">在proxy端解析
		boolean noExit = true;
		Boolean[] clicks = new Boolean[4];
		int i = 0;
		for(CityFacility tmp : player.getCityFacility().getDestinations()){
			if(tmp == null){
				result.setList(TAG_DIRECT, "null", "null");
				clicks[i] = false;
			}
			else{
				result.setList(TAG_DIRECT, tmp.getAlias(), String.valueOf(tmp.getId()));
				clicks[i] = tmp.getFacility().getIsClick();
				noExit = false;
			}	
			i++;
		}
		result.setVO(TAG_IS_CLICKS, clicks);

		// 操作
		addAction(player, result, noExit);
		
		//升级页面提示
		if (Command.STATUS_PLAYER_UPGRADE.equals(result.getStatus())) {
			return result;
		}
		Calendar calendar=Calendar.getInstance();
		Calendar car=Calendar.getInstance();
		Date date = player.getLoginTime();
		Date datetime = new Date();
		calendar.setTime(datetime);
		car.setTime(date);
		long num =0;
		if (player.getZaixian()==null) {
				num= 60
						- (calendar.getTimeInMillis() - car.getTimeInMillis())
						/ (60 * 1000);
		}else {
			car = player.getZaixian();
			 num = 60
					- (calendar.getTimeInMillis() - car.getTimeInMillis()) / (60 * 1000);
		}
		result.setVO("minutes", num);
		String key = player.getId().toString() + "_"
		+ "onlinegive" + "_" + 1;
		PlayerActive pa = player.getPlayerActive().get(key);
		int type=1;
		if (pa != null) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			if (sf.format(datetime).equals(sf.format(pa.getJoinDate()))) {
				type=0;
			}
		}
		result.setVO("onlinetype", type);
		
		if (Command.STATUS_PLAYER_ONLINE_GIVING.equals(result.getStatus())) {
			return result;
		}
		
		Displayer displayer = DisplayerService.getInstance().checkAndDisplay();
		result.setVO("displayer", displayer);
		if (player.getLevel() < 7) {
			result.setStatus("succ_sp");
		}
		
		//设置玩家在主界面看到的自己的信息
		result.setVO("label_level", player.getLevel());
		result.setVO("label_adv_gold", player.getAdvGold());
		result.setVO("label_gold", player.getGold());
		result.setVO("label_copper", player.getCopper());
		result.setVO("label_fame", player.getFame());
		result.setVO("label_fame_max", player.getDyn().getFame());
		//获取玩家vip信息
		
		return result;
	}
	
	
	/**
	 * 
	 * @param player
	 * @param result
	 * @param cityFacilityId
	 */
	private void showNpc(Player player, CommandResult  result, int cityFacilityId){
		
		Map<Integer, CityFacilityNpc> npcs = NpcMG.instance.getNpcInFacility(cityFacilityId);
		if(npcs == null || npcs.values() == null){
			//log.error("get city facility npc failed!!!!!!!cityFacilityId="+ cityFacilityId);
			return;
		}
		
		for(CityFacilityNpc cfn:npcs.values()){
			Npc npc = cfn.getNpc();
			String prefix = "";
			Mission m = null;
			
			Collection<Mission> c = npc.getMissions().values();
			
			for(Mission mission : c){
				if(!mission.getIsValidate() || mission.getType().equals(MissionType.TYPE_INSTANCE)){
					m = mission;
					continue;
				}
				
				PlayerMission playerMission = player.getMission(String.valueOf(mission.getId()));
				
				if(playerMission != null && !playerMission.isComplete()){
					// 有该NPC发布未完成的任务
					prefix = "？";
					break;
				}else if(!player.inPastMission(mission.getId())){	
					if(playerMission != null && 
							player.getLevel() >= mission.getConditionLevel() &&
							(mission.getConditionLevelEnd() == 0 || player.getLevel() < mission.getConditionLevelEnd()) &&
							player.getFame() >= mission.getConditionFame() &&
							((mission.getRepeatTimes() > 0 && 
							playerMission.getTimesBeDone() <= mission.getRepeatTimes()))){
						// 任务允许重复做
						prefix = "！";
						break;
					}else if(playerMission == null && 
							player.getLevel() >= mission.getConditionLevel() &&
							(mission.getConditionLevelEnd() == 0 || player.getLevel() < mission.getConditionLevelEnd()) &&
							player.getFame() >= mission.getConditionFame() &&
							satisfyPreMission(player, mission) && 
							mission.canStartMission(player, npc, null)){
						// 未做过该任务，并且满足任务条件
						prefix = "！";
						break;
					}
				}
			}
			
			if(npc.getStartMissions().size() > 0 && 
					npc.getStartMissions().get(0).getType() == MissionType.TYPE_DAILY && 
					prefix.equals("！")){
				// 每日任务
				for(Mission mission : c){
					if(player.getOnGoingMissions().get(String.valueOf(mission.getId())) != null){
						prefix = "？";
						break;
					}
				}
			}
			
			if(!prefix.equals("") || (m != null && m.getType().equals(MissionType.TYPE_INSTANCE)) ||
			   	(!npc.getShowForMission() && (!npc.getRandomShowup() || RND.nextInt(100) > 50) &&
			   	((npc.getCommand() != null && !npc.getCommand().equals("")) || !npc.execute(player).getDialog().equals(Constants.DEFAULT_DIALOG))))
				result.setList(TAG_NPC, prefix + npc.getName(), String.valueOf(npc.getId()));			
		}
		
	}
	/**
	 * 
	 * @param player
	 * @param oldCfid
	 * @param newCfid
	 * @param result
	 */
	private void showMonster(Player player, int oldCfid, int newCfid, CommandResult result, String[] ps)
	{
		//位置有更新，重新产生怪
		if((oldCfid != newCfid) || player.isFirstEntry()){
			player.clrMonster();
			player.setFirstEntry(false);
			MonsterMG.instance.getMonsterToPlayer(player);
		}
		//副本怪
//		if (player.isInInstacneCity()) {
//			Team tm = TeamMG.instance.getTeam(player.getTeamId());
//			if (tm == null) {
//				log.error("isInInstacneCity but team is null, player:" + player.getId() + "," + player.getName());
//			}
//			if (tm != null && tm.isInstanceTeam()) {
//				//副本怪物列表
//				ConcurrentHashMap<Integer, Monster> all = tm.getAllMonsterIn(player.getCityFacilityId());
//				for (Monster m : all.values()) {
//					result.setList(TAG_FIGHT_MONSTER, m.getPlayer().getName(), String.valueOf(m.getPlayer().getId()));
//				}
//			}
//		}
//		else {
			//普通怪物列表
			for (Monster m : player.getMonster().values()) {
				if (!m.isNpc()) {
					result.setList(TAG_FIGHT_MONSTER, m.getPlayer().getName(), String.valueOf(m.getPlayer().getId()));				
				}
			}
//		}
		//检查怪主动攻击(waldCmd需要增加参数判断，防止系统move导致主动攻击??)
		FightService.checkMonsterAttack(player, result, ps);
	}
	
	/**
	 * 进行中任务数量
	 * 
	 * @param player
	 * @param result    
	 * @return void    
	 * @throws
	 */
	private Integer ongoingMissionCount(Player player, CommandResult result){
		boolean hasInstanceMission = false;
		
		Integer cfId = 0;

		int i = 0;
		Iterator<String> it = player.getOnGoingMissions().keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			
			PlayerMission pm = player.getOnGoingMissions().get(key);
			if(!pm.isComplete()){
				i ++;
				
				if(pm.getMission().getType().equals(MissionType.TYPE_INSTANCE)){
					hasInstanceMission = true;
					
					if(player.getCityFacility() != null){
						if(!player.getCityFacility().getCity().getIsInstance()){
							// 结束任务
							pm.addTimes();
							pm.setComplete(Boolean.TRUE);
							DBService.commit(pm);
							
							// 脱离副本队伍
							InstanceSet.removeFromApplyTeam(player);
							player.setSpecialCity(false);
						}
					}
				}
			}else{
				it.remove();
				player.getOnGoingMissions().remove(key);
			}
				
		}
		//CityFacility newCityFacility = MapMG.instance.getCityFacility(newCityFacilityId);
		
		if(!hasInstanceMission 
				&& ((player.getCityFacility() != null 
						&& player.getCityFacility().getCity() != null 
						&& player.getCityFacility().getCity().getIsInstance()))){
			
			// 无副本任务而在副本地图中，移动到广场
			if(player.getInstanceEntrance() == 0){
				cfId = PlayerService.getCenterId(player);
			}else{
				cfId = player.getInstanceEntrance();
				player.setInstanceEntrance(0);
			}
			player.setSpecialCity(false);
			// 脱离副本队伍
			InstanceSet.removeFromApplyTeam(player);

		}
		
		i += MissionMG.instance.getPlayerEnemyMission(player.getId()).size();
		result.setText(TAG_AMOUNT, i);
		
		return cfId;
	}
	
	/**
	 * 事件进入打怪设施的出口
	 * 
	 * @param player
	 * @param result    
	 * @return void    
	 * @throws
	 */
	private void addAction(Player player, CommandResult result, boolean noExit){
		if(player.getStartCity() != 0 && player.getEndCity() != 0 && EventMG.instance.hasRouteEvent(player.getCityFacilityId())){
			List<Action> list = new ArrayList<Action>();
			Action action = new Action();
			action.setName("离开");
			action.setCommand(COMMAND_LEAVE);
			
			list.add(action);
			result.setVO(TAG_ACTION, list);
		}else if(player.getFromCityFacilityId() > 0 && EventMG.instance.hasFacilityEvent(player.getCityFacilityId())){
			List<Action> list = new ArrayList<Action>();
			Action action = new Action();
			action.setName("离开");
			action.setCommand(COMMAND_LEAVE);
			action.setParam(String.valueOf(player.getFromCityFacilityId()));
			
			list.add(action);
			result.setVO(TAG_ACTION, list);
		}else{
			result.setVO(TAG_ACTION, player.getCityFacility().getActions());
		}
	}
	


}
