package com.ppsea.ds.command.mission;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ItemService;

/**
 * ClassName:FastMissionCmd 快速进行任务
 * 
 * @author Daniel Hao
 * @version
 * @since Ver 1.0
 * @Date 2009 Feb 16, 2009 2:23:52 PM
 * 
 * @see
 */
public class FastMissionCmd extends BaseCmd {
	/**
	 * ps[0] = facilityId (non-Javadoc)
	 * 
	 * @see com.ppsea.server.command.BaseCmd#done(com.ppsea.server.core.model.Player,
	 *      java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		PlayerItem playerItem = ItemService.findPlayerItem(player,
				ItemMG.instance.getItem(Constants.ITEM_MISSION_GUIDE));

		CommandResult result = new CommandResult();
		ErrorMsg ret = ItemService.releasePlayerItem(player, playerItem, true);
		if (ret != Constants.SUCC) {
			result.setVO(TAG_ERR_MSG, ret);
			result.setStatus(STATUS_FAIL);
			return result;
		}

		// 转向行走
		return callOtherCmd(COMMAND_WALK, player, ps[0]);
	}

}
