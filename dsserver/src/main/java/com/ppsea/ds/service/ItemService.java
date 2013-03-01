package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.fight.SystemSettingCmd;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.BuffRandVo;
import com.ppsea.ds.data.model.Compose;
import com.ppsea.ds.data.model.ComposeItem;
import com.ppsea.ds.data.model.ComposeVo;
import com.ppsea.ds.data.model.Goods;
import com.ppsea.ds.data.model.GoodsItem;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemBetterProty;
import com.ppsea.ds.data.model.ItemDecompose;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.ItemIdentify;
import com.ppsea.ds.data.model.ItemImprove;
import com.ppsea.ds.data.model.ItemPosition;
import com.ppsea.ds.data.model.ItemProperty;
import com.ppsea.ds.data.model.ItemSuit;
import com.ppsea.ds.data.model.ItemVo;
import com.ppsea.ds.data.model.Npc;
import com.ppsea.ds.data.model.NpcItem;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.data.playeritem.Factory;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.IdentifyCenter;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.ItemSuitMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.ItemUtil;
import com.ppsea.ds.util.PropertyBuilder;

/**
 * 玩家道具相关逻辑放到该类实现
 **/
public class ItemService {

	// -------------------------------------------------------------------------------
	// 静态常量

	/**
	 * 道具最大能买的数量
	 **/
	private static int MAX_BUY_NUM = 1000;

	/**
	 * 升星最大级别3星
	 **/
	public static int MAX_STAR_LEVEL = 3;
//	public static final int MAX_IMPROVE_LEVEL = 12;// 最大强化等级
	public static final int SEG_INSTANCE_ITEM_BEG = -10000;
	public static final int SEG_INSTANCE_ITEM_END = -20000;
	public static int PLAYER_DART_SHORTCUT = 100; // 玩家的暗器快捷id
	public static int EQMENT_TYPE_ARMS = 1; // 武器
	public static int EQMENT_TYPE_ARMOR = 2; // 防具
	public static int EQMENT_TYPE_ACCES = 3; // 配饰
	public static int GOLD_REPAIR_ONE = 10; // 单件装备
	public static int GOLD_REPAIR_ALL = 50; // 所有装备
	public static int MIN_DIG_LEVEL = 0; // 所有装备
	
	public static final int XINGNANG = 12;
	
	/**
	 * 增加玩家负重的道具
	 */
	public static final int ROOMITEMIDS[] = {};

	/** 装备升星名称 */
	private static String[] starName = { "", "☆", "☆☆", "☆☆☆" };

	/**
	 * Log
	 **/
	private static final Logger log = Logger.getLogger(ItemService.class);
	public static final Logger logIdentify = Logger.getLogger("IDENTIFY");

	/**
	 * 升序排列
	 **/
	private static Comparator<PlayerItem> compAsc = new Comparator<PlayerItem>() {
		public int compare(PlayerItem a, PlayerItem b) {
			return (int) (a.getItem().getLevel() - b.getItem().getLevel());
		}
	};

	/**
	 * 降序排列
	 **/
	private static Comparator<PlayerItem> compDesc = new Comparator<PlayerItem>() {
		public int compare(PlayerItem a, PlayerItem b) {
			return (int) (b.getItem().getLevel() - a.getItem().getLevel());
		}
	};


	/**
	 * 根据给定的ITEM查找用户拥有的道具,返回找到的PlayerItem,没有查找到则返回NULL
	 * 
	 * @param player
	 *            使用者 item 道具
	 * @return 返回查到到PlayerItem
	 * */
	public static PlayerItem findPlayerItem(Player player, Item item) {
		if (item == null)
			return null;
		// 非武器类装备
		if (item.getType() != Item.ARM_TYPE) {
			Map<String, PlayerItem> typeMap = player.getNonArms().get(
					item.getTypeStr());
			if (typeMap == null) {
				return null;
			}
			return typeMap.get(item.getIdStr());
		}
		// 武器可以根据装备的位置属性查找到能装备到位置上的所有武器,然后遍历找到第一个则返回
		List<PlayerItem> playerItems = player.getArms().get(
				item.getPositionStr());
		if (playerItems == null) {
			return null;
		}
		for (PlayerItem playerItem : playerItems) {
			if (playerItem.getItemId().equals(item.getId())) {
				return playerItem;
			}
		}
		return null;
	}

	public static PlayerItem findPlayerItem(Player player, int itemId) {
		Item item = ItemMG.instance.getItem(itemId);
		if (item != null) {
			return findPlayerItem(player, item);
		}
		return null;
	}

	/**
	 * 根据给定的ITEM查找用户拥有的道具，如果是装备需满足未使用的,返回找到的PlayerItem,没有查找到则返回NULL
	 * 
	 * @param player
	 *            使用者 item 道具
	 * @return 返回查到到PlayerItem
	 * */
	public static PlayerItem findUnUsePlayerItem(Player player, Item item) {
		if (item == null)
			return null;
		// 非武器类装备
		if (item.getType() != Item.ARM_TYPE) {
			Map<String, PlayerItem> typeMap = player.getNonArms().get(
					item.getTypeStr());
			if (typeMap == null) {
				return null;
			}
			return typeMap.get(item.getIdStr());
		}
		// 武器可以根据装备的位置属性查找到能装备到位置上的所有武器,然后遍历找到第一个未使用的则返回
		List<PlayerItem> playerItems = player.getArms().get(
				item.getPositionStr());
		if (playerItems == null) {
			return null;
		}
		for (PlayerItem playerItem : playerItems) {
			if (playerItem.getItemId().equals(item.getId())
					&& playerItem.getIsUse() == 0
					&& !playerItem.getInExchange()
					&& (playerItem.getBindId() == null || playerItem
							.getBindId() == 0)
					&& (playerItem.getImproveLevel() == null || playerItem
							.getImproveLevel() == 0)) {
				return playerItem;
			}
		}
		return null;
	}

	public static PlayerItem findUnImporvePlayerItem(Player player, Item item) {
		if (item == null)
			return null;
		// 非武器类装备
		if (item.getType() != Item.ARM_TYPE) {
			Map<String, PlayerItem> typeMap = player.getNonArms().get(
					item.getTypeStr());
			if (typeMap == null) {
				return null;
			}
			return typeMap.get(item.getIdStr());
		}
		// 武器可以根据装备的位置属性查找到能装备到位置上的所有武器,然后遍历找到第一个未使用的则返回
		List<PlayerItem> playerItems = player.getArms().get(
				item.getPositionStr());
		if (playerItems == null) {
			return null;
		}
		for (PlayerItem playerItem : playerItems) {
			if (playerItem.getItemId().equals(item.getId())
					&& playerItem.getIsUse() == 0
					&& !playerItem.getInExchange()
					&& (playerItem.getBindId() == null || playerItem
							.getBindId() == 0)) {
				return playerItem;
			}
		}
		return null;
	}

	public static PlayerItemUsing findPlayerUsingItem(Player player,
			String itemId) {
		PlayerItemUsing piu = player.getUsingItem(itemId);
		if (piu != null) {
			if (piu.getLeft() <= 0) {
				return null;
			} else {
				return piu;
			}
		}
		return null;
	}

	/**
	 * 丢弃道具并被系统回收,使用中的道具不能丢弃 默认数量为1
	 **/
	public static ErrorMsg releasePlayerItem(Player player,
			PlayerItem playerItem, boolean force) {
		return releasePlayerItem(player, playerItem, 1, force);
	}

	/**
	 * 丢弃道具并被系统回收,使用中的道具不能丢弃
	 * 
	 * @param player
	 *            ：使用者<br>
	 * @param Item
	 *            ：道具 <br>
	 * @param force
	 *            ：true 强制丢弃，不需要检查丢弃属性
	 * @return SUCC,ERR_SYS, ERR_USING,ERR_NO_ITEM, ERR_DROP
	 **/
	public static ErrorMsg releasePlayerItem(Player player, Item item,
			boolean force) {
		PlayerItem playerItem = findPlayerItem(player, item);
		return releasePlayerItem(player, playerItem, force);
	}

	/**
	 * 丢弃道具并被系统回收,使用中的道具不能丢弃
	 * 
	 * @param player
	 *            使用者
	 * @param playerItem
	 *            用户道具
	 * @param amount
	 *            释放数量
	 * @param forcetrue
	 *            强制丢弃，不需要检查丢弃属性
	 * @return: SUCC,ERR_SYS, ERR_USING,ERR_NO_ITEM, ERR_DROP
	 * */
	public static ErrorMsg releasePlayerItem(Player player,
			PlayerItem playerItem, int amount, boolean force) {
		return releasePlayerItem(player, playerItem, amount, force, true);
	}

	/**
	 * 删除道具，和附属物.
	 * 
	 * @param player
	 * @param playerItem
	 * @param amount
	 * @param force
	 * @param isWithAppend
	 *            true表示删除附属物，false表示不强制删除附属物.
	 * @return
	 */
	public static ErrorMsg releasePlayerItem(Player player,
			PlayerItem playerItem, int amount, boolean force,
			boolean isWithAppend) {
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "道具不存在");
		}

		if (amount <= 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "输入数量错误");
		}
		ErrorMsg ret = drop(player, playerItem, amount, force);// 丢弃道具
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}
		if (playerItem.getAmount() > 0) {
			DBService.commit(playerItem);
			return Constants.SUCC;
		}

		if (isWithAppend) {
			// 删除道具上面的镶嵌物品
			if (playerItem.getAppends() != null) {
				PlayerItemAppend append = new PlayerItemAppend();
				append.setPlayerItemId(playerItem.getId());
				List<Integer> idList = new LinkedList<Integer>();
				for (PlayerItemAppend pia : playerItem.getAppends()) {
					idList.add(pia.getId());
				}
				playerItem.getAppends().clear();
				DBService.commitWhereDelete(append, idList);
			}
		}

		// 删除PlayerItem表
		DBService.commitDelete(playerItem);
		return Constants.SUCC;
	}

	/**
	 * 
	 * @param player
	 * @param item
	 * @param force
	 * @return
	 */
	public static ErrorMsg removeArmAppertain(Player player,
			PlayerItem playerItem, int amount) {
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "道具不存在");
		}

		if (amount <= 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "输入数量错误");
		}

		// 用户没有该道具
		if (playerItem == null || playerItem.getAmount() == 0
				|| playerItem.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有相应的装备");
		}
		// 如果不是强制丢弃，需要检查道具的丢弃属性和绑定属性
		if (!playerItem.getItem().getDropable()) {
			return new ErrorMsg(ErrorCode.ERR_DROP, "该物品不能丢弃");
		}
		if (playerItem.getBindId() > 0) {
			return new ErrorMsg(ErrorCode.ERR_BIND, "该道具已锁定，需要解锁后才能进行该操作");
		}

		// 不能丢弃正在使用的道具
		if (playerItem.getIsUse() == 1) {
			return new ErrorMsg(ErrorCode.ERR_USING, "物品目前正在使用中");
		}
		Map<String, PlayerItem> allItems = player.getAllItems();

		// 更新当前负重,不需要同步到DB,加载用户时会自动计算
		player.setRoom(player.getRoom() - playerItem.getItem().getRoom()
				* amount);

		allItems.remove(playerItem.getIdStr());
		Item item = playerItem.getItem();

		List<PlayerItem> ls = player.getArms().get(item.getPositionStr());
		if (ls != null) {
			ls.remove(playerItem);
		}

		if (playerItem.getAmount() > 0) {
			playerItem.setPlayerId(0);
			DBService.commitNoCacheUpdate(playerItem);
			return Constants.SUCC;
		}

		return Constants.SUCC;
	}

	/**
	 * 增加装备关系
	 * 
	 * @param player
	 * @param playerItem
	 * @param amount
	 * @return
	 */
	public static ErrorMsg addArmAppertain(Player player, int playerItemId,
			int amount) {

		PlayerItem playerItem = DBManager.queryPlayerItemById(playerItemId);
		Item item = ItemMG.instance.getItem(playerItem.getItemId());
		playerItem.setItem(item);
		playerItem.setPlayerId(player.getId());

		playerItem = Factory.mountHandler(playerItem);
		// 向数据库提交playerItem
		DBService.commit(playerItem);

		// 设置镶嵌列表
		List<PlayerItemAppend> appends = DBManager
				.queryPlayerItemAppendsByPItemId(playerItemId);
		List<PlayerItemAppend> list = new ArrayList<PlayerItemAppend>();
		for (PlayerItemAppend append : appends) {
			Item ti = ItemMG.instance.getItem(append.getAppendItemId());
			if (ti == null)
				continue;
			append.setItem(ti);
			list.add(append);
		}

		playerItem.setAppends(list);

		// 添加到AllItem列表中
		player.getAllItems().put(playerItem.getIdStr(), playerItem);

		// 添加到对应装备位置的集合中
		List<PlayerItem> playerItems = player.getArms().get(
				item.getPositionStr());
		if (playerItems == null) {
			playerItems = new LinkedList<PlayerItem>();
			player.getArms().put(item.getPositionStr(), playerItems);
		}
		playerItems.add(playerItem);

		// 更新负重
		player.setRoom(player.getRoom() + item.getRoom());

		return new ErrorMsg(ErrorCode.SUCC, null, playerItem);
	}

	/**
	 * 道具放入玩家物品栏
	 **/
	public static ErrorMsg addItem(Player player, int itemId, int num) {
		return addItem(player, itemId, num, true);
	}

	/**
	 * 道具放入玩家物品栏
	 **/
	public static ErrorMsg addItemForce(Player player, Item item, int num) {
		if (player == null || item == null) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "参数错误");
		}
		Player p = PlayerMG.instance.getOnlinePlayer(player.getId());
		if (p != null) {// 在线
			return addItem(p, item, num, false);
		}
		// 直接更新数据库
		PlayerItem playerItem = DBManager.queryPlayerItemByItem(player.getId(),
				item.getId());
		if (playerItem == null) {
			playerItem = Factory.create(player, item);
			playerItem.setAmount(num);
		} else
			playerItem.setAmount(playerItem.getAmount() + num);
		DBService.commit(playerItem);
		return new ErrorMsg(ErrorCode.SUCC, null, playerItem);
	}

	/**
	 * 将某种道具放入用户物品栏
	 * 
	 * @param player
	 *            使用者
	 * @param itemId
	 *            道具id
	 * @param num
	 *            数量
	 * @param isCheckRoom
	 *            是否校验负重
	 * @throws PpseaException
	 **/
	public static ErrorMsg addItem(Player player, Item item, int num,
			boolean isCheckRoom) {
		if (item == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}

		if (item.getRoom() > 0) {
			// 检查物品栏是否有空间放下物品
			if (isCheckRoom) {
				if (item.getRoom() > 0
						&& player.getDyn().getMaxRoom() < player.getRoom()
								+ item.getRoom() * num) {
					return new ErrorMsg(ErrorCode.ERR_ROOM_LIMIT,
							"你背包已满，无法添加新道具，你可以去商城购买行囊增加负重");
				}
			}

		}

		// 按道具类型进行添加到玩家物品栏
		if (item.getType() != Item.ARM_TYPE) {
			return addNonArm(player, item, num);
		} else if (num == 1) {
			return addArm(player, item, 0);
		}

		// 添加装备类是NUM必须为1
		log.error("[ItemService]{can't addArm, because num =" + num + "}");
		return new ErrorMsg(ErrorCode.ERR_SYS, "服务器异常");
	}

	/**
	 * 将某种道具放入用户物品栏
	 * 
	 * @param player
	 *            使用者
	 * @param itemId
	 *            道具id
	 * @param num
	 *            数量
	 * @param isCheckRoom
	 *            是否校验负重
	 * @throws PpseaException
	 **/
	public static ErrorMsg addItem(Player player, int itemId, int num,
			boolean isCheckRoom) {
		Item item = ItemMG.instance.getItem(itemId);
		if (item == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}
		return addItem(player, item, num, isCheckRoom);
	}

	/**
	 * 用铜币购买道具
	 **/
	public static ErrorMsg buyInCopper(Player player, Item item, int num)
			throws PpseaException {
		if (item == null || num <= 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "购买道具个数不能必须大于0小于"
					+ MAX_BUY_NUM);
		}
		// 检查是否溢出
		if (item.getPrice() * num < 0) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统错误");
		}
		if ((player.getTotalCopper() - (item.getPrice() * num)) < 0) {
			throw new NoMoneyException("余额不足");
		}
		ErrorMsg ret = buy(player, item, num);
		if (ret.code < 0) {
			return ret;
		}
		// 扣铜贝
		player.consumeInCopper(item.getPrice() * num);
		DBService.commit(player);
		return Constants.SUCC;
	}

	/**
	 * 用金锭购买道具.
	 * 
	 * @param player
	 * @param item
	 * @param num
	 * @return
	 * @throws PpseaException
	 */
	public static ErrorMsg buyInGold(Player player, Item item, int num)
			throws PpseaException {
		if (item == null || num <= 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "购买道具个数必须大于0小于"
					+ MAX_BUY_NUM);
		}
		// 检查是否溢出
		if (item.getGold() * num < 0) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统错误");
		}
		int toPay = item.getGold() * num;
		if ((player.getGold() - toPay) < 0) {
			throw new NoMoneyException("余额不足");
		}
		ErrorMsg ret = buy(player, item, num);
		if (ret.code < 0) {
			return ret;
		}
		// 扣金锭
		player.setGold(player.getGold() - toPay);
		DBService.commit(player);
		return Constants.SUCC;
	}

	/**
	 * 用金票购买道具.
	 * 
	 * @param player
	 * @param item
	 * @param num
	 * @return
	 * @throws PpseaException
	 */
	public static ErrorMsg buyInAdvGold(Player player, Item item, int num)
			throws PpseaException {
		if (item == null || num <= 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "购买道具个数必须大于0小于"
					+ MAX_BUY_NUM);
		}
		// 检查是否溢出
		if (item.getGold() * num < 0) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统错误");
		}
		int toPay = item.getGold() * num;
		if ((player.getAdvGold() - toPay) < 0) {
			throw new NoMoneyException("余额不足");
		}
		ErrorMsg ret = buy(player, item, num);
		if (ret.code < 0) {
			return ret;
		}
		// 扣金票
		player.setAdvGold(player.getAdvGold() - toPay);
		DBService.commit(player);
		return Constants.SUCC;
	}

	/**
	 * 卖出指定道具
	 * 
	 * @param Player
	 * @param PlayerItem
	 * @param num
	 *            卖出数量
	 * @return >0 卖出价格 <0 失败
	 **/
	public static ErrorMsg sell(Player player, PlayerItem playerItem, int num) {
		if (playerItem == null || num < 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "非法的数量");
		}
		// 检查是否可以卖出
		if (!ItemUtil.sellCheck(player, playerItem)) {
			return new ErrorMsg(ErrorCode.ERR_SYS, playerItem.item.getName()
					+ "不可交易.");
		}
		if (playerItem.getInExchange() == true) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "交易中的物品不可再进行交易.");
		}

		ErrorMsg ret = ItemService.releasePlayerItem(player, playerItem, num,
				false);
		if (ret.code == ErrorCode.SUCC) {
			// int money = num * playerItem.getItem().getPrice()*
			// Constants.getIntValue("item.sell.discount", 10)/ 100;
			int money = computeSellPrice(playerItem, num)
					* Constants.getIntValue("item.sell.discount", 10) / 100;
			// 有耐久,要根据当前耐久折旧
//			if (playerItem.getItem().getEndure() > 0) {
//				money = money * playerItem.getCurrEndure()
//						/ playerItem.getItem().getEndure();
//			}
			player.addCopper(money);
			DBService.commit(player);
			return new ErrorMsg(money, playerItem.getItem().getName());
		}
		return ret;
	}

	/**
	 * 计算装备的价格,根据装备的强化，升星，打孔加成终价.
	 * 
	 * @param playerItem
	 * @param num
	 * @return
	 */
	private static int computeSellPrice(PlayerItem playerItem, int num) {
		try {
			int price = 0;
			if (playerItem == null || playerItem.getItem() == null) {
				return price;
			}
			int origin = playerItem.getItem().getPrice();
			int newPrice = origin * num;
			int improve = playerItem.getImproveLevel();
			for (int i = 0; i < improve; i++) {
				newPrice += num * (origin * 6 / 100);
			}
			int star = playerItem.getStarLevel();
			for (int i = 0; i < star; i++) {
				newPrice += num * (origin * 6 / 100);
			}
			int hole = playerItem.getCurrHoleAmount();
			for (int i = 0; i < hole; i++) {
				newPrice += num * (origin * 6 / 100);
			}
			return newPrice;
		} catch (Exception e) {
		}
		return 0;

	}

	/**
	 * 使用道具
	 * */
	public static ErrorMsg useItem(Player player, int itemId, int amount) {
		Item item = ItemMG.instance.getItem(itemId);
		PlayerItem pi = findPlayerItem(player, item);
		if (pi == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "道具不存在");
		}
		// 可以重复使用
		// if (pi.getAmount() > 0 ) {
		// return Constants.SUCC;
		// }
		return releasePlayerItem(player, pi, amount, true);
	}

	/**
	 * 验证玩家是否有指定类型的道具
	 * 
	 * @param player
	 *            使用者
	 * @param item
	 *            要验证的道具
	 **/
	public static boolean hasItem(Player player, Item item) {
		if (player == null || item == null)
			return false;
		return (findPlayerItem(player, item) != null);
	}

	/**
	 * 判断指定的NPC是否可以买指定的道具
	 * 
	 * @param npc
	 *            要判断的NPC
	 * @param item
	 *            道具
	 **/
	public static boolean npcHasItem(Npc npc, Item item) {
		if (npc == null || item == null)
			return false;
		return (npcHasItem(npc.getId(), item));
	}

	/**
	 * 判断指定的NPC是否可以买指定的道具
	 * 
	 * @param npcId
	 *            要判断的NPC编号
	 * @param itemId
	 *            道具
	 **/
	public static boolean npcHasItem(int npcId, Item item) {
		List<Item> itemLst = ItemMG.instance.getNpcItems(npcId,
				NpcItem.NPC_ITEM_TYPE_BUY);
		if (itemLst == null || itemLst.size() == 0)
			return false;
		return (itemLst.contains(item));
	}

	/**
	 * 获取指定玩家身上需要进行修理的装备列表
	 * 
	 * @param 需要修理装备的玩家
	 * @return 需要修理的列表
	 **/
