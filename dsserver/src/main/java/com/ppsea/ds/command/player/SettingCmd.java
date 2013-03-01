package com.ppsea.ds.command.player;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
/** 
 **/
public class SettingCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SettingCmd.class);

	/**
	 * @param 
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
//		result = callOtherCmd("p_SS", player, ps);
//		result.setName("p_S");
		return result;
	}
	
	
}
