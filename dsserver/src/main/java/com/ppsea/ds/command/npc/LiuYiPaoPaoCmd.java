package com.ppsea.ds.command.npc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class LiuYiPaoPaoCmd extends BaseCmd {
	Map<Integer, Item> itemMapMin = new HashMap<Integer, Item>();
	Map<Integer, Item> itemMapMax = new HashMap<Integer, Item>();

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		// 初始化不同级别人物获取装备
		itemMapMin = getMapMin(itemMapMin);
		itemMapMax = getMapMax(itemMapMax);
		boolean flag = checkActiveTime();
		if (!flag) {
			this.setFailResult(result, new ErrorMsg(
					ErrorCode.ERR_ACTIVE_INVALID, "活动时间无效，有效时间为：10月1日-10月15日"));
			return result;
		}
		//检查玩家负重
		/*if (player.getDyn().getMaxRoom() <= player.getRoom()) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(
					ErrorCode.ERR_ACTIVE_INVALID, " 你负重已满，不能取出物品"));
			return result;
		}*/
		if (ps == null || ps.length == 0) {
			result.setStatus("main");
			return result;
		}
		String action = ps[0];
		if ("d".equals(action) || "suiji".equals(action)) {
			String number = null;
			if ("suiji".equals(action)) {
				number = "" + (1 + new Random().nextInt(12));
			} else {
				number = ps[1];
			}
			result.setVO("number", number);
			getPlayerItem(result, player);
			giveAward(result, player);
		} else if ("liuyi".equals(action)) {
			result.setStatus("main");
		}else {
			this.setFailResult(result, new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "地址解析错误"));
		}
		return result;
	}

	/**
	 * 生成奖品并扣钱
	 * 
	 * @param result
	 * @param player
	 * @param itemId
	 * @return
	 */
	private void giveAward(CommandResult result, Player player) {
		String key = player.getId().toString() + "_" + "liuyipaopao" + "_" + 0;
		PlayerActive pa = player.getPlayerActive().get(key);
		boolean goldEn = false;
		goldEn = checkPlayer(player, pa , key);
		deductMoney(result, player, goldEn);
	}

	private void deductMoney(CommandResult result, Player player, Boolean bool) {
		int probability = new Random().nextInt(10000);

		if (bool) {
			if (player.consumeInAdvGlod(30)) {
				if (player.getLevel() <= 50 && probability >= 8290
						&& probability <= 9540) {
					getExpAward(result, player, 5000);
				} else if (player.getLevel() > 50 && probability >= 3984
						&& probability <= 6984) {
					getExpAward(result, player, 10000);
				} else if (player.getLevel() <= 50) {
					getItemAward(result, player, itemsMin[probability]);
				} else {
					getItemAward(result, player, itemsMax[probability]);
				}
			} else {
				this.setFailResult(result, new ErrorMsg(
						ErrorCode.ERR_ACTIVE_DONE, "没有足够的金票不能抽取，请进行充值"));
			}
		} else {
			if (player.getLevel() <= 50 && probability >= 8290
					&& probability <= 9540) {
				getExpAward(result, player, 5000);
			} else if (player.getLevel() > 50 && probability >= 3984
					&& probability <= 6984) {
				getExpAward(result, player, 10000);
			} else if (player.getLevel() <= 50) {
				getItemAward(result, player, itemsMin[probability]);
			} else {
				getItemAward(result, player, itemsMax[probability]);
			}
		}
	}


	/**
	 * 经验奖励
	 * @param result
	 * @param player
	 * @param EXP
	 */
	private void getExpAward(CommandResult result, Player player, int EXP) {
		result.setVO("item", null);
		result.setStatus("award");
		ChatService.sayAll(null, player.getName() + "在参与国庆节活动点泡泡时获得了" + EXP
				+ "经验。", ChatService.CHAT_WORLD);
		player.addExp(EXP);
	}

	/**
	 * 道具奖励
	 * @param result
	 * @param player
	 * @param itemId
	 */
	private void getItemAward(CommandResult result, Player player, int itemId) {
		ItemService.addItem(player, itemId, 1, false);
		result.setStatus("award");
		Item item = ItemMG.instance.getItem(itemId);
		if (item != null) {
			result.setVO("item", item);
			ChatService.sayAll(null, player.getName() + "在参与国庆节活动点泡泡时获得了"
					+ item.getName() + "。", ChatService.CHAT_WORLD);
		}
	}

	/**
	 * 检查日期是否在规定时间 6月1日-6月15日
	 * @return
	 */
	private boolean checkActiveTime() {
		// 6月1日-6月15日
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if (month != Calendar.OCTOBER) {
			return false;
		}
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth > 15) {
			return false;
		}

		return true;
	}

	/**
	 * 查看是否需要扣除玩家金币
	 * @param player
	 * @param pa
	 * @param key
	 * @return
	 */
	private boolean checkPlayer(Player player, PlayerActive pa ,String key) {
		boolean bool = true;
		if (pa == null) {
			bool = false;
			pa = PlayerMG.instance.createPlayerActive(player, "wuyidangtian", 0);
			player.getPlayerActive().put(key, pa);
			DBService.commit(pa);
			if (player.getLevel() < 50) {
				bool = true;
				return bool;
			}
		}
		Calendar cal = pa.getLastCal();
		Calendar nowCal = Calendar.getInstance();
		try {
			nowCal.setTime(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean dayBoo = nowCal.get(Calendar.DAY_OF_MONTH) == cal
				.get(Calendar.DAY_OF_MONTH);
		if (!dayBoo) {
			pa.setJoinDate(new Date());
			DBService.commitNoCacheUpdate(pa);
		}
		if (player.getLevel() >= 50 && !dayBoo) {
			bool = false;
		}
		return bool;
	}
	
	/**
	 * 随机生成12个奖励填充到页面上
	 * @param result
	 * @param player
	 */
	private void getPlayerItem(CommandResult result, Player player) {
		// 小于51级的获取级别小的奖励
		if (player.getLevel() < 51) {
			// 随机生成10件可得奖励放到页面上
			List<Integer> list = getNmuber(25);
			List<Item> itemList = new ArrayList<Item>();
			for (int i = 1; i < 4; i++) {
				itemList.add(itemMapMin.get(i));
			}
			for (Integer i : list) {
				if (i == 27) {
					itemList.add(null);
				} else {
					itemList.add(itemMapMin.get(i));
				}
			}
			result.setVO("itemList", itemList);
			// giveAward(result, player, itemsMin[probability]);
		} else {
			List<Integer> list = getNmuber(34);
			List<Item> itemList = new ArrayList<Item>();
			for (int i = 1; i < 4; i++) {
				itemList.add(itemMapMax.get(i));
			}
			for (Integer i : list) {
				if (i == 25) {
					itemList.add(null);
				} else {
					itemList.add(itemMapMax.get(i));
				}
			}
			result.setVO("itemList", itemList);
		}
		
	}
	
	/**
	 * 用于50级以下奖励
	 * @param itemMapMin
	 * @return
	 */
	private Map<Integer, Item> getMapMin(Map<Integer, Item> itemMapMin) {
		if (itemMapMin == null || itemMapMin.size() == 0) {
			itemMapMin.put(1, ItemMG.instance.getItem(10346));
			itemMapMin.put(2, ItemMG.instance.getItem(10347));
			itemMapMin.put(3, ItemMG.instance.getItem(10457));
			itemMapMin.put(4, ItemMG.instance.getItem(10529));
			itemMapMin.put(5, ItemMG.instance.getItem(10530));
			itemMapMin.put(6, ItemMG.instance.getItem(10273));
			itemMapMin.put(7, ItemMG.instance.getItem(10274));
			itemMapMin.put(8, ItemMG.instance.getItem(10275));
			itemMapMin.put(9, ItemMG.instance.getItem(10276));
			itemMapMin.put(10, ItemMG.instance.getItem(10277));
			itemMapMin.put(11, ItemMG.instance.getItem(10375));
			itemMapMin.put(12, ItemMG.instance.getItem(10376));
			itemMapMin.put(13, ItemMG.instance.getItem(10377));
			itemMapMin.put(14, ItemMG.instance.getItem(10378));
			itemMapMin.put(15, ItemMG.instance.getItem(10379));
			itemMapMin.put(16, ItemMG.instance.getItem(10693));
			itemMapMin.put(17, ItemMG.instance.getItem(36));
			itemMapMin.put(18, ItemMG.instance.getItem(37));
			itemMapMin.put(19, ItemMG.instance.getItem(31));
			itemMapMin.put(20, ItemMG.instance.getItem(10312));
			itemMapMin.put(21, ItemMG.instance.getItem(10327));
			itemMapMin.put(22, ItemMG.instance.getItem(10317));
			itemMapMin.put(23, ItemMG.instance.getItem(10322));
			itemMapMin.put(24, ItemMG.instance.getItem(70));
			itemMapMin.put(25, ItemMG.instance.getItem(10414));
			itemMapMin.put(26, ItemMG.instance.getItem(10504));
			// 27为5000经验
			itemMapMin.put(28, ItemMG.instance.getItem(47));
			itemMapMin.put(29, ItemMG.instance.getItem(10698));
		}
		return itemMapMin;
	}
	
	/**
	 * 用于50级以上奖励
	 * @param itemMapMax
	 * @return
	 */
	private Map<Integer, Item> getMapMax(Map<Integer, Item> itemMapMax) {
		if (itemMapMax == null || itemMapMax.size() == 0) {
			itemMapMax.put(1, ItemMG.instance.getItem(10534));
			itemMapMax.put(2, ItemMG.instance.getItem(10536));
			itemMapMax.put(3, ItemMG.instance.getItem(10532));
			itemMapMax.put(4, ItemMG.instance.getItem(10647));
			itemMapMax.put(5, ItemMG.instance.getItem(10648));
			itemMapMax.put(6, ItemMG.instance.getItem(10649));
			itemMapMax.put(7, ItemMG.instance.getItem(10426));
			itemMapMax.put(8, ItemMG.instance.getItem(10428));
			itemMapMax.put(9, ItemMG.instance.getItem(10427));
			itemMapMax.put(10, ItemMG.instance.getItem(10478));
			itemMapMax.put(11, ItemMG.instance.getItem(10479));
			itemMapMax.put(12, ItemMG.instance.getItem(10480));
			itemMapMax.put(13, ItemMG.instance.getItem(10482));
			itemMapMax.put(14, ItemMG.instance.getItem(10488));
			itemMapMax.put(15, ItemMG.instance.getItem(10525));
			itemMapMax.put(16, ItemMG.instance.getItem(36));
			itemMapMax.put(17, ItemMG.instance.getItem(37));
			itemMapMax.put(18, ItemMG.instance.getItem(31));
			itemMapMax.put(19, ItemMG.instance.getItem(10312));
			itemMapMax.put(20, ItemMG.instance.getItem(10327));
			itemMapMax.put(21, ItemMG.instance.getItem(10317));
			itemMapMax.put(22, ItemMG.instance.getItem(10322));
			itemMapMax.put(23, ItemMG.instance.getItem(70));
			itemMapMax.put(24, ItemMG.instance.getItem(10414));
			// 25为10000经验
			itemMapMax.put(26, ItemMG.instance.getItem(10513));
			itemMapMax.put(27, ItemMG.instance.getItem(10013));
			itemMapMax.put(28, ItemMG.instance.getItem(10028));
			itemMapMax.put(29, ItemMG.instance.getItem(10043));
			itemMapMax.put(30, ItemMG.instance.getItem(10058));
			itemMapMax.put(31, ItemMG.instance.getItem(10073));
			itemMapMax.put(32, ItemMG.instance.getItem(10014));
			itemMapMax.put(33, ItemMG.instance.getItem(10029));
			itemMapMax.put(34, ItemMG.instance.getItem(10044));
			itemMapMax.put(35, ItemMG.instance.getItem(10059));
			itemMapMax.put(36, ItemMG.instance.getItem(10074));
			itemMapMax.put(37, ItemMG.instance.getItem(10504));
			itemMapMax.put(38, ItemMG.instance.getItem(10698));
		}
		return itemMapMax;
	}

	/**
	 * 随机获取9个不相等的7个数字
	 * @param max	9个数字的范围
	 * @return
	 */
	private List<Integer> getNmuber(int max) {
		List<Integer> list = new ArrayList<Integer>();
		Random rand = new Random();
		boolean[] bool = new boolean[max];
		int num = 0;
		// 7为剩余7个随机出现的奖品
		for (int i = 0; i < 9; i++) {
			do {
				num = rand.nextInt(max);
			} while (bool[num]);
			bool[num] = true;
			num = 4 + num;
			list.add(num);
		}
		return list;
	}

	/**
	 * 产生小于50级奖励的概率
	 */
	private static int[][] itemMinConfig = { { 10346, 20 }, { 10347, 20 },
			{ 10457, 20 }, { 10529, 100 }, { 10530, 20 }, { 10273, 500 },
			{ 10274, 500 }, { 10275, 500 }, { 10276, 500 }, { 10277, 500 },
			{ 10375, 500 }, { 10376, 500 }, { 10377, 500 }, { 10378, 500 },
			{ 10379, 500 }, { 10693, 300 }, { 36, 100 }, { 37, 20 },
			{ 31, 20 }, { 10312, 300 }, { 10327, 300 }, { 10317, 300 },
			{ 10322, 300 }, { 70, 100 }, { 10414, 300 }, { 10504, 1249 },
			{ 5000, 1250 }, { 47, 280 }, {10698, 1} };

	/**
	 * 产生大于50级奖励的概率
	 */
	private static int[][] itemMaxConfig = { { 10647, 10 }, { 10648, 60 },
			{ 10649, 1 }, { 10426, 20 }, { 10428, 1 }, { 10427, 20 },
			{ 10478, 10 }, { 10479, 10 }, { 10480, 10 }, { 10482, 10 },
			{ 10488, 10 }, { 10525, 3000 }, { 10534, 1 }, { 10536, 1 },
			{ 10532, 1 }, { 36, 2 }, { 37, 2 }, { 31, 2 }, { 10312, 2 },
			{ 10327, 2 }, { 10317, 2 }, { 10322, 2 }, { 70, 10 },
			{ 10414, 795 }, { 10000, 3000 }, { 10513, 1 }, { 10013, 2 },
			{ 10028, 2 }, { 10043, 2 }, { 10058, 2 }, { 10073, 2 },
			{ 10014, 1 }, { 10029, 1 }, { 10044, 1 }, { 10059, 1 },
			{ 10074, 1 }, { 10504, 2999 }, {10698, 1} };
	private static int[] itemsMin = new int[10000];
	private static int[] itemsMax = new int[10000];

	/**
	 * 初始化奖励概率
	 */
	public static void initItemConfiguration() {
		int pos = 0;
		for (int i = 0; i < itemMinConfig.length; i++) {
			int itemId = itemMinConfig[i][0];
			int size = itemMinConfig[i][1];
			for (int j = 0; j < size; j++) {
				itemsMin[pos] = itemId;
				pos++;
			}
		}
		pos = 0;
		for (int i = 0; i < itemMaxConfig.length; i++) {
			int itemId = itemMaxConfig[i][0];
			int size = itemMaxConfig[i][1];
			for (int j = 0; j < size; j++) {
				itemsMax[pos] = itemId;
				pos++;
			}
		}
	}
}
