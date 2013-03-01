package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * 打开箱子
 **/
public class OpenBoxCmd extends BaseCmd {
	
	/**
	 * @param ps[0] PlayerItem_id
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
//		PlayerItem playerItem = player.getAllItems().get(ps[0]);
//		if ( playerItem == null ){
//			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"物品已经不存在.");			
//			return result;
//		}
//		
//		//打开箱子
//		ErrorMsg ret = ItemService.openBox(player, playerItem);
//		result.setVO(TAG_ERR_MSG, ret);
		return result;
	}
}
