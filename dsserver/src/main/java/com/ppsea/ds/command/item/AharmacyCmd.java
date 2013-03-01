package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.ItemUtil;

public class AharmacyCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int itemId = 0;
		try{ itemId = Integer.parseInt(ps[0]);
		}catch(Exception e){ itemId = 0;}
		Item item = ItemMG.instance.getItem(itemId);
		ItemForge itemForge = ItemMG.instance.getItemForge(itemId);
		if(item == null || itemForge == null){
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"没有该类型的药材.");
			return result;
		}
//		if (itemForge.getType() == 5) {
//			boolean flag = ItemUtil.checkPlayerSkillLevel(player, 13, item.getLevel());
//			if (!flag) {
//				setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"没有学习制药技能，或者技能级别不够制造该物品");
//				return result;
//			}
//		}
		ItemUtil.MaterialRequirement material = ItemUtil.extractMaterialRequirement(itemForge);
		result.setVO(TAG_MATERIAL_REQUIRE, material);
		//has
		List<Item> itemLst = material.getItems();
		List<PlayerItem> hasLst = new LinkedList<PlayerItem>();
		for(Item tItem : itemLst){
			PlayerItem pItem = ItemService.findPlayerItem(player, tItem);
			if(pItem == null) continue;
			hasLst.add(pItem);
		}
		if(hasLst.size() > 0)result.setVO(TAG_ITEMS, hasLst);
		result.setVO(TAG_ITEM, item);
		result.setVO(TAG_NPC_ID, ps[1]);
		return result;
	}

}
