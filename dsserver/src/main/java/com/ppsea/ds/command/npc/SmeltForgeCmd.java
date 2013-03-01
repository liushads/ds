/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * @author Administrator
 * @date 2010-11-15
 */
public class SmeltForgeCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String action = ps[0];
		if ("c".equals(action))	 {
			int type = Integer.parseInt(ps[1]);
			List<ItemForge> itemList = ItemService.listComposeItemsByType(type);
			Collections.sort(itemList, new Comparator<ItemForge>() {
                public int compare(ItemForge o1, ItemForge o2) {
					if (o1.getCopper() > o2.getCopper()) {
						return -1;
					}
	                return 1;
                }
				
			});
			result.setVO("itemForgeList", itemList);
			result.setStatus("list");
		} else if ("d".equals(action)) {
			result.setStatus("decompose");
			if (ps.length >= 2) {
				String type = ps[1];
				if ("l".equals(type)) {
					if (ps.length >= 3) {
						//学习分解术
						String key = player.getId().toString() +"_"+PlayerMG.ACTIVE_NAME_FENJIESHU+"_0";
						PlayerActive pa = player.getPlayerActive().get(key);
						if (pa != null) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE,"你已经学习过分解术,不需要重复学习"));
							return result;
						} else {
							//10462书页
							PlayerItem pi = ItemService.findPlayerItem(player, 10462);
							if (pi == null || pi.getAmount() < 10) {
								result.setStatus(STATUS_FAIL);
								result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"你没有书页或者数量不够，不能学习分解术"));
								return result;
							}
						
							if (!player.consumeInCopper(100 * 1000)) {
								throw new NoMoneyException("no money");
							}
							ItemService.releasePlayerItem(player, pi, 10, true);
							pa = PlayerMG.instance.createPlayerActive(player, PlayerMG.ACTIVE_NAME_FENJIESHU, 0);
							player.getPlayerActive().put(key, pa);
							DBService.commit(pa);
							result.setVO("info", "你成功学习了分解术");
							result.setStatus("info");
						}
					} else {
						result.setStatus("fenjieshu_confirm");
					}
				} else if ("i".equals(type)) {
					//分解装备
					int itemId = Integer.parseInt(ps[2]);
					if (ps.length > 3) {
						String key = player.getId().toString() +"_"+PlayerMG.ACTIVE_NAME_FENJIESHU+"_0";
						PlayerActive pa = player.getPlayerActive().get(key);
						if (pa == null) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"你还没学习分解术，不能分解，请先学习分解术"));
							return result;
						}
						Item item = ItemMG.instance.getItem(itemId);
						PlayerItem pi  = ItemService.findUnImporvePlayerItem(player, item);
						if (pi == null || pi.getAmount() <= 0) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"该物品不存在或已绑定或者交易中，不能分解"));
							return result;
						}
						if ((pi.getBindId() != null && pi.getBindId() > 0) || pi.getInExchange()) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"该物品已绑定或者交易中，不可以分解"));
							return result;
						}
						if (!player.consumeInCopper(10 * 1000)) {
							throw new NoMoneyException("no money");
						}
						ErrorMsg msg = ItemService.releasePlayerItem(player, pi, 1, true);
						if (ErrorCode.SUCC  != msg.code) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, msg);
							return result;
						}
						ItemService.addItem(player, 10489, 1, false);//10489神兵之魂
						player.addExp(1000);
						DBService.commit(player);
						result.setVO("info", "恭喜你成功分解得一个神兵之魂,经验加1000");
						result.setStatus("info");
					} else {
						result.setVO("item", ItemMG.instance.getItem(itemId));
						result.setStatus("decompose_confirm");
					}
				} else if ("r".equals(type)) {
					result.setStatus("rule");
				} else {
					result.setStatus("decompose_list");
				}
			}
		}
		
		return result;
	}

}
