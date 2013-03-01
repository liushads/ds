package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;

/**
 * 镶嵌,升星,强化装备需要二次确认
 **/
public class SellAckCmd extends BaseCmd {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SellAckCmd.class);	
	
	/**
	 * 提供页面显示对象(道具)
	 * @param ps[0] playerItem
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if (playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具查询为NULL");			
			return result;
		}
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}
}
