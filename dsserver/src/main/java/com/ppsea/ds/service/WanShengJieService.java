/*
 * 
 */
package com.ppsea.ds.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2010-10-25
 */
public class WanShengJieService {

	private static Logger logger = Logger.getLogger("Reward");

	public static final String ACTIVE_NAME_WANSHENGJIE = "wanshengjie";

	public static ErrorMsg exchange(Player player, int type) {
		String activeKey = player.getId().toString() + "_"
				+ ACTIVE_NAME_WANSHENGJIE + "_" + type;
		PlayerActive pa = player.getPlayerActive().get(activeKey);
		PlayerItem pi = ItemService.findPlayerItem(player, 10459);// 斗神糖
		if (pi == null || pi.getAmount() <= 0) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "你没有斗神糖，不能参与兑换");
		}
		int itemId = 0;
		int amount = 1;
		int jhtAmount = 0;
		switch (type) {
		case 1:
			// 强化宝石36
			jhtAmount = 50;
			itemId = 36;
			amount = 1;
			break;
		case 2:
			// 升星宝石37

			if (pa != null && pa.getTime() >= 10) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE,
						"你已经兑换过升星宝石总数达到上限，不能兑换了");
			} else {
				itemId = 37;
				amount = 1;
				jhtAmount = 100;
			}
			break;
		case 3:
			// 混元金手
			if (pa != null && pa.getTime() >= 1) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE,
						"你已经兑换过混元金手，不能重复兑换");
			} else {
				itemId = 10427;
				amount = 1;
				jhtAmount = 400;
			}
			break;
		case 4:
			// 百花项链
			if (pa != null && pa.getTime() >= 1) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE,
						"你已经兑换过百花项链，不能重复兑换");
			} else {
				itemId = 10426;
				amount = 1;
				jhtAmount = 450;
			}
			break;
		case 5:
			// 血魔指10428
			if (pa != null && pa.getTime() >= 1) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE,
						"你已经兑换过血魔指，不能重复兑换");
			} else {
				itemId = 10428;
				amount = 1;
				jhtAmount = 500;
			}
			break;
		default:
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "不能重复参加活动");

		}
		if (pi.getAmount() < jhtAmount) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "你剩余斗神糖不足兑换强化宝石");
		}
		if (pa == null) {
			pa = PlayerMG.instance.createPlayerActive(player,
					ACTIVE_NAME_WANSHENGJIE, type);
			player.getPlayerActive().put(activeKey, pa);
		}
		pa.setTime(pa.getTime() + amount);
		DBService.commit(pa);
		ItemService.releasePlayerItem(player, pi, jhtAmount, true);
		ItemService.addItem(player, itemId, amount, false);
		logger.info(LoggerHelper.ZONE_ID + "|wanshengjie|E|" + type + "|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|" + amount
				+ "|" + itemId);
		return new ErrorMsg(ErrorCode.SUCC, "恭喜你成功兑换到" + amount + "个"
				+ ItemMG.instance.getItem(itemId).getName());
	}

	public static boolean checkMission(Player player, CommandResult result) {
		// player.setInteraction(key, value);
		boolean isFinish = true;
		for (int id : monsters) {
			String key = "monster" + id;
			int ni = player.getInteraction(key);
			if (ni == 0) {
				player.setInteraction(key, 0);
				isFinish = false;
			} else if (ni < 10) {
				isFinish = false;
			}
			result.setVO(key, ni);
		}
		return isFinish;
	}

	public static ErrorMsg finishMission(Player player, CommandResult result) {
		String ckey = player.getId().toString() + "_" + "chunjiemission" + "_"
				+ 1;
		PlayerActive pa = player.getPlayerActive().get(ckey);
		boolean flag = checkMission(player, result);

		if (flag) {
			int num = 0;
			if (pa != null) {
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				num = pa.getTime();
				if (!sf.format(date).equals(sf.format(pa.getJoinDate()))){
					num = 0;
					pa.setJoinDate(date);
				}
				if (num > 10) {
						return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,
						"交任务失败，你今天已经做过10次任务了请明天再来");
				}
				pa.setTime(num + 1);
				DBService.commit(pa);
				DBService.commit(player);
			} else {
				pa = PlayerMG.instance.createPlayerActive(player,
						"chunjiemission", 1);
				pa.setTime(num + 1);
				player.getPlayerActive().put(ckey, pa);
				DBService.commit(pa);
				DBService.commit(player);
			}
			for (int id : monsters) {
				String key = "monster" + id;
				int ni = player.getInteraction(key);
				int tt = ni - 10;
				if (tt < 0) {
					tt = 0;
				}
				player.setInteraction(key, tt);
			}
			ItemService.addItem(player, 10565, 1, false);
			checkMission(player, result);
			logger.info(LoggerHelper.ZONE_ID + "|chunjie|" + player.getUserId()
					+ "|" + player.getId() + "|" + player.getName() + "|"
					+ player.getLevel() + "|1|" + 10565);
			return new ErrorMsg(ErrorCode.SUCC, "交任务成功，系统赠送你1绚烂烟花");
		} else {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "交任务失败，任务未完成");
		}

	}

	// 10459
	private static int[][] itemConfig = { { 36, 10 }, { 37, 5 }, { 31, 5 },
			{ 10312, 10 }, { 10327, 10 }, { 10317, 5 }, { 10322, 5 },
			{ 70, 5 }, { 10459, 15 }, { 10457, 10 }, { 10460, 5 },
			{ 10403, 5 }, { 38, 5 }, { 60, 5 } };
	private static int[] monsters = { 910002, 910003, 910004 };
	private static int[] items = new int[100];
	private static Random rand = new Random();

	public static void initItemConfiguration() {
		int pos = 0;
		for (int i = 0; i < itemConfig.length; i++) {
			int itemId = itemConfig[i][0];
			int size = itemConfig[i][1];
			for (int j = 0; j < size; j++) {
				items[pos] = itemId;
				pos++;
			}
		}
	}

	public static ErrorMsg killMonsterDeadBody(Player player, int monsterId) {
		// 铲子10460
		PlayerItem pi = ItemService.findPlayerItem(player, 10460);// 铲子
		if (pi == null || pi.getAmount() < 1) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "你没有铲子，不能对怪物进行挖尸");
		}
		if (monsterId != 901516 && (monsterId != 901517)
				&& (monsterId != 901518) && (monsterId != 901519)
				&& (monsterId != 901600) && (monsterId != 901601)) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID,
					"非法请求，你只能对万圣节活动怪进行挖尸");
		}
		ItemService.releasePlayerItem(player, pi, 1, true);
		int pos = rand.nextInt(items.length);

		int itemId = 0;
		int amount = 1;
		try {
			itemId = items[pos];
			if (itemId == 10459) {
				amount = 10;
			}
		} catch (Exception e) {
			itemId = 10459;
		}
		ItemService.addItem(player, itemId, amount, false);
		logger.info(LoggerHelper.ZONE_ID + "|wanshengjie|K|" + monsterId + "|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|" + amount
				+ "|" + itemId);
		return new ErrorMsg(ErrorCode.SUCC, "挖尸成功，恭喜你获得" + amount + "个"
				+ ItemMG.instance.getItem(itemId).getName());

	}
}
