package com.ppsea.ds.command.item;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;

/**
 * 玩家卖掉道具 
 **/
public class SellItemCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(SellItemCmd.class);
	
	/**
	 * @param ps[0] PlayerItem_id
	 * @param ps[1] 要卖掉的数量
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		ErrorMsg msg = PlayerService.checkPlayerInCity(player);
		if (msg.code != ErrorCode.SUCC) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG,msg);
			return result;
		}
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		int num = 1;
		try{
			num = Integer.parseInt(ps[1]);
		}
		catch(Exception e){
			log.warn("[SellItemCmd]{palyer sell amount parse fail,use default value 1.}");
		}
		if ( playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具查询为NULL");			
			return result;
		}
		//如果有镶嵌,强化,带星装备需要二次确认,这里重定向到i_SeI
		if(ps.length <= 2 && ((playerItem.getAppends()!=null && playerItem.getAppends().size() > 0) || 
			playerItem.getStarLevel() > 0 || playerItem.getImproveLevel() > 0)){	
			result = callOtherCmd("i_SeA",player,playerItem.getId().toString());
		}
		else {
			ErrorMsg ret = ItemService.sell(player, playerItem, num);
			if( ret.code < 0){
				setResult(result,STATUS_FAIL,ret);				
				return result;
			}	
			//跳转到卖出列表页面		
			result = callOtherCmd("i_LS",player,playerItem.getItem().getTypeStr());
			StringBuffer sb = new StringBuffer();
			sb.append("你卖出了").append(num).append("个").append(playerItem.getItem().getName());
			result.setVO(TAG_DESC, sb.toString());
			result.setVO(TAG_PLAYER, player);
			result.setVO(TAG_COPPER, ret.code);
			//NPC
			Npc npc = NpcMG.instance.getNpc(player.getLastNpcId());
			if(npc != null)
			result.setHref(TAG_NPC, npc.getName(), npc.getId().intValue());
			//记录交易日志
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(playerItem.getItemId()).append("|").append(num).append("|");
			result.setExtMsg(stringBuffer);
		}
		return result;
	}
}
