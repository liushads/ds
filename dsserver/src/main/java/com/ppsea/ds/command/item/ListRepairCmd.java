package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
/**
 * 显示需要修理的装备列表和要花费的铜币
 **/
public class ListRepairCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListRepairCmd.class);
	
	/**
	 * @param ps[0] 当前页
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
//		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
//		List<Integer> money = new LinkedList<Integer>();
//		int sum = 0;
//		for(List<PlayerItem> pis:player.getArms().values()){
//			if(pis != null){
//				for(PlayerItem pi:pis){
//					if(pi.getItem().getEndure() >0 && 
//						pi.getItem().getEndure() > pi.getCurrEndure()){
//						if (pi.getItem().getId()!=10650) {
//							playerItems.add(pi);
//						}
//						int n = ItemService.repairMoney(pi);
//						sum += n;
//						money.add(n);
//					}
//				}
//			}
//		}
//		if(sum <= 0){
//			return result;
//		}		
//		int pageId = 0;
//		try{
//			pageId = Integer.parseInt(ps[0]);
//		}
//		catch (Exception e) {
//		}
//		//分页		
//		Util.page(playerItems, pageId, result);
//		result.setVO(TAG_OBJS, money);
//		result.setVO(TAG_COPPER, sum);
		return result;
	}
}
