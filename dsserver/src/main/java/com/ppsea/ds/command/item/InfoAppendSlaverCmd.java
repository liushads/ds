package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
/**
 * 装备上面镶嵌的宝石描述等详细信息 
 **/
public class InfoAppendSlaverCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(InfoAppendSlaverCmd.class);
	
	/**
	 * 显示宝石详细信息
	 * @param ps[0] itemId
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int itemId = Integer.parseInt(ps[0]);
		Item item = ItemMG.instance.getItem(itemId);
		if( item == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"宝石不存在");
			return result;
		}
		result.setVO(TAG_ITEM, item);
		return result;
	}
}
