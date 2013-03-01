package com.ppsea.ds.command.mission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.NpcMG;

/**
 * ClassName:NextMissionCmd 查找下一任务
 *
 * @author   dan hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Jul 6, 2009		11:01:46 AM
 *
 * @see 	 
 */
public class NextMissionCmd extends BaseCmd {
	
	private static Random RND = new Random();
	
	/**
	 * (non-Javadoc)
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.core.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		boolean mainStory = true;
		
		if(ps!=null){
			if(ps.length > 1){
				Npc npc = NpcMG.instance.getNpcInFacility(player.getCityFacility().getId()).get(Integer.valueOf(ps[1])).getNpc();
				result.setHref(TAG_NPC, npc.getName(), npc.getId());
			}
			
			if(ps.length > 0){
				if(ps[0].equals("0")) mainStory = false;
			}
		}
		
		for(PlayerMission pm : player.getOnGoingMissions().values()){
			if(pm.getMission().getId() > 0 && !pm.isComplete() && pm.getMission().getRepeatTimes() == 0){
				result.setText(TAG_DESC, "有未完成任务，请先完成“" + pm.getMission().getName() + "”！");
				return result;				
			}
		}
		
		List<Mission> list = new ArrayList<Mission>();
		Mission newMission = null;
		
		Map<Integer, Integer> map = MissionMG.instance.getPostMissions();
		
		for(Mission mission : MissionMG.instance.getMissions().values()){
			if(player.inPastMission(mission.getId()) || mission.getRepeatTimes() > 0)continue;
			
			PlayerMission pm = player.getMissions().get(String.valueOf(mission.getId()));
			if(pm != null){
				if(!pm.isComplete()){
					result.setText(TAG_DESC, "有未完成任务，请先完成“" + pm.getMission().getName() + "”！");
					break;
				}
			}
			
			if(mainStory){
				// 主线任务
				if(!mission.getType().equals(MissionType.TYPE_MAIN))
					continue;
			}else{
				// 非主线任务
				if(mission.getType().equals(MissionType.TYPE_MAIN))
					continue;
			}
			
			if(pm != null){
				if(pm.isComplete()){
					Integer nextMissionId = map.get(mission.getId());
					if(nextMissionId != null){
						newMission = newMissionHint(player, nextMissionId);
						if(newMission != null && !newMission.getType().equals(MissionType.TYPE_DAILY) && newMission.getRepeatTimes() == 0){
							break;
						}else
							list.add(newMission);
					}
				}
			}else{
				Integer preId = MissionMG.instance.getPreMissionIds().get(mission.getId());
				pm = player.getMissions().get(preId);
				if(preId == null || (preId != null && ((pm != null && pm.isComplete()) || player.inPastMission(preId)))){
					newMission = newMissionHint(player, mission.getId());
					if(newMission != null && !newMission.getType().equals(MissionType.TYPE_DAILY) && newMission.getRepeatTimes() == 0){
						break;
					}else
						list.add(newMission);
					
				}
			}
		}
		
		if(newMission != null && !newMission.getType().equals(MissionType.TYPE_DAILY) && newMission.getRepeatTimes() == 0){
		}else if(list.size() > 0){
			newMission = list.get(RND.nextInt(list.size()));
		}
		
		if(newMission != null){
			Integer npcId = NpcMG.instance.getMissionNpcs().get(newMission.getId());
			if(npcId != null){
				CityFacility cf = NpcMG.instance.findCityFacilityByNpc(npcId);
				
				result.setText(TAG_DESC, "接新任务请到" + cf.getCity().getName() + "的" + cf.getAlias());
			}
		}
		return result;
	}
	
	private Mission newMissionHint(Player player, Integer missionId){
		PlayerMission nextMission = player.getMissions().get(String.valueOf(missionId));
		if(nextMission != null) return null;
		Mission m = MissionMG.instance.getMission(missionId);
		
		if (m == null 
				|| m.getType().equals(MissionType.TYPE_INSTANCE))
			return null;
		
		if(player.getLevel() < m.getConditionLevel()  ||
				(m.getConditionLevelEnd() > 0 && player.getLevel() >= m.getConditionLevelEnd()))
			return null;
		
		Integer npcId = NpcMG.instance.getMissionNpcs().get(m.getId());
		if(npcId != null){
			Npc npc = NpcMG.instance.findNpcByCityFacilityNpc(npcId);
			if(!m.canStartMission(player, npc, null)) return null;	
		}
		
		return m;
	}
}
