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
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
/**
 * 显示可以进行打孔的装备列表
 **/
public class ListPunctureCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListPunctureCmd.class);

	/**
	 * 显示可以打孔装备列表
	 * @param ps[0] pageId
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<PlayerItem> items = new LinkedList<PlayerItem>();
		//查找可以进行打孔的装备
		List<PlayerItem> usedLst = player.getUsedArmsList();		
		for(PlayerItem pi : usedLst){
			Item item = pi.getItem();
			if( pi.getCurrHoleAmount()< item.getMaxAppend() &&
				item.getPosition().intValue() != Item.POS_MOUNTS){
				items.add(pi);
			}
		}	
		int pageId = 0;
		try{
			pageId = Integer.parseInt(ps[0]);
		}
		catch(Exception e){
			pageId = 0;
		}
		Util.page(items, pageId, result);
		//验证是否有金刚钻
		if(!ItemService.hasItem(player, ItemMG.ITEM_EMERY)){
			result.setVO(TAG_DESC, "你当前物品栏中可能没有精钢钻");
		}			
		return result;
	}
}