//	public static List<PlayerItem> getRequireRepairArms(Player player) {
//		List<PlayerItem> playerItems = new LinkedList<PlayerItem>();
//		for (List<PlayerItem> pis : player.getArms().values()) {
//			if (pis != null) {
//				for (PlayerItem pi : pis) {
//					if (pi.getItem().getEndure() > 0
//							&& pi.getItem().getEndure() > pi.getCurrEndure()) {
//						playerItems.add(pi);
//					}
//				}
//			}
//		}
//		return playerItems;
//	}

	/**
	 * 返回玩家可以进行打孔的装备,并且必须是正在使用的装备,而且当前孔数小于最大允许孔数
	 **/
	public static List<PlayerItem> getCandDrillable(Player player) {
		List<PlayerItem> list = new LinkedList<PlayerItem>();
		List<PlayerItem> usedItems = player.getUsedArmsList();
		Iterator<PlayerItem> itr = usedItems.iterator();
		while (itr.hasNext()) {
			PlayerItem pi = itr.next();
			if (pi != null && pi.getItem() != null
					&& pi.getCurrHoleAmount() < pi.getItem().getMaxAppend())
				list.add(pi);
		}
		return list;
	}

	/**
	 * 计算修理装备需要的铜币数量
	 * 
	 * @param repairArms
	 *            需要修理的列表
	 * @return money 需要的铜币
	 **/
//	public static int repairMoney(List<PlayerItem> playerItems)
//			throws PpseaException {
//		if (playerItems == null || playerItems.size() <= 0)
//			return 0;
//		int sum = 0;
//		Iterator<PlayerItem> itr = playerItems.iterator();
//		while (itr.hasNext()) {
//			sum += repairMoney(itr.next());
//		}
//		return sum;
//	}

	/**
	 * 计算修复道具花费
	 * 
	 * @param player
	 * @param playerItem
	 * @return 需要的铜币数量
	 **/
//	public static int repairMoney(PlayerItem playerItem) throws PpseaException {
//		int endure = playerItem.getItem().getEndure();
//		int currEndure = playerItem.getCurrEndure();
//		// 本次修复需要花费
//		// int repairCost = playerItem.getItem().getPrice() * (endure -
//		// currEndure) / endure;
//		int repairCost = computeSellPrice(playerItem, 1)
//				* (endure - currEndure) / endure;
//		return (repairCost * Constants.getIntValue("item.repair.cost", 5) / 100);
//	}

	/**
	 * 对给的的装备列表进行升序排列
	 **/
	public static void sortByLevelAsc(List<PlayerItem> playerItems) {
		Collections.sort(playerItems, compAsc);
	}

	/**
	 * 对给的的装备列表进行升序排列
	 **/
	public static void sortByLevelDesc(List<PlayerItem> playerItems) {
		Collections.sort(playerItems, compDesc);
	}

	/**
	 * 修复道具
	 * 
	 * @param player
	 * @param playerItem
	 * @return SUCC, NO_MONEY
	 * */
//	public static ErrorMsg repair(Player player, PlayerItem playerItem,
//			boolean consume, boolean useGold) throws PpseaException {
//		String tooltip = "";
//		// 是否扣钱
//		if (playerItem.getItem().getId() == 10650) {
//			return new ErrorMsg(ErrorCode.SUCC, "不允许修理该道具");
//		}
//		if (consume == true) {
//			// 金修理
//			if (useGold == true) {
//				if (!player.consumeInGlod(GOLD_REPAIR_ONE)
//						|| !player.consumeInAdvGlod(GOLD_REPAIR_ONE)) {
//					return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "没有足够的金钱.");
//				}
//				tooltip = "你花费了" + GOLD_REPAIR_ONE + "修理了" + playerItem.item.getName();
//			} else {
//				// 本次修复需要花费
//				int money = repairMoney(playerItem);
//				// System.out.println("money = "+ money +"; cost="+ cost);
//				if (!player.consumeInCopper(money)) {
//					return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "没有足够的金钱.");
//				}
//				tooltip = "你花费了" + money + "铜修理了" + playerItem.item.getName();
//			}
//		}
//		playerItem.setCurrEndure(playerItem.getItem().getEndure());
//		DBService.commit(playerItem);
//		return new ErrorMsg(ErrorCode.SUCC, tooltip);
//	}

	/**
	 * 修复道具
	 * 
	 * @param player
	 * @param playerItem
	 * @return SUCC, NO_MONEY
	 * */
