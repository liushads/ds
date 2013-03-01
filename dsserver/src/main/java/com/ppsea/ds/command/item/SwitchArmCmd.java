package com.ppsea.ds.command.item;

import java.util.List;

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
 * 装备交换 
 **/
public class SwitchArmCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(SwitchArmCmd.class);
	
	/**
	 * @param ps[0] 卸下装备ID
	 * @param ps[1] 装备
	 * */
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem oldPlayerItem = player.getAllItems().get(ps[0]);
		PlayerItem newPlayerItem = player.getAllItems().get(ps[1]);
		//验证
		List<PlayerItem> usedArms = player.getUsedArms(oldPlayerItem.getItem().getPositionStr());
		if(usedArms != null && usedArms.size() > 0){
			if(!usedArms.contains(oldPlayerItem) || oldPlayerItem.getIsUse() == 0 ||
				usedArms.contains(newPlayerItem) || newPlayerItem.getIsUse() == 1){//重复切换装备 
				setResult(result,STATUS_FAIL,ErrorCode.ERR_ITEM_SWITH_DUPLICATE,"装备已经交换成功");
				return result;
			}
		}
//		ItemService.unequipItem(player, oldPlayerItem);//卸下旧装备
//		ErrorMsg ret = ItemService.equipItem(player, newPlayerItem);
		oldPlayerItem.unuse();
		ErrorMsg ret = newPlayerItem.use();
		if( ret.code != ErrorCode.SUCC){			
			setResult(result,STATUS_FAIL,ret);
			return result;
		}		
		//显示装备		
		return callOtherCmd("p_LA",player);
	}
}
