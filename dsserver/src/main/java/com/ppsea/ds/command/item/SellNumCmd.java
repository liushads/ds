package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.ItemUtil;

/**
 * 玩家输入要卖掉道具个数
 **/
public class SellNumCmd extends BaseCmd {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SellNumCmd.class);	
	
	/**
	 * 提供页面显示对象(道具)
	 * @param ps[0] playerItem
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem pi = player.getAllItems().get(ps[0]);
		if(pi == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具查询为NULL");
		}
		if(!ItemUtil.sellCheck(player, pi)){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"该物品不能交易.");
			return result;
		}
		result.setVO(TAG_PLAYER_ITEM,  pi);
		return result;
	}
}
