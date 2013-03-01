/*
 * 
 */
package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.exception.PpseaException;

/**
 * @author Administrator
 * @date 2010-5-11
 */
public class ShowLifeDetailCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String itemId = ps[0];
		PlayerItemUsing usingItem = player.getUsingItem(itemId);
		if (usingItem != null) {
			result.setVO(TAG_ITEM, usingItem);
		}
		result.setName("i_SLD");
		return result;
	}

}
