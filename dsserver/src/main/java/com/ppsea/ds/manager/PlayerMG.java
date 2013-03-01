package com.ppsea.ds.manager;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.InstanceSet;
import com.ppsea.ds.data.dao.MessageDAO;
import com.ppsea.ds.data.dao.MessageDAOImpl;
import com.ppsea.ds.data.dao.NpcInteractionDAO;
import com.ppsea.ds.data.dao.NpcInteractionDAOImpl;
import com.ppsea.ds.data.dao.PlayerMissionDAO;
import com.ppsea.ds.data.dao.PlayerMissionDAOImpl;
import com.ppsea.ds.data.dao.PlayerMissionPastDAO;
import com.ppsea.ds.data.dao.PlayerMissionPastDAOImpl;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Gm;
import com.ppsea.ds.data.model.GmLog;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Message;
import com.ppsea.ds.data.model.MessageExample;
import com.ppsea.ds.data.model.Mission;
import com.ppsea.ds.data.model.MissionType;
import com.ppsea.ds.data.model.NpcInteraction;
import com.ppsea.ds.data.model.NpcInteractionExample;
import com.ppsea.ds.data.model.NpcRandomWords;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerEnemy;
import com.ppsea.ds.data.model.PlayerFishing;
import com.ppsea.ds.data.model.PlayerItemAppend;
import com.ppsea.ds.data.model.PlayerKf;
import com.ppsea.ds.data.model.PlayerLevel;
import com.ppsea.ds.data.model.PlayerMission;
import com.ppsea.ds.data.model.PlayerMissionExample;
import com.ppsea.ds.data.model.PlayerMissionPast;
import com.ppsea.ds.data.model.PlayerMissionPastExample;
import com.ppsea.ds.data.model.PlayerPay;
import com.ppsea.ds.data.model.PlayerStoreRoom;
import com.ppsea.ds.data.model.PlayerUpgrade;
import com.ppsea.ds.exception.BookMarkException;
import com.ppsea.ds.exception.CacheFullException;
import com.ppsea.ds.exception.LockException;
import com.ppsea.ds.service.ActiveService;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.LakeWatcherService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.util.LoggerHelper;

/**
 * 用户资源管理器
 * */
public class PlayerMG {
	private static final Logger log = Logger.getLogger(PlayerMG.class);
	public static PlayerMG instance = new PlayerMG();
	private static Map<String, String> applemap = new ConcurrentHashMap<String, String>();
	public static int MAX_PLAYER_LEVEL = 80; // 开放最大等级150级
	public static int MAX_EXP; // 最大等级对应经验值
	private long lastCleanOfflineTime = 0;
	private int onlineNum = 0;
	private static List<GmLog> gmlogList = new LinkedList<GmLog>();
	public List<GmLog> getGmlogList() {
		return gmlogList;
	}

	public void setGmlogList(List<GmLog> gmlogList) {
		this.gmlogList = gmlogList;
	}

	private Map<Integer, Player> map = new ConcurrentHashMap<Integer, Player>(
			Constants.PLAYER_CACHE_LIMIT);
	private Map<Integer, PlayerLevel> playerLevelMap = new HashMap<Integer, PlayerLevel>();

	private Map<Integer, Gm> gmMap = new HashMap<Integer, Gm>();

	private Map<Integer, PlayerUpgrade> playerUpgradeInfoMap = new HashMap<Integer, PlayerUpgrade>();

	// 上报数据
	private Map<Integer, Player> reportMap = new ConcurrentHashMap<Integer, Player>();
	
//	public void addReport(Player player) {
//		Player p = new Player();
//		p.setId(player.getId());
//		p.setUserId(player.getUserId());
//		p.setName(player.getName());
//		p.setLevel(player.getLevel());
//		reportMap.put(player.getId(), p);
//	}

	public Map<Integer, Player> getReportMap() {
		return reportMap;
	}

	public void clearReportMap() {
		reportMap.clear();
	}

	private PlayerMG() {
	}

	public void init() {
		loadPlayerLevel();
		loadUpgradeInfo();
		MAX_EXP = playerLevelMap.get(MAX_PLAYER_LEVEL).getExp();
	}

