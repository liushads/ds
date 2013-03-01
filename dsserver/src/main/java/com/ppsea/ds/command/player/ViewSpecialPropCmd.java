/*
 * 
 */
package com.ppsea.ds.command.player;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;

/**
 * @author Administrator
 * @date 2010-6-12
 */
public class ViewSpecialPropCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		
		String action = ps[0];
		CommandResult result = new CommandResult();
		
		if ("effect".equals(action)) {
			//特性效果
			result.setStatus("effect");
		} else if ("res".equals(action)) {
			//特性抗性
			result.setStatus("res");
		} else if ("info".equals(action)) {
			result.setStatus("info");
		}
		
		return result;
	}

}
