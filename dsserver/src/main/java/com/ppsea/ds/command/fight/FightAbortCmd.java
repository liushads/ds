/**
 * 
 */
package com.ppsea.ds.command.fight;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.PlayerService;

public class FightAbortCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(FightAbortCmd.class);
	/**
	 * @param ps
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		//攻城战斗的攻方
//		if (player.getFightCityStatus() == FightService.FIGHT_STATUS_ATK){
//			Integer cid = player.getCityFacility().getCityId();
//			FightCityInfo fci = FightCityService.instance.getFightCityInfo(cid);
//			if (fci != null) fci.leaveAtkPlayers(player);
//		}
		//攻城战斗的守方			
//		if (player.getFightCityStatus() == FightService.FIGHT_STATUS_DEF){
//			Integer cid = player.getCityFacility().getCityId();
//			FightCityInfo fci = FightCityService.instance.getFightCityInfo(cid);
//			if (fci != null) fci.leaveDefPlayers(player);
//		}
		//在王战中
		CommandResult result = new CommandResult(STATUS_SUCC);
		//丢失铜贝
//		int lostCopper = Math.abs(Integer.valueOf(ps[0]).intValue());
//		player.addCopper(0 - lostCopper);
		//降低士气
		if (ps!=null||ps.length>0) {
			String action=ps[0];
			if (action.equals("queren")) {
				result.setStatus("index");
				return result;
			}
		}
		int decMorale = 0;
		FightService.rangeRandom(5, 10);
		DBService.commit(player);
		//取消和最后一个敌人的绑定
		player.setLastEnemy(null);
		if (player.getFightCityStatus() > 0)
		{
			player.setFightCityStatus(0);
		}
		if (player.getFightPlayerStatus() > 0)
		{
			player.setFightPlayerStatus(0);
			player.setLeitaiDate(null);
			player.setLeitype(0);
		}
		player.setLastAttackHp(0);
		player.setLastBeAttack(0);
		//撤退到广场
		int level = player.getCityFacility().getCity().getLevel();
		if (level == 0) {
			if (player.isInInstacneCity() && player.getInstanceEntrance() > 0) {
				PlayerService.move(player, player.getInstanceEntrance(), false);
				player.setSpecialCity(false);
			} 
			else {
				FightService.gotoMainCityFacility(player, Constants.CENTER_ID);
			}
		}
		else {
			FightService.gotoCurrCityFacility(player, Constants.CENTER_ID);
		}
		result.setText(TAG_LOST_MORALE, decMorale);
//		result.setText(TAG_MONSTER_ABORT, String.valueOf(lostCopper));
		result.setText(TAG_MONSTER_ABORT, String.valueOf(0));
		return result;
	}
}
