package com.ppsea.ds.command.sect;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.util.LoggerHelper;

public class SectJoinCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(SectJoinCmd.class);
	
	private static Map<Integer, Integer> initArm;
	static{
		initArm = new HashMap<Integer, Integer>();
		//毒龙谷的装备上折扇,金刚堡的装备上砍刀、暗河宫的装备上铁剑、百花门的装备上匕首、丐帮的装备上木棒；
		initArm.put(1, 10001);
		initArm.put(2, 10016);
		initArm.put(3, 10031);
		initArm.put(4, 10046);
		initArm.put(5, 10061);
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int sectId = Integer.valueOf(ps[0]);
		ErrorMsg ret = PlayerService.joinSect(player, sectId);
		if( ret.code != ErrorCode.SUCC){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		//新手礼包
		getGift(player);
		LoggerHelper.PlayerRole.logForRoleCreation(player);
		return result;
	}
	
	/**
	 * 获赠新手礼包
	 * @param player
	 */
	private void getGift(Player player){
		//奖励武器，并装备
		int armId = initArm.get(player.getSectId());
		ErrorMsg ret = ItemService.addItem(player, armId, 1);
		if(ret.code != ErrorCode.SUCC){
			log.error("playerId = "+ player.getId()+":"+ret.text);
		}
		else{
			PlayerItem pi = (PlayerItem)(ret.obj);
			pi.use();
		}
		
		//奖励肉干10个,并放到药品栏
//		ret = ItemService.addItem(player, Constants.ITEM_MEAT_ID, 10);
		if(ret.code != ErrorCode.SUCC){
			log.error("playerId = "+ player.getId()+":"+ret.text);
		}
		else{
			PlayerItem pi = (PlayerItem)(ret.obj);
			player.getShortCuts().put(1, pi);
			pi.setShortcutId(1);
			DBService.commit(pi);
		}
//		
//		//奖励遁地符
//		ItemService.addItem(player, Constants.ITEM_MISSION_GUIDE, 20);
//		
//		//奖励新手礼包令牌
//		ItemService.addItem(player, Constants.ITEM_PRIZE_TOKEN, 1);
	}
}
