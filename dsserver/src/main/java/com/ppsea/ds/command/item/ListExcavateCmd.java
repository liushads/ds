package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.Util;
/**
 * 列出玩家身上可以进行宝石移除的装备列表
 **/
public class ListExcavateCmd extends BaseCmd {

	/**
	 * @param ps[0] npc_id
	 * @param ps[1]
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = -1;
		if((npcId = Integer.parseInt(ps[0])) <= 0)
			throw new PpseaException("非法命令请求.");
		if( ItemUtil.bothInFacility(player, npcId) == false){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"请到NPC所在设置操作.");			
			return result;
		}
		int pageId = 0;
		try{pageId = Integer.parseInt(ps[1]);
		}catch (Exception e) {}
		//列出镶嵌了的装备
		List<PlayerItem> usedLst = player.getUsedArmsList();
		List<PlayerItem> armLst = new LinkedList<PlayerItem>();	
		for(PlayerItem playerItem : usedLst){
			Item item = playerItem.getItem();
			if(item != null && item.getType() == Item.ARM_TYPE && 
				playerItem.getAppends() != null &&
				playerItem.getAppends().size() > 0 &&
				playerItem.getItem().getPosition().intValue() != Item.POS_MOUNTS)
				armLst.add(playerItem);
		}		
		//分页		
		Util.page(armLst, pageId, result);
		result.setVO(TAG_NPC_ID, npcId);
		return result; 
	}
}