	public void loadPlayerLevel() {
		List<PlayerLevel> list = DBManager.queryAllPlayerLevel();
		for (PlayerLevel playerLevel : list) {
			playerLevelMap.put(playerLevel.getLevel(), playerLevel);
		}
	}

//	public void loadPlayerstore(Player player) {
//		List<PlayerStoreRoom> list = DBManager.queryAllStoreRoom(player);
//		Map<String, List<PlayerItemAppend>> appendMap = loadPlayerStoreAppends(player.getId().intValue());
//		for (PlayerStoreRoom playerStoreRoom : list) {
//			playerStoreRoom.setAppendlist(appendMap.get(playerStoreRoom.getId().toString()));
//		}
//		player.setStorelist(list);
//	}
//	private static Map<String, List<PlayerItemAppend>> loadPlayerStoreAppends(
//			int pid) {
//		Map<String, List<PlayerItemAppend>> appendMap = new LinkedHashMap<String, List<PlayerItemAppend>>();
//		List<PlayerItemAppend> appends = DBManager.queryPlayerItemAppends(pid);
//		for (PlayerItemAppend append : appends) {
//			Item item = ItemMG.instance.getItem(append.getAppendItemId());
//			if (item == null)
//				continue;
//			List<PlayerItemAppend> list = appendMap.get(append
//					.getPlayerItemIdStr());
//			if (list == null) {
//				list = new LinkedList<PlayerItemAppend>();
//				appendMap.put(append.getPlayerItemIdStr(), list);
//			}
//			append.setItem(item);
//			list.add(append);
//		}
//		return appendMap;
//	}
	public void loadUpgradeInfo() {
		List<PlayerUpgrade> list = DBManager.queryUpgradeInfo();
		for (PlayerUpgrade ui : list) {
			playerUpgradeInfoMap.put(ui.getLevel(), ui);
		}
	}

	public void loadGmInfo() {
		List<Gm> gmList = DBManager.queryPlayerGM();
		gmMap.clear();
		for (Gm gm : gmList) {
			gmMap.put(gm.getPlayerId(), gm);
		}
	}

	public PlayerUpgrade getPlayerUpgradeInfo(Integer level) {
		return playerUpgradeInfoMap.get(level);
	}

	public PlayerLevel getPlayerLevel(int level) {
		return playerLevelMap.get(level);
	}

	public int getOnlineNum() {
		return onlineNum;
	}

	/**
	 * 从cache加载用户
	 * */
	public Player getPlayerFromCache(int pid) {
		return map.get(pid);
	}

	/**
	 * 在线玩家
	 * 
	 * @param pid
	 * @return
	 */
	public Player getOnlinePlayer(int pid) {
		Player p = getPlayerFromCache(pid);
		if (p != null && p.isOnline()) {
			return p;
		}
		return null;
	}

	/**
	 * 获得player对象，如果cache中没有，则需要从db加载
	 * 
	 * @param pid
	 * @return
	 * @throws CacheFullException
	 * @throws LockException
	 * @throws BookMarkException
	 */
	public Player getAndCheckPlayer(CommandRequest cmdReq, int pid,
			boolean needVerify) throws CacheFullException, LockException,
			BookMarkException {
		// GM 跳过检测
		Player player = null;
		//synchronized (Integer.valueOf(pid)) {
			if (gmMap.containsKey(pid)) {
				player = getPlayer(pid, true);
			} else {
				player = getPlayer(pid, false);
			}
			if (needVerify) {
				PlayerService.verifySign(cmdReq, player.getPasswd(), cmdReq
				        .getProxyMd5Key());
			}

			// TODO 封号用户
			if (player.isLocked()) {
				throw new LockException("违反游戏规定，封号处理！");
			}

			// 玩家已经在线
			if (player.isOnline()) {
				return player;
			}

			// 设置为上线状态
			PlayerService.setOnline(player);
      //  }
			
		return player;
	}
	

	/**
	 * @param pid
	 * @param force
	 * @return
	 * @throws CacheFullException
	 * @throws LockException
	 */
	public Player getPlayer(int pid, boolean force) throws CacheFullException,
			LockException {
		Player player = map.get(pid);
		if (player == null) {
			player = loadPlayer(pid, force);
		}

		return player;
	}

	/**
	 * 从db加载用户，并更新cache, 当cache满时抛出异常
	 * 
	 * @param force
	 *            ：为true时，无条件加载用户。
	 * */
	public Player loadPlayer(int pid, boolean force) throws CacheFullException {
		if (!force && map.size() >= Constants.PLAYER_CACHE_LIMIT) {
			log.error("play cache has full:" + Constants.PLAYER_CACHE_LIMIT
					+ ", waiting for remove offline player.");
			throw new CacheFullException("cache full");
		}

		// 必须先把合并cache中的sql写入flush到db，然后在加载，防止数据不一致
		// 可以忽略返回值
		DBService.flush(Player.class.getName(), pid);

		Player player = initPlayerFromDB(pid);
		if (player != null) {
			addPlayer(player);
		}
		return player;
	}