//	public static ErrorMsg repairAll(Player player, boolean useGold)
//			throws PpseaException {
//		boolean consume = true;
//		if (useGold == true) {// 金修理 全部装备
//			if (!player.consumeInCopper(GOLD_REPAIR_ALL)
//					|| !player.consumeInAdvGlod(GOLD_REPAIR_ALL)) {
//				return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "没有足够的金钱.");
//			}
//			consume = false;
//		}
//
//		ErrorMsg ret = new ErrorMsg(ErrorCode.SUCC, null);
//		for (List<PlayerItem> pis : player.getArms().values()) {
//			if (ret.code != ErrorCode.SUCC) {
//				break;
//			}
//			if (pis != null) {
//				for (PlayerItem pi : pis) {
//					if (ret.code != ErrorCode.SUCC) {
//						break;
//					}
//					if (pi.getItem().getEndure() > 0
//							&& pi.getItem().getEndure() > pi.getCurrEndure()) {
//						ret = ItemService.repair(player, pi, consume, useGold);
//					}
//				}
//			}
//		}
//		return Constants.SUCC;
//	}

	/**
	 * 装备打孔
	 * 
	 * @param player
	 *            玩家
	 * @param playerItem
	 *            要打孔的装备
	 **/
	public static ErrorMsg puncture(Player player, PlayerItem playerItem,
			List<PlayerItem> materials, int[] amounts) {
		// 验证
		if (playerItem == null)
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		if (playerItem.getCurrHoleAmount().intValue() + 1 > playerItem
				.getItem().getMaxAppend().intValue())
			return new ErrorMsg(ErrorCode.ERR_HOLE_LIMIT, "不能再进行打孔");

		int punctureCost = Constants.getIntValue("item.puncture.cost", 100);
		if (punctureCost > 0 && !player.consumeInCopper(punctureCost)) {
			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "抱歉,打孔需要金钱"
					+ punctureCost + ",你没有足够数量");
		}

		// 扣道具
		boolean isExchange = true;
		int index = 0;
		for (; index < materials.size(); index++) {
			PlayerItem itm = materials.get(index);
			if (itm.item.getIsExchange() != null
					&& itm.item.getIsExchange().booleanValue() == false)
				isExchange = false;
			ErrorMsg ret = releasePlayerItem(player, itm, amounts[index], true);
			if (ret.code != ErrorCode.SUCC) {// 扣除道具失败
				return new ErrorMsg(ErrorCode.SUCC, ret.text);
			}
		}

		playerItem
				.setCurrHoleAmount(playerItem.getCurrHoleAmount().intValue() + 1);
		if (playerItem.getIsExchange()) {
			playerItem.setIsExchange(isExchange);
		}
		// 更新数据库
		DBService.commit(playerItem);
		return Constants.SUCC;
	}

	/**
	 * 装备镶嵌
	 * 
	 * @param player
	 *            使用者
	 * @param master
	 *            主装备
	 * @param slaver
	 *            镶嵌物
	 * @return: SUCC,ERR_SYS, ERR_BIND, ERR_APPEND_LIMIT
	 * @throws PpseaException
	 **/
	public static ErrorMsg embedItem(Player player, PlayerItem master,
			PlayerItem slaver) throws PpseaException {
		if (master == null || slaver == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}

		// 物品如果正在交易中是不能镶嵌和被镶嵌的
		if (master.getExchangeAmount() > 0 || slaver.getExchangeAmount() > 0) {
			return new ErrorMsg(ErrorCode.ERR_EX_IN, "交易中的物品不能进行绑定");
		}

		// 是否为宝石
		if (!slaver.getItem().getEmbedable()) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "该物品不可镶嵌");
		}

		// 是否可以镶嵌到该位置
		if (!ItemUtil.isMergeable(master.item, slaver.item)) {
			return new ErrorMsg(ErrorCode.ERR_SYS, slaver.item.getName()
					+ "不能镶嵌到该位置上面.");
		}

		// 检查是否被player拥有
		if (!master.getPlayerId().equals(player.getId())
				|| !slaver.getPlayerId().equals(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "只能绑定自己的装备");
		}

		// 检查绑定属性
		if (master.checkBindOther(player.getId())
				|| slaver.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_BIND, "绑定到别人");
		}

		// 主装备是否可以被镶嵌
		if (!master.embedable()) {
			return new ErrorMsg(ErrorCode.ERR_CAN_NOT_EMBED, "改装备不能进行镶嵌");
		}

		// 主装备是否有镶嵌孔
		if (!master.hasEmbedSpace()) {
			return new ErrorMsg(ErrorCode.ERR_NO_EMBED_HOLE, "没有镶嵌的空间");
		}

		// 钱是否足够
		int composeCost = Constants.getIntValue("item.embed.cost", 1);
		if (composeCost > 0 && !player.consumeInCopper(composeCost)) {
			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "装备镶嵌需要" + composeCost
					+ "铜币,你数量不够.");
		}

		// 系统回收宝石
		ErrorMsg ret = releasePlayerItem(player, slaver, true);
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}

		// 将Slaver属性增加给用户
		if (master.getIsUse().intValue() == 1)
			player.getDyn().changeProperty(player, slaver, 1);

		// 创建Appends
		if (master.getAppends() == null) {
			master.setAppends(new LinkedList<PlayerItemAppend>());
		}

		// 新建PlayerItemAppend
		PlayerItemAppend append = new PlayerItemAppend();
		append.setId(GlobalGenerator.instance.getIdForNewObj(append));
		append.setPlayerId(master.getPlayerId());
		append.setPlayerItemId(master.getId());
		append.setAppendItemId(slaver.getItemId());
		append.setItem(slaver.getItem());
		master.getAppends().add(append);
		// 设置镶嵌后的装备是否可交易,如果镶嵌物为不可交易;则镶嵌后装备也为不可交易
		if (master.getIsExchange()) {
			master.setIsExchange(slaver.getIsExchange());
		}
		if (!slaver.getIsExchange()) {
			master.setIsExchange(slaver.getIsExchange());
		}
		// 提交到数据库
		DBService.commit(append);
		DBService.commit(player);
		DBService.commit(master);
		return Constants.SUCC;
	}

	/**
	 * 装备挖孔
	 * 
	 * @param player
	 *            使用者
	 * @param master
	 *            主装备
	 * @param slaver
	 *            镶嵌物
	 **/
	public static ErrorMsg wachu(Player player, PlayerItem master,
			PlayerItem slaver, int itemid) throws PpseaException {
		if (master == null || slaver == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}
		if (master.getAmount() < 1 || slaver.getAmount() < 1) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}

		// 物品如果正在交易中是不能挖除和被挖除的
		if (master.getExchangeAmount() > 0 || slaver.getExchangeAmount() > 0) {
			return new ErrorMsg(ErrorCode.ERR_EX_IN, "交易中的物品不能进行拆卸");
		}
		// 检查是否被player拥有
		if (!master.getPlayerId().equals(player.getId())
				|| !slaver.getPlayerId().equals(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "只能拆卸绑定自己的装备");
		}
		// 检查绑定属性
		if (master.checkBindOther(player.getId())
				|| slaver.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_BIND, "绑定到别人");
		}
		// 主装备是否可以被挖除
		if (master.getAppends() == null) {
			return new ErrorMsg(ErrorCode.ERR_CAN_NOT_EMBED, "该装备不能进行拆卸");
		}
		ErrorMsg ret = releasePlayerItem(player, slaver, true);
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}

		List<PlayerItemAppend> appends = master.getAppends();
		if (appends != null) {
			for (int i = 0; i < appends.size(); i++) {
				PlayerItemAppend pia = appends.get(i);
				if (pia.getId() == itemid) {
					appends.remove(i);
					DBService.commitDelete(pia);
					if (master.getIsUse().intValue() == 1) {
						player.getDyn().changeProperty(pia.getItem(), -1);
					}
				}
			}
		}
		DBService.commit(player);
		DBService.commit(master);
		return Constants.SUCC;
	}

	/**
	 * 更改交易属性
	 * 
	 * @param player
	 *            使用者
	 * @param master
	 *            主装备
	 * @param slaver
	 *            易洗符
	 **/
	public static ErrorMsg gengai(Player player, PlayerItem master,
			PlayerItem slaver) throws PpseaException {
		if (master == null || slaver == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}
		if (slaver.getAmount() < 1) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "物品不存在");
		}
		// 物品如果正在交易中是不能更改属性
		if (master.getExchangeAmount() > 0 || slaver.getExchangeAmount() > 0) {
			return new ErrorMsg(ErrorCode.ERR_EX_IN, "交易中的物品不能进行绑定");
		}
		// 检查是否被player拥有
		if (!master.getPlayerId().equals(player.getId())
				|| !slaver.getPlayerId().equals(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "只能绑定自己的装备");
		}
		// 检查绑定属性
		if (master.checkBindOther(player.getId())
				|| slaver.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_BIND, "绑定到别人");
		}
		// 主装备是否可以被更改属性
		Item item = ItemMG.instance.getItem(master.getItemId());
		if (item.getIsExchange() == false) {
			return new ErrorMsg(ErrorCode.ERR_CAN_NOT_EMBED, "该装备不能进行更改属性");
		}
		ErrorMsg ret = releasePlayerItem(player, slaver, true);
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}
		Random ran = new Random();
		int num = ran.nextInt(100);
		if (num < 30) {
			master.setIsExchange(true);
		} else {
			return Constants.SUCC;
		}
		DBService.commit(master);
		return Constants.SUCC;

	}
	
	/**
	 * 加载玩家道具上的所有镶嵌物品
	 **/
	private static Map<String, List<PlayerItemAppend>> loadPlayerItemAppends(
			int pid) {
		Map<String, List<PlayerItemAppend>> appendMap = new LinkedHashMap<String, List<PlayerItemAppend>>();
		List<PlayerItemAppend> appends = DBManager.queryPlayerItemAppends(pid);
		for (PlayerItemAppend append : appends) {
			Item item = ItemMG.instance.getItem(append.getAppendItemId());
			if (item == null)
				continue;
			List<PlayerItemAppend> list = appendMap.get(append
					.getPlayerItemIdStr());
			if (list == null) {
				list = new LinkedList<PlayerItemAppend>();
				appendMap.put(append.getPlayerItemIdStr(), list);
			}
			append.setItem(item);
			list.add(append);
		}
		return appendMap;
	}
	/**
	 * 加载玩家道具列表
	 **/
	public static void loadPlayerItems(Player player) {
		// 加载镶嵌物
				Map<String, List<PlayerItemAppend>> appendMap = loadPlayerItemAppends(player
						.getId().intValue());
		// 快捷栏
		Map<Integer, PlayerItem> shortcutItems = player.getShortCuts();
		// 加载玩家道具和装备
		List<PlayerItem> items = DBManager.queryPlayerItems(player.getId());
		Iterator<PlayerItem> itr = items.iterator();
		while (itr.hasNext()) {
			PlayerItem pitem = itr.next();
			Item item = ItemMG.instance.getItem(pitem.getItemId());
			if (item == null)
				continue;
			pitem.setItem(item);
			pitem.computeImprove();
			Factory.setPlayerItemBuff(pitem);

			// 设置handler
			Factory.mountHandler(pitem);

			player.getAllItems().put(pitem.getIdStr(), pitem);

			// 装备类物品按位置缓存
			if (item.getType() == Item.ARM_TYPE) {
				List<PlayerItem> arms = player.getArms().get(
						item.getPositionStr());
				if (arms == null) {
					arms = new LinkedList<PlayerItem>();
					player.getArms().put(item.getPositionStr(), arms);
				}
				arms.add(pitem);
			} else {
				Map<String, PlayerItem> nonArms = player.getNonArms().get(
						item.getTypeStr());
				if (nonArms == null) {
					nonArms = new HashMap<String, PlayerItem>();
					player.getNonArms().put(item.getTypeStr(), nonArms);
				}
				nonArms.put(item.getIdStr(), pitem);
			}
			
			// 设置镶嵌列表
			pitem.setAppends(appendMap.get(pitem.getIdStr()));
			
			// 快捷栏道具(暗器为特殊id，仅一个，其他为药品)
			if (pitem.getShortcutId().intValue() > 0) {
				if (pitem.getShortcutId().intValue() == ItemService.PLAYER_DART_SHORTCUT) {
					player.setCurrAQ(pitem);
				} else {
					shortcutItems.put(pitem.getShortcutId(), pitem);
				}
						}
			// 使用中的装备
			if (pitem.getIsUse() == 1) {
				// 如果超过位置容量，则提示用户先卸下装备留出空间
				ItemPosition itemPosition = ItemMG.instance
						.getItemPostion(pitem.getItem().getPosition());
				String position = pitem.getItem().getPositionStr();
				List<PlayerItem> pis = player.getUsedArms().get(position);
				if (pis == null) {
					pis = new LinkedList<PlayerItem>();
					player.getUsedArms().put(position, pis);
				}
				if (pis.size() < itemPosition.getRoom()) {
					pis.add(pitem);
				} else {
					log.error("超过装备位限制:" + player.getId() + ","
							+ pitem.getItem().getName());
				}
			}
		}
	}

	/**
	 * 装载玩家的乾坤袋属性。
	 * 
	 * @param playerItem
	 */
	private static ErrorMsg checkSpecialItem(Player player,
			PlayerItem playerItem) {
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "道具不存在");
		}
		// 乾坤袋增加负重.
		int item_type = playerItem.getItem().getType();
		if (item_type == XINGNANG) {
			PlayerService.reloadPlayerDyn(player);
		}
		return Constants.SUCC;
	}

	/**
	 * 检查player装备的套装信息
	 * 
	 * @param player
	 */
	public static void checkItemSuit(Player player) {
		// 清空套装信息，重新计算
		player.getItemSuits().clear();

		List<PlayerItem> usedArmList = player.getUsedArmsList();
		Map<Integer, List<Integer>> temp = new HashMap<Integer, List<Integer>>();
		for (PlayerItem pi : usedArmList) {
			if (pi.item.getItemSuitId() == 0) {
				continue;
			}
			ItemSuit itemSuit = ItemSuitMG.instance.getItemSuit(pi.item
					.getItemSuitId());
			if (itemSuit == null) {
				log.error("无套装配置，套装id:" + pi.item.getItemSuitId());
				continue;
			}

			List<Integer> pis = temp.get(pi.item.getItemSuitId());
			if (pis == null) {
				pis = new LinkedList<Integer>();
				temp.put(pi.item.getItemSuitId(), pis);
			}
			int itemId = pi.getItem().getId();
			if (!pis.contains(itemId)) {
				pis.add(itemId);
			}

			// 检查是否够成套装
			if (pis.size() >= itemSuit.getItemNum()) {
				player.getItemSuits().put(itemSuit.getId(), itemSuit);
			}
		}
	}

	public static void loadUsedItem(Player player) {
		if (player == null)
			return;
		List<PlayerItem> list = DBManager.queryPlayerUesdItems(player.getId());
		for (PlayerItem pitem : list) {
			Item item = ItemMG.instance.getItem(pitem.getItemId());
			pitem.setItem(item);
			ItemPosition itemPosition = ItemMG.instance.getItemPostion(pitem
					.getItem().getPosition());
			String position = pitem.getItem().getPositionStr();
			List<PlayerItem> pis = player.getUsedArms().get(position);
			if (pis == null) {
				pis = new LinkedList<PlayerItem>();
				player.getUsedArms().put(position, pis);
			}
			if (pis.size() < itemPosition.getRoom()) {
				pis.add(pitem);
			} else {
				log.error("超过装备位限制:" + player.getId() + ","
						+ pitem.getItem().getName());
			}
		}
	}

	/**
	 * 根据给定的铸造材料产生装备并销毁材料
	 * 
	 * @param player
	 *            使用者
	 * @param item
	 *            产生的装备
	 * @param materials
	 *            材料列表
	 * @param amounts
	 *            材料数量
	 **/
	public static ErrorMsg forge(Player player, Item item,
			List<PlayerItem> materials, int[] amounts) {
		// 锻造的道具不存在
		if (materials.size() == 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_FORGE_NO_EXISTS, "材料不存在");
		}

		// 是否满足条件
		ItemForge itemForge = ItemMG.instance.getItemForge(item.getId());
		int copper = itemForge.getCopper();
		ErrorMsg ret = checkMoneyAndMaterial(player, item, materials, copper);
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}

		// 扣钱
		if (!player.consumeInCopper(copper)) {
			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "需要金钱" + copper
					+ ",你没有足够数量");
		}
		// 扣道具
		int index = 0;
		for (; index < materials.size(); index++) {
			ret = releasePlayerItem(player, materials.get(index),
					amounts[index], true);
			if (ret.code != ErrorCode.SUCC) {
				return ret;
			}
		}

		// 增加加装备
		int playerItemId = -1;
		PlayerItem playerItem = null;
		ret = addItem(player, item.getId(), 1, false);
		if (ret.code != ErrorCode.SUCC) {// 有异常
			return ret;
		}

		playerItemId = ret.code;// code为道具ID

		// 装备类
		StringBuffer sb = new StringBuffer();
		if (item.getType().intValue() == Item.ARM_TYPE) {
			// 随机增加一些附加属性 是否有几率增加星
			int starLevel = PropertyBuilder.getInstance().randomPromote();
			if (starLevel > 0) {
				playerItem = player.getAllItems().get(
						String.valueOf(playerItemId));
				if (playerItem != null) {
					for (int i = 0; i < starLevel; i++) {
						ItemProperty property = PropertyBuilder.getInstance()
								.build();
						playerItem.setPromoteProperty(ItemUtil.computeProperty(
								playerItem.getPromoteProperty(), property
										.getId()));
						// 提示附加属性
						sb.append(property.getName()).append("<br/>");
					}
					// 设置升星等级
					playerItem.setStarLevel(Integer.valueOf(starLevel));
					sb.delete(0, sb.length());
					// 更新数据库
					DBService.commit(playerItem);
				}
			}
		}
		return new ErrorMsg(ErrorCode.SUCC, sb.toString(), ret.obj);
	}

	/**
	 * 合成宝石
	 * 
	 * @param player
	 *            玩家
	 * @param item
	 *            合成的宝石
	 * @param materials
	 *            材料列表
	 * @param amounts
	 *            数量
	 * @param type
	 *            类型 0-合成单件,1-合成全部
	 **/
