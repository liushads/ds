package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class ListVaryableTwoCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//正在使用的装备,并已经升星
//		PlayerItem playerItem = player.getAllItems().get(ps[0]);
//		//筛选宝石是否可以更改该装备特性
//		PlayerItem stoneMaterial = ItemService.findPlayerItem(player, ItemMG.ITEM_FORGET_STONE );
//		PlayerItem jewelMaterial =  ItemService.findPlayerItem(player, 10511);
//		Map<String, PlayerItem> gems = player.getNonArms().get(Item.GEM_TYPE_STR);
//		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
//		if (stoneMaterial!=null) {
//			playerItems.add(stoneMaterial);
//		}
//		if (jewelMaterial !=null) {
//			playerItems.add(jewelMaterial);
//		}
//		if(playerItems.size() > 0){ 
//			result.setVO(TAG_ITEMS, playerItems);
//		}
//		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}

}