	/**
	 * 初始化玩家仇人对应关系。
	 * 
	 * @param player
	 * @return
	 */
	public void loadPlayerEnemy(Player player) {
		if (player == null) {
			return;
		}
		player.getEnemies().clear();
		List<PlayerEnemy> enemyList = DBManager
				.queryPlayerEnemy(player.getId());
		for (PlayerEnemy enemy : enemyList) {
			player.addEnemy(enemy);
		}
	}
//	public void loadPlayerfried(Player player) {
//		try {
//			Collection<PlayerFriend> intimates 
//			= RelationMG.instance.getAllPlayerFriend(player.getId(), RelationService.TYPE_INTIMATE).values();
//			if (intimates != null && intimates.size() > 0) {
//				for (PlayerFriend pf : intimates) {
//					if (pf.getIntimateScore() != null && pf.getIntimateScore()>0) {
//						Player player2=PlayerMG.instance.getOnlinePlayer(pf.getFriendId());
//						if (player2!=null) {
//							if (!SystemSettingCmd.isFlagSet(player2.getSettingFlag(),
//					SystemSettingCmd.FLAG_FRIEND_DESC)) {
//								ChatService.sendMessageSystem(pf.getFriendId(), "你的亲友"+player.getName()+"上线了");
//							}
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
//	}

//	public void loadGM(Player player) {
//		// List<Gm> glist = DBManager.queryPlayerGM(player.getId());
//		// if (glist != null && glist.size() > 0) {
//		// Gm gm = glist.get(0);
//		// player.setGm(gm);
//		// }
////		Gm gm = gmMap.get(player.getId());
////		if (gm != null) {
////			player.setGm(gm);
////		}
//	}
	
	public Map<Integer, Gm> getGMMap() {
		 return gmMap;
	}
	public void loadGMLog() {
		List<GmLog> list= DBManager.queryPlayerGMLog();
		setGmlogList(list);
	}

	

	public void freeJailPlayer(Player player) {
		if (player.getChatStatus() != null && player.getChatStatus() >= 0) {
			List<GmLog> gmLogList = DBManager.queryGMLog(player.getId(), 1);
			if (gmLogList != null && gmLogList.size() > 0) {
				GmLog log = gmLogList.get(0);
				Date now = new Date();
				if (log.getFreeTime().before(now)) {
					player.setChatStatus((byte) -1);
					DBService.commit(player);
					log.setJailDays(0);
					DBService.commit(log);
					ChatService.sendMessageSystem(player.getId(),
							"您的禁言被解封，现在可以发言了.");
				}
			}
		}
		if (player.getStatus() != null && player.getStatus() == 2) {
			// 被封号.
			List<GmLog> gmLogList = DBManager.queryGMLog(player.getId(), 0);
			if (gmLogList != null && gmLogList.size() > 0) {
				GmLog log = gmLogList.get(0);
				Date now = new Date();
				if (log.getFreeTime().before(now)) {
					player.setStatus((byte) 1);
					DBService.commit(player);
					log.setJailDays(0);
					DBService.commit(log);
				}
			}
		}
	}

	/**
	 * 获取玩家仇人列表.
	 * 
	 * @param player
	 * @return
	 */
	public List<PlayerEnemy> getPlayerEnemyList(Player player) {
		if (player == null) {
			return new ArrayList<PlayerEnemy>();
		}
		List<PlayerEnemy> enemyList = new ArrayList<PlayerEnemy>();
		Map<String, PlayerEnemy> enemyMap = player.getEnemies();
		for (Map.Entry<String, PlayerEnemy> entry : enemyMap.entrySet()) {
			enemyList.add(entry.getValue());
		}
		return enemyList;
	}

	/**
	 * 将在线用户增加到map中
	 * */
	public Player addPlayer(Player player) {
		return map.put(player.getId(), player);
	}

	/**
	 * 将离线用户从map中移除
	 * */
	public void removePlayer(Player player) {
		map.remove(player.getId());
	}

