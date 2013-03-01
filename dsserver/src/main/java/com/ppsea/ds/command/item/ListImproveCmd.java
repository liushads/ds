package com.ppsea.ds.command.item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
/**
 * 显示可以强化的装备列表 
 **/
public class ListImproveCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	private static final Logger log = Logger.getLogger(ListImproveCmd.class);

	/**
	 * @param null
	 **/
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = player.getLastNpcId();
		//验证玩家当前是否在NPC所在的设施中
		Map<Integer, CityFacilityNpc> facilityNpcs = NpcMG.instance.getNpcInFacility(player.getCityFacility().getId());
		Iterator<CityFacilityNpc> itr = facilityNpcs.values().iterator();
		boolean isIn = false;
		while(itr.hasNext()){
			if(itr.next().getNpcId().intValue() == npcId){
				isIn = true;break;
			}
		}		
		if( isIn == false){
			if(log.isInfoEnabled())
				log.warn("[ListBuyCmd]{player " +player.getName() + " not int npc's facility.}");
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"");			
			return result;
		}
		List<PlayerItem> items = ItemService.getImproveItem(player);
		
		int pageId = 0;
		try{
			pageId = Integer.parseInt(ps[0]);
		}
		catch(Exception e){
			pageId = 0;
		}
		Util.page(items, pageId, result);
		return result;
	}
}
