/**
 * 
 */
package com.ppsea.ds.command.fight;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;

public class FightShortcutCmd extends BaseCmd{
	/**
	 * @param ps
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		CommandResult result = new CommandResult(STATUS_SUCC);
		String shortcutType = ps[0];
		String shortcutIndex = ps[1];
		Integer index = Integer.valueOf(shortcutIndex);
		
		//药品列表
		if (shortcutType.equals("yp")) {
			doShortcutYP(player, index, result);
		}
		// 暗器列表
		if (shortcutType.equals("aq")) {
			doShortcutAQ(player, index, result);
		}
		return result;
	}
	/**
	 * 药品栏的设置和使用
	 */
	private void doShortcutYP(Player player, int index, CommandResult result) 
	{
		//设置药品快捷栏
		if (index < 0) {
			List<PlayerItem> all = new LinkedList<PlayerItem>();
			Map<String, PlayerItem> msp = player.getNonArms().get(Item.REMEDIES_TYPE_STR);
			if (msp != null) {
				for (PlayerItem pi : msp.values()) {
					if (pi == null) continue;
					if (pi.getShortcutId() == null) {
						pi.setShortcutId(0);
					}
					if (pi.getShortcutId().intValue() <= 0){
						if (pi.getItem().getId() == 10574 || pi.getItem().getId() == 10575) {
							pi.setShortcutId(0);
							continue;
						}
						all.add(pi);
					} 
				}
			}
			result.setStatus(STATUS_SET_SHORTCUT);
			result.setVO(TAG_OBJS, all);
			result.setText(TAG_TYPE, "yp");
			result.setText(TAG_RETURN, index);
			return;
		} 
		String indexStr = String.valueOf(index);
		//使用药品
		PlayerItem playerItem = player.getAllItems().get(indexStr);
		if (playerItem == null) {
			//清理药品快捷栏			
			Map<Integer, PlayerItem> scs = player.getShortCuts();
			Set<Integer> allnulls = new HashSet<Integer>();
			for (Integer i : scs.keySet()) {
				if (scs.get(i) == null || scs.get(i).getPlayerId().intValue() != player.getId().intValue())
				{
					allnulls.add(i);
				}
			}
			for (Integer d : allnulls) {
				scs.remove(d);
			}
		}
		//使用该药品快捷栏位置的道具
		else {
			if (playerItem.getAmount() > 0)
			{
				//max add 2010-5-7
				ErrorMsg ret = playerItem.use();
				if(ret.code == ErrorCode.SUCC) {
					if (playerItem.getItem().getHp() > 0) {
						Integer[] hpmp = (Integer[])(ret.obj);
						result.setText(TAG_ITEM_ADD_HP, hpmp[0]);
					}
				}
			}
			if (player.getAutoFightMonsterFlag() == 1) {
				player.setFight_note(false);
			}
			//用光了，清除该药品快捷栏
			if (playerItem.getAmount() == 0) {
				Integer scid = playerItem.getShortcutId();
				player.getShortCuts().remove(scid);
				//用完为0的应该在use里面已经被删除了，不需要commit了
				if (player.getAllItems().get(indexStr) != null) {
					playerItem.setShortcutId(0);
					DBService.commit(playerItem);
				}
			}
			result.setVO(TAG_ITEM, playerItem);
		}
		result.setText(TAG_TYPE, "2");
		result.setStatus(STATUS_USE_ITEM);
		//返回战斗场景
		Player lastEnemy = player.getLastEnemy();
		if (lastEnemy == null) {
			result =  player.getLastResult();
		}
		else {
			FightService.checkFightMessage(player, result, lastEnemy);
		}
	}
	/**
	 * 暗器栏的设置
	 */
	private void doShortcutAQ(Player player, int index, CommandResult result) 
	{
		List<PlayerItem> all = new LinkedList<PlayerItem>();
		Map<String, PlayerItem> msp = player.getNonArms().get(Item.TYPE_DART_STR);
		if (msp != null) {
			for (PlayerItem pi : msp.values()) {
				if (pi == null) continue;
				if (pi.getShortcutId() == null) {
					pi.setShortcutId(0);
				}
				if (pi.getShortcutId().intValue() <= 0) all.add(pi);
			}
		}
		result.setStatus(STATUS_SET_SHORTCUT);
		result.setVO(TAG_OBJS, all);
		result.setText(TAG_TYPE, "aq");
		result.setText(TAG_RETURN, index);
		return;
	}
}