	public void scan() {
		try {
			long now = System.currentTimeMillis();
			// 本次扫描前已经离线用户
			List<Player> offlinePlayers = new LinkedList<Player>();
			// 本次扫描新增的超时离线用户
			List<Player> newOfflinePlayers = new LinkedList<Player>();
			int onlineNumTemp = 0;

			// 扫描出所有离线用户和锁定用户
			for (Player player : map.values()) {
				if (!player.isOnline()) {
					offlinePlayers.add(player);
				}
				// 超时，自动标记用户离线
				else if (now - player.getLastAccessTime() > Constants.IDLE_TIME) {
					int time = PlayerService.setOffline(player);
					
					newOfflinePlayers.add(player);
					offlinePlayers.add(player);

					log.info("mark offline player: " + player.getName());
					updatePlayerFBMission(player);
					DBService.commit(player);
				}
				if (player.getLeitaiDate() != null) {
					Date datetime = new Date();
					Calendar cal = Calendar.getInstance();
					cal.setTime(datetime);
					long num = 150 - (cal.getTimeInMillis() - player
							.getLeitaiDate().getTimeInMillis()) / 1000;
					if (0 < num && num <= 5) {
						String express = player.getName() + "在"
								+ player.getCity().getName() + "摆设了擂台竟然无人敢挑战";
						ChatService.sayAll(null, express,
								ChatService.CHAT_WORLD);
					}
				}
				if (player.getStatus() == Constants.STATUS_ONLINE) {
					onlineNumTemp++;
				}

			}
			onlineNum = onlineNumTemp;

			// 缓存满了，清理离线用户，释放内存
			cleanOfflinePlayer(offlinePlayers);

			offlinePlayers.clear();
			newOfflinePlayers.clear();

			// 每个30分钟执行一次(360).
			if (counter == 360) {
				counter = 0;
				NpcRandomWords words = NpcMG.instance.getNpcRandomWords();
				if (words != null) {
					ChatService.sayAll(null, words.getWords(),
							ChatService.CHAT_WORLD);
				}

			}
//			Date dummy = new Date(); // 生成Data对象
//			String datetime = "2011-4-11";
//			Date date = new SimpleDateFormat("yyyy-MM-dd").parse(datetime);
//			String btime = "2011-4-1";
//			Date bdate = new SimpleDateFormat("yyyy-MM-dd").parse(btime);
//			if (date.after(dummy)&&bdate.before(dummy)) {
//				GregorianCalendar cal = new GregorianCalendar();
//				cal.setTime(dummy);
//				SimpleDateFormat format = new SimpleDateFormat("HH", Locale
//						.getDefault());
//				format.format(dummy);
//				cal.get(cal.SECOND);
//				if (cal.get(cal.MINUTE) == 0 && cal.get(cal.SECOND) < 5) {
//					loadGMLog();
//					String express = "现在是斗神时间" + format.format(dummy)
//							+ "点整，答题卷，已经出现在各大城市驿站，赶紧去捡取吧！";
//					ChatService.sayAll(null, express, ChatService.CHAT_WORLD);
//					ChristmasApple();
//				}
//			}

			counter++;
		} catch (Exception e) {
			log.error("excecption scan:", e);
		}
	}

	// 每个30分钟执行一次.
	private int counter = 0;
	private void updatePlayerFBMission(Player player) {
		try {

			Iterator<String> it = player.getOnGoingMissions().keySet()
					.iterator();
			while (it.hasNext()) {
				try {
					String key = it.next();

					PlayerMission pm = player.getOnGoingMissions().get(key);
					if (!pm.isComplete()) {
						if (pm.getMission().getType().equals(
								MissionType.TYPE_INSTANCE)) {
							// 结束副本任务
							pm.addTimes();
							pm.setComplete(Boolean.TRUE);
							DBService.commit(pm);
							player.setInstanceEntrance(0);
							player.setSpecialCity(false);
							InstanceSet.removeFromApplyTeam(player);
							Integer cfId = PlayerService.getCenterId(player);
							// 结束副本任务后，移动到校场.
							PlayerService.move(player, cfId, false);
							break;
						}
					}

				} catch (Exception e) {
					log
							.error(
									"player offline!!, update player mission status failed.",
									e);
				}
			}

		} catch (Exception e) {
			log.error("player offline!!, updatePlayerFBMission failed.", e);
		}
	}

