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
 * 玩家丢弃物品命令 
 **/
public class DropCmd extends BaseCmd {
	
	private static final Logger log = Logger.getLogger(DropCmd.class);
	
	/**
	 * 丢弃指定的物品 
	 * @param ps[0] playerItem_id
	 * @param ps[1] amount
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"你没有该物品,不可丢弃.");	
			return result;
		}
		//是否可以丢弃
		if(playerItem.item.getDropable().booleanValue() == false){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_CAN_NOT_DROP,"该物品不可丢弃.");	
			return result;
		}
		if(playerItem.getInExchange().booleanValue() == true){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_IN_EXCHANGE,"交易中的物品不能丢弃.");	
			return result;
		}
		if(playerItem.getIsUse().intValue() == 1){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_USING,"使用中的物品不能丢弃.");	
			return result;
		}
		
		//数量是否正确
		int amount = 1;
		try{ amount = Integer.parseInt(ps[1]);}
		catch(Exception e){ amount = 1; }
		if(amount <= 0 || amount > playerItem.getAmount()){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"丢弃的数量不正确.");	
			return result;
		}		
		//销毁道具
		ErrorMsg ret = ItemService.releasePlayerItem(player, playerItem, amount, false);
		
		if(ret.code != ErrorCode.SUCC ){
			setResult(result,STATUS_FAIL,ret);			
			return result;
		} else {
			if (player.getUsingHPFeeder() != null) {
				if (playerItem.getId().intValue() == player.getUsingHPFeeder().getId()) {
					player.setUsingHPFeeder(null);
				}
			}
			if (player.getUsingMPFeeder() != null) {
				if (playerItem.getId().intValue() == player.getUsingMPFeeder().getId()) {
					player.setUsingMPFeeder(null);
				}
			}
			
		}
		//记录
		if(log.isInfoEnabled())
			log.warn("[DropCmd]{player "+player.getName()+" drop item "+
			playerItem.getItem().getName()+"successful.}");
			
		//列出用户对应类型的物品	
		result= callOtherCmd("i_L",player,playerItem.getItem().getTypeStr());
		return result;		
	}
}