//	public static ErrorMsg compose(Player player, Item item,
//			List<PlayerItem> materials, int[] amounts, int type) {
//		int count = 1;// 默认合成单件宝石
//		ItemForge itemForge = ItemMG.instance.getItemForge(item.getId());
//		if (type == 1)
//			count = composeAmount(player, itemForge);
//		if (count <= 0)
//			return new ErrorMsg(ErrorCode.ERR_SYS, "缺少材料.");
//		//		
//		int tmpCount = 0;
//		int copper;
//		if (itemForge.getCopper() != 0)
//			copper = itemForge.getCopper();
//		else
//			copper = 1000;
//		tmpCount = (int) (player.getCopper() / copper);
//		if (tmpCount <= 0)
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "金钱不够,需要金币"
//					+ itemForge.getGold());
//		else if (tmpCount < count)
//			count = tmpCount;
//
//		// 材料是否可扣除
//		for (PlayerItem pItem : materials) {
//			if (pItem.getBindId() != null && pItem.getBindId() > 0) {
//				return new ErrorMsg(ErrorCode.ERR_BIND, pItem.getItem()
//						.getName()
//						+ "已锁定，需要解锁后才能进行该操作");
//			}
//			if (pItem.getInExchange() != null && pItem.getInExchange()) {
//				return new ErrorMsg(ErrorCode.ERR_BIND, pItem.getItem()
//						.getName()
//						+ "交易中，不能被使用");
//			}
//		}
//		if (item.getId() != 10058 && item.getId() != 10073
//				&& item.getId() != 10013 && item.getId() != 10028
//				&& item.getId() != 10043 && item.getId() != 10483
//				&& item.getId() != 10487 && item.getId() != 10486
//				&& item.getId() != 10485 && item.getId() != 10484) {
//			List<PlayerSkill> lfList = player.getPlayerSkillsByType(12);
//			PlayerSkill con = null;
//			for (PlayerSkill playerSkill : lfList) {
//				con = playerSkill;
//			}
//			if (item.getLevel() > 40 && con.getSectSkill().getId() == 4738) {
//				return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够,锻造初级");
//			}
//			if (item.getLevel() > 80 && con.getSectSkill().getId() == 4739) {
//				return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够,锻造中级");
//			}
//			if (item.getLevel() > 120 && con.getSectSkill().getId() == 4740) {
//				return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够,锻造专家");
//			}
//			if (item.getLevel() > 150 && con.getSectSkill().getId() == 4741) {
//				return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够,锻造大师");
//			}
//		}
//		copper = copper * count;
//		if (!player.consumeInCopper(copper)) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "金钱不够,需要金钱" + copper
//					/ 1000 + "银.");
//		}
//		// 扣道具
//		int index = 0;
//		for (; index < materials.size(); index++) {
//			int recycleAmount = amounts[index] * count;
//			ErrorMsg ret = releasePlayerItem(player, materials.get(index),
//					recycleAmount, true);
//			if (ret.code != ErrorCode.SUCC) {
//				return ret;
//			}
//		}
//		ErrorMsg ret = addItem(player, item.getId(), count, false);
//		if (itemForge.getGdegree() != null && itemForge.getGdegree() > 0) {
//			int d = player.getDegreeDuanzao() + itemForge.getGdegree();
//			player.setDegreeDuanzao(d);
//			DBService.commit(player);
//		}
//		if (ret.code == ErrorCode.SUCC) {
//			Object[] carrayBack = new Object[2];
//			carrayBack[0] = ret.obj;// 宝石
//			carrayBack[1] = count;// 数量
//			ret.obj = carrayBack;
//		}
//		return ret;
//	}

	/**
	 * 合成宝石
	 * 
	 * @param player
	 *            玩家
	 * @param item
	 *            合成的宝石
	 * @param materials
	 *            材料列表
	 * @param amounts
	 *            数量
	 * @param type
	 *            类型 0-合成单件,1-合成全部
	 **/
//	public static ErrorMsg aharmacy(Player player, Item item,
//			List<PlayerItem> materials, int[] amounts, int type) {
//		int count = 1;// 默认合成单件宝石
//		ItemForge itemForge = ItemMG.instance.getItemForge(item.getId());
//		if (type == 1)
//			count = aharmacyAmount(player, itemForge);
//		if (count <= 0)
//			return new ErrorMsg(ErrorCode.ERR_SYS, "缺少材料.");
//		//		
//		int tmpCount = 0;
//		int copper;
//		if (itemForge.getCopper() != 0)
//			copper = itemForge.getCopper();
//		else
//			copper = 1000;
//		tmpCount = (int) (player.getCopper() / copper);
//		if (tmpCount <= 0)
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "金钱不够,需要金币"
//					+ itemForge.getCopper());
//		else if (tmpCount < count)
//			count = tmpCount;
//
//		// 材料是否可扣除
//		for (PlayerItem pItem : materials) {
//			if (pItem.getBindId() != null && pItem.getBindId() > 0) {
//				return new ErrorMsg(ErrorCode.ERR_BIND, pItem.getItem()
//						.getName()
//						+ "已锁定，需要解锁后才能进行该操作");
//			}
//			if (pItem.getInExchange() != null && pItem.getInExchange()) {
//				return new ErrorMsg(ErrorCode.ERR_BIND, pItem.getItem()
//						.getName()
//						+ "交易中，不能被使用");
//			}
//		}
//		List<PlayerSkill> lfList = player.getPlayerSkillsByType(13);
//		PlayerSkill con = null;
//		for (PlayerSkill playerSkill : lfList) {
//			con = playerSkill;
//		}
//		if (itemForge.getDegree() == 300 && con.getSectSkill().getId() == 4746) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够需要制药中级");
//		}
//		if (itemForge.getDegree() == 600 && con.getSectSkill().getId() == 4747) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够需要制药高级");
//		}
//		if (itemForge.getDegree() == 1000 && con.getSectSkill().getId() == 4748) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "技能等级不够需要制药专家");
//		}
//		copper = copper * count;
//		if (!player.consumeInCopper(copper)) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "金钱不够,需要金钱" + copper
//					/ 1000 + "银.");
//		}
//		// 扣道具
//		int index = 0;
//		for (; index < materials.size(); index++) {
//			int recycleAmount = amounts[index] * count;
//			ErrorMsg ret = releasePlayerItem(player, materials.get(index),
//					recycleAmount, true);
//			if (ret.code != ErrorCode.SUCC) {
//				return ret;
//			}
//		}
//		ErrorMsg ret = addItem(player, item.getId(), count, false);
//		if (itemForge.getGdegree() != null && itemForge.getGdegree() > 0) {
//			int d = player.getDegreeZhiyao() + itemForge.getGdegree();
//			player.setDegreeZhiyao(d);
//			DBService.commit(player);
//		}
//		if (ret.code == ErrorCode.SUCC) {
//			Object[] carrayBack = new Object[2];
//			carrayBack[0] = ret.obj;// 宝石
//			carrayBack[1] = count;// 数量
//			ret.obj = carrayBack;
//		}
//		return ret;
//	}


	/**
	 * 根据给定的材料给装备强化等级
	 * 
	 * @param player
	 *            使用者
	 * @param item
	 *            要强化的装备
	 * @param improve
	 *            强化参数
	 * @param materials
	 *            材料列表
	 * @param amounts
	 *            材料数量
	 **/
//	public static ErrorMsg improve(Player player, PlayerItem item,
//			ItemImprove improve, List<PlayerItem> materials, int[] amounts) {
//		// 锻造的道具不存在
//		if (materials.size() == 0) {
//			return new ErrorMsg(ErrorCode.SUCC, "强化的装备不存在");
//		}
//
//		int nextLevel = item.getImproveLevel().intValue() + 1;// 下一等级
//		if (nextLevel <= 0 || nextLevel > MAX_IMPROVE_LEVEL) {
//			return new ErrorMsg(ErrorCode.SUCC, "装备最多能强化" + MAX_IMPROVE_LEVEL
//					+ "级");
//		}
//
//		// 是否满足条件
//		ErrorMsg ret = checkMoneyAndMaterial(player, item.getItem(), materials,
//				improve.getCopper());
//		if (ret.code != ErrorCode.SUCC) {
//			return ret;
//		}
//
//		// 扣钱
//		int copper = improve.getCopper().intValue();
//		if (copper <= 0)
//			return new ErrorMsg(ErrorCode.ERR_SYS, "参数错误");
//		if (!player.consumeInCopper(copper)) {
//			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "强化"
//					+ item.item.getName() + "需要" + copper + "铜币，你没有足够数量");
//		}
//		// 扣道具
//		boolean isExchange = true;
//		int index = 0;
//		boolean flag = false;
//		for (; index < materials.size(); index++) {
//			PlayerItem itm = materials.get(index);
//			if (itm.item.getIsExchange() != null
//					&& itm.item.getIsExchange().booleanValue() == false) {
//				isExchange = false;
//			}
//			if (itm.getBindId() != null && itm.getBindId() > 0) {
//				continue;
//			}
//			if (itm.getInExchange()) {
//				continue;
//			}
//			ret = releasePlayerItem(player, itm, amounts[index], true);
//			if (ret.code != ErrorCode.SUCC) {// 扣除道具失败
//				return new ErrorMsg(ErrorCode.SUCC, ret.text);
//			}
//			flag = true;
//		}
//		if (!flag) {
//			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "扣除道具失败10");
//		}
//		// 成功概率
//		int successfulProbability = improve.getProbability().intValue();
//		int sysProbability = (new Random()).nextInt(100) + 1;
//		if (successfulProbability < sysProbability) {
//			// 强化失败
//			return new ErrorMsg(ErrorCode.ERR_ITEM_IMPROVE_FAIL, "强化失败");
//		}
//
//		// 附加强化属性
//		ImproveProperty property = PropertyBuilder.getInstance()
//				.getImproveProperty(nextLevel, item.getItem().getPosition());
//		if (property == null) {
//			return new ErrorMsg(ErrorCode.ERR_NO_PROPERTY, null);
//		}
//
//		// 附加属性到玩家身上
//		if (item.getIsUse().intValue() == 1) {
//			item.unuse();
//			item.setImproveProperty(property);
//			item.computeImproveProperty();
//			item.use();
//		}
//
//		// 增加强化等级
//		item.setImproveLevel(Integer.valueOf(nextLevel));
//		if (item.getIsExchange()) {
//			item.setIsExchange(isExchange);
//		}
//		DBService.commit(item);
//		if (nextLevel == 8) {
//			ChatService.sayAll(null, "神兵现世," + player.getName() + "强化出+8的武器",
//					ChatService.CHAT_WORLD);
//		}
//		return new ErrorMsg(ErrorCode.SUCC, property.getName());
//	}

	/**
	 * 洗掉装备上面镶嵌的宝石
	 * 
	 * @param player
	 *            玩家
	 * @param playerItem
	 *            装备
	 * @param appendId
	 *            镶嵌宝石
	 **/
//	public static ErrorMsg excavateAppend(Player player, PlayerItem playerItem,
//			int appendId) {
//		List<PlayerItemAppend> appends = playerItem.getAppends();
//		if (appends == null || appends.size() == 0) {
//			return new ErrorMsg(ErrorCode.ERR_NO_EMBED_HOLE, "该装备没有镶嵌物品.");
//		}
//		PlayerItemAppend slaver = null;
//		for (PlayerItemAppend append : appends) {
//			if (append.getId().intValue() == appendId) {
//				slaver = append;
//				break;
//			}
//		}
//		if (slaver == null)
//			return new ErrorMsg(ErrorCode.ERR_NO_EMBED_HOLE, "没有指定要洗掉的宝石.");
//		// 有挖空石?
//		int exStoneId = Constants.getIntValue("item.excstone.id", 0);
//		Item exStoneItem = ItemMG.instance.getItem(exStoneId);
//		if (exStoneItem == null) {
//			return new ErrorMsg(ErrorCode.ERR_SYS, "洗宝石失败,系统配置异常.");
//		}
//		PlayerItem exStone = ItemService.findPlayerItem(player, exStoneItem);
//		if (exStone == null) {
//			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "失败,缺少"
//					+ exStoneItem.getName() + "");
//		}
//		ErrorMsg ret = releasePlayerItem(player, exStone, true);
//		if (ret.code != ErrorCode.SUCC)
//			return ret;
//
//		// 移除宝石
//		appends.remove(slaver);
//		if (playerItem.getIsUse().intValue() == 1) {
//			PlayerDyn dyn = player.getDyn();
//			dyn.changeProperty(slaver.getItem(), -1);
//		}
//		DBService.commitDelete(slaver);
//		return new ErrorMsg(ErrorCode.SUCC, "成功洗掉" + slaver.getItem().getName());
//	}

	// /**
	// * 使用药品,不同种类的药品恢复玩家状态不一样
	// **/
	// public static ErrorMsg useRemedy(Player player, PlayerItem playerItem) {
	// if (playerItem == null) {
	// return new ErrorMsg(ErrorCode.ERR_NO_ITEM,"物品不存在");
	// }
	// if (playerItem.getItem().getHp() > 0) {//恢复体力药品
	// return restoreHp(player, playerItem);
	// }
	// return Constants.SUCC;
	// }

	// /**
	// * 使用道具恢复HP
	// * >=0 实际补充HP
	// * <0 失败 ERR_SYS,ERR_NO_ITEM
	// * */
	// private static ErrorMsg restoreHp(Player player, PlayerItem playerItem) {
	// if (playerItem.getAmount() == 0 ||
	// !playerItem.getItem().getTypeStr().equals(Item.REMEDIES_TYPE_STR)) {
	// return new ErrorMsg(ErrorCode.ERR_NO_ITEM,"道具不存在");
	// }
	// //检查使用等级
	// if (player.getLevel() < playerItem.getItem().getLevel()) {
	// return new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL,"等级不够");
	// }
	// //销毁物品
	// ErrorMsg ret = releasePlayerItem(player, playerItem, false);
	// if (ret.code != ErrorCode.SUCC) {
	// return ret;
	// }
	// //增加玩家加体力
	// int hp = player.addHp(playerItem.getItem().getHp());
	// DBService.commit(player);
	// return new ErrorMsg(hp,null);
	// }

	/**
	 * 获取玩家宠物目前所有的食品
	 * 
	 * @param player
	 * @return
	 */
//	public static List<PlayerItem> getPlayerPetFoods(Player player) {
//		List<PlayerItem> petAllItem = new ArrayList<PlayerItem>();
//		Map<String, PlayerItem> mpi = player.getAllItems();
//		for (Entry<String, PlayerItem> pi : mpi.entrySet()) {
//			PlayerItem playerItem = pi.getValue();
//			Item item = playerItem.getItem();
//			if (item.getType().equals(Item.TYPE_PET_FOOD)
//					&& item != ItemMG.ITEM_SUPER_PET_FOOD
//					&& item != ItemMG.ITEM_SUPER_PET_FOOD_USING) {
//				petAllItem.add(playerItem);
//			}
//		}
//		return petAllItem;
//	}

	/**
	 * 打开箱子
	 **/
