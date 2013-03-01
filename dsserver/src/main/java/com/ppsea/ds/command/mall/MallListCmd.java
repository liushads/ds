package com.ppsea.ds.command.mall;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

/**
 * 商城道具
 * */
public class MallListCmd extends BaseCmd {	
	/**
	 * @param ps ps[0] = item_id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//列出商城商品		
		return result;
	}
}
