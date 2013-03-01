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
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
/**
 * 显示可以卖掉物品列表
 **/
public class ListSellCmd extends BaseCmd {

	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListSellCmd.class);
	
	/**
	 * @param ps[0] 显示类型
	 * @param ps[1] 当前页
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
		if( ps[0].equals(Item.ARM_TYPE_STR)){//装备类
			for(List<PlayerItem> ls:player.getArms().values()){
				if(ls !=null){					
					for(PlayerItem pi:ls){
						if(pi.getIsUse() == 0  && (pi.getBindId() == null || pi.getBindId()==0) && !pi.getInExchange()){
							playerItems.add(pi);
						}
					}
				}
			}
		}
		else{
			Map<String ,PlayerItem> map =  player.getNonArms().get(ps[0]);
			if(map != null){
				for(PlayerItem pi:map.values()){
					//过滤掉价格为0和不可丢弃的
					if(pi.getItem().getPrice() > 0 && pi.getItem().getDropable()){
						playerItems.add(pi);
					}
				}
			}
		}		
		//等级由低到高排序
		ItemService.sortByLevelAsc(playerItems);		
		int pageId = 0;
		try {
			if (ps[1].startsWith("page_")) {
				pageId = Integer.parseInt(ps[1].split("_")[1]);
				pageId -= 1;
			} else {
				pageId = Integer.parseInt(ps[1]);
			}

			if (pageId < 0) {
				pageId = 0;
			}
			int totalPage = Util.totalPage(playerItems.size()) - 1;
			if (pageId > totalPage) {
				pageId = totalPage;
			}
		} catch (Exception e) {
		}
		//分页		
		Util.page(playerItems, pageId, result);
		
		result.setVO("itype", ps[0]);
		return result;
	}
}