	/**
	 * 缓存满了，清理离线用户，释放内存,每1分钟扫描一次
	 * */
	private void cleanOfflinePlayer(List<Player> offlinePlayers) {
		if (map.size() < Constants.PLAYER_CACHE_LIMIT * 0.9) {
			return;
		}

		// 判断是否超过1分钟没回收离线用户
		long now = System.currentTimeMillis();
		if (now - lastCleanOfflineTime < 60000) {
			return;
		}
		// 更新回收时间
		lastCleanOfflineTime = now;

		// 开始清理，按访问时间排序
		Collections.sort(offlinePlayers, new Comparator<Player>() {
			public int compare(Player a, Player b) {
				return (int) (a.getLastAccessTime() - b.getLastAccessTime());
			}
		});

		// 删除最老的（不超过最大在线数的10%）
		int maxRemove = Constants.PLAYER_CACHE_LIMIT / 10;
		int i = 0;
		for (Player player : offlinePlayers) {
			// 加上db缓写时间，保证commit的都写入db，才从cache移除
			if (now - player.getLastAccessTime() < Constants.IDLE_TIME
					+ DBService.getOperaThread().getDelayTime()) {
				break;
			}
			log.info("remove offline player: " + player.getName());
			map.remove(player.getId());

			LakeWatcherService.removeFishWishes(player);

			i++;
			if (i > maxRemove) {
				break;
			}
		}
		log.error("本次清理用户数:" + i + ",当前容量:" + map.size());
	}

	/***
	 * 从数据库初始化player对象
	 * 
	 * @param pid
	 * @return
	 */
	public Player initPlayerFromDB(int pid) {
		Player player = DBManager.queryPlayer(pid);
		// 从DB加载，如果不是封号状态，设置为offline状态。
		if (!player.isLocked()) {
			player.setStatus(Constants.STATUS_OFFLINE);
		}

		int cfid = player.getCityFacilityId();
		if (cfid == 0) {
			cfid = player.getFromCityFacilityId();
		}
		CityFacility cf = MapMG.instance.getCityFacility(cfid);
		player.setCityFacility(cf);
		// 解析地图
		PlayerService.decodeWorldMap(player);

		// 加载玩家道具
		ItemService.loadPlayerItems(player);

		// NPC和怪的交互计数
		loadNpcInteraction(player);
		
		// 任务列表
		loadPlayerMissions(player);
		
		//加载玩家镖车
		DBManager.loadPlayerDart(player);
		
		//加载玩家倒卖信息
		DBManager.loadPlayerResell(player);
		
		//加载玩家每日完成事件
		DBManager.loadPlayerGetOnce(player);
		
		//加载玩家打劫获得奖励
		DBManager.loadPlayerDartPrize(player);
		
		//加载玩家技能
		DBManager.loadPlayerSkill(player);

		//计算玩家道具
		PlayerService.compareRoom(player);
		
		
		return player;
	}

	private void loadNpcInteraction(Player player) {
		NpcInteractionDAO dao = (NpcInteractionDAO) DBManager
				.getDao(NpcInteractionDAOImpl.class);
		NpcInteractionExample nie = new NpcInteractionExample();
		nie.createCriteria().andPlayerIdEqualTo(player.getId());

		try {
			Collection<NpcInteraction> c = dao.selectByExample(nie);

			for (NpcInteraction ni : c) {
				player.getInteractions().put(ni.getK(), ni);
			}
		} catch (SQLException e) {

			log.error("exception", e);
		}
	}


	public String printPlayerSet() {
		return map.toString();
	}

	/**
	 * 玩家任务列表
	 * 
	 * @param player
	 * @return void
	 * @throws
	 */
	public void loadPlayerMissions(Player player) {
		PlayerMissionDAO dao = (PlayerMissionDAO) DBManager
				.getDao(PlayerMissionDAOImpl.class);

		try {
			// 1. 过期任务
			loadPlayerMissionPast(player);

			// 2. 完成任务
			PlayerMissionExample pme = new PlayerMissionExample();

			pme.createCriteria().andPlayerIdEqualTo(player.getId())
			// .andCompleteEqualTo(false)
					.andNpcIdIsNotNull();

			Collection<PlayerMission> c = dao.selectByExample(pme);
			addMissions(player, c);

		} catch (SQLException e) {
			log.error("exception", e);
		}
	}


