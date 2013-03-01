/**
 * 
 */
package com.ppsea.ds.command.fight;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Monster;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;

public class FightMonsInfCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(FightMonsInfCmd.class);
	/**
	 * @param ps
	 * ps[0] : player's monster instance id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		CommandResult result = new CommandResult(STATUS_SUCC);
		int targetId = Integer.valueOf(ps[0]).intValue();
		
		if (targetId < 0) 
		{
			Monster monster = null;
			//攻城时的守卫
			/*if (targetId <= FightService.SEG_FIGHT_CITY_MONSTER_BEG
				&& targetId >= FightService.SEG_FIGHT_CITY_MONSTER_END) 
			{
				Integer cid = player.getCityFacility().getCityId();
				FightCityInfo fci = FightCityService.instance.getFightCityInfo(cid);
				if (fci != null) {
					monster = fci.getDefMonster(targetId);
				}
			}
			//副本中的怪
			else if (targetId <= FightService.SEG_FIGHT_INSTANCE_MONSTER_BEG
					 && targetId >= FightService.SEG_FIGHT_INSTANCE_MONSTER_END)
			{
				
				Team tm = TeamMG.instance.getTeam(player.getTeamId());
				if (tm != null) {
					monster = tm.getMonsterIn(player.getCityFacilityId(), targetId);
				}
				if (monster != null) {
					result.setText(TAG_MONSTER_NAME, monster.getMonster().getName());
					result.setVO(TAG_MONSTER, monster.getInstanceMonster());
					result.setVO(TAG_MONSTER_PLAYER, monster.getPlayer());
					return result;
				}
			}
			//普通怪物
			else */{
				monster = player.getMonster().get(targetId);
			}
			if (monster == null) {
				result.setText(TAG_ENEMY_NAME, "敌人");
				setFailResult(result, ErrorCode.ERR_TARGET_LOST);
				return result;
			}
			result.setVO(TAG_MONSTER, monster.getMonster());
			result.setVO(TAG_MONSTER_PLAYER, monster.getPlayer());
			result.setText(TAG_MONSTER_NAME, monster.getShowName());
		} 
		else {
			Player p = PlayerMG.instance.getOnlinePlayer(targetId);
			result.setVO(TAG_ENEMY, p);
		}
		/////////////////////////////////////////////////////////	
		return result;
	}

}
