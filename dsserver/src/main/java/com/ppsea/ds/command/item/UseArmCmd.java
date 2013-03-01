package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
/**
 * 玩家穿上装备
 **/
public class UseArmCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(UseArmCmd.class);
	
	/**
	 * @param ps[0] 装备ID
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){			
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"您没有该道具");
			return result;
		}
		//级别验证ItemService方法也进行了验证,这里主要是为了给玩家提示跟清晰
		if(playerItem.getItem().getLevel().intValue() > player.getLevel().intValue()){
			result.setVO(TAG_DESC, "您的等级不够,暂时不能使用该道具.");
			setResult(result,STATUS_FAIL,ErrorCode.ERR_PLAYER_LEVEL,"您的等级不够,暂时不能使用该道具.");
			return result;
		}
//		ErrorMsg ret = ItemService.equipItem(player, playerItem);
		ErrorMsg ret = playerItem.use();
		//如果该位置装满了道具,列出位置上的所有道具,让用户选择替换
		if( ret.code == ErrorCode.ERR_EQUIP_FULL){
			result.setVO(TAG_ITEMS, player.getUsedArms().get(playerItem.getItem().getPositionStr()));
			result.setVO(TAG_PLAYER_ITEM, playerItem);
			return result;
		}
		//装备失败
		if( ret.code != ErrorCode.SUCC){		
			setResult(result,STATUS_FAIL,ret);
			return result;
		}		
		//显示装备		
		return callOtherCmd("p_LA",player);
	}
}
