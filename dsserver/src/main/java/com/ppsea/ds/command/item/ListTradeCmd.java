package com.ppsea.ds.command.item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;

public class ListTradeCmd extends BaseCmd {
	private static final Logger log = Logger.getLogger(ListTradeCmd.class);
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<PlayerItem> usedLst = player.getUsedArmsList();
		if (usedLst.size() > 0) {
			List<PlayerItem> list = new LinkedList<PlayerItem>();
				for(PlayerItem playerItem : usedLst){
					if(playerItem.getIsExchange()==false&&playerItem.getItem().getIsExchange()==true&&
						playerItem.getItem().getPosition().intValue() != Item.POS_MOUNTS){
							list.add(playerItem);
					}
				}
				
				// 返回装备列表
				if (list.size() > 0) {
					result.setVO(TAG_ITEMS, list);
				}
			}
		return result;
	}

}
