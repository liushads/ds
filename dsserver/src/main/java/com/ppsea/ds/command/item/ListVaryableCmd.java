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
 * 显示玩家身上可以进行属性改变的装备列表,装备必装备到身上
 **/
public class ListVaryableCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListVaryableCmd.class);

	/**
	 * @param ps null
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//装备已经卸下,并已经升星
		List<PlayerItem> usedLst = player.getUsedArmsList();
		if(usedLst.size() > 0){
			List<PlayerItem> list = new LinkedList<PlayerItem>();		
			for(PlayerItem playerItem : usedLst){
				if(playerItem.getStarLevel() > 0 && 
					playerItem.item.getPosition().intValue() != Item.POS_MOUNTS &&
					playerItem.getInExchange().booleanValue() == false){
					list.add(playerItem);
				}
			}			
			//返回装备列表
			if(list.size() > 0){
				result.setVO(TAG_ITEMS, list);
			}
		}
		return result;
	}
}
