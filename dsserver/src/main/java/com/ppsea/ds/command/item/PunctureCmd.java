package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.StringUtil;

/**
 * 装备钻孔
 **/
public class PunctureCmd extends BaseCmd{
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(PunctureCmd.class);
	
	/**
	 * @param ps[0] playerItem
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[1]);
		if (playerItem==null||playerItem.getIsUse()==0) {
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"非法装备操作");
			return result;
		}
		//材料是否足够
		int stone=Integer.valueOf(ps[0]);
		PlayerItem stoneMaterial = ItemService.findPlayerItem(player, stone);
		List<PlayerItem> playerItems = new ArrayList<PlayerItem>(2);
		int[] amounts = new int[2];
		ItemUtil.MaterialRequirement requireMaterial = ItemUtil.checkMaterialRequirement(stoneMaterial, 
				null, 1, playerItems, amounts,ItemMG.ITEM_HOLE_JEWEL);
				
		//材料是否齐全
		if(requireMaterial != null){
			result.setVO(TAG_MATERIAL_REQUIRE, requireMaterial);
			return result;
		}	
		
		
		//提示
		int ack = 0;
		ItemUtil.RedirectResult redirectResult = null;
		try{
			ack = Integer.parseInt(ps[2]);
		}catch(Exception e){}
		if(playerItem.getIsExchange().booleanValue() == true && 
				ack == 0 && (redirectResult = ItemUtil.checkBindRedirect(playerItems)) != null){
			redirectResult.setCtnueCmd("i_P");
			redirectResult.setParam(StringUtil.splitSlash(ps)+"1");
			result.setVO(TAG_REDIRECT, redirectResult);	
			result.setName("i_r");
			result.setVO(TAG_DESC, "【"+redirectResult.getItem().getName()+"】不可交易,打孔以后"+
					playerItem.item.getName()+"也将变成不可交易.是否继续打孔?");
			return result;
		}
		
		ErrorMsg ret = ItemService.puncture(player, playerItem,playerItems,amounts);
		if(ret.code != ErrorCode.SUCC){
			result.setVO(TAG_DESC, ret.text);
			setResult(result,STATUS_SUCC,ret);
			return result;
		}
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}
}
