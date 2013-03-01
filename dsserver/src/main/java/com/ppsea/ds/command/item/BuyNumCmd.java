package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
/**
 * 玩家输入要买入道具的个数 
 **/
public class BuyNumCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BuyNumCmd.class);

	/**
	 * 玩家输入买入个数,提供要买入的道具信息 
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int itemId = Integer.parseInt(ps[0]);
		Item item = ItemMG.instance.getItem(itemId);
		result.setVO(TAG_ITEM, item );
		result.setVO(TAG_PLAYER, player);		
		return result;
	}
}