	/**
	 * 添加任务
	 * 
	 * @param player
	 * @param c
	 * @return void
	 * @throws
	 */
	private void addMissions(Player player, Collection<PlayerMission> c) {
		boolean converted = true;
		if (player.getMissionPast().getMissionIds() == null) {
			converted = false;
			player.getMissionPast().setMissionIds(new byte[128]);
		}

		for (PlayerMission pm : c) {
			Mission mission = MissionMG.instance.getMissions().get(
					pm.getMissionId());
			if (mission == null
					|| player.getMission(String.valueOf(pm.getMissionId())) != null)
				continue;

			if (!converted) {
				if (pm.isComplete()
						&& mission.getType() != MissionType.TYPE_INSTANCE
						&& (mission.getRepeatTimes() == 0 || mission
								.getRepeatTimes() < pm.getTimesBeDone())) {
					player.addPastMission(mission.getId());
				} else {
					pm.setMission(mission);
					pm.setCity(MapMG.instance.getCity(pm.getCityId()));
					pm.setNpc(NpcMG.instance.getNpc(pm.getNpcId()));
					PlayerMission ppm = player.getMission(String.valueOf(pm
							.getPrePlayerMissionId()));
					if (ppm != null) {
						pm.setPrePlayerMission(ppm);
					}

					if (!pm.isComplete())
						player.getOnGoingMissions().put(
								String.valueOf(pm.getMissionId()), pm);

					player.addMission(String.valueOf(pm.getMissionId()), pm);
				}
			} else {
				if (!player.inPastMission(mission.getId())) {
					pm.setMission(mission);
					pm.setCity(MapMG.instance.getCity(pm.getCityId()));
					pm.setNpc(NpcMG.instance.getNpc(pm.getNpcId()));
					PlayerMission ppm = player.getMission(String.valueOf(pm
							.getPrePlayerMissionId()));
					if (ppm != null) {
						pm.setPrePlayerMission(ppm);
					}

					if (!pm.isComplete())
						player.getOnGoingMissions().put(
								String.valueOf(pm.getMissionId()), pm);

					player.addMission(String.valueOf(pm.getMissionId()), pm);
				}
			}
		}
	}

	/**
	 * 加载过期任务
	 * 
	 * @param player
	 * @return void
	 * @throws
	 */
	private void loadPlayerMissionPast(Player player) {
		PlayerMissionPastDAO dao = (PlayerMissionPastDAO) DBManager
				.getDao(PlayerMissionPastDAOImpl.class);
		PlayerMissionPastExample pmpe = new PlayerMissionPastExample();
		pmpe.createCriteria().andPlayerIdEqualTo(player.getId());

		try {
			List<PlayerMissionPast> list = dao.selectByExampleWithBLOBs(pmpe);
			if (list.size() == 0) {
				PlayerMissionPast pmp = new PlayerMissionPast();
				pmp.init();
				player.setMissionPast(pmp);
			} else {
				PlayerMissionPast pmp = list.get(0);

				player.setMissionPast(pmp);
			}
		} catch (SQLException e) {
			log.error("exception", e);
		}
	}

	/**
	 * 加载用户用于查看他人资料，带上装备等信息。
	 * 
	 * @param pid
	 * @return
	 */
	public Player getPlayerWithArm(int pid) {
		// 是否在缓存中
		Player player = map.get(pid);
		if (player != null) {
			return player;
		}

		// 从DB加载
		player = DBManager.queryPlayer(pid);
		ItemService.loadUsedItem(player);
		return player;
	}

	/**
	 * 得到简单的player对象，如果不在缓存中，则查询数据库，不需要加载其他数据 从DB中加载过来的用户默认为离线用户
	 * 
	 * @param pid
	 * @return
	 */
	public Player getPlayerSimple(int pid) {
		// 是否在缓存中
		Player player = map.get(pid);
		if (player != null) {
			return player;
		}

		// 从DB加载
		player = DBManager.queryPlayer(pid);
		player.setStatus(Constants.STATUS_OFFLINE);
		return player;
	}

	public Player getPlayerWithPlayerItems(int pid) {
		// 是否在缓存中
		Player player = map.get(pid);
		if (player != null) {
			return player;
		}
		player = DBManager.queryPlayer(pid);
		ItemService.loadPlayerItems(player);
		return player;
	}

	public Map<Integer, Player> getPlayerMap() {
		return map;
	}

