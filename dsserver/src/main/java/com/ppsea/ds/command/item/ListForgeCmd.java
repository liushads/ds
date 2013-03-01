package com.ppsea.ds.command.item;

import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.ItemUtil;
/**
 * 显示NPC可以铸造的装备列表 
 **/
public class ListForgeCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(ListForgeCmd.class);

	/**
	 * 显示可以购买道具列表
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = player.getLastNpcId();		
		if( ItemUtil.bothInFacility(player, npcId) == false){
			if(log.isInfoEnabled())
				log.warn("[ListBuyCmd]{player " +player.getName() + " not int npc's facility.}");
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"");			
			return result;
		}
		List<Item> forgeList = ItemUtil.getNpcForgeList(npcId);
		if(forgeList != null && forgeList.size() > 0)
			result.setVO(TAG_ITEMS, forgeList);
		result.setVO(TAG_RETURN, npcId);
		return result;
	}
}
