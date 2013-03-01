package com.ppsea.ds.command.item;

import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
/**
 * 卸下装备,并改变用户属性 
 **/
public class UnuseArmCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(UnuseArmCmd.class);
	
	/**
	 * @param ps[0] 要卸下装备ID
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if ("all".equals(ps[0])) {
			List<PlayerItem> useItems = player.getUsedArmsList();
			if (useItems != null && useItems.size() > 0) {
				for (PlayerItem pi : useItems) {
					pi.unuse();
				}
			}
			return callOtherCmd("p_LA",player);
		}
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){//没有该类装备
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"您没有该装备");
			return result;
		}
		//卸下
//		ItemService.unequipItem(player, playerItem);	
		playerItem.unuse();
		//跳转到显示装备		
		return callOtherCmd("p_LA",player);
	}
}
