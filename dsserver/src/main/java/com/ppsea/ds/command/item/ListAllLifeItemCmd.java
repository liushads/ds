/*
 * 
 */
package com.ppsea.ds.command.item;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.util.Util;

/**
 * @author Administrator
 * @date 2010-5-11
 */
public class ListAllLifeItemCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player,
	 *      java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		int pageId = 0;
		try {
			pageId = Integer.parseInt(ps[0]);
		} catch (Exception e) {
		}
		List<PlayerItemUsing> usingList = player.getUsingItemList();
//		List<Item> usingItemList = new ArrayList<Item>();
		CommandResult result = new CommandResult(STATUS_SUCC);
		//result.setVO(TAG_ITEMS, usingList);
		result.setName("p_LL");
		result.setVO(TAG_PLAYER, player);
		
		// 分页
		Util.page(usingList, pageId, result);
		return result;
	}

}
