package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
/**
 * 空命令 
 **/
public class PipeCmd extends BaseCmd {
	
	/**
	 * @param ps null 
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		Npc lastNpc = NpcMG.instance.getNpc(player.getLastNpcId());
		if(lastNpc != null)result.setVO(TAG_NPC, lastNpc);
		return result;
	}

}