//	public static ErrorMsg openBox(Player player, PlayerItem box) {
//		if (box.isBind() == true) {
//			return new ErrorMsg(ErrorCode.ERR_BIND, "请先解除绑定.");
//		}
//		if (box.item == ItemMG.ITEM_CASH_BOX) {// 钱箱
//			return openCashBox(player, box);
//		}
//		if (box.item == ItemMG.ITEM_ORE_STONE) {// 银矿石
//			return epurateStone(player, box);
//		}
//		if (box.item == ItemMG.ITEM_SMALL_PRIZE
//				|| // 中秋礼包
//				box.item == ItemMG.ITEM_MID_PRIZE
//				|| box.item == ItemMG.ITEM_BIG_PRIZE) {
//			return handleFestival(player, box);
//		}
//		// 其他箱子处理
//		return Constants.ERR_SYS;
//	}

	// --------------------------------------------------------------------------
	// 私有方法

	/**
	 * 打开钱箱
	 **/
	private static ErrorMsg openCashBox(Player player, PlayerItem box) {
		// 销毁道具
		ErrorMsg ret = releasePlayerItem(player, box, true);
		if (ret.code != ErrorCode.SUCC)
			return ret;

		// 随机开出银
		int copper = ItemUtil.extractCopper();
		player.addCopper(copper);
		String msg = null;
		if (copper >= 20000) {
			msg = "天啊,【" + player.getName() + "】打开钱箱居然获得了【" + copper / 1000
					+ "】银";
			ChatService.sayAll(null, msg, ChatService.CHAT_WORLD);
		}
		msg = "恭喜你,获得" + copper / 1000 + "银.";
		// 记录日志
		logIdentify.debug("open|" + player.getId() + "|" + player.getName()
				+ "|" + copper / 1000);
		return new ErrorMsg(ErrorCode.SUCC, msg, null);
	}

	/**
	 * 提炼银矿石
	 **/
	private static ErrorMsg epurateStone(Player player, PlayerItem box) {
		// 销毁道具
		ErrorMsg ret = releasePlayerItem(player, box, true);
		if (ret.code != ErrorCode.SUCC)
			return ret;

		// 随机开出银
		int copper = ItemUtil.epurateCopper();
		player.addCopper(copper);
		String msg = null;
		if (copper >= 200000) {
			msg = "天啊,【" + player.getName() + "】从银矿石中居然提炼出来了【" + copper / 1000
					+ "】银";
			ChatService.sayAll(null, msg, ChatService.CHAT_WORLD);
		}
		msg = "恭喜你,获得" + copper / 1000 + "银.";
		// 记录日志
		logIdentify.debug("e|" + player.getId() + "|" + player.getName() + "|"
				+ copper / 1000);
		return new ErrorMsg(ErrorCode.SUCC, msg, null);
	}

	/**
	 * 开启中秋礼包 活动到9月30号结束，30号后就停止兑换。临时添加.
	 **/
	private static ErrorMsg handleFestival(Player player, PlayerItem box) {
		// 临时添加.避免玩家兑换失败，包裹丢失.
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if (month != Calendar.SEPTEMBER) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "中秋活动已经结束，不能兑换.");
		}
		// 空间是否足够
		if (player.getRoom() + 1 > player.getDyn().getMaxRoom()) {
			return new ErrorMsg(ErrorCode.ERR_EQUIP_FULL, "包裹空间不足够,请先扩展包裹.");
		}

		// 销毁道具
		ErrorMsg ret = releasePlayerItem(player, box, true);
		if (ret.code != ErrorCode.SUCC)
			return ret;

		// 随机开出道具
		Item item = IdentifyCenter.instance.burst(box.item);
		if (item == null)
			return Constants.ERR_SYS;

		ret = ItemService.addItem(player, item, 1, true);
		if (ret.code != ErrorCode.SUCC)
			return ret;
		// 记录日志
		logIdentify.debug("b|" + player.getId() + "|" + player.getName() + "|"
				+ item.getId() + "|" + item.getName());
		return new ErrorMsg(ErrorCode.SUCC, "恭喜你,获得【" + item.getName() + "】x1",
				ret.obj);
	}

	/**
	 * 将非装备类的物品放入玩家物品栏
	 **/
	private static ErrorMsg addNonArm(Player player, Item item, int num) {
		// 如果用户已经拥有同类道具，则更新道具数量和当前负重，并提交数据库
		PlayerItem playerItem = findPlayerItem(player, item);
		if (playerItem != null) {
//			if ((ItemMG.ITEM_BIGBOX.getId().equals(playerItem.getItemId()) || ItemMG.ITEM_SMALL_PACKET
//					.getId().equals(playerItem.getItemId()))
//					&& (playerItem.getAmount() + num > ItemMG.ITEM_BIGBOX_MAX_NUM)) {
//				return new ErrorMsg(ErrorCode.ERR_ITEM_MAX_NUM, "超过最大数量");
//			}
			playerItem.setAmount(playerItem.getAmount() + num);
		} else {

			playerItem = Factory.create(player, item);
//			if ((ItemMG.ITEM_BIGBOX.getId().equals(playerItem.getItemId()) || ItemMG.ITEM_SMALL_PACKET
//					.getId().equals(playerItem.getItemId()))
//					&& (num > ItemMG.ITEM_BIGBOX_MAX_NUM)) {
//				return new ErrorMsg(ErrorCode.ERR_ITEM_MAX_NUM, "超过最大数量");
//			}

			// 添加到AllItems中
			player.getAllItems().put(playerItem.getIdStr(), playerItem);
			// 添加到对应分类中,如果不存在该分类,则创建分类
			String type = item.getTypeStr();
			Map<String, PlayerItem> typeMap = player.getNonArms().get(type);
			if (typeMap == null) {
				typeMap = new HashMap<String, PlayerItem>();
				player.getNonArms().put(type, typeMap);
			}
			typeMap.put(item.getIdStr(), playerItem);
			playerItem.setAmount(num);
		}

		// 更新负重
		player.setRoom(player.getRoom() + item.getRoom() * num);

		// 检查是否为乾坤袋.
		checkSpecialItem(player, playerItem);
		// 向数据库提交
		DBService.commit(playerItem);

		return new ErrorMsg(ErrorCode.SUCC, null, playerItem);
	}

	/**
	 * 将装备类物品放入物品栏,装备每次只能加一个
	 * 
	 * @param player
	 *            道具所属玩家
	 * @param item
	 *            道具
	 * @param improveLevel
	 *            强化等级
	 **/
	private static ErrorMsg addArm(Player player, Item item, int improveLevel) {
		// 创建PlayerItem
		// PlayerItem playerItem = createPlayerItem(player, item);
		PlayerItem playerItem = Factory.create(player, item);
		playerItem.setImproveLevel(improveLevel);

		// 添加到AllItem列表中
		player.getAllItems().put(playerItem.getIdStr(), playerItem);

		// 添加到对应装备位置的集合中
		List<PlayerItem> playerItems = player.getArms().get(
				item.getPositionStr());
		if (playerItems == null) {
			playerItems = new LinkedList<PlayerItem>();
			player.getArms().put(item.getPositionStr(), playerItems);
		}
		playerItems.add(playerItem);

		// 更新负重
		player.setRoom(player.getRoom() + item.getRoom());

		// 向数据库提交playerItem
		DBService.commit(playerItem);
		return new ErrorMsg(ErrorCode.SUCC, null, playerItem);
	}
	

	/**
	 * 洗练，将改变后的属性写入临时变量中
	 * 玩家确定后再写入db
	 * @param player
	 * @param itemId
	 * @return	-1：没有附加属性可以洗练
	 * 			-3:金币不足
	 * 			-4：游戏币不够
	 * 			-5：没有需要消耗道具
	 */
	public static int changBuff(Player player,String itemId){
		PlayerItem playerItem = player.getAllItems().get(itemId);
		if (!checkPlayerItemBuffs(playerItem)) {
			return -1;
		}
		//金不足
		if (player.getAdvGold() < Constants.ITEM_XILIAN_GOLD) {
			return -3;
		}
		
		if (player.getCopper() < Constants.ITEM_XILIAN_COPPER) {
			return -4;
		}
		//消耗转换所需要资源
		// TODO 需要
		PlayerItem resItem = findPlayerItem(player, Constants.ITEM_XILIAN_ID);
		if (resItem == null || resItem.getAmount() < 1) {
			return -5;
		}
		player.consumeInAdvGlod(Constants.ITEM_XILIAN_GOLD);
		player.consumeInCopper(Constants.ITEM_XILIAN_COPPER);
		releasePlayerItem(player, resItem, true);
		changeRandBuff(playerItem);
		return ErrorCode.SUCC;
	}
	
	private static boolean checkPlayerItemBuffs(PlayerItem playerItem){
		boolean res = true;
		if (playerItem == null) {
			res = false;
		}else {
			if (playerItem.getItem().getBuffNum() <= 0) {
				res = false;
			}
			String ss = "";
			for (BuffRandVo brv : playerItem.getBuffRands().values()) {
				if (res && "0".equals(brv.getLock())) {
					ss = brv.getLock();
					break;
				}
			}
			if (ss.length() <= 0) {
				res = false;
			}
		}
		return res;
	}
	
	/**
	 * 根据玩家道具进行随机技能的洗练
	 * 绑定的特性不能洗练，洗练出来的特性不能与之前的相同
	 * @param playerItem
	 */
	private static void changeRandBuff(PlayerItem playerItem){
		Map<Integer, BuffRandVo> map = playerItem.getBuffRands();
//		List<BuffRand> keys = null;
		StringBuffer buffrandids = new StringBuffer();
		TreeMap<Integer, BuffRandVo> mapRes = new TreeMap<Integer, BuffRandVo>();
		for (BuffRandVo brv : map.values()) {
			if ("0".equals(brv.getLock())) {
				continue;
			}
			
			mapRes.put(brv.getBuffRand().getId(), brv);
			if (buffrandids.length() > 0) {
				buffrandids.append(Factory.SPLIT_KEY_2);
			}
			buffrandids.append(brv.getBuffRand().getId());
			buffrandids.append(Factory.SPLIT_KEY);
			buffrandids.append(1);
		}
		
		List<BuffRand> buffs = Factory.getBuffRand(mapRes, playerItem.getItem().getBuffNum() - mapRes.size());
		
		if (buffs != null) {
			for (BuffRand br : buffs) {
				if (buffrandids != null) {
					buffrandids.append(Factory.SPLIT_KEY_2);
				}
				buffrandids.append(br.getId());
				buffrandids.append(Factory.SPLIT_KEY);
				buffrandids.append(0);
			}
		}
		playerItem.setTmpBuffRands(mapRes);
	}
	
	public static List<PlayerItem> getBufferRandItems(Player player){
		List<PlayerItem> pItems = null;
		for (PlayerItem pi : player.getAllItems().values()) {
			if (pItems == null) {
				pItems = new ArrayList<PlayerItem>();
			}
			if (checkPlayerItemBuffs(pi)) {
				pItems.add(pi);
			}
		}
		return pItems;
	}
	
	public static int comitBuffRand(Player player,PlayerItem playerItem){
		if (playerItem == null) {
			return -1;
		}
		
		if (playerItem.getTmpBuffRands() == null || playerItem.getTmpBuffRands().size() <= 0) {
			return -2;
		}
		StringBuffer sb = new StringBuffer();
		for (BuffRandVo br : playerItem.getTmpBuffRands().values()) {
			if (sb.length() > 0) {
				sb.append(Factory.SPLIT_KEY_2);
			}
			sb.append(br.getBuffRand().getIdStr());
			sb.append(Factory.SPLIT_KEY);
			sb.append(br.getLock());
		}
		playerItem.setBuffrandids(sb.toString());
		playerItem.setBuffRands(playerItem.getTmpBuffRands());
		DBService.commit(playerItem);
		return ErrorCode.SUCC;
	}

	public static boolean lockBuffRand(PlayerItem playerItem,BuffRand buff){
		if (playerItem == null || buff == null) {
			return false;
		}
		TreeMap<Integer, BuffRandVo> itemBuffers = playerItem.getBuffRands();
		if (itemBuffers == null || itemBuffers.size() <= 0) {
			return false;
		}
		if (!itemBuffers.containsKey(buff.getId())) {
			return false;
		}
		BuffRandVo brv = itemBuffers.get(buff.getId());
		if (brv == null) {
			brv = new BuffRandVo();
			brv.setBuffRand(buff);
		}
		brv.setLock("1");
		itemBuffers.put(buff.getId(), brv);
		playerItem.setBuffRands(itemBuffers);
		StringBuffer sb = new StringBuffer();
		for(BuffRandVo br : itemBuffers.values()){
			if (sb.length() > 0) {
				sb.append(Factory.SPLIT_KEY_2);
			}
			sb.append(br.getBuffRand().getIdStr());
			sb.append(Factory.SPLIT_KEY);
			sb.append(br.getLock());
		}
		playerItem.setBuffrandids(sb.toString());
		DBService.commit(playerItem);
		return true;
		
	}
	
	/**
	 * 将PlayerItem从Player上移除.没有考虑DB同步,非public方法
	 * 
	 * @param player
	 *            使用者
	 * @param playerItem
	 *            用户道具
	 * @return SUCC,ERR_SYS, ERR_USING,ERR_NO_ITEM, ERR_DROP
	 **/
	private static ErrorMsg drop(Player player, PlayerItem playerItem,
			int amount, boolean force) {
		// 用户没有该道具
		if (playerItem == null || playerItem.getAmount() == 0
				|| playerItem.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有相应的装备");
		}
		// 如果不是强制丢弃，需要检查道具的丢弃属性和绑定属性
		if (!force) {
			if (!playerItem.getItem().getDropable()) {
				return new ErrorMsg(ErrorCode.ERR_DROP, "该物品不能丢弃");
			}
			if (playerItem.getBindId() > 0) {
				return new ErrorMsg(ErrorCode.ERR_BIND, "该道具已锁定，需要解锁后才能进行该操作");
			}

		}

		// 不能丢弃正在使用的道具
		if (playerItem.getIsUse() == 1) {
			return new ErrorMsg(ErrorCode.ERR_USING, "物品目前正在使用中");
		}
		Map<String, PlayerItem> allItems = player.getAllItems();
		// 丢弃数量超过拥有数量
		if (playerItem.getAmount() - amount < 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "丢弃的数量不正确");
		}
		// 更新数量
		playerItem.setAmount(playerItem.getAmount() - amount);
		// 更新当前负重,不需要同步到DB,加载用户时会自动计算
		player.setRoom(player.getRoom() - playerItem.getItem().getRoom()
				* amount);

		// 当PlayerItem的amount为0时，要从对应集合中清除
		if (playerItem.getAmount() == 0) {
			allItems.remove(playerItem.getIdStr());

			// 如果是武器要从arms中移除，如果是非武器要从nonArms中移除
			Item item = playerItem.getItem();
			if (item.getType() != Item.ARM_TYPE) {
				Map<String, PlayerItem> typeMap = player.getNonArms().get(
						item.getTypeStr());
				if (typeMap != null) {
					typeMap.remove(item.getIdStr());
				}
			} else {
				List<PlayerItem> ls = player.getArms().get(
						item.getPositionStr());
				if (ls != null) {
					ls.remove(playerItem);
				}
			}
		}
		checkSpecialItem(player, playerItem);
		return Constants.SUCC;
	}

	/**
	 * 买入给定数量道具
	 * 
	 * @param Player
	 * @param Item
	 * @param num
	 *            购买个数
	 * @return SUCC, ERR_SYS, ERR_NO_MONEY
	 **/
	private static ErrorMsg buy(Player player, Item item, int num)
			throws PpseaException {
		if (item == null || num <= 0) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "系统参数错误");
		}

		// 添加道具
		return addItem(player, item.getId(), num);
	}


	/**
	 * 检查玩家是否有足够的金钱和道具是否可以被扣除,如果玩家没有足够的金钱 则返回金钱不够,如果材料不能被扣除则返回材料了被绑定
	 **/
	private static ErrorMsg checkMoneyAndMaterial(Player player, Item item,
			List<PlayerItem> materials, int copper) {

		/*
		 * //只有装备才可以的操作 if(item.getType().intValue() != Item.ARM_TYPE){ return
		 * new ErrorMsg(ErrorCode.ERR_IS_NOT_ARM,"非装备类物品"); }
		 */

		// 金钱是否足够
		if (!player.hasEnoughCopper(copper)) {
			return new ErrorMsg(ErrorCode.ERR_NO_MONEY, "需要金钱" + copper
					+ ",你没有足够数量");
		}

		// 材料是否可扣除
		for (PlayerItem pItem : materials) {
			if (pItem.getBindId() > 0) {
				return new ErrorMsg(ErrorCode.ERR_BIND, pItem.getItem()
						.getName()
						+ "已锁定，需要解锁后才能进行该操作");
			}
		}
		return Constants.SUCC;
	}

	public static int ATTACK_POSITION_INT = 1;
	public static int HOUSE_POSITION_INT = 12;

	/**
	 * 减所有装备耐久
	 * 
	 * @param player
	 * @param attackTimes
	 * @param defenceTimes
	 * @return
	 */
//	public static List<PlayerItem> wasteAll(Player player, int attackTimes,
//			int defenceTimes) {
//		List<PlayerItem> pis = new LinkedList<PlayerItem>();
//
//		List<PlayerItem> usedArms = player.getUsedArmsList();
//		for (PlayerItem playerItem : usedArms) {
//			int times = 0;
//			int position = playerItem.getItem().getPosition();
//			// 马匹不减耐久
//			if (position == HOUSE_POSITION_INT) {
//				continue;
//			}
//			if (position == ATTACK_POSITION_INT) {
//				times = attackTimes;
//			} else {
//				times = defenceTimes;
//			}
//			if (waste(player, playerItem, times)) {
//				pis.add(playerItem);
//			}
//		}
//		return pis;
//	}

	/***
	 * 战败时，身上所有装备耐久减10%
	 * 
	 * @param player
	 * @return: 因耐久为0，卸下的装备列表
	 */
