package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
/**
 * 显示玩家可以进行镶嵌的装备列表
 **/
public class ListMasterCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListMasterCmd.class);
	
	/**
	 * 装备要可以进行镶嵌并且已经打好了空
	 * @param null
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<PlayerItem> masters = new LinkedList<PlayerItem>();
		//获取装备列表
		List<PlayerItem> usedLst = player.getUsedArmsList();	
		for(PlayerItem playerItem : usedLst){
			int maxAppend = playerItem.getItem().getMaxAppend();
			if(maxAppend > 0 && 
				((playerItem.getAppends() == null?0:playerItem.getAppends().size()) < playerItem.getCurrHoleAmount().intValue())&&
				playerItem.getItem().getPosition().intValue() != Item.POS_MOUNTS){
				masters.add(playerItem);
			}
		}
		if (masters.size() > 0) {
			result.setVO(TAG_ITEMS, masters);
		}else {
			for(PlayerItem pi : usedLst){
				Item item = pi.getItem();
				if( pi.getCurrHoleAmount()< item.getMaxAppend() &&
					item.getPosition().intValue() != Item.POS_MOUNTS){
					masters.add(pi);
				}
			}
			result.setVO("label_da_item", masters);
		}
		return result;
	}
}
