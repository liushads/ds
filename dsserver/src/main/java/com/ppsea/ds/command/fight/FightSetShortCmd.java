/**
 * 
 */
package com.ppsea.ds.command.fight;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.ItemService;


public class FightSetShortCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(FightSetShortCmd.class);
	/**
	 * @param ps
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException 
	{
		CommandResult result = new CommandResult(STATUS_SUCC);
		String shortcutType = ps[0];
		String shortCutIndex = ps[1];
		String itemId = ps[2];
		Integer index = Math.abs(Integer.valueOf(shortCutIndex));
		//设置药品快捷栏
		if (shortcutType.equals("yp")) {
			doSetShortcutYP(player, index, itemId, result);
		}
		//设置绝学快捷栏
//		if (shortcutType.equals("jx")) {
//			doSetShortcutJX(player, index, itemId, result);
//		}
		//设置暗器快捷栏
		if (shortcutType.equals("aq")) {
			doSetShortcutAQ(player, index, itemId, result);
		}
		return result;
	}
	/**
	 * 
	 * @param player
	 * @param index
	 * @param itemId
	 * @param result
	 */
//	private void doSetShortcutJX(Player player, int index, String itemId, CommandResult result)
//	{
//		PlayerSkill oldJx = player.getUsedJx();
//		PlayerSkill newJx = player.getAllSkills().get(Integer.valueOf(itemId));
//		if (newJx == null) {
//			setFailResult(result, new ErrorMsg(ErrorCode.ERR_SYS, "无效的绝学id:" + itemId));
//			return;
//		}
//		if (oldJx != newJx) {
//			if (oldJx != null) {
//				oldJx.setIsUse(false);
//				DBService.commit(oldJx);
//			}
//			newJx.setIsUse(true);
//			player.setUsedJx(newJx);
//			DBService.commit(newJx);
//		}
//		result.setText(TAG_RETURN, String.valueOf(index));
//		result.setText(TAG_TYPE, "3");
//		result.setVO(TAG_ITEM, newJx);		
//		//返回战斗场景
//		Player lastEnemy = player.getLastEnemy();
//		if (lastEnemy == null) {
//			result = player.getLastResult();
//		}
//		FightService.checkFightMessage(player, result, lastEnemy);
//		return;
//	}
	/**
	 * 
	 * @param player
	 * @param index
	 * @param itemId
	 * @param result
	 */
	private void doSetShortcutAQ(Player player, int index, String itemId, CommandResult result)
	{
		PlayerItem newAQ = player.getAllItems().get(itemId);
		if (newAQ == null) {
			setFailResult(result, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有该物品"));
			return;
		}
		PlayerItem oldAQ = player.getCurrAQ();
		if (newAQ != oldAQ) {
			if (oldAQ != null) {
				oldAQ.setShortcutId(0);
				DBService.commit(oldAQ);
			}
			player.setCurrAQ(newAQ);
			newAQ.setShortcutId(ItemService.PLAYER_DART_SHORTCUT);
			DBService.commit(newAQ);
		}
		result.setText(TAG_RETURN, String.valueOf(index));
		result.setText(TAG_TYPE, "3");
		result.setVO(TAG_ITEM, newAQ);		
		//返回战斗场景
		Player lastEnemy = player.getLastEnemy();
		if (lastEnemy == null) {
			result = player.getLastResult();
		}
		FightService.checkFightMessage(player, result, lastEnemy);
		return;
	}
	/**
	 * 
	 * @param player
	 * @param index
	 * @param itemId
	 * @param result
	 */
	private void doSetShortcutYP(Player player, int index, String itemId, CommandResult result)
	{
		PlayerItem pi = player.getAllItems().get(itemId);
		if (pi == null) {
			setFailResult(result, new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有该物品"));
			return;
		}
		Map<Integer, PlayerItem> scs = player.getShortCuts();
		PlayerItem nowPlayerItem = scs.get(index);
		if (nowPlayerItem != null) {
			nowPlayerItem.setShortcutId(0);
			DBService.commit(nowPlayerItem);
		}
		scs.put(index, pi);
		pi.setShortcutId(index);
		DBService.commit(pi);
		
		result.setText(TAG_RETURN, String.valueOf(index));
		result.setText(TAG_TYPE, "1");
		result.setVO(TAG_ITEM, pi);		
		//返回战斗场景
		Player lastEnemy = player.getLastEnemy();
		if (lastEnemy == null) {
			result = player.getLastResult();
		}
		FightService.checkFightMessage(player, result, lastEnemy);
	}

}
