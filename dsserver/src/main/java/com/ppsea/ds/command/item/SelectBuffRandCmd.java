package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class SelectBuffRandCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		//ps[1] page num
		if (ps != null && ps.length <= 2) {//分页
			result.setVO("label_player_items", ItemService.getBufferRandItems(player));
			return result;
		}
		Item item = ItemMG.instance.getItem(Constants.ITEM_XILIAN_ID);
		result.setVO("label_item", item.getName());
		result.setVO("label_gold", Constants.ITEM_XILIAN_GOLD);
		result.setVO("label_copper", Constants.ITEM_XILIAN_COPPER);
		String action = "";
		String player_item_id = ps[2];
		if (ps != null && ps.length == 4 ){
			action = ps[3];
		}
		PlayerItem pi = player.getAllItems().get(player_item_id);
		if (pi == null) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_DESC, "道具不存在！");
			return result;
		}
		result.setVO("item_name", pi.getItem().getName());
		result.setVO("label_item_id",player_item_id);
		if ("se".equals(action)) {//查看某个道具
			result.setVO("label_buff_res", pi.getBuffRands());
			result.setVO("label_action", "se");
			result.setStatus("stat_ok");
			return result;
		}
//		-1：没有附加属性可以洗练 -3:金币不足 -4：游戏币不够 -5：没有需要消耗道具
		if ("ok".equals(action)) {//洗练到临时
			int res = ItemService.changBuff(player, player_item_id);
			
			if (res == ErrorCode.SUCC) {
				result.setVO("label_buff_res", pi.getBuffRands());
				result.setVO("label_buff_tmp", pi.getTmpBuffRands());
				result.setStatus("stat_ok");
				result.setVO("label_action", "ok");
				return result;
			}else {
				String desc = "";
				switch (res) {
				case -1:
					desc = "没有附加属性可以洗练";
					break;
				case -3:
					desc = "金不足";
					break;
				case -4:
					desc = "游戏币不足";
					break;
				case -5:
					
					desc = "缺少道具" + item.getName();
					break;
				default:
					break;
				}
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_DESC, desc);
				return result;
			}
			
		}
		
		if ("cm".equals(action)) {//确认洗练，写入db
			ItemService.comitBuffRand(player, pi);
			result.setVO("label_buff_res", pi.getBuffRands());
			result.setStatus("stat_ok");
			result.setVO("label_action", "cm");
			return result;
		}
		
		return result;
	}

}
