/*
 * 
 */
package com.ppsea.ds.command.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.util.Util;

/**
 * @author Administrator
 * @date 2010-9-2
 */
public class ViewSellItemCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		List<PlayerItem> playerItemList = new ArrayList<PlayerItem>();
		int pageId = Integer.parseInt(ps[0]);
		Map<Integer, PlayerItem> allExItem = player.getExchangeItems();
		for (Map.Entry<Integer, PlayerItem> entry : allExItem.entrySet()) {
			PlayerItem pi = entry.getValue();
			if (pi.getInExchange()) {
				playerItemList.add(pi);
			}
		}
		
		Util.page(playerItemList, pageId, result);
		return result;
	}

}
