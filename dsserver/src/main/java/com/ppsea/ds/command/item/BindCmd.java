package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
/**
 * 绑定道具
 **/
public class BindCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BindCmd.class);

	/**
	 * @param ps[0] = playerItemId
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//如果没有道具密码，提示先设置道具密码
		if(player.getPasswd() == null){
//			result.setStatus(STATUS_FAIL);
//			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "请先设置道具密码"));
			
			return callOtherCmd("p_IPP", player, new String[]{"1"});
		}
		
		PlayerItem pi = player.getAllItems().get(ps[0]);
		if(pi == null){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "不存在该道具"));
			return result;
		}
		if (pi.getInExchange()!=null&&pi.getInExchange()) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "该物品正在摆摊不能锁定"));
			return result;
		}
		
		pi.setBindId(player.getId());
		DBService.commit(pi);
		return result;
	}
}