//	public static List<PlayerItem> wasteForDead(Player player) {
//		List<PlayerItem> pis = new LinkedList<PlayerItem>();
//		List<PlayerItem> usedArms = player.getUsedArmsList();
//		for (PlayerItem playerItem : usedArms) {
//			int position = playerItem.getItem().getPosition();
//			// 马匹不减耐久
//			if (position == HOUSE_POSITION_INT) {
//				continue;
//			}
//			// 耐久减10%
////			int times = playerItem.item.getEndure() / 10;
////			if (waste(player, playerItem, times)) {
////				pis.add(playerItem);
////			}
//		}
//		return pis;
//	}

	/**
	 * 道具损耗
	 * 
	 * @param player
	 * @param playerItem
	 *            产生损耗的道具
	 * @param times
	 *            需要减耐久次数，没打一次减一点
	 * @return true：耐久减为0，装备卸下 false：装备未卸下
	 * */
//	private static boolean waste(Player player, PlayerItem playerItem, int times) {
//		boolean ret = false;
//		if (playerItem == null) {
//			return ret;
//		}
//		int n = playerItem.getCurrEndure() - times;
//		// 减耐久,卸装备
//		if (n <= 0) {
//			n = 0;
//			playerItem.unuse();
//			ret = true;
//		}
//
//		playerItem.setCurrEndure(n);
//		DBService.commit(playerItem);
//		return ret;
//	}

	
	//最大可以掉落的道具数量
	private static int MAX_TMP_ITEM = 10;
	/**
	 * 放入用户掉落物品列表
	 * */
	public static int addTempItem(Player player, PlayerItem playerItem,
			boolean isInstance) {
		try {
			// 掉落物品增加随机空个数
//			checkBostBetterProty(playerItem);

			// 在海上
			String key = null;
			/*
			 * if (player.getCityFacility() == null) { key = "s" +
			 * SailService.getRoute(player).getKey(); } else
			 */
			{
				key = "c" + player.getCityFacilityId();
			}
			ArrayList<PlayerItem> temps = null;
			// 副本中
//			if (isInstance) {
//				Team tm = TeamMG.instance.getTeam(player.getTeamId());
//				if (tm == null) {
//					return ErrorCode.ERR_NO_TEAM;
//				}
//				synchronized (tm) {
//					temps = tm.getTempItems().get(key);
//					if (temps == null) {
//						temps = new ArrayList<PlayerItem>();
//						tm.getTempItems().put(key, temps);
//					}
//					temps.add(playerItem);
//					return ErrorCode.SUCC;
//				}
//			} else {
				temps = player.getTempItems().get(key);
				if (temps == null) {
					temps = new ArrayList<PlayerItem>();
					player.getTempItems().put(key, temps);
				}

				if (temps.size() >= MAX_TMP_ITEM) {
					for (int i = temps.size() - 1; i >= 0; i--) {
						PlayerItem pi = (PlayerItem) temps.get(i);
						if (pi == null) {
							temps.remove(i);
						}
					}
				}
				if (temps.size() <= MAX_TMP_ITEM) {
					temps.add(playerItem);
				}
				return ErrorCode.SUCC;
//			}
		} catch (Exception e) {
			log.error("exception addTempItem", e);
		}
		return ErrorCode.ERR_SYS;
	}

	/**
	 * 返回地上没有捡起的道具
	 * 
	 * @param player
	 * @param inInstance
	 * @return
	 */
	public static List<PlayerItem> getTempItems(Player player,
			boolean inInstance) {
		try {
			// 在海上
			String key = "c" + player.getCityFacilityId();
			return player.getTempItems().get(key);
		} catch (Exception e) {
			log.error("exception getTempItems", e);
			return null;
		}
	}

	public static ErrorMsg pickTempItems(Player player,
			List<PlayerItem> tempItems, int id, boolean inInstance)
			throws PpseaException {
		ErrorMsg ret = new ErrorMsg(ErrorCode.ERR_SYS, "系统忙");
		if (tempItems == null || id >= tempItems.size()) {
			return ret;
		}
		synchronized (tempItems) {
			PlayerItem playerItem = tempItems.get(id);
			if (playerItem == null) {
				return ret;
			}
			if (playerItem.getId() == 0) {
				ret = addItem(player, playerItem.getItemId(), 1);
				if (ret.code == ErrorCode.SUCC) {
					PlayerItem pickItem = (PlayerItem) ret.obj;
					if (pickItem != null) {
						copyProperty(playerItem, pickItem);
						DBService.commit(pickItem);
					}
				}
			} else {
				playerItem.setAmount(1);
				playerItem.setPlayerId(player.getId());
				// 检查物品栏是否有空间

				if (playerItem.item.getRoom() > 0
						&& player.getDyn().getMaxRoom() < player.getRoom()
								+ playerItem.item.getRoom()) {
					return new ErrorMsg(ErrorCode.ERR_ROOM_LIMIT,
							"你背包已满，无法拾取道具，你可以去商城购买行囊增加负重");
				}

				List<PlayerItem> playerItems = player.getArms().get(
						playerItem.getItem().getPositionStr());
				if (playerItems == null) {
					playerItems = new LinkedList<PlayerItem>();
					player.getArms().put(playerItem.getItem().getPositionStr(),
							playerItems);
				}
				playerItems.add(playerItem);
				player.getAllItems().put(playerItem.getIdStr(), playerItem);
				// 更新负重
				player.setRoom(player.getRoom()
						+ playerItem.getItem().getRoom());
				// 提交
				DBService.commit(playerItem);

				// 更新player_item_append表
				if (playerItem.getAppends() != null) {
					for (PlayerItemAppend pia : playerItem.getAppends()) {
						pia.setPlayerId(player.getId());
						DBService.commit(pia);
					}

				}
				ret = new ErrorMsg(ErrorCode.SUCC, null, playerItem);
			}

			// TODO：max 可能又临时物品太多的隐患
			// 捡起后将临时列表中的物品设置为null
			if (ret.code == ErrorCode.SUCC) {
				tempItems.set(id, null);
			}
		}
		return ret;
	}

	/**
	 * 捡起掉落物品,如果playerItem.id=0表示捡起的是item，要创建plahyerItem，
	 * 否则表示捡起的playerItem，直接改playerItem的playerid
	 * 注意，现在隐含逻辑是如果playerItem.id>0只能表示是武器类物品
	 * 
	 * @return >0 成功捡起，值为捡起后的playeritem id.
	 * */
	public static ErrorMsg pickTempItems(Player player, int id,
			boolean inInstance) throws PpseaException {
		ErrorMsg ret = new ErrorMsg(ErrorCode.ERR_SYS, "系统忙");
		List<PlayerItem> temps = getTempItems(player, inInstance);
		ret = pickTempItems(player, temps, id, inInstance);
		return ret;
	}

	/**
	 * 计算副本道具的真实id
	 * 
	 * @param index
	 * @return
	 */
	public static int getRealItemIndex(int index) {
		if (index > 0)
			return index;
		if (index <= SEG_INSTANCE_ITEM_BEG && index > SEG_INSTANCE_ITEM_END) {
			return SEG_INSTANCE_ITEM_BEG - index;
		}
		return -1;
	}

	// 自动补血冷却时间
	private static long FEED_INTERVAL_TIME = 5L * 1000;

	/**
	 * 自动补HP: 1.先用使用中的feeder 2.如果feeder用光后，自动使用剩余的feeder 3.体力宝优先
	 * 4.借用currentEndure字段记录当前剩余值
	 * 
	 * @throws PpseaException
	 * */
//	public static ErrorMsg autoRestoreHp(Player player) {
//		long now = System.currentTimeMillis();
//		// 是否触发体力宝,体力值小于70%才补
//		int maxHp = player.getDyn().getMaxHp();
//		if (player.getHp() > maxHp * 0.7) {
//			return Constants.SUCC;
//		}
//
//		// 是否有使用回血符,没有则创建一个
//		if (player.getUsingHPFeeder() == null) {
//			// 创建青铜回血符
//			ErrorMsg ret = createUsingFeeder(player, Constants.ITEM_HP_QT_ID,
//					Constants.ITEM_USING_HP_QT_ID);
//			if (ret.code != ErrorCode.SUCC) {
//				// 创建白银回血符
//				ret = createUsingFeeder(player, Constants.ITEM_HP_BY_ID,
//						Constants.ITEM_USING_HP_BY_ID);
//				if (ret.code != ErrorCode.SUCC) {
//					ret = createUsingFeeder(player, Constants.ITEM_HP_HJ_ID,
//							Constants.ITEM_USING_HP_HJ_ID);
//					if (ret.code != ErrorCode.SUCC) {
//						return ret;
//					}
//				}
//			}
//			player.setUsingHPFeeder((PlayerItem) (ret.obj));
//		}
//
//		// // 数据一致性检查：max 2010-08-05
//		PlayerItem usingFeeder = player.getUsingHPFeeder();
//		// if( player.getAllItems().containsKey(usingFeeder.getIdStr()) == false
//		// ){
//		// log.error("autoRestoreHp usingFeeder not in allItems, player=: " +
//		// player.getId());
//		// player.setUsingHPFeeder(null);
//		// return Constants.SUCC;
//		// }
//
//		// 检查冷却时间
//		if (now - usingFeeder.getLastFeedTime() < FEED_INTERVAL_TIME) {
//			return Constants.SUCC;
//		}
//		int needHp = maxHp - player.getHp();
//		ErrorMsg ret = autoRestoreOnce(player, usingFeeder, needHp);
//		if (ret.code == ErrorCode.SUCC) {
//			usingFeeder.setLastFeedTime(now);
//		}
//		return ret;
//	}


//	private static ErrorMsg createUsingRepairItem(Player player) {
//		ErrorMsg msg = createUsingRepairItem(player,
//				ItemMG.ITEM_AUTO_REPAIR_ARM_USING, ItemMG.ITEM_AUTO_REPAIR_ARM);
//		if (msg.code != ErrorCode.SUCC) {
//			msg = createUsingRepairItem(player,
//					ItemMG.ITEM_BIG_AUTO_REPAIR_ARM_USING,
//					ItemMG.ITEM_BIG_AUTO_REPAIR_ARM);
//		}
//		return msg;
//		/**
//		 * PlayerItem usingRepairItem = findPlayerItem(player,
//		 * ItemMG.ITEM_AUTO_REPAIR_ARM_USING); if (usingRepairItem == null ||
//		 * usingRepairItem.getCurrEndure() <= 0) { PlayerItem repairItem =
//		 * findPlayerItem(player, ItemMG.ITEM_AUTO_REPAIR_ARM); if (repairItem
//		 * == null || repairItem.getAmount() <= 0) { return new
//		 * ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有修复神水"); } else { // 开一个 ErrorMsg
//		 * msg = releasePlayerItem(player, repairItem, 1, true); if (msg.code !=
//		 * ErrorCode.SUCC) { return msg; } msg = ItemService.addItem(player,
//		 * ItemMG.ITEM_AUTO_REPAIR_ARM_USING.getId(), 1, false); if (msg.code !=
//		 * ErrorCode.SUCC) { return msg; } usingRepairItem = (PlayerItem)
//		 * msg.getObj(); } } return new ErrorMsg(ErrorCode.SUCC, "",
//		 * usingRepairItem);
//		 */
//	}

//	private static ErrorMsg createUsingRepairItem(Player player,
//			Item usingItem, Item unuseditem) {
//		PlayerItem usingRepairItem = findPlayerItem(player, usingItem);
//		if (usingRepairItem == null || usingRepairItem.getCurrEndure() <= 0) {
//			PlayerItem repairItem = findPlayerItem(player, unuseditem);
//			if (repairItem == null || repairItem.getAmount() <= 0) {
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有修复神水");
//			} else {
//				// 开一个
//				ErrorMsg msg = releasePlayerItem(player, repairItem, 1, true);
//				if (msg.code != ErrorCode.SUCC) {
//					return msg;
//				}
//				msg = ItemService.addItem(player, usingItem.getId(), 1, false);
//				if (msg.code != ErrorCode.SUCC) {
//					return msg;
//				}
//				usingRepairItem = (PlayerItem) msg.getObj();
//			}
//		}
//		return new ErrorMsg(ErrorCode.SUCC, "", usingRepairItem);
//	}

//	public static ErrorMsg autoRepairArm(Player player, boolean isAuto) {
//		try {
//			if (player.getId() < 0) {
//				return new ErrorMsg(ErrorCode.SUCC, 0);
//			}
//			ErrorMsg msg = createUsingRepairItem(player);
//			if (msg.code != ErrorCode.SUCC) {
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有修复神水");
//			}
//			PlayerItem usingRepairItem = (PlayerItem) msg.getObj();
//			Map<String, List<PlayerItem>> usedArms = player.getUsedArms();
//			List<PlayerItem> armList = usedArms.get(1 + "");// 获取武器装备
//			if (armList == null || armList.size() == 0) {
//				// 没有装备武器，不修复.
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有武器装备，不进行修复");
//			}
//			PlayerItem arm = armList.get(0);
//			if (arm.getItem().getType() != 1) {
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "没有武器装备，不进行修复");
//			}
//			boolean flag = false;
//			if (isAuto) {
//				if (arm.getCurrEndure() < arm.getItem().getEndure() * 80 / 100) {
//					flag = true;
//				}
//			} else {
//				flag = true;
//			}
//			if (flag) {
//				AtomicInteger counter = new AtomicInteger();
//				// System.out.println("begin to repair......");
//				for (int index = 1; index < 12; index++) {
//					List<PlayerItem> posArm = usedArms.get(index + "");
//					if (posArm != null && posArm.size() > 0) {
//						for (PlayerItem pi : posArm) {
//							if (pi.getItem().getId().intValue() == 10650) {
//								continue;
//							}
//							if (pi.getCurrEndure() < pi.getItem().getEndure()) {
//								// System.out.println("begin to repair......"+pi.getItem().getName());
//								log.error("before auto repair arm!!!"
//										+ player.getId() + "|" + pi.getIdStr()
//										+ "|" + pi.getItem().getName() + "|"
//										+ pi.getCurrEndure() + "|"
//										+ usingRepairItem.getCurrEndure());
//								usingRepairItem = autoRepairOneArm(player, pi,
//										usingRepairItem, counter);
//								if (usingRepairItem == null) {
//									log.error("stop auto repair arm!!!"
//											+ player.getId() + "|"
//											+ pi.getIdStr() + "|"
//											+ pi.getItem().getName() + "|"
//											+ pi.getCurrEndure() + "|");
//									break;
//								}
//								log.error("after auto repair arm!!!"
//										+ player.getId() + "|" + pi.getIdStr()
//										+ "|" + pi.getItem().getName() + "|"
//										+ pi.getCurrEndure() + "|"
//										+ usingRepairItem.getCurrEndure());
//							}
//						}
//					}
//					// System.out.println("begin to repair......"+counter.get());
//				}
//				return new ErrorMsg(ErrorCode.SUCC, counter.get());
//			}
//
//		} catch (Exception e) {
//		}
//		return new ErrorMsg(ErrorCode.SUCC, 0);
//	}

