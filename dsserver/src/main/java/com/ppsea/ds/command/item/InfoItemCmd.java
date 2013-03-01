package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
/**
 * 查看道具详细信息 
 **/
public class InfoItemCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(InfoItemCmd.class);
	
	/**
	 * 显示道具详细信息
	 * @param ps[0] itemId
	 * @param ps[1] NPC_ID
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int itemId = Integer.parseInt(ps[0]);
		Item item = ItemMG.instance.getItem(itemId);
		if( item == null){
			result.setVO(TAG_DESC, "没有该道具");
			result.setStatus(STATUS_FAIL);
			return result;
		}
		result.setVO(TAG_ITEM, item);
		//标记是	NPC对话，可以出现购买链接
		if(ps.length>1){
			result.setVO(TAG_NPC, ps[1]);
		}
		if(ps.length > 2){
			result.setVO(TAG_NO_BUY, "0");
		}		
		return result;
	}
}
