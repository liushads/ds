package com.ppsea.ds.command.player;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.SectMG;
import com.ppsea.ds.service.PlayerService;
/**
 * 查看自己的状态 
 **/
public class ViewSelfCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ViewSelfCmd.class);

	/**
	 * @param ps 
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);		
		result.setVO(TAG_PLAYER, player);
		Sect sect = SectMG.instance.getSect(player.getSectId());
		result.setVO("label_sect_name", sect.getName());
		PlayerService.reloadPlayerDyn(player);
		return result;
	}
}
