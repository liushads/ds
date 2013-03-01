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
 * 洗掉装备上面的宝石
 **/
public class ExcavateCmd extends BaseCmd {

	/**
	 * @param ps[0] playerItem
	 * @param ps[1] appendId
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
//		PlayerItem playerItem = player.getAllItems().get(ps[0]);
//		if(playerItem == null){
//			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"你没有该装备.");
//			return result;
//		}
//		int appendId = 0;
//		try{ appendId = Integer.parseInt(ps[1]);
//		}catch(Exception e){ appendId = 0; }
//		//洗掉宝石
//		ErrorMsg ret = ItemService.excavateAppend(player, playerItem, appendId);
//		result = callOtherCmd("i_EA",player,ps[0]);		
//		result.setVO(TAG_DESC, ret.text);
		return result; 
	}
}
