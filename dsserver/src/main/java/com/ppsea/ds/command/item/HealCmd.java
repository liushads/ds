package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
/**
 * 使用恢复类药品对玩家的状态和HP等进行恢复 
 **/
public class HealCmd extends BaseCmd {
	
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(HealCmd.class);
	
	/**
	 * @param ps[0] plalyerItem
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
//		ErrorMsg ret = ItemService.useRemedy(player, playerItem);
		ErrorMsg ret = playerItem.use();
		if(ret.code < 0 ){
			setResult(result,STATUS_FAIL,ret);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		
		//列出用户对应类型的物品
		result= callOtherCmd("i_L",player, Item.REMEDIES_TYPE_STR);
		result.setVO("usedPlayerItem", playerItem);
		return result;
	}
}
