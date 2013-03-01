/**
 * 
 */
package com.ppsea.ds.command.fight;

//import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;

public class FightPickupCmd extends BaseCmd{
//	private static final Logger log = Logger.getLogger(FightPickupCmd.class);
	/**
	 * 
	 * @param winner
	 * @param monster
	 */
	public void notifyTeamMessage(Player winner, PlayerItem pi) {
		StringBuffer sb = new StringBuffer();
		CityFacility cf = winner.getCityFacility();
		sb.append("【" + winner.getName() + "】");
		if (cf != null) sb.append("在" + cf.getFacility().getName());
		sb.append("捡起了" + pi.getItem().getName());
		ChatService.sayTeam(winner, sb.toString());
	}
	/**
	 * @param ps
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		CommandResult result = new CommandResult(STATUS_SUCC);
		int dropItemId = Integer.valueOf(ps[0]);
		ErrorMsg ret;
		//副本队中的共享道具
		if (dropItemId < 0) {
			dropItemId = ItemService.getRealItemIndex(dropItemId);
			ret = ItemService.pickTempItems(player, dropItemId, true);
		} 
		//自有的独享道具
		else {
			ret = ItemService.pickTempItems(player, dropItemId, false);
		}
		if (ret.code != ErrorCode.SUCC) 
		{
			setFailResult(result, ret);
			if (ret.code == ErrorCode.ERR_SYS) {
				//在副本中
				if (player.isInInstacneCity()) {
					FightService.gotoCurrCityFacility(player, player.getCityFacilityId());
					/*
					CommandResult cr = 
					if (cr != null) {
						result.getData().putAll(cr.getData());
						result.setText(TAG_ENEMY_NAME, "敌人");
						result.setStatus(STATUS_INSTANCE_FAIL);
					}
					*/
				}
				setFailResult(result, ErrorCode.ERR_PICK_ITEM_LOST);
			}
			return result;			
		}
	
		PlayerItem pi = (PlayerItem)ret.obj;
		result.setVO(TAG_ITEM, pi);

		if (player.isInInstacneCity()) {
			this.notifyTeamMessage(player, pi);
		}
		//检查并返回任务进度
		String msg = MissionService.getMissionStatusByItem(pi.getItem().getId(), player);
		if(!msg.equals("")) result.setText(TAG_MISSION, msg);
		
		//检查并设置没有捡起的道具
		FightService.checkWinPrize(player, result);
		return result;
	}

}
