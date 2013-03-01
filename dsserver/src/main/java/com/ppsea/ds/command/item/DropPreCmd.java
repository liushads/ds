package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
/**
 * 显示要丢弃的物品信息 
 **/
public class DropPreCmd extends BaseCmd{
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DropPreCmd.class);

	/**
	 * 检索要丢弃的物品
	 * @param ps[0] 物品ID 
	 **/
	protected CommandResult done(Player player, String[] ps)throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);		
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}

}
