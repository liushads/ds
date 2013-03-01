package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
/**
 * 对装备进行解除绑定 
 **/
public class UnBindCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(UnBindCmd.class);
	
	/**
	 * @param ps[0] = playeritem_id, ps[1]=passwd
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem pi = player.getAllItems().get(ps[0]);
		if(pi == null){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "不存在该道具"));
			return result;
		}
		
		if(!player.getPasswd().equals(ps[1])){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PASSWD, "道具密码错误"));
			return result;
		}
		pi.setBindId(0);
		DBService.commit(pi);
		return result;
	}
}
