package com.ppsea.ds.command.player;

import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.DialogAction;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;

/**
 * 与NPC交互，如送花、打听消息等
 *
 * @author   Daniel.Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2008	Dec 20, 2008		8:59:48 PM
 *
 * @see 	 
 */
public class NpcInteractionCmd extends BaseCmd {

	/**
	 * (non-Javadoc)
	 */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {

		CommandResult result = new CommandResult();
		
		Integer npcId = Integer.valueOf(ps[1]);
		String action = ps[0];
		Npc npc = NpcMG.instance.getNpcInFacility(player.getCityFacility().getId()).get(npcId).getNpc();
		
		result.setText(TAG_CUSTOM, npcId);
		result.setVO(TAG_NPC, npc);
		
		// 返回值
		DialogAction nda = npc.execute(player, action);
		
		// 重定向，如任务、打怪等
		if(nda != null && nda.getRedirect() != null && !nda.getRedirect().equals("")){
			result = callOtherCmd(nda.getRedirect(), player, String.valueOf(npc.getId()));
		}else		
			result.setVO(TAG_DIALOG, nda);
		
		return result;
	}

}
