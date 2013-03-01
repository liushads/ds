package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;
/**
 * 道具鉴定
 **/
public class IdentifyCmd extends BaseCmd {

	/**
	 * @param ps
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
//		PlayerItem unknownItem = player.getAllItems().get(ps[0]);
//		
//		/**
//		 * 鉴定
//		 **/
//		ErrorMsg ret = ItemService.identify(player, unknownItem);
//		if( ret.code < 0 || ret.obj == null){
//			result.setVO(TAG_ERR_MSG, ret);
//			result.setStatus(STATUS_FAIL);
//			return result;
//		}
//		
//		//鉴定出来的道具
//		PlayerItem identifiedItem = (PlayerItem)ret.obj;
//		//显示装备
//		result = callOtherCmd("i_I",player,identifiedItem.getIdStr());
//		result.setVO(TAG_DESC, "您的未知道具鉴定结果如下");
		return result;
	}
}
