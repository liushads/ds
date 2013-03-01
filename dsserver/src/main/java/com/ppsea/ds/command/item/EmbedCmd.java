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
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.StringUtil;
/**
 * 装备镶嵌 
 **/
public class EmbedCmd extends BaseCmd {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(EmbedCmd.class);
	
	/**
	 * 装备上进行宝石镶嵌
	 * @param ps[0] 打孔的装备
	 * @param ps[1] 宝石
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem master = player.getAllItems().get(ps[0]);
		PlayerItem slaver = player.getAllItems().get(ps[1]);	
		if(master == null || slaver == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"宝石或者道具为NULL");
			return result;
		}
		
		//提示
		int ack = 0;
		ItemUtil.RedirectResult redirectResult = null;
		try{
			ack = Integer.parseInt(ps[1]);
		}catch(Exception e){}
		if (master.getIsUse() != 1) {
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,master.getItem().getName()+"未在使用中，不可镶嵌");
			return result;
		}
		if(master.getIsExchange().booleanValue() == true && 
				ack == 0 && (redirectResult = ItemUtil.checkBindRedirect(slaver.item)) != null){
			redirectResult.setCtnueCmd("i_E");
			redirectResult.setParam(StringUtil.splitSlash(ps)+"/1");
			result.setVO(TAG_REDIRECT, redirectResult);	
			result.setName("i_r");
			result.setVO(TAG_DESC, "【"+redirectResult.getItem().getName()+"】不可交易,镶嵌以后"+
					master.item.getName()+"也将变成不可交易.是否继续镶嵌?");
			return result;
		}
		
		ErrorMsg ret = ItemService.embedItem(player, master, slaver);
		if( ret.code != ErrorCode.SUCC){
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		result.setVO(TAG_PLAYER_ITEM, master);
		return result;
	}
}
