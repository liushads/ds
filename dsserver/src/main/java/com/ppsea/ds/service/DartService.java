package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.FightResult;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Dart;
import com.ppsea.ds.data.model.DartAward;
import com.ppsea.ds.data.model.DartAward.DartItem;
import com.ppsea.ds.data.model.GetOnce;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerDart;
import com.ppsea.ds.data.model.PlayerDartPrize;
import com.ppsea.ds.data.model.PlayerDartVo;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.DateUtil;
import com.ppsea.ds.util.Util;

/**
 * 押镖服务
 * @author small
 *
 */
public class DartService {

	public static final int REF_ONE_GOLD = 100;
	private static final int DART_STAT_START = 0;//开始押送镖车
	private static final int DART_STAT_END = 1;//提交镖车
	private static final int DART_STAT_BORN = -1;//已经生成镖车，防止玩家下线后重新上线刷
	public static final int DART_TIMES = 10;//玩家每天可以押镖次数
	private static final int COOL_TIME = 10;//打劫冷却时间，10分钟
	
	/**
	 * 劫镖奖励百分比
	 */
	private static final int PRIZE_PRE = 5;
	
	/**
	 * 每天定时多给奖励
	 */
	public static List<String> time_pre = new ArrayList<String>();
	public static final int pre = 10;//每天定时多给的百分比
	
	static{
		time_pre.add("12：30-14：30");
		time_pre.add("20：00-22：00");
	}
	
	public static List<Dart> getDart(){
		List<Dart> list = new ArrayList<Dart>();
		list.addAll(ResourceCache.instance.getDarts().values());
		Collections.sort(list, new Comparator<Dart>() {
			public int compare(Dart o1, Dart o2) {
				return o1.getId() - o2.getId();
			}
		});
		
		return list;
	}
	
	/**
	 * 随机获取一个玩家当前镖车
	 * @return
	 */
	private static Dart getPlayerDart(PlayerDart pd){
		Map<String, Integer> keyChanceMap = new HashMap<String, Integer>();
		keyChanceMap.clear();
		for (Dart d : ResourceCache.instance.getDarts().values()) {
			keyChanceMap.put(d.getId().toString(), d.getPre());
		}
		String key = Util.getPreKey(keyChanceMap);
		Dart dd = ResourceCache.instance.getDarts().get(Integer.parseInt(key));
		if (pd != null) {
			while (pd.getDartId().intValue() == dd.getId().intValue()) {
				key = Util.getPreKey(keyChanceMap);
				dd = ResourceCache.instance.getDarts().get(Integer.parseInt(key));
			}
		}
		return dd;
	}
	
	/**
	 * 获取一个临时的镖车放到玩家身上
	 * @param player
	 * @param gold 是否需要钻石，如果钻石不足，则直接返回
	 */
	public static ErrorMsg getTmpPlayerDart(Player player,int gold){
		PlayerDart pd = player.getPlayerDart();
		if (pd != null && pd.getDartStatus() == DART_STAT_START) {
			return new ErrorMsg(-1, "您已经有镖车在押送了，还是等这趟生意做完了再来吧！");
		}
		Dart dart = getPlayerDart(player.getPlayerDart());
		if (gold > 0) {
			if (!player.consumeInAdvGlod(gold)) {
				return new ErrorMsg(-1, "钻石不足");
			}
			pd = createPlayerDart(player, dart);
		}else if (player.getPlayerDart() == null || player.getPlayerDart().getDartStatus() == DART_STAT_END) {
			pd = createPlayerDart(player, dart);
			player.setPlayerDart(pd);
		}
		return new ErrorMsg(0, ResourceCache.instance.getDarts().get(player.getPlayerDart().getDartId()));
	}
	
