package com.ppsea.ds.command.item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.NpcItem;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.Util;
/**
 * 显示NPC可以卖的道具列表 
 **/
public class ListBuyCmd extends BaseCmd {

	/**
	 * 显示可以购买道具列表
	 * @param ps[0] npcId
	 * @param ps[1] pageId
	 **/
	@SuppressWarnings("unchecked")
    protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = 0;
		try{ npcId = Integer.parseInt(ps[0]);
		}catch(Exception e){ npcId = 0; }
		int pageId = 0;
		try{pageId = Integer.parseInt(ps[1]);			
		}catch(Exception e){ pageId = 0; }
		//验证玩家当前是否在NPC所在的设施中
		if(!ItemUtil.bothInFacility(player, npcId)){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_SYS,"没有在NPC位置.");
			return result;
		}
		List<Item> itemLst = ItemMG.instance.getNpcItems(npcId,NpcItem.NPC_ITEM_TYPE_BUY);
		if(itemLst != null){
			Collections.sort(itemLst, new Comparator() {
	            public int compare(Object o1, Object o2) {
					Item i1 = (Item)o1;
					Item i2 = (Item)o2;
					if (i1.getPrice() - i2.getPrice() > 0) {
						return -1;
					} else if (i1.getGold() - i2.getGold() > 0) {
						return -1;
					}else {
						return 0;					
					}
	            }
				
			});
		}	
		Util.page(itemLst, pageId, result);
		result.setVO(TAG_NPC_ID, npcId);
		return result;
	}
}