//	private static PlayerItem autoRepairOneArm(Player player,
//			PlayerItem needRepairItem, PlayerItem usingRepairItem,
//			AtomicInteger sb) {
//		int armNeed = needRepairItem.getItem().getEndure()
//				- needRepairItem.getCurrEndure();
//		if (usingRepairItem.getCurrEndure() >= armNeed) {
//			needRepairItem.setCurrEndure(needRepairItem.getCurrEndure()
//					+ armNeed);
//			DBService.commit(needRepairItem);
//			usingRepairItem.setCurrEndure(usingRepairItem.getCurrEndure()
//					- armNeed);
//			DBService.commit(usingRepairItem);
//			sb.addAndGet(armNeed);
//		} else {
//			needRepairItem.setCurrEndure(needRepairItem.getCurrEndure()
//					+ usingRepairItem.getCurrEndure());
//			sb.addAndGet(usingRepairItem.getCurrEndure());
//			DBService.commit(needRepairItem);
//			usingRepairItem.setCurrEndure(0);
//			ErrorMsg msg = releasePlayerItem(player, usingRepairItem, 1, true);
//			// log.error("#########autoRepairOneArm######releasePlayerItem########"+usingRepairItem.getIdStr());
//			if (msg.code != ErrorCode.SUCC) {
//				log
//						.error("#########autoRepairOneArm######releasePlayerItem########"
//								+ msg.getText());
//
//				return null;
//			}
//			msg = createUsingRepairItem(player);
//			if (msg.code != ErrorCode.SUCC) {
//				log
//						.error("#########autoRepairOneArm######createUsingRepairItem########"
//								+ msg.getText());
//				return null;
//			}
//			usingRepairItem = (PlayerItem) msg.getObj();
//			usingRepairItem = autoRepairOneArm(player, needRepairItem,
//					usingRepairItem, sb);
//		}
//		return usingRepairItem;
//	}

	/**
	 * 百宝箱自捡起功能
	 * 
	 * @param player
	 * @return
	 * @throws PpseaException
	 */
	public static List<Item> autoPick(Player player) throws PpseaException {
		// PlayerItem itemBox = findPlayerItem(player,ItemMG.ITEM_BOX);

		PlayerItemUsing itemBox = findPlayerUsingItem(player, ItemMG.ITEM_BOX
				.getId().toString());

		List<Item> pickedItems = new LinkedList<Item>();
		if (itemBox == null) {
			// 没有宝莲灯是不允许自检的.
			return pickedItems;
		}

		List<PlayerItem> playerTempItems = getTempItems(player, false);
		if (playerTempItems == null || playerTempItems.size() == 0) {
			// 没有掉落的装备.
			return pickedItems;
		}

		for (int pos = 0; pos < playerTempItems.size(); pos++) {
			PlayerItem playerItem = playerTempItems.get(pos);
			if (playerItem == null) {
				continue;
			}
			ErrorMsg msg = pickTempItems(player, playerTempItems, pos, false);
			if (msg.code != ErrorCode.SUCC) {
				log.error("can not pick item up.itemId="
						+ playerItem.getItemId());
				break;
			}
			pickedItems.add(playerItem.getItem());
		}
		return pickedItems;
	}

	/**
	 * 自动恢复hp或者mp
	 * 
	 * @param pkp
	 * @param usingFeeder
	 *            内力宝或者体力宝
	 * @param needValue
	 *            需要恢复的值
	 * @return
	 */
//	private static ErrorMsg autoRestoreOnce(Player player,
//			PlayerItem usingFeeder, int needValue) {
//		log.info("prepare auto restore:" + usingFeeder.item.getName() + ", id="
//				+ usingFeeder.getId());
//		int newValue = usingFeeder.getCurrEndure() - needValue;
//		int restoreValue = needValue;
//		if (newValue <= 0) {
//			restoreValue = needValue + newValue;
//			ErrorMsg ret = releasePlayerItem(player, usingFeeder, 1, true);
//			if (ret.code != ErrorCode.SUCC) {
//				log.error("autoRestoreOnce release fail:" + ret.text);
//				return ret;
//			}
//			if (usingFeeder.item.getHp() > 0) {
//				player.setUsingHPFeeder(null);
//			} /*else if (usingFeeder.item.getMp() > 0) {
//				player.setUsingMPFeeder(null);
//			}*/
//			log.info("delete :" + usingFeeder.item.getName());
//		} else {
//			usingFeeder.setCurrEndure(newValue);
//			DBService.commit(usingFeeder);
//		}
//
//		// 加体力
//		if (usingFeeder.item.getHp() > 0) {
//			player.addHp(restoreValue);
//		}
//		// 加内力
////		if (usingFeeder.item.getMp() > 0) {
////			player.addMp(restoreValue);
////		}
//		DBService.commit(player);
//		log.info("restore value:" + restoreValue);
//		return new ErrorMsg(ErrorCode.SUCC, null, Integer.valueOf(restoreValue));
//	}

	/**
	 * 创建使开封的体力宝或者内力宝
	 * 
	 * @param pid
	 * @return
	 * @throws pException
	 */
	private static ErrorMsg createUsingFeeder(Player player, int feederId,
			int usingFeederId) {
		PlayerItem feeder = findPlayerItem(player, feederId);
		if (feeder == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "道具不存在");
		}

		ErrorMsg ret = releasePlayerItem(player, feeder, 1, true);
		if (ret.code != ErrorCode.SUCC) {
			return ret;
		}
		// 建立当前feeder记录
		return addItem(player, usingFeederId, 1);
	}


	public static int aharmacyAmount(Player player, ItemForge itemForge) {
		int count = -1;
		String itemMterial = itemForge.getMaterial();
		if (itemForge == null || itemMterial.length() == 0)
			return count;
		String[] itemMterialArray = itemMterial.split("[|]");
		int tmpAmount = 0;
		for (int i = 0; i < itemMterialArray.length; i++) {
			String[] oneItem = itemMterialArray[i].split("[+]");
			Item item = ItemMG.instance.getItem(Integer.parseInt(oneItem[0]));
			int amount = Integer.parseInt(oneItem[1]);
			PlayerItem pItem = findPlayerItem(player, item);
			if (pItem == null)
				return -1;// 缺少材料
			tmpAmount = pItem.getAmount() / amount;
			if (tmpAmount <= 0)
				return -1;// 缺少数量
			if (count < tmpAmount)
				count = tmpAmount;
		}
		return count;
	}

	/**
	 * 用金锭购买礼包
	 * */
	public static ErrorMsg buyPackageInGold(Player player, Goods pkg, int num)
			throws PpseaException {
		if (pkg == null || num <= 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "购买失败,购买物品个数范围为1~1000。");
		}

		// 判断用户是否有足够的负重
		if (pkg.getRoom() > 0) {
			if (player.getRoom() + pkg.getRoom() > player.getDyn().getMaxRoom()) {
				return new ErrorMsg(ErrorCode.ERR_ROOM_LIMIT,
						"购买失败，超过了最大负重，你可以去商城购买行囊增加负重。");
			}
		}

		// 判断用户是否有足够的金币
		int discount = 0;
//		PlayerItem pi = ItemService.findPlayerItem(player, 10504);// 打折卡
//		if (pi != null && pi.getAmount() > 0) {
//			if (!((pi.getBindId() != null && pi.getBindId() > 0) || (pi
//					.getInExchange()))) {
//				discount = pi.getItem().getEndure();
//			}
//		}
		int needGold = 0;
		if (discount > 0) {
			needGold = pkg.getGold() * num * discount / 100;
		} else {
			needGold = pkg.getGold() * num;
		}

		if (needGold > player.getGold()) {
			// return ErrorCode.ERR_NO_GOLD;
			throw new NoMoneyException("no money");
		}
//		if (discount > 0) {
//			ErrorMsg msg = ItemService.releasePlayerItem(player, pi, 1, true);
//			if (msg.code != ErrorCode.SUCC) {
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "购买失败，使用打折卡失败!"
//						+ msg.getText());
//			}
//		}

		// 加道具
		for (GoodsItem pkgItem : pkg.getGoodsItems()) {
			ErrorMsg ret = ItemService.addItem(player, pkgItem.getItemId(),
					pkgItem.getAmount() * num);
			if (ret.getCode() < 0) {
				return ret;
			}
		}

		// 扣金币
		player.consumeInGlod(needGold);
		DBService.commit(player);
		return new ErrorMsg(ErrorCode.SUCC, needGold / 100 + "." + needGold
				% 100, discount);
	}

	/**
	 * 用金票购买礼包
	 * */
	public static ErrorMsg buyPackageInAdvGold(Player player, Goods pkg, int num)
			throws PpseaException {
		if (pkg == null || num <= 0 || num > MAX_BUY_NUM) {
			return new ErrorMsg(ErrorCode.ERR_SYS, "购买失败,购买物品个数范围为1~1000。");
		}
		// 判断用户是否有足够的负重
		if (pkg.getRoom() > 0) {
			if (player.getRoom() + pkg.getRoom() > player.getDyn().getMaxRoom()) {
				return new ErrorMsg(ErrorCode.ERR_ROOM_LIMIT,
						"购买失败，超过了最大负重，你可以去商城购买行囊增加负重。");
			}
		}

		// 判断用户是否有足够的金币
		int discount = 0;
//		PlayerItem pi = ItemService.findPlayerItem(player, 10504);// 打折卡
//		if (pi != null && pi.getAmount() > 0) {
//			if (!((pi.getBindId() != null && pi.getBindId() > 0) || (pi
//					.getInExchange()))) {
//				discount = pi.getItem().getEndure();
//			}
//		}
		int needGold = 0;
		if (discount > 0) {
			needGold = pkg.getGold() * num * discount / 100;
		} else {
			needGold = pkg.getGold() * num;
		}
		// int needGold = pkg.getGold() * num;
		if (needGold > player.getAdvGold()) {
			throw new NoMoneyException("no money");
		}

//		if (discount > 0) {
//			ErrorMsg msg = ItemService.releasePlayerItem(player, pi, 1, true);
//			if (msg.code != ErrorCode.SUCC) {
//				return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "购买失败，使用打折卡失败!"
//						+ msg.getText());
//			}
//		}

		// 加道具
		for (GoodsItem pkgItem : pkg.getGoodsItems()) {
			ErrorMsg ret = ItemService.addItem(player, pkgItem.getItemId(),
					pkgItem.getAmount() * num);
			if (ret.getCode() < 0) {
				return ret;
			}
		}

		// 扣元宝
		player.consumeInAdvGlod(needGold);
		DBService.commit(player);
		return new ErrorMsg(ErrorCode.SUCC, needGold / 100 + "." + needGold
				% 100, discount);
	}

	/**
	 * list all used items which the current endure less than 5 percent of the
	 * normal value.
	 * 
	 * @param player
	 * @return
	 */
//	public static List<PlayerItem> listLessEndureItem(Player player) {
//		Map<String, List<PlayerItem>> arms = player.getArms();
//		List<PlayerItem> piList = new ArrayList<PlayerItem>();
//		for (Map.Entry<String, List<PlayerItem>> entry : arms.entrySet()) {
//			List<PlayerItem> pitemList = entry.getValue();
//			for (PlayerItem pi : pitemList) {
//				if ("1".equals(pi.getIsUse().toString())) {
//					if (pi.getCurrEndure() <= pi.getItem().getEndure() * 5 / 100) {
//						piList.add(pi);
//					}
//				}
//			}
//		}
//		return piList;
//	}

	/**
	 * 返回道具的强化的升星名称
	 * 
	 * @param playerItem
	 *            道具
	 **/
	public static String getBeautifulName(PlayerItem playerItem) {
		String name = "";
		int starLevel = playerItem.getStarLevel();
		if (starLevel > 0 && starLevel <= MAX_STAR_LEVEL)
			name += starName[starLevel];
		name = name + playerItem.item.getName();
		int improveLevel = playerItem.getImproveLevel();
		if (improveLevel > 0 && improveLevel <= MAX_IMPROVE_LEVEL) {
			name = name + "+" + improveLevel;
		}
		name = name + "(" + playerItem.getItem().getLevel() + "级)";
		return name;
	}

	/**
	 * 鉴定道具
	 * 
	 * @param player
	 * @param PlayerItem
	 *            ：指定要鉴定的未知道具
	 * @return >0: 鉴定出的道具加入用户后的playeritemId <0: 鉴定失败
	 * */
//	public static ErrorMsg identify(Player player, PlayerItem pi) {
//		// 是否有鉴定卷轴
//		Item scroll = ItemMG.IDENTIFY_SCROLL;
//		PlayerItem playerScroll = findPlayerItem(player, scroll);
//		if (playerScroll == null || playerScroll.getAmount() <= 0) {
//			return new ErrorMsg(ErrorCode.ERR_NO_SCROLL, "缺少鉴定卷轴,请到【商城】购买");
//		}
//
//		// 该道具是否是未知道具
//		if (pi == null
//				|| pi.getItemId().intValue() != ItemMG.UNKOWN_ITEM.getId()
//						.intValue()) {
//			return new ErrorMsg(ErrorCode.ERR_SYS, "只能鉴定未知道具");
//		}
//
//		// 该道具是否在使用
//		if (pi.getIsUse() == 1) {
//			return new ErrorMsg(ErrorCode.ERR_USING, "该道具正在使用中");
//		}
//
//		if (playerScroll.checkBindOther(player.getId()) == true) {
//			return new ErrorMsg(ErrorCode.ERR_BIND, "卷轴已经锁定,请先解除锁定.");
//		}
//
//		if (pi.checkBindOther(player.getId()) == true) {
//			return new ErrorMsg(ErrorCode.ERR_BIND, pi.item.getName()
//					+ "已经锁定,请先解除锁定.");
//		}
//
//		// 给出鉴定结果，将鉴定后的道具加入用户。
//		Item item = IdentifyCenter.instance.fetchItem();
//		int value = 0;
//		ItemIdentify ii = IdentifyCenter.ITEM_VALUE_MAP.get(item.getId());
//		if (ii == null)
//			return Constants.ERR_SYS;
//
//		// 判断包裹是否
//		int num = ii.getAmount();
//		if (player.getRoom() + num > player.getDyn().getMaxRoom()) {
//			return new ErrorMsg(ErrorCode.ERR_ROOM_LIMIT, "包裹容量不够,鉴定失败.");
//		}
//
//		// 回收卷轴和未知道具
//		ErrorMsg ret = ItemService.releasePlayerItem(player, pi, 1, true);
//		if (ret.code != ErrorCode.SUCC) {
//			return ret;
//		}
//		ret = ItemService.releasePlayerItem(player, playerScroll, 1, true);
//		if (ret.code != ErrorCode.SUCC) {
//			return ret;
//		}
//
//		// 通告
//		value = ii.getPrice();
//		String msg = "【" + player.getName() + "】鉴定出【" + item.getName() + "x"
//				+ num + "】";
//		ChatService.sendMessageSystem(player.getId(), msg);
//		// 是否通告
//		if (ii.getNotice().shortValue() == 1) {
//			msg = "恭喜【" + player.getName() + "】鉴定出【" + item.getName() + "x"
//					+ num + "】";
//			ChatService.sayAll(null, msg, ChatService.CHAT_WORLD);
//		}
//
//		// i|PID|道具id|道具名称|道具银价|道具估值（金，元）
//		logIdentify.debug("i|" + player.getId() + "|" + item.getId() + "|"
//				+ item.getName() + "|" + item.getPrice() + "|" + value);
//		return addItem(player, item.getId(), num);
//	}

	/**
	 * 检查装备是否可以爆出一些好的特殊属性,如果可以爆出则直接附加到道具上面 目前可以爆出打孔,强化,升星的装备
	 * 
	 * @param playerItem
	 *            掉落的装备
	 **/
