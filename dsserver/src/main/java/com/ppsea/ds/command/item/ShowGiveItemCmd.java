/*
 * 
 */
package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;

/**
 * @author Administrator
 * @date 2010-5-18
 */
public class ShowGiveItemCmd extends BaseCmd {
	
	public final static String TYPE_ARM = Item.ARM_TYPE_STR;
	
	public final static String TYPE_UNARM = "unarm";

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		int otherId = Integer.parseInt(ps[0]);
		String playerItemId = ps[1];
		PlayerItem playerItem = player.getAllItems().get(playerItemId);
		Player otherPlayer = PlayerMG.instance.getOnlinePlayer(otherId);
		result.setVO("otherId", otherPlayer.getId());
		if (playerItem == null) {
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "道具不存在"));
			result.setStatus(Command.STATUS_FAIL);
			return result;
		}
//		if (playerItem.getBindId() == 1) {
//			result.setVO("reason", "道具已绑定，请先解除绑定再送人。");
//			result.setName(COMMAND_ITEM_GIVE_SHOW);
//			return result;
//		}
		if (playerItem.getItem().getType() == Integer.parseInt(Item.OTHER_TYPE_STR)) {
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "道具类型错误"));
			result.setStatus(Command.STATUS_FAIL);
			return result;
		}
		if (playerItem.getItem().getType() == Item.ARM_TYPE) {
			result.setVO("itemType", TYPE_ARM);
//			if (playerItem.getAmount() <= 0) {
//				result.setVO("reason", "道具数量不够。");
//				result.setName(COMMAND_ITEM_GIVE);
//				return result;
//			}
		} else {
			result.setVO("itemType", playerItem.getItem().getType()+"");
		}
		
		result.setVO("playerItem", playerItem);
		result.setVO("otherPlayer", otherPlayer);
		//result.setVO("otherId", otherPlayer.getId());
		result.setName(COMMAND_ITEM_GIVE);
		return result;
	}

}
