package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class ListDigHoleTwoCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		// TODO Auto-generated method stub
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem=player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具错误");
			return result;
		}
		result.setVO(TAG_NUM, playerItem.getAppends().size());
		result.setVO("label_all", playerItem.getCurrHoleAmount());
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		
		return result;
	}

}
