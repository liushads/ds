package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
/**
 * 显示玩家身上可以进行属性改变的装备列表,装备必须装备到身上
 **/
public class VaryPropertyPreCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(VaryPropertyPreCmd.class);

	/**
	 * @param ps [0] playerItem
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//正在使用的装备,并已经升星
//		PlayerItem playerItem = player.getAllItems().get(ps[0]);
//		if (playerItem==null||playerItem.getIsUse()==0) {//没有道具
//			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"非法装备操作");
//			return result;
//		}	
//		result.setVO(TAG_PLAYER_ITEM, playerItem);
//		//遗忘石
//		result.setVO(TAG_ITEM, ItemMG.ITEM_FORGET_STONE);
//		//宝石ID
//		result.setVO(TAG_TIQIU_DIANQIU, ps[1]);
		return result;
	}
}
