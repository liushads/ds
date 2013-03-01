package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;
/**
 * 修理装备
 **/
public class RepairCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	private static final Logger log = Logger.getLogger(RepairCmd.class);
	
	/**
	 * @param ps[0] 定制修理类型 < 0 修理所以装备  > 0 修理指定ID的装备
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);	
//		if(ps.length < 2){
//			result.setStatus(STATUS_FAIL);
//			result.setVO(TAG_ERR_MSG,new ErrorMsg(ErrorCode.ERR_SYS,"非法请求"));
//			return result;
//		}
//		ErrorMsg msg = PlayerService.checkPlayerInCity(player);
//		if (msg.code != ErrorCode.SUCC) {
//			result.setStatus(STATUS_FAIL);
//			result.setVO(TAG_ERR_MSG,msg);
//			return result;
//		}
//		boolean useGold = ps[1].equals("1")?true:false;
//		//修理制定装备
//		ErrorMsg ret = Constants.SUCC;
//		if(!ps[0].equals("0")){
//			PlayerItem playerItem = player.getAllItems().get(ps[0]);
//			ret = ItemService.repair(player, playerItem,true,useGold);
//			if( ret.code != ErrorCode.SUCC){
//				result.setVO(TAG_RETURN,ret.code);
//				result.setStatus(STATUS_FAIL);
//				return result;
//			}		
//			result = callOtherCmd("i_LR",player);
//			result.setVO(TAG_DESC, ret.text);
//			if(log.isInfoEnabled())
//			log.info("[RepairCmd]{player "+player.getName()+" repair "+
//			playerItem.getItem().getName()+" complet.}");
//			return result;
//		}
//		
//		//修理所有装备
//		if(ps[0].equals("0")){	
//			ret = ItemService.repairAll(player,useGold);
//			//跳转到装备
//			result =  callOtherCmd("i_LR",player);
//			if(ret.code != ErrorCode.SUCC){
//				result.setVO(TAG_DESC, "您的金钱不足,只修复了部分装备");
//				setResult(result,STATUS_SUCC,ret);
//			}
//			else{
//				result.setVO(TAG_DESC, "已经将全部装备修复了");
//				if(log.isInfoEnabled())
//					log.info("[RepairCmd]{player "+player.getName()+" repair all arms.}");
//			}
//		}		
		return result;
	}
}
