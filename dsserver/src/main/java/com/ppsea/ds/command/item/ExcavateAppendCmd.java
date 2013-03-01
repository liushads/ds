package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
/**
 * 列出装备上面镶嵌宝石
 **/
public class ExcavateAppendCmd extends BaseCmd {

	/**
	 * @param ps[0] playerItem
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"你没有该装备.");
			return result;
		}
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result; 
	}
}
