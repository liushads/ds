/*
 * 
 */
package com.ppsea.ds.command.move;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.MissionService;

/**
 * @author Administrator
 * @date 2010-6-12
 */
public class TransDeleteMissionCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		
		MissionService.deleteMissionByType(player, MissionType.TYPE_REWARD);
		return callOtherCmd("mv_Tr", player, ps);
	}

}
