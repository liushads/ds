package com.ppsea.ds.command.item;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.PlayerDyn;
import com.ppsea.ds.data.model.CityFacilityNpc;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.NpcMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;

/**
 * 显示玩家身上可以进行挖孔的装备列表 要挖孔的装备必须当前装备得到身上
 **/
public class ListDigHoleCmd extends BaseCmd {

	private static final Logger log = Logger.getLogger(ListDigHoleCmd.class);

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int npcId = player.getLastNpcId();
		// 验证玩家当前是否在NPC所在的设施中
		Map<Integer, CityFacilityNpc> facilityNpcs = NpcMG.instance
				.getNpcInFacility(player.getCityFacility().getId());
		Iterator<CityFacilityNpc> itr = facilityNpcs.values().iterator();
		boolean isIn = false;
		while (itr.hasNext()) {
			if (itr.next().getNpcId().intValue() == npcId) {
				isIn = true;
				break;
			}
		}
		if (isIn == false) {
			if (log.isInfoEnabled())
				log.warn("[ListBuyCmd]{player " + player.getName()
						+ " not int npc's facility.}");
			setResult(result, STATUS_FAIL, ErrorCode.ERR_SYS, "");
			return result;
		}
		List<PlayerItem> usedLst = player.getUsedArmsList();
		if (usedLst.size() > 0) {
			List<PlayerItem> list = new LinkedList<PlayerItem>();
				for(PlayerItem playerItem : usedLst){
					
					if(playerItem.getAppends()!=null&&
						playerItem.getItem().getPosition().intValue() != Item.POS_MOUNTS){
						if (playerItem.getAppends().size()>0) {
							list.add(playerItem);
						}
					}
				}
				// 返回装备列表
				if (list.size() > 0) {
					result.setVO(TAG_ITEMS, list);
				}
			}
		return result;
	}
}
