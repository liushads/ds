

package com.ppsea.ds.command.player;

import java.util.Map;

import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionCondition;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.util.Util;

/**
 * ClassName:TalkCmd 与NPC对话
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Nov 29, 2008		5:01:47 PM
 *
 * @see 	 
 */
public class TalkCmd extends PlayerBaseCmd {
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = Integer.valueOf(ps[0]);
		
		Map<Integer, CityFacilityNpc> facilityMap = NpcMG.instance.getNpcInFacility(player.getCityFacilityId());
		if(facilityMap == null){
			result.setVO(TAG_DIALOG, new DialogAction("", null));
			return result;
		}
		CityFacilityNpc cfn = facilityMap.get(npcId);
		if(cfn == null){
			result.setVO(TAG_DIALOG, new DialogAction("", null));
			return result;
		}
		result.setVO(TAG_PLAYER, player);
		Npc npc = cfn.getNpc();
		
		if(npc != null){
			player.setLastNpcId(npc.getId());
			result.setVO(TAG_NPC, npc);
		}else{
			result.setVO(TAG_DIALOG, new DialogAction("", null));
			return result;
		}
		
		// 转向
		CommandResult r = checkRedirect(player, npc);
		if(r != null) {//是否有对话
			return r;
		}

		// 检查任务时可能产生的提示性对话
		MissionCondition condition = new MissionCondition();
		
		// 检查用户任务列表，如果不存在，则继续
		boolean flag = checkUserMissions(player, npc, result, condition);
		
		// 返回对话
		if(!flag){
			result.setVO(TAG_DIALOG, 
				((condition == null || condition.getDialog() == null || 
				condition.getDialog().equals("")) ? 
				npc.execute(player)	: new DialogAction(condition.getDialog(), null)));

			result.setText(TAG_WIN_UPGRADE, player.getLevel());
		}
		return result;
	}
	
	/**
	 * 非标准对象，转向指定的Command去执行
	 * 
	 * @param npc
	 * @param result
	 * @return
	 * @throws PpseaException    
	 * @return boolean    
	 * @throws
	 */
	private CommandResult checkRedirect(Player player, Npc npc) throws PpseaException{
		// 非标准对话，转向
		if(npc != null && npc.getCommand() != null && !npc.getCommand().equals("")){
			Map<String, String> data = Util.decodeMessage(npc.getCommand());
			CommandRequest cmdReq = new CommandRequest();
			cmdReq.setPid(player.getId().toString());
			cmdReq.setUid(player.getUserId().toString());
			cmdReq.setCmd(data.get("cmd"));
			String param = data.get("param");
			if(param != null){
				String[] ps = param.split("/");
				cmdReq.setPs(ps);
			}
			return callOtherCmd(cmdReq);
		}
		return null;
	}
	
	/**
	 * 检查用户任务列表和发布新任务给用户
	 * 
	 * @param player
	 * @param npc
	 * @param result
	 * @return    
	 * @return boolean 
	 * @throws
	 */
	private boolean checkUserMissions(Player player, Npc npc, CommandResult result, MissionCondition condition){
		result.setText(TAG_CUSTOM, npc.getId());
		for(Mission mission : npc.getMissions().values()){
			PlayerMission playerMission = player.getMissionOnGoing(String.valueOf(mission.getId()));
			
			if(playerMission != null){
				if(checkUserMission(player, playerMission, result, npc)) return true;
			}
			else if(checkNewMission(player, mission, result, npc, condition)) return true;
		}		
		
		return false;
	}
}