//	private static void checkBostBetterProty(PlayerItem playerItem) {
//		Item item = playerItem.item;
//		int bost = 0;
//		// 带孔装备
//		ItemBetterProty holeProty = ItemMG.instance.getItemBetterProty(ItemUtil
//				.convertUseType(item), item.getLevel(),
//				ItemBetterProty.TYPE_OUT_HOLE);
//		if (holeProty != null && playerItem.getCurrHoleAmount() == 0) {
//			bost = ItemUtil.bost(holeProty.getPrbtyArray());
//			if (bost > 0)
//				playerItem.setCurrHoleAmount(bost);
//		}
//		// 带强化装备
//		ItemBetterProty ipveProty = ItemMG.instance.getItemBetterProty(ItemUtil
//				.convertUseType(item), item.getLevel(),
//				ItemBetterProty.TYPE_OUT_IPVE);
//		if (ipveProty != null
//				&& playerItem.getImproveLevel() <= ItemService.MAX_IMPROVE_LEVEL) {
//			bost = ItemUtil.bost(ipveProty.getPrbtyArray());
//			if (bost > 0) {
//				playerItem.setImproveLevel(bost);
//				ImproveProperty property = PropertyBuilder.getInstance()
//						.getImproveProperty(bost,
//								playerItem.getItem().getPosition());
//				if (property != null)
//					playerItem.setImproveProperty(property);
//			}
//		}
//		// 带星装备
//		ItemBetterProty starProty = ItemMG.instance.getItemBetterProty(ItemUtil
//				.convertUseType(item), item.getLevel(),
//				ItemBetterProty.TYPE_OUT_STAR);
//		if (starProty != null
//				&& playerItem.getStarLevel() < ItemService.MAX_STAR_LEVEL) {
//			bost = ItemUtil.bost(starProty.getPrbtyArray());
//			if (bost > 0) {
//				String starProperty = "";
//				for (int i = 0; i < bost; i++) {
//					// 随机增加属性
//					ItemProperty property = PropertyBuilder.getInstance()
//							.build();
//					starProperty = ItemUtil.computeProperty(starProperty,
//							property.getId());
//					playerItem.appendPromoteProperty(property);
//				}
//				playerItem.setPromoteProperty(starProperty);
//				playerItem.setStarLevel(bost);
//			}
//		}
//	}

	/**
	 * 复制两个道具的一些特殊属性
	 * 
	 * @param srcItem
	 *            源道具
	 * @param dstItem
	 *            目标道具
	 **/
	private static void copyProperty(PlayerItem srcItem, PlayerItem dstItem) {
		dstItem.setCurrHoleAmount(srcItem.getCurrHoleAmount());
		dstItem.setImproveLevel(srcItem.getImproveLevel());
		dstItem.computeImprove();
		dstItem.setStarLevel(srcItem.getStarLevel());
		dstItem.setPromoteProperty(srcItem.getPromoteProperty());
		dstItem.setItemProperties(srcItem.getItemProperties());
		dstItem.setAppends(srcItem.getAppends());
	}

	public static List<ItemForge> listComposeItemsByType(Integer type) {
		List<ItemForge> itemList = new ArrayList<ItemForge>();
		Map<Integer, ItemForge> all = ItemMG.instance.getAllItemForges();
		for (Map.Entry<Integer, ItemForge> entry : all.entrySet()) {
			try {
				ItemForge tif = entry.getValue();
				if (type.intValue() == tif.getType()) {
					Item item = ItemMG.instance.getItem(tif.getItemId());
					if (item != null) {
						tif.setItem(item);
					}
					itemList.add(tif);
				}
			} catch (Exception e) {
			}
		}
		return itemList;
	}

	
	/**
	 * 获取玩家合成所需要的材料
	 * @param player
	 * @param compose_item_id
	 * @return
	 */
	public static ComposeVo getPlayerComposeVo(Player player,int compose_item_id){
		ComposeItem ci = ResourceCache.instance.getComposeItem().get(compose_item_id);
		if (ci == null) {
			return null;
		}

		ComposeVo cv = new ComposeVo();
		cv.setCompose(copy(player, ci.getCompose()));
		cv.setCopper(ci.getNeedCopper());
		cv.setGold(ci.getNeedGold());
		return cv;
	}
	
	/**
	 * 根据资源copy出一个临时玩家自身的对象
	 * @param player
	 * @param srcComPose
	 * @return
	 */
	private static List<Compose> copy(Player player,List<Compose> srcComPose){
		List<Compose> composes = new ArrayList<Compose>();
		Compose c = null;
		PlayerItem pi = null;
		for (Compose co : srcComPose) {
			c = new Compose();
			c.setItemId(co.getItemId());
			c.setName(co.getName());
			c.setNeedNum(co.getNeedNum());
			//如果是装备呢？
			pi = findPlayerItem(player, c.getItemId());
			if (pi == null) {
				c.setAmount(0);
			}else {
				//如果是装备
				if (pi.getItem().getType() == Item.ARM_TYPE) {
					List<PlayerItem> list = getPlayerItemByItemId(player, pi.getItemId());
					c.setAmount(list.size());
				}else {
					c.setAmount(pi.getAmount());
				}
			}
			composes.add(c);
		}
		return composes;
	}

	/**
	 * 根据道具id获取玩家道具数量，绑定、使用、正在交易的不计算在内
	 * @param player
	 * @param item_id
	 * @return
	 */
	public static List<PlayerItem> getPlayerItemByItemId(Player player,int item_id){
		List<PlayerItem> list = new ArrayList<PlayerItem>();
		Map<String, PlayerItem> allItems = player.getAllItems();
		for (PlayerItem pi : allItems.values()) {
			if (pi.getItemId().intValue() == item_id && checkPlayerItem(pi)) {
				list.add(pi);
			}
		}
		return list;
	} 
	
	/**
	 * 根据玩家道具判断是否正在使用，绑定，正在交易
	 * @param playerItem
	 * @return
	 */
	private static boolean checkPlayerItem(PlayerItem playerItem){
		if (playerItem == null) {
			return true;
		}
		return (playerItem.getIsUse() == 0  
				&& (playerItem.getBindId() == null || playerItem.getBindId()==0) 
				&& !playerItem.getInExchange());
	}
	
	public static boolean copose(Player player,int compose_item_id,CommandResult result){
		return false;
	}
	
	public static void setItemPositions(CommandResult result){
		result.setVO("label_positions", ItemMG.instance.getItemPositions());
	}
	
	/********************************** 分解道具 **************************************/
	/**
	 * 根据玩家、玩家道具获取分解资源
	 * @param player
	 * @param playerItem
	 * @return
	 */
	public static ItemDecompose getItemDescomposeByPlayerItem(Player player,PlayerItem playerItem){
		if (playerItem == null || playerItem.getImproveLevel() <= 0) {
			return null;
		}
		ItemDecompose iDecompose = null;
		List<ItemDecompose> decomposeList = ResourceCache.instance.getItemDecomposeMap().get(playerItem.getImproveLevel());
		if (decomposeList != null) {
			for (ItemDecompose id : decomposeList) {
				if (player.getLevel() >= id.getStartLevel()) {
					if (playerItem.getItem().getLevel() >= id.getItemLevelMin() && playerItem.getItem().getLevel() <= id.getItemLevelMax()) {
						iDecompose = id;
						break;
					}
				}
			}
		}
		
		return iDecompose;
	}
	
	/**
	 * 确认分解
	 * @param player
	 * @param playerItem
	 * @return
	 * 			-1：道具不能分解
	 * 			-2：金不足
	 * 			-3：银不足
	 * 			-4：道具删除失败
	 * 			0：成功
	 */
	public static ErrorMsg doItemDescompose(Player player,PlayerItem playerItem){
		if (playerItem == null) {
			return new ErrorMsg(-1, "道具不能分解", null);
		}
		ItemDecompose decompose = getItemDescomposeByPlayerItem(player, playerItem);
		if (decompose == null) {
			return new ErrorMsg(-1, "道具不能分解", null);
		}
	
		if (player.getAdvGold() < decompose.getGold()) {
			return new ErrorMsg(-1, "金不足", null);
		}
		if (player.getCopper() < decompose.getColor()) {
			return new ErrorMsg(-1, "银不足", null);
		}
		ErrorMsg res = releasePlayerItem(player, playerItem, true);
		if (res.code != ErrorCode.SUCC) {
			return new ErrorMsg(-1, res.getText(), null);
		}
		
		player.consumeInAdvGlod(decompose.getGold());
		player.consumeInCopper(decompose.getCopper());
		
		Item item  = null;
		for (ItemVo iv : decompose.getItemVos()) {
			item = ItemMG.instance.getItem(iv.getId());
			if (item != null) {
				if (item.getType() == Item.ARM_TYPE) {
					for(int i = 0 ; i < iv.getNum(); i ++){
						addItem(player, item, 1, false);
					}
				}else {
					addItem(player, item, iv.getNum(), false);
				}
			}
			
		}
		return new ErrorMsg(0, "分解成功", decompose);
	}
	
	/**
	 * 获取玩家可以分解的道具
	 * @param player
	 * @return
	 */
	public static List<PlayerItem> getPlayerItemDescompose(Player player){
		List<PlayerItem> arms = new ArrayList<PlayerItem>();
		for (PlayerItem pi : player.getAllItems().values()) {
			if (getItemDescomposeByPlayerItem(player, pi) != null) {
				arms.add(pi);
			}
		}
		return arms;
	}
	
	/********************************** 分解道具 **************************************/
	
	/********************************** 强化道具 **************************************/
	
	public static int MAX_IMPROVE_LEVEL = 0;
	/**
	 * 获取玩家可以强化的装备
	 * @param player
	 * @return
	 */
	public static List<PlayerItem> getImproveItem(Player player) {
		Map<String, List<PlayerItem>> map = player.getArms();
		List<PlayerItem> improves = new ArrayList<PlayerItem>();
		for (List<PlayerItem> list : map.values()) {
			if (list != null && list.size() > 0) {
				for (PlayerItem pi : list) {
					if (pi.getItem().getType().intValue() == Item.ARM_TYPE
							&& pi.getIsUse() == 1
							&& pi.getImproveLevel() < MAX_IMPROVE_LEVEL
							&& pi.getItem().getPosition() != Item.POS_HP) {
						improves.add(pi);
					}
				}
			}
		}
		return improves;
	}
	
	/**
	 * 根据道具强化等级获取下一等级
	 * @param playerItem
	 * @return
	 */
	public static int getNextImproveLevel(PlayerItem playerItem){
		if (playerItem == null 
				|| playerItem.getImproveLevel() >= MAX_IMPROVE_LEVEL
				|| playerItem.getItem().getType() != Item.ARM_TYPE) {
			return 0;
		}
		return playerItem.getImproveLevel() + 1;
	}
	
	/**
	 * 幸运石
	 */
	private static Item FORTUNE_ITEM = ItemMG.instance.getItem(Constants.ITEM_FORTUNE);
	
	/**
	 * 强化
	 * @param player
	 * @param playerItem
	 * @param is_user_spec_item 是否使用可选物品
	 * @param fortune 使用幸运石个数，0时不使用
	 * @return
	 */
	public static ErrorMsg improve(Player player , PlayerItem playerItem,boolean is_user_spec_item,int fortune){
		int next_level = getNextImproveLevel(playerItem);
		if (next_level <= 0) {
			return new ErrorMsg(-1, "该装备不能强化！");
		}
		ItemImprove improve = ResourceCache.instance.getImproves().get(next_level);
		if (player.getAdvGold() < improve.getGold()) {
			return new ErrorMsg(-2, "金不足！");
		}
		if (player.getCopper() < improve.getCopper()) {
			return new ErrorMsg(-3, "银不足！");
		}
		
		Map<Integer, Integer> releasePlayerItems = new HashMap<Integer, Integer>();
		//检查必须道具
		List<ItemVo> needItems = improve.getNeedItemVos();
		PlayerItem pi = null;
		for(ItemVo iv : needItems){
			pi = findPlayerItem(player, iv.getId());
			if (pi == null || pi.getAmount() < iv.getNum()) {
				return new ErrorMsg(-iv.getId(), "道具" + iv.getName() + "不足！");
			}
			releasePlayerItems.put(pi.getId(), iv.getNum());
		}
		//有必要时检查可以选择使用的道具
		List<ItemVo> spetItems = improve.getSpecItemVos();
		if (is_user_spec_item) {
			for (ItemVo iv : spetItems) {
				pi = findPlayerItem(player, iv.getId());
				if (pi == null || pi.getAmount() < iv.getNum()) {
					return new ErrorMsg(-iv.getId(), "道具" + iv.getName() + "不足！");
				}
				releasePlayerItems.put(pi.getId(), iv.getNum());
			}
		}
		
		if (fortune > 0) {//使用幸运石
			PlayerItem pp = findPlayerItem(player, FORTUNE_ITEM);
			if (pp == null || pp.getAmount() < fortune) {
				return new ErrorMsg(-FORTUNE_ITEM.getId(), "道具" + FORTUNE_ITEM.getName() + "不足！");
			}
			releasePlayerItems.put(pp.getId(), fortune);
		}
		//检查通过后扣钱和道具
		player.consumeInAdvGlod(improve.getGold());
		player.consumeInCopper(improve.getCopper());
		for (int player_item_id : releasePlayerItems.keySet()) {
			pi = player.getAllItems().get(player_item_id + "");
			releasePlayerItem(player, pi, releasePlayerItems.get(player_item_id), true);
		}
		//扣完钱和道具，给玩家升级，如果有保护，则不降级
		int res = ResourceCache.instance.random.nextInt(100);
		if (res < improve.getProbability()) {
			playerItem.setImproveLevel(improve.getImproveLevel());
			DBService.commit(playerItem);
			// TODO 发公告？
			return new ErrorMsg(ErrorCode.SUCC, "强化成功！", playerItem);
		}else {
			if(!is_user_spec_item){
				int down_pre = ResourceCache.instance.random.nextInt(100);
				int down_level = 0;
				if (down_pre < improve.getDownProbability()) {
					down_level = ResourceCache.instance.random.nextInt(improve.getDownLevel()) + 1;
					if (playerItem.getImproveLevel() - down_level <= 0) {
						playerItem.setImproveLevel(0);
					}else {
						playerItem.setImproveLevel(playerItem.getImproveLevel() - down_level);
					}
				}
				String msg = "强化失败！";
				if (down_level > 0) {
					msg = "强化失败！使道具降低" + down_level + "级！";
				}
				return new ErrorMsg(1, msg, playerItem);
			}else {
				return new ErrorMsg(1, "强化失败！", playerItem);
			}
		}
		
	}
	
	public static ItemVo getNewItemVo(Player player,ItemVo itemVo){
		if (itemVo == null ) {
			return null;
		}
		ItemVo tmp = new ItemVo();
		tmp.setId(itemVo.getId());
		tmp.setName(itemVo.getName());
		tmp.setNum(itemVo.getNum());
		tmp.setDesception(itemVo.getDesception());
		PlayerItem pi = findPlayerItem(player, tmp.getId());
		if (pi != null) {
			tmp.setPlayerNum(pi.getAmount());
		}else {
			tmp.setPlayerNum(0);
		}
		return tmp;
	}
	
	public static boolean isAutImprove(Player player){
		return !SystemSettingCmd.isFlagSet(player.getSettingFlag(), SystemSettingCmd.FLAG_IMPROVE);
	}
	/********************************** 强化道具 **************************************/
}