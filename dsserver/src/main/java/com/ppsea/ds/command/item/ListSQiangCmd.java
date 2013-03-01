package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.ItemVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;

public class ListSQiangCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result=new CommandResult(STATUS_SUCC);
		PlayerItem playerItem = player.getAllItems().get(ps[0]);
		if(playerItem == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"道具错误");
			return result;
		}
		//显示强化道具
		int next_improve_level = ItemService.getNextImproveLevel(playerItem);
		if (next_improve_level > 0) {
			ItemImprove improve = ResourceCache.instance.getImproves().get(next_improve_level);
			List<ItemVo> list = improve.getNeedItemVos();
			ItemVo tmp = null;
			if (list != null && list.size() > 0) {
				List<ItemVo> needItems = new ArrayList<ItemVo>();
				for (ItemVo iv : list) {
					tmp = ItemService.getNewItemVo(player, iv);
					if (tmp != null) {
						needItems.add(tmp);
					}
				}
				result.setVO("label_need_items", needItems);
			}
			
			list = improve.getSpecItemVos();
			if (list != null && list.size() > 0) {
				List<ItemVo> specItems = new ArrayList<ItemVo>();
				for (ItemVo iv : list) {
					tmp = ItemService.getNewItemVo(player, iv);
					if (tmp != null) {
						specItems.add(tmp);
					}
				}
				result.setVO("label_spec_items", specItems);
			}
			
			result.setVO("label_improve", improve);
			result.setVO(TAG_PLAYER_ITEM, playerItem);
		}
		result.setVO("label_auto", ItemService.isAutImprove(player));
		return result;
	}

}
