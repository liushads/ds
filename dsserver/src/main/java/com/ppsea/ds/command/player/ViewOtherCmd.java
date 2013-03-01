package com.ppsea.ds.command.player;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
/**
 * 查看自己的状态 
 **/
public class ViewOtherCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ViewOtherCmd.class);

	/**
	 * @param ps 
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);		
		int otherId = Integer.valueOf(ps[0]);
		
		Player other = PlayerMG.instance.getPlayerWithArm(otherId);
		result.setVO(TAG_OTHER, other);	
		result.setVO(TAG_OBJS, other.getUsedArms());
		return result;
	}
}
