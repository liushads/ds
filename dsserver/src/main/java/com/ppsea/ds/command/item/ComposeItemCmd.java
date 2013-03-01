package com.ppsea.ds.command.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Compose;
import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.ComposeVo;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ItemService;

public class ComposeItemCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String action = null;
		int id = Integer.parseInt(ps[0]);
		ComposeItem ci = ResourceCache.instance.getComposeItem().get(id);
		if (ci == null) {
			result.setVO(TAG_DESC, "没有需要合成的道具");
			result.setStatus(STATUS_FAIL);
			return result;
		}
		int item_id = ci.getItemId();
		if (ps != null && ps.length > 1) {
			action = ps[1];
		}
		if (action != null) {
			result.setStatus("stat_desc");
			if ("desc".equals(action)) {
				//查看合成所需要材料
				result.setVO("label_cv", ItemService.getPlayerComposeVo(player, id));
				result.setVO("label_suit_id", id);
				return result;
			}
			if ("ok".equals(action)) {
				//确认合成
				ComposeVo cVo = ItemService.getPlayerComposeVo(player, id);
				ComposeVo tmp = checkVo(cVo,player);
				if (tmp != null) {
					result.setVO("label_less", tmp);
					return result;
				}else {
					//合成
					String desc = "";
					if(compose(cVo, player)){
						ErrorMsg msg = ItemService.addItem(player, item_id, 1,false);
						PlayerItem pi = (PlayerItem)msg.obj;
						ChatService.sayXiaoQ("恭喜"+player.getName()+"在铁匠铺合成了"+ pi.getItem().getName());
						desc = "恭喜您成功合成了" + pi.getItem().getName();
						ChatService.sendMessageSystem(player.getId(), desc, false);
					}
					if (desc == null || desc.length() == 0) {
						desc = "合成失败";
					}
					result.setVO("label_desc", desc);
					
				}
			}
		}else {
			result.setVO("label_compose",sort(player,id));
		}
		return result;
	}

	private List<ComposeItem> sort(Player player,int item_suit_id){
		
		List<ComposeItem> list = new ArrayList<ComposeItem>();
		PlayerItem pi = null;
		Item item = null;
		ItemSuit is = ResourceCache.instance.getItemSuits().get(item_suit_id);;
		for (ComposeItem ci : ResourceCache.instance.getComposeItem().values()) {
			item = ItemMG.instance.getItem(ci.getItemId());
			if (item == null) {
				continue;
			}
			if (is == null) {
				continue;
			}
			if (item.getItemSuitId().intValue() != is.getId().intValue()) {
				continue;
			}
			pi = ItemService.findPlayerItem(player, is.getLimitComposeId());
			if (pi != null) {
				list.add(ci);
			}
		}
		Collections.sort(list, new Comparator<ComposeItem>() {

			@Override
			public int compare(ComposeItem o1, ComposeItem o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
	}
	
	/**
	 * 根据合成材料判断玩家材料是否够用
	 * @param cVo
	 * @return
	 */
	private ComposeVo checkVo(ComposeVo cVo,Player player){
		ComposeVo cV = null;
		Compose tmp = null;
		List<Compose> tmpList = null;
		List<Compose> list = cVo.getCompose();
		for (Compose c : list) {
			if (c.getAmount() < c.getNeedNum()) {
				tmp = new Compose();
				tmp.setAmount(c.getNeedNum() - c.getAmount());
				tmp.setName(c.getName());
				tmp.setItemId(c.getItemId());
				if (tmpList == null) {
					tmpList = new ArrayList<Compose>();
				}
				tmpList.add(tmp);
			}
		}
		if (tmpList != null) {
			cV = new ComposeVo();
			cV.setCompose(tmpList);
		}
		if (cVo.getGold() > 0 && cVo.getGold() > player.getAdvGold()) {
			if (cV == null) {
				cV = new ComposeVo();
			}
			cV.setGold(cVo.getGold() - player.getAdvGold());
		}else if (cVo.getCopper() > 0 && cVo.getCopper() > player.getCopper()){
			if (cV == null) {
				cV = new ComposeVo();
			}
			cV.setCopper((int)(cVo.getCopper() - player.getCopper()));
		}
		return cV;
	}
	
	/**
	 * 合成装备
	 * @param cVo
	 * @param player
	 */
	private boolean compose(ComposeVo cVo,Player player){
		List<Compose> composes = cVo.getCompose();
		Item item = null;
		PlayerItem pi = null;
		List<PlayerItem> pItems = null;
		for (Compose c : composes) {
			item = ItemMG.instance.getItem(c.getItemId());
			if (item != null) {
				if (item.getType() == Item.ARM_TYPE) {
					pItems = ItemService.getPlayerItemByItemId(player, c.getItemId());
					for (PlayerItem pii : pItems) {
						ItemService.releasePlayerItem(player, pii, true);
					}
				}else {
					pi = ItemService.findPlayerItem(player, c.getItemId());
					ItemService.releasePlayerItem(player, pi, c.getNeedNum(), true);
				}
			}else {
				return false;
			}
		}
		if (cVo.getGold() > 0) {
			player.consumeInAdvGlod(cVo.getGold());
		}else {
			player.consumeInCopper(cVo.getCopper());
		}
		return true;
	}
}
