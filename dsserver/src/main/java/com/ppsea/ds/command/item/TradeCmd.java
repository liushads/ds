package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class TradeCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){//没有道具
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"非法装备操作");
			return result;
		}		
		PlayerItem playerItem2=ItemService.findPlayerItem(player, 10513);
		if (playerItem2==null) {
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"你没有易洗符");
			return result;
		}
		ErrorMsg msg = ItemService.gengai(player, playerItem, playerItem2);
		result.setVO(TAG_DESC, msg.text);
		setResult(result,STATUS_SUCC,msg);
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}

}