	/**
	 * 根据玩家检查当前押镖是否结束
	 * @param player
	 * @return
	 */
	private static boolean checkPlayerDart(Player player){
		if (player.getPlayerDart() != null && player.getPlayerDart().getDartStatus() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取在线玩家镖车信息
	 * @param player
	 * @param size	大于0时获取指定数量，0时不限制
	 * @return
	 */
	public static List<PlayerDartVo> getDartVos(Player player,int size){
		Map<Integer, Player> map = PlayerMG.instance.getPlayerMap();
		
		List<PlayerDartVo> list = new ArrayList<PlayerDartVo>();
		PlayerDartVo pdv = null;
		ErrorMsg msg = null;
		for (Player p : map.values()) {
			if (p.getId().intValue() == player.getId().intValue()) {
				continue;
			}
			if (checkPlayerDart(p)) {
				
				pdv = getPDV(p);
				msg = checkTodayRob(player, p);
				if (msg.code == ErrorCode.SUCC) {
					pdv.setIsRob(1);
				}
				list.add(pdv);
				
				if (size > 0 && list.size() >= size) {
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取临时镖车信息
	 * @param player
	 * @return
	 */
	private static PlayerDartVo getPDV(Player player){
		PlayerDartVo pdv = new PlayerDartVo();
		pdv.setPlayerId(player.getId());
		pdv.setDartName(ResourceCache.instance.getDarts().get(player.getPlayerDart().getId()).getName());
		pdv.setPlayerLevel(player.getLevel());
		pdv.setPlayerName(player.getName());
		pdv.setIsRob(0);
		return pdv;
	} 
	
	
	/**
	 * 生成一个玩家镖车
	 * @param player
	 * @param dart
	 * @return
	 */
	private static PlayerDart createPlayerDart(Player player,Dart dart){
		PlayerDart pd = player.getPlayerDart();
		if (pd == null) {
			pd = new PlayerDart();
			pd.setId(GlobalGenerator.instance.getIdForNewObj(pd));
			pd.setDayTimes(0);
			pd.setPlayerId(player.getId());
			pd.setStartTime(new Date());
		}
		pd.setDartId(dart.getId());
		pd.setDartStatus(DART_STAT_BORN);
		pd.setRobed(0);
		player.setPlayerDart(pd);
		pd.setGoTime(0);
		DBService.commit(pd);
		return pd;
	} 
	
	/**
	 * 开始押镖
	 * @param player
	 * @return
	 */
	public static ErrorMsg begin(Player player) {
		PlayerDart pd = player.getPlayerDart();
		if (pd == null) {
			return new ErrorMsg(-1, "还没有领取镖车，还是先领取镖车再来吧！");
		}
		if (pd != null && pd.getDartStatus() == DART_STAT_START) {
			return new ErrorMsg(0, pd);
		}
		if (checkSameDay(pd)) {
			if (pd.getDayTimes() >= DART_TIMES) {
				return new ErrorMsg(-1, "今天已经做完了，还是明天再来吧");
			}
			pd.setDayTimes(pd.getDayTimes() + 1);
		}else {
			pd.setDayTimes(1);
		}
		pd.setDartStatus(DART_STAT_START);
		pd.setGoTime(0);
		pd.setRobed(0);
		pd.setStartTime(new Date());
		player.setPlayerDart(pd);
		DBService.commit(pd);
		return new ErrorMsg(0, pd);
	}
	
	private static boolean checkSameDay(PlayerDart pd){
		if (pd == null) {
			return false;
		}
		boolean isToday = DateUtil.isSameDay(pd.getStartTime().getTime(), System.currentTimeMillis());
		if (isToday) {//是同一天，检查今天做过次数
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 获取剩余时间
	 * @param pd
	 * @return
	 */
	public static long getLastTime(PlayerDart pd){
		if (pd == null ) {
			return -1;
		}
		if (pd.getDartStatus() != DART_STAT_START) {
			return -1;
		}
		Dart dart = ResourceCache.instance.getDarts().get(pd.getDartId());
		if (dart == null) {
			return -1;
		}
		long now = System.currentTimeMillis();
		long end = pd.getStartTime().getTime() + dart.getAllTime() * Constants.ONE_MINUTE;
		long res = end - now;
		res = res - pd.getGoTime() * dart.getGoTime() * Constants.ONE_MINUTE;
		if(res < 0) {
			res = 0;
		}
		return res / 1000;
	}
	
	/**
	 * 获取今天已经做过几次
	 * @param pd
	 * @return
	 */
	public static int getTodayTime(PlayerDart pd){
		if (pd == null || !DateUtil.isSameDay(pd.getStartTime().getTime(), System.currentTimeMillis())) {
			return 0;
		}else {
			return pd.getDayTimes();
		}
	}
	
	/**
	 * 领取镖车奖励
	 * @param player
	 * @return
	 */
	public static ErrorMsg getDartPrize(Player player){
		PlayerDart pd = player.getPlayerDart();
		long res = getLastTime(pd);
		if (res < 0) {
			return new ErrorMsg(-1, "领取奖励错误！");
		}
		if (res > 0) {
			return new ErrorMsg(-1, "时间还没到，不能领取奖励，还是等时间到了再来吧！");
		}
		int r = doneDart(pd);
		if (r < 0) {
			return new ErrorMsg(-1, "镖车不存在！");
		}
		//成功领取奖励
		//正常奖励
		//每天定时额外奖励
		int real_pre = 0;
		if (isInTime()) {
			real_pre = pre; 
		}
		if (pd.getRobed() != 0) {//被劫镖需要减掉劫镖的奖励
			real_pre = real_pre - pd.getRobed() * PRIZE_PRE;
		}
		Dart dart = ResourceCache.instance.getDarts().get(pd.getDartId());
		String desc = getPrize(player,dart.getDartAwards(),real_pre);
		
		//没被打劫过有额外奖励
		if (pd.getRobed() == 0) {
			String extr = getPrize(player,dart.getExtraAwards(),real_pre);
			desc = desc + extr;
		}
		return new ErrorMsg(ErrorCode.SUCC, desc);
		
	}
	
	/**
	 * 获取奖励
	 * @param player
	 * @param dart
	 * @param real_pre
	 * @param type		类型，0正常奖励、1额外奖励
	 * @return
	 */
	private static String getPrize(Player player,DartAward da,int real_pre){
		if (da == null) {
			return "";
		}
//		（100级-1级）*0.1 
		// TODO 奖励跟等级关系，金或游戏币可以，道具加成太多，不方便做
		StringBuffer sb = new StringBuffer();
		int real_num = 0;
//		DartAward da = null;
//		if (type == 0) {
//			da = dart.getDartAwards();
//		}else {
//			da = dart.getExtraAwards();
//		}
		
		if (da.getCopper() > 0) {
			real_num = da.getCopper() + da.getCopper() * real_pre / 100;
			player.addCopper(real_num);
			sb.append("金币：").append(real_num).append("、");
		}
		if (da.getGold() > 0) {
			real_num = da.getGold() + da.getGold() * real_pre / 100;
			player.addGold(real_num);
			sb.append("水晶：").append(real_num).append("、");
		}
		Item item = null;
		if (da.getDartItems() != null && da.getDartItems().size() > 0) {
			for (DartItem di : da.getDartItems()) {
				item = ItemMG.instance.getItem(di.getId());
				if (item != null) {
					real_num = di.getAmount() + di.getAmount() * real_pre / 100;
					if (item.getType() == Item.ARM_TYPE) {
						for (int i = 0; i < real_num; i++) {
							ItemService.addItem(player, item, 1, false);
						}
					}else {
						ItemService.addItem(player, item, real_num, false);
					}
					sb.append(item.getName()+"X").append(real_num).append("、");
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 检查当前时间是否在规定时间范围内
	 * @return
	 */
	private static boolean isInTime(){
		String times[] = null;
		boolean isIn = false;
		for (String time : time_pre) {
			time.replaceAll("：", "");
			times = time.split("-");
			isIn = DateUtil.isBetweenTime(times[0], times[1]);
			if (isIn) {
				break;
			}
		}
		return isIn;
	}
	/**
	 * 完成押镖
	 * @param pd
	 */
	private static int doneDart(PlayerDart pd){
		if (pd != null) {
			pd.setDartStatus(DART_STAT_END);
			DBService.commit(pd);
			return ErrorCode.SUCC;
		}
		return -1;
	}
	
	/**
	 * 加速
	 * @param player
	 * @param type	0普通加速，按照单位计算。1全速加速，直接完成
	 * @return
	 */
	public static ErrorMsg speedUp(Player player,int type){
		PlayerDart pd = player.getPlayerDart();
		long res_time = getLastTime(pd);
		if (res_time > 0) {
			//延时一定时间后才能加速
			Dart dart = ResourceCache.instance.getDarts().get(pd.getDartId());
			if (((System.currentTimeMillis() - pd.getStartTime().getTime()) / Constants.ONE_MINUTE) < dart.getDelayTime()) {
				return  new ErrorMsg(-1, "镖车开始" + dart.getDelayTime() + "分钟后才能加速，现在不能加速！");
			}
			int real_time = 0;
			if (type == 0) {
				if (!player.consumeInAdvGlod(dart.getGold())) {
					return new ErrorMsg(-1, "钻石不足不能加速！");
				}
				real_time = 1;
			}
			if (type == 1) {
				if (!player.consumeInAdvGlod(dart.getTotalGold())) {
					return new ErrorMsg(-1, "钻石不足不能加速！");
				}
				real_time = dart.getAllTime();
			}
			pd.setGoTime(pd.getGoTime() + real_time);
			DBService.commit(pd);
			return new ErrorMsg(ErrorCode.SUCC, "加速成功！");
		}
		return new ErrorMsg(-1, "已经完成，不需要加速了！");
	}
	
	
	public synchronized static ErrorMsg robDart(Player player,Player enemy) throws PpseaException{
		GetOnce getOnce = GetOnceService.getByType(player, Constants.DART_ROBE);
		PlayerDart enemyDart = enemy.getPlayerDart();
		ErrorMsg msg = checkTodayRob(player,enemy);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		//记录今天打劫次数
		getOnce = GetOnceService.addGetOnce(player, getOnce, Constants.DART_ROBE);
		player.getGetOnce().put(Constants.DART_ROBE, getOnce);
		//打劫
		boolean res = rob(player, enemy);
		enemyDart.setRobed(enemyDart.getRobed() + 1);
		DBService.commit(enemyDart);
		StringBuffer sb = new StringBuffer();
		if (res) {//打劫成功，给奖励
			ErrorMsg msg2 = getDartAwardPrize(player,enemy);
			createPlayerDartPrize(player, (DartAward)msg2.obj);
			
			// TODO 打劫胜利后奖励计算方式，被打劫玩家奖励扣除方式
			sb.append(player.getName()).append("拦劫了").append(enemy.getName());
			sb.append(ResourceCache.instance.getDarts().get(enemyDart.getId()).getName());
			sb.append("，可获得劫镖奖励").append(msg2.text);
		}else {
			sb.append("一番激战后，").append(enemy.getName()).append("将");
			sb.append(player.getName()).append("击败，保护了自己镖车");
		}
		addNewMessage(sb.toString());
		String succ = "";
		if (res) {
			succ = sb.toString();
			
		}else {
			succ = "fail";
		}
		
		player.getTmpMap().put(Constants.COLL_TIME, System.currentTimeMillis() + "");
		return new ErrorMsg(ErrorCode.SUCC, succ);
	}
	
	/**
	 * 劫镖战斗
	 * @param player
	 * @param enemy
	 * @return
	 * @throws PpseaException
	 */
	private static boolean rob(Player player,Player enemy) throws PpseaException{
		int playerHp = player.getHp();
		int enemyHp = enemy.getHp();
		FightResult fightResult[] = null;
		FightResult atkResult = null;
		FightResult defResult = null;
		player.setHp(player.getDyn().getMaxHp());
		enemy.setHp(enemy.getDyn().getMaxHp());
		int huihe = 0;
		do {
			if (huihe % 2 == 0) {
				fightResult = FightService.fightBetween(player, enemy, 0, 0,false);
				atkResult = fightResult[0];
				defResult = fightResult[1];
			}else {
				fightResult = FightService.fightBetween(enemy, player, 0, 0,false);
				atkResult = fightResult[1];
				defResult = fightResult[0];
			}
			
			if (atkResult.isKiller()) {
				return true;
			}
			if (defResult.isKiller()) {
				return false;
			}
			huihe ++;
		} while (huihe < 1000);
		player.setHp(playerHp);
		enemy.setHp(enemyHp);
		return false;
	}
	
	private static LinkedList<String> msgList = new LinkedList<String>();
	public static LinkedList<String> getMsgList(int size) {
		if (size > 0) {
			LinkedList<String> list = new LinkedList<String>();
			for (int i = 0; i < size; i++) {
				if (msgList.size() > i) {
					list.add(msgList.get(i));
				}else {
					 break;
				}
			}
			return list;
		}
		return msgList;
	}

	private synchronized static void addNewMessage(String msg){
		msgList.addFirst(msg);
		while (msgList.size() > 100) {
			msgList.pollLast();
		}
	}
	
	
	
	/**
	 * 劫镖奖励
	 * @param player
	 * @param playerDart
	 * @return
	 */
	public static ErrorMsg getDartAwardPrize(Player player,Player enemy){
		ErrorMsg msg = checkTodayRob(player, enemy);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		PlayerDart playerDart = enemy.getPlayerDart();
		Dart dart = ResourceCache.instance.getDarts().get(playerDart.getDartId());
		DartAward da = dart.getDartAwards();
		DartAward daPrize =new DartAward();
		StringBuffer sb = new StringBuffer();
		int res = da.getCopper() * PRIZE_PRE / 100; 
		if (res > 0) {
			daPrize.setCopper(res);
			sb.append("金币+").append(res).append("、");
		}
		res = da.getGold() * PRIZE_PRE / 100;
		if (res > 0) {
			daPrize.setGold(res);
			sb.append("水晶+").append(res).append("、");
		}
		if (da.getDartItems() != null && da.getDartItems().size() > 0) {
			List<DartItem> list = null;
			DartItem dItem = null;
			for (DartItem di : da.getDartItems()) {
				res = di.getAmount() * PRIZE_PRE / 100;
				if (res > 0) {
					if (list == null) {
						list = new ArrayList<DartAward.DartItem>();
					}
					dItem = daPrize.new DartItem();
					dItem.setId(di.getId());
					dItem.setName(di.getName());
					dItem.setAmount(res);
					list.add(dItem);
					sb.append(dItem.getName()).append("X").append(res).append("、");
				}
			}
			if (list != null) {
				daPrize.setDartItems(list);
			}
		}
		return new ErrorMsg(ErrorCode.SUCC, sb.toString(),daPrize);
	}
	
	/**
	 * 检查当前是否能够被打劫
	 * @param player
	 * @param enemy
	 * @return
	 */
	private static ErrorMsg checkTodayRob(Player player ,Player enemy){
		// TODO 测试将限制条件注释
		if (enemy == null || enemy.getPlayerDart() == null || enemy.getPlayerDart().getDartStatus() != DART_STAT_START) {
			return new ErrorMsg(-1, "对方不在线或是对方没有镖车！");
		}
		Dart dart = ResourceCache.instance.getDarts().get(enemy.getPlayerDart().getDartId());
		if (dart == null) {
			return new ErrorMsg(-1, "镖车不存在！");
		}
		GetOnce getOnce = GetOnceService.getByType(player, Constants.DART_ROBE);
		boolean checkToday = GetOnceService.checkToday(getOnce, Constants.DART_TIME);
		if (!checkToday) {
			return new ErrorMsg(-1, enemy.getName() + "你今天已经打劫过" + Constants.DART_TIME + "次了，不能再打劫了！");
		}
		PlayerDart enemyDart = enemy.getPlayerDart();
		if (enemyDart.getRobed() >= Constants.DART_ROB_TIME) {
			return new ErrorMsg(-1, enemy.getName() + "今天已经被打劫了" + enemyDart.getRobed() + "次了，还是放过他吧！");
		}
		
		//对方等级是否大于等级自己等级-5
		if (!(enemy.getLevel() >= player.getLevel() - 5)) {
			return new ErrorMsg(-1, enemy.getName() + "等级太低，还是去打劫其他玩家的吧！");
		}	
		//冷却时间
		Map<String, String> tmpMap = player.getTmpMap();
		if (tmpMap.containsKey(Constants.COLL_TIME)) {
			long last_rob_time = Long.parseLong(tmpMap.get(Constants.COLL_TIME));
			if ((System.currentTimeMillis() / Constants.ONE_MINUTE - last_rob_time / Constants.ONE_MINUTE) <= COOL_TIME) {
				return new ErrorMsg(-1, "你刚才打劫了别人，还是"+(COOL_TIME - (System.currentTimeMillis() / Constants.ONE_MINUTE - last_rob_time / Constants.ONE_MINUTE) )+"等分钟再来吧");
			}
			
		}
		return new ErrorMsg(ErrorCode.SUCC, null);
	}
	
	/**
	 * 生成打劫奖励
	 * @param player
	 * @param dartAward
	 */
	private static void createPlayerDartPrize(Player player,DartAward dartAward){
		PlayerDartPrize pdp = new PlayerDartPrize();
		pdp.setId(GlobalGenerator.instance.getIdForNewObj(pdp));
		pdp.setPlayerId(player.getId());
		pdp.setPrizes(getAwards(dartAward));
		player.getPlayerDartPrize().put(pdp.getId(), pdp);
		DBService.commit(pdp);
	}
	
	/**
	 * 将奖励转换成字符串
	 * @param dartAward
	 * @return
	 */
	private static String getAwards(DartAward dartAward){
		StringBuffer sb = new StringBuffer();
		if (dartAward.getCopper() > 0) {
			sb.append("0_").append(dartAward.getCopper()).append("|");
		}
		if (dartAward.getGold() > 0) {
			sb.append("1_").append(dartAward.getGold()).append("|");
		}
		if (dartAward.getDartItems() != null && dartAward.getDartItems().size() > 0) {
			sb.append("2_");
			for (DartItem di : dartAward.getDartItems()) {
				if (di.getAmount() > 0) {
					sb.append(di.getId()).append(":").append(di.getAmount()).append(",");
				}
			}
		}
		return sb.toString();
	}
	
	public static String getPlayerDartPrize(Player player,PlayerDartPrize pdp){
		DBService.commitDelete(pdp);
		player.getPlayerDartPrize().remove(pdp.getId());
		return getPrize(player, pdp.getDartAward(), 0);
	}
	
}
