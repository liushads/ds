package com.ppsea.ds.command.player;

import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;
/**
 * 显示自己装备信息 
 **/
public class ListArmCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListArmCmd.class);
	
	/**
	 *  
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);	
		result.setVO(TAG_OBJS, player.getUsedArms());
		List<ItemSuit> itemSuitList = player.getItemSuitList();
		
		result.setVO(Command.TAG_ITEM_SUIT_LIST, itemSuitList);
		ItemService.setItemPositions(result);
		return result;
	}
}
