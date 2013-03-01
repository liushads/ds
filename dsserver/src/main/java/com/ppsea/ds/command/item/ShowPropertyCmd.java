package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;
/**
 * 显示装备的升星和强化属性,只有装备类道具才有升星和强化附加属性
 **/
public class ShowPropertyCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ShowPropertyCmd.class);

	/**
	 * @param ps[0] = playerItemId
	 * @param ps[1] type 0-升星,1-强化
	 * @param ps[2] 玩家ID
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);	
		Player owner = player;
		if(ps.length>1){
			// 只提供缓存中用户的道具详情
			owner = PlayerMG.instance.getPlayerFromCache(Integer.valueOf(ps[1]));
			if(owner == null ){
				result.setStatus(STATUS_FAIL);
				ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_NOT_ONLINE, "玩家不在线");
				result.setVO(TAG_ERR_MSG, errMsg);
				return result;
			}
		}
		
		PlayerItem pi = owner.getAllItems().get(ps[0]);
		if(pi == null){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "不存在该道具"));
			return result;
		}
		
		//是否为装备类物品
		if(pi.getItem().getType().intValue() != Item.ARM_TYPE){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_IS_NOT_ARM,"非装备类型");
			return result;
		}
		
		//属性
		result.setVO(TAG_PLAYER_ITEM, pi);
		result.setVO(TAG_PLAYER_LEVEL, PlayerMG.instance.getPlayerLevel(pi.getItem().getLevel()));
		return result;
	}
}
