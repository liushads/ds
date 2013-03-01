package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.ItemUtil;
/**
 * 显示可以镶嵌到装备上的道具列表 
 **/
public class ListSlaverCmd extends BaseCmd {
	
	/**
	 * Log
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListSlaverCmd.class);
	
	/**
	 * @param ps[0] PlayerItem.id
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具错误");
			return result;
		}
		//筛选宝石是否可以镶嵌到该装备
		Map<String, PlayerItem> gems = player.getNonArms().get(Item.GEM_TYPE_STR);
		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
		if(gems != null && gems.size() > 0){
			for(PlayerItem pi:gems.values()){
				if(pi != null && ItemUtil.isMergeable(playerItem.item, pi.item) && pi.getItem().getEmbedable()){
					playerItems.add(pi);
				}
			}
		}				
		if(playerItems.size() > 0) result.setVO(TAG_ITEMS, playerItems);
		result.setVO(TAG_PLAYER_ITEM, playerItem);
		return result;
	}
}