	/**
	 * 从DB加载消息
	 * 
	 * @param player
	 * @param page
	 */
	public void loadMessages(Player player, int page) {
		MessageDAO dao = (MessageDAO) DBManager.getDao(MessageDAOImpl.class);
		MessageExample me = new MessageExample();
		me.createCriteria().andPlayerIdEqualTo(player.getId());
		me.setOrderByClause("id desc");
		me.setPage(page);

		try {
			List<Message> list = dao.selectByExample(me);

			for (Message message : list) {
				if (!player.getMessages().contains(message))
					player.getMessages().add(message);
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	public void loadPlayerKf(Player player) {
		List<PlayerKf> playerKfList = DBManager.queryPlayerKF(player.getId());
		if (playerKfList != null && playerKfList.size() > 0) {
			PlayerKf kf = playerKfList.get(0);
			player.setKf(kf);
		}
	}

	public static final String ACTIVE_NAME_FENJIESHU = "fenjieshu";

	public static final String ACTIVE_NAME_THANKS = "thanks";

	public static final String ACTIVE_NAME_THANKS_26 = "thanks26";

	public static final String ACTIVE_NAME_THANKS_27 = "thanks27";

	public static final String ACTIVE_NAME_THANKS_28 = "thanks28";

	public static final String ACTIVE_NAME_LOGIN = "login9";

	public static final String BLUNT_NAME_AWARD = "award";

	public static final String ACTIVE_NAME_SP = "chunjie";

	public static final String ACTIVE_NAME_NIAN = "nian";

//	public void loadPlayerActive(Player player) {
//		// ===================end========================================
//		List<PlayerActive> actives = DBManager
//				.queryPlayerActive(player.getId());
//		for (PlayerActive ac : actives) {
//			String key = ac.getPlayerId().toString() + "_" + ac.getKeyWord()
//					+ "_" + ac.getType();
//			player.getPlayerActive().put(key, ac);
//			/**
//			 * if (ac.getKeyWord().equals(ACTIVE_NAME_LOGIN)) { key =
//			 * ac.getPlayerId().toString()+"_" + ac.getKeyWord();
//			 * player.getPlayerActive().put(key, ac);
//			 * loginActiveForZone9(player); } else {
//			 * player.getPlayerActive().put(key, ac); }
//			 */
//		}
//	}

	private void loadPlayerPay(Player player) {
		try {
			List<PlayerPay> list = DBManager.queryPlayerPay(player.getId());
			if (list != null && list.size() > 0) {
				PlayerPay pp = list.get(0);
				if (pp != null) {
					player.setPlayerPay(pp);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param player
	 *            默认保留时间为三天.
	 */
//	private void loadPlayerFishingWishes(Player player) {
//		try {
//
//			List<PlayerFishing> list = DBManager.queryPlayerFishing(player
//					.getId());
//			Date now = new Date();
//			if (list != null && list.size() > 0) {
//
//				for (PlayerFishing pf : list) {
//					if (pf.getCreateDate().getTime()
//							+ Constants.TIME_DAY_IN_MILLIONSECOND * 3 <= now
//							.getTime()) {
//						// 过期
//						DBService.commitDelete(pf);
//					} else if (pf.getReceiverId() != null
//							&& pf.getReceiverId() > 0) {
//						// 已经被吊起
//						int lastOpId = pf.getLastOperateId();
//						Player p = getOnlinePlayer(pf.getReceiverId());
//						if (p != null) {
//							if (lastOpId == player.getId()) {
//								p.getFishWishes().put(pf.getId(), pf);
//							} else {
//								player.getFishWishes().put(pf.getId(), pf);
//							}
//						}
//					} else if (pf.getReceiverId() == null
//							|| pf.getReceiverId() <= 0) {
//						// 未被吊起
//						LakeWatcherService.addFishWishes(player, pf);
//					}
//
//				}
//			}
//			List<PlayerFishing> listReceive = DBManager
//					.queryPlayerFishingReceive(player.getId());
//			if (listReceive != null && listReceive.size() > 0) {
//				for (PlayerFishing pf : listReceive) {
//					if (pf.getCreateDate().getTime()
//							+ Constants.TIME_DAY_IN_MILLIONSECOND * 3 <= now
//							.getTime()) {
//						// 过期
//						DBService.commitDelete(pf);
//					} else {
//						int lastOpId = pf.getLastOperateId();
//						Player p = getOnlinePlayer(pf.getPlayerId());
//						if (p != null) {
//							if (lastOpId == player.getId()) {
//								p.getFishWishes().put(pf.getId(), pf);
//							} else {
//								player.getFishWishes().put(pf.getId(), pf);
//							}
//						}
//
//					}
//
//				}
//			}
//
//		} catch (Exception e) {
//
//		}
//	}

	private static Logger logger = Logger.getLogger("Reward");

	private void loginActiveForZone9(Player player) {
		try {

			String key = player.getId().toString() + "_" + ACTIVE_NAME_LOGIN;
			PlayerActive pa = player.getPlayerActive().get(key);
			if (pa == null) {
				pa = PlayerMG.instance.createPlayerActive(player,
						ACTIVE_NAME_LOGIN, 0);
				pa.setTime(player.getLevel());
				DBService.commit(pa);
				ItemService.addItem(player, 10284, 20, false);// 遁地符20个、◆青铜凝神符*1、◆青铜回血符*1、双倍经验卡x4
				ItemService.addItem(player, 84, 1, false);
				ItemService.addItem(player, 82, 1, false);
				ItemService.addItem(player, 10368, 4, false);
				ChatService.sendMessageSystem(player.getId(),
						"欢迎来到9区，登陆有奖，赠送你：遁地符x20、青铜凝神符x1、青铜回血符x1、双倍经验卡x4");
				logger.info(LoggerHelper.ZONE_ID + "|login9|"
						+ ACTIVE_NAME_LOGIN + "|" + player.getUserId() + "|"
						+ player.getId() + "|" + player.getName() + "|"
						+ player.getLevel());
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 清理掉5级以下且二个月内没有登录的用户.
	 * 
	 * @param player
	 */
	public void checkLongtimeNotLoginGiving(Player player) {
		try {

			if (LoggerHelper.ZONE_ID.equals("7")) {

				int level = player.getLevel();
				if (level > 5) {
					return;
				}
				Date lastLogin = player.getLoginTime();
				String datetime = "2011-02-26";
				Date oldate = new SimpleDateFormat("yyyy-MM-dd")
						.parse(datetime);
				if (!lastLogin.before(oldate)) {
					return;
				}
				ItemService.addItem(player, 10284, 20, false);
				ItemService.addItem(player, 10286, 10, false);
				ItemService.addItem(player, 10221, 1, false);
				ItemService.addItem(player, 10149, 1, false);

				ItemService.addItem(player, 10125, 1, false);
				ItemService.addItem(player, 10364, 1, false);
				ItemService.addItem(player, 10196, 1, false);

				ItemService.addItem(player, 10172, 1, false);
				ItemService.addItem(player, 10046, 1, false);

				ItemService.addItem(player, 10286, 20, false);
				ItemService.addItem(player, 10336, 1, false);
				ItemService.addItem(player, 10284, 16, false);

				ItemService.addItem(player, 10077, 1, false);
				ItemService.addItem(player, 10365, 1, false);
				ItemService.addItem(player, 10280, 1, false);
				ItemService.addItem(player, 10076, 1, false);
				ItemService.addItem(player, 10124, 1, false);
				ItemService.addItem(player, 10244, 1, false);

				ItemService.addItem(player, 10196, 1, false);
				ItemService.addItem(player, 10337, 1, false);

				logger.info(LoggerHelper.ZONE_ID + "|clear|login|"
						+ player.getUserId() + "|" + player.getId() + "|"
						+ player.getName() + "|" + player.getLevel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<String, String> ChristmasApple() {
		applemap.clear();
		String apple = "答题卷";
		String oranges = "答题卷";
		for (int i = 0; i < 5; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 5; i < 10; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 10; i < 15; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 15; i < 20; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 20; i < 25; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 25; i < 30; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 30; i < 35; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 35; i < 40; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 40; i < 45; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 45; i < 50; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 50; i < 55; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 55; i < 60; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		for (int i = 60; i < 65; i++) {
			applemap.put(String.valueOf(i), apple);
		}
		for (int i = 65; i < 80; i++) {
			applemap.put(String.valueOf(i), oranges);
		}
		return applemap;
	}

	public PlayerActive createPlayerActive(Player player, String key, int type) {
		PlayerActive pa = new PlayerActive();
		pa.setId(GlobalGenerator.instance.getIdForNewObj(pa));
		pa.setPlayerId(player.getId());
		pa.setKeyWord(key);
		pa.setType(type);
		pa.setTime(0);
		pa.setJoinDate(new Date());
		return pa;
	}

	public static void setApplemap(Map<String, String> applemap) {
		PlayerMG.applemap = applemap;
	}

	public static Map<String, String> getApplemap() {
		return applemap;
	}

	public static PlayerLevel getPlayerLevel(Player player){
		return PlayerMG.instance.getPlayerLevel(player.getLevel());
	}

	
}
