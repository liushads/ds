package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ItemService;

public class ComposeItemSuitCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		result.setVO("label_compose_suit",sort(player));
		return result;
	}

	private List<ItemSuit> sort(Player player){
		
		List<ItemSuit> list = new ArrayList<ItemSuit>();
		PlayerItem pi = null;
		for (ItemSuit is : ResourceCache.instance.getItemSuits().values()) {
			pi = ItemService.findPlayerItem(player, is.getLimitComposeId());
			if (pi != null) {
				list.add(is);
			}
		}
		Collections.sort(list, new Comparator<ItemSuit>() {

			@Override
			public int compare(ItemSuit o1, ItemSuit o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
	}
}
