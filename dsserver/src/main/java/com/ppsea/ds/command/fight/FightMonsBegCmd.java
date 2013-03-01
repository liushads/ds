/**
 * 
 */
package com.ppsea.ds.command.fight;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.CityFacilityMonster;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.PlayerPet;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.MonsterMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;

public class FightMonsBegCmd extends BaseCmd{
	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(FightMonsBegCmd.class);
	
	private static final Logger monsterLog = Logger.getLogger("Monster");
	/**
	 * @param ps
	 * ps[0] : player's monster instance id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		CommandResult result = new CommandResult(STATUS_SUCC);
		Integer targetId = Integer.valueOf(ps[0]);
		
		Monster npcMonster = null;
		//NPC类型的怪物
		if (ps.length > 1 && ps[1].equals("n")) 
		{
			Monster m = MonsterMG.instance.getMonsterById(targetId);
			if (m != null)
			{
				boolean hasFighted = false;
				//刷怪判断
				List<Integer> list = MissionMG.instance.getMissionIdByMonster(targetId);
				if(list != null){
					for(Integer missionId : list){
						PlayerMission pm = player.getMissionOnGoing(missionId.toString());
						if(pm == null || pm.getComplete() || player.getInteraction("monster" + targetId) == 1){
							hasFighted = true;
							break;
						}
					}
				}
				////////////////////////////////////////////////////////////////
				if (hasFighted) {
					player.setLastEnemy(null);
					result.setStatus(STATUS_FAIL);
					result.setText(TAG_ENEMY_NAME, "敌人");
					result.setVO(TAG_ERR_MSG, ErrorCode.ERR_TARGET_LOST);
					return result;
				}
				
				//已经产生过该怪物，只产生一次
				Map<Integer, Monster> mm = player.getMonster();
				if (mm != null) {
					for (Monster med : mm.values()) {
						if (med.getMonster().getId().intValue() == m.getId().intValue()) {
							npcMonster = med;
							break;
						}
					}
				}
				if (npcMonster == null) {
					npcMonster = new Monster();
					CityFacilityMonster cm = new CityFacilityMonster();
					cm.setAttackProb(0);
					cm.setShowupProb(0);
					cm.setNoDisappear(0);
					targetId = -1 - player.getMonster().size();
					npcMonster.init(cm, m, targetId);
					npcMonster.setNpc(true);
					player.getMonster().put(targetId, npcMonster);
				}
				/*
				if (hasFighted == false) {
					//已经产生过该怪物，只产生一次
					Map<Integer, Monster> mm = player.getMonster();
					if (mm != null) {
						for (Monster med : mm.values()) {
							if (med.getMonster().getId().intValue() == m.getId().intValue()) {
								hasFighted = true;
								break;
							}
						}
					}
				}
				if (hasFighted) {
					player.setLastEnemy(null);
					result.setStatus(STATUS_FAIL);
					result.setText(TAG_ENEMY_NAME, "敌人");
					result.setVO(TAG_ERR_MSG, ErrorCode.ERR_TARGET_LOST);
					return result;
				}
				
				Monster fm = new Monster();
				CityFacilityMonster cm = new CityFacilityMonster();
				cm.setAttackProb(0);
				cm.setShowupProb(0);
				cm.setNoDisappear(0);
				targetId = -1 - player.getMonster().size();
				fm.init(cm, m, targetId);
				fm.setNpc(true);
				player.getMonster().put(targetId, fm);
				*/
				//////////////////////////////////////////////////////////
			}
		}
		Monster monster = null;
		boolean isFightMonsterShow = false;
		//副本中的怪物
		/*if (player.getCity() != null && player.getCity().getIsInstance() && targetId >= FightService.SEG_FIGHT_INSTANCE_MONSTER_END  
				&& targetId <= FightService.SEG_FIGHT_INSTANCE_MONSTER_BEG)
		{
			Team tm = TeamMG.instance.getTeam(player.getTeamId());
			if (tm != null) monster = tm.getMonsterIn(player.getCityFacilityId(), targetId);
		}
		//NPC怪物
		else */if (npcMonster != null) {
			monster = npcMonster;
		}
		//普通的怪物
		else
		{
			monster = player.getMonster().get(targetId);
			if (monster == null) {
				result.setText(TAG_ENEMY_NAME, "敌人");
				setFailResult(result, ErrorCode.ERR_TARGET_LOST);
				return result;
			}
			if (monster.getMonsterType() != null && (monster.getMonsterType() >= 1)) {
				monster.updateAutoRestoreInterval();
				//test.
				monster.setTestTime(System.currentTimeMillis());
				monsterLog.info(player.getId()+"|"+monster.getMonster().getId()+"|S");
			}
			isFightMonsterShow = true;
		}
		if (monster == null) {
			result.setText(TAG_ENEMY_NAME, "敌人");
			setFailResult(result, ErrorCode.ERR_TARGET_LOST);
			return result;
		}
		result.setText(TAG_TYPE, "0");
		//进入战斗场景
		FightService.checkFightMessage(player, result, monster.getPlayer());
		return result;
	}
}
