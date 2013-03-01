package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;

/**
 * 购买参数指定个数的道具
 **/
public class BuyItemCmd extends BaseCmd{
	
	/**
	 * Log
	 **/
	private static final Logger log = Logger.getLogger(BuyItemCmd.class);
	
	/**
	 * 主要实现道具的购买和购买验证功能 
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		ErrorMsg msg = PlayerService.checkPlayerInCity(player);
		if (msg.code != ErrorCode.SUCC) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG,msg);
			return result;
		}
		int itemId = Integer.parseInt(ps[0]);
		int num = Integer.parseInt(ps[1]);
		int buyType = 0;
		try {
			buyType= Integer.parseInt(ps[2]);			
		} catch (Exception e) {
			
		}
		int npcId = player.getLastNpcId();
		Item item = ItemMG.instance.getItem(itemId);
		// 判断该NPC是否有该道具		
		if(	ItemMG.instance.getItem(itemId) == null || 
			!ItemService.npcHasItem(npcId, item)){		
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"不允许买该道具");	
			return result;
		}
		//青铜回血符和青铜凝神符最多玩家背包里面最多只能有3个
//		if(itemId == Constants.AUTO_RE_HP_ID || itemId == Constants.AUTO_RE_MP_ID){
//			PlayerItem foundItem = ItemService.findPlayerItem(player, itemId);
//			int count = num + (foundItem == null?0:foundItem.getAmount());
//			if(count > 3){
//				setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"【"+item.getName()+"】包裹里面最多只能有3个");
//				return result;
//			}
//		}
		
		//购买道具
		ErrorMsg ret = null;
		if (buyType == 1) {
			// 金票购买 
			ret = ItemService.buyInAdvGold(player, item, num);
		} else if (buyType == 2) {
			//金锭购买 
			ret = ItemService.buyInGold(player, item, num);
		} else {
			ret = ItemService.buyInCopper(player, item, num);			
		}
		if(ret.code != ErrorCode.SUCC){		
			if(log.isInfoEnabled())
				log.warn("[BuyItem]{player "+player.getName() + " buy item fail. ErrorCode="+ret+"}");
			setResult(result,STATUS_FAIL,ret);	
			return result;
		}
		//显示
		result = callOtherCmd("i_LB", player, String.valueOf(npcId));
		result.setVO(TAG_ITEM, item);		
		result.setVO(TAG_AMOUNT, num);
		result.setVO("buyType", buyType);
		
		//记录交易日志
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(item.getId()).append("|").append(num).append("|");
		result.setExtMsg(stringBuffer);		
		return result;
	}
}
