package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class ListDigHoleThreeCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){//没有道具
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"非法装备操作");
			return result;
		}		
		PlayerItem playerItem2=ItemService.findPlayerItem(player, Constants.ITEM_WAKONG);
		if (playerItem2==null) {
			Item item = ItemMG.instance.getItem(Constants.ITEM_WAKONG);
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"你没有" + item.getName());
			return result;
		}
		if (ps != null && ps.length >= 3 && "ok".equals(ps[2])) {
			int itemid=Integer.valueOf(ps[1]);
			ErrorMsg msg = ItemService.wachu(player, playerItem, playerItem2, itemid);
			result.setVO(TAG_DESC, msg.text);
			setResult(result,STATUS_SUCC,msg);
			return result;
		}else {
			Item item = ItemMG.instance.getItem(Integer.valueOf(ps[1]));
			result.setVO("label_player_item_name", playerItem.getItem().getName());
			result.setVO("label_item_name", item.getName());
			result.setVO("label_0", ps[0]);
			result.setVO("label_1", ps[1]);
			result.setStatus("stat_ok");
		}
		return result;
	}

}
