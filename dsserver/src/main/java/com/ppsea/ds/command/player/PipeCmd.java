package com.ppsea.ds.command.player;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.PlayerService;
/** 
 **/
public class PipeCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(PipeCmd.class);

	/**
	 * @param ps[0]
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);		
		if(ps != null && ps.length > 0 ){
			result.setVO(TAG_OBJ, ps[0]);
		}
		return result;
	}
	
	
}
