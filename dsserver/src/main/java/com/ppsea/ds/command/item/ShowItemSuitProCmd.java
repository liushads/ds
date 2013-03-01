/*
 * 
 */
package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemSuitMG;

/**
 * @author Administrator
 * @date 2010-5-17
 */
public class ShowItemSuitProCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		Integer itemSuitId = Integer.valueOf(ps[0]);
		ItemSuit itemSuit = ItemSuitMG.instance.getItemSuit(itemSuitId);
		
		result.setVO(TAG_ITEM_SUIT, itemSuit);
		return result;
	}

}
