package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ErrorCode;

/**
 * 查看物品详细信息 
 **/
public class InfoCmd extends BaseCmd {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(InfoCmd.class);	
	
	/**
	 * 查到玩家道具信息
	 * @param ps[0] 物品ID编号
	 * @param ps[1] 玩家ID编号 
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		Player owner = player;
		if(ps.length>1 && !"0".equals(ps[1])){
			// 只提供缓存中用户的道具详情
			owner = PlayerMG.instance.getPlayerFromCache(Integer.valueOf(ps[1]));
			if(owner == null ){
				result.setStatus(STATUS_FAIL);
				ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_NOT_ONLINE, "玩家不在线");
				result.setVO(TAG_ERR_MSG, errMsg);
				return result;
			}
		}
		PlayerItem playerItem = owner.getAllItems().get(ps[0]);
		if( playerItem == null){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_SYS, "道具不存在");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		if(ps.length > 2)result.setVO(TAG_NO_OP, "0");
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}
}
