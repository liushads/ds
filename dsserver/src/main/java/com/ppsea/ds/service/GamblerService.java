/*
 * 
 */
package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GambleEntity;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2011-1-24
 */
public class GamblerService {

	private static Logger logger = Logger.getLogger("Reward");
	
	public final static int[] SPOT_BALL = { 1, 2, 3, 4, 5, 6};
	
	public final static int[] HORSE_QUEUE = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	
	//1银
	public final static int M_REWARD = 1000;
	
	public final static int M_HORSE_REWARD = 5 * M_REWARD;
	

	/**
	 * number of the ball to be played.
	 * 
	 * @param amount
	 */
	public static int [] openQuotation(int amount) {
		
		int [] result = new int[amount];
		for (int num = 0; num < amount; num++) {
			Random rand = new Random();
			int pos = rand.nextInt(SPOT_BALL.length);
			int value = SPOT_BALL[pos];
			result[num] = value;
		}
		return result;
	}

	/**
	 * 1 : is the same,2 the sequence,x
	 * @param results
	 */
	public static int publishResult(int[] results) {
		
		Arrays.sort(results);
		int first = results[0];
		int total = first;
		boolean isSame = true;
		boolean isSequence = true;
		for (int i = 1; i < results.length; i++) {
			if (first != results[i]) {
				isSame = false;
			}
			if (first == results[i] - 1) {
				first = results[i];
			} else {
				isSequence = false;
			}
			total += results[i];
		}
		if (isSame) {
			return 1;
		} else if (isSequence) {
			return 2;
		} else {
			if (total > 10) {
				return 3;
			} else {
				return 4;
			}
		}
	}
	public static int calResult(int[] results) {
		if (results == null || results.length == 0) {
			return 0;
		}
		Arrays.sort(results);
		int first = results[0];
		int total = first;
		for (int i = 1; i < results.length; i++) {
			total += results[i];
		}
		
		return total;
	}
	
	public static void gamble(Player player, int channel,String mtype, CommandResult result) throws NoMoneyException {
		if (!player.consumeInCopper(M_REWARD)) {
			throw new NoMoneyException("no money");
		}
		int [] results = openQuotation(channel);
		int value = publishResult(results);
		String str = "";
		for (int v : results) {
			str += " " + v;
		}
		if (mtype.equals(String.valueOf(value))) {
			int rewards = 0;
			switch(value) {
			case 1:
				rewards = 200;
				str += "(豹子)";
				break;
			case 2:
				rewards = 10;
				str += "(顺子)";
				break;
			case 3:
				rewards = 2;
				str += "(大)";
				break;
			case 4:
				rewards = 2;
				str += "(小)";
				break;
				default:
			}
			player.addCopper(rewards * M_REWARD * 90/100);
			result.setVO("info", "恭喜你押注成功，赔率"+rewards+"倍,获得"+(rewards * M_REWARD *90/100)+"铜");
			result.setVO("mytype", mtype);
			logger.info(LoggerHelper.ZONE_ID + "|gamble|"+channel+"|" + player.getUserId() + "|" + player.getId() + "|"
					+ player.getName() + "|" + player.getLevel() + "|" + rewards + "|");
		} else {
			result.setVO("info","运气不佳，什么也没中");
		}
		
		result.setVO("gambler", str);
	}
	
	public static ErrorMsg horse(Player player, String mtype, CommandResult result) throws NoMoneyException {
		
		Map<Integer, Player> tm = gamblerHorseMap.get(mtype);
		if (tm != null) {
			Player p = tm.get(player.getId());
			if (p != null) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "你已经下注"+mtype+"号马，请勿重复下注");
			}
		}
		
		if (!player.consumeInCopper(M_HORSE_REWARD)) {
			throw new NoMoneyException("no money");
		}
		
		checkAndStartHorseRound(player);
		
		Map<Integer, Player> map = gamblerHorseMap.get(mtype);
		if (map == null) {
			synchronized (mtype) {
				if (map == null) {
					map = new HashMap<Integer, Player>();
					gamblerHorseMap.put(mtype, map);
				}
			}
		}
		map.put(player.getId(), player);
		return Constants.SUCC;
		/**
		if (mtype.equals(String.valueOf(horse))) {
			int rewards = 10;
			player.addCopper(rewards * M_REWARD * 90/100);
			result.setVO("info", "恭喜你押注成功，赔率"+rewards+"倍,获得"+(rewards * M_REWARD *90/100)+"铜");
			result.setVO("mytype", mtype);
			logger.info(LoggerHelper.ZONE_ID + "|gamble|"+"horse"+"|" + player.getUserId() + "|" + player.getId() + "|"
					+ player.getName() + "|" + player.getLevel() + "|" + rewards + "|");
		} else {
			result.setVO("info","看走眼了，此马并非神马");
		}
		*/
	}
	
	public static void checkAndStartHorseRound(Player player) {

		if (gamblerHorseMap.size() == 0) {
			synchronized (gamblerHorseMap) {
				expireHorseTime = System.currentTimeMillis() + roundHorseTime * 60 * 1000;
			}
		}
	}
	
	public static void checkAndEndHorseRound(CommandResult result) {
		try {
			if (expireHorseTime > 0 && (expireHorseTime - System.currentTimeMillis() <= 0)) {
				synchronized (gamblerHorseMap) {
					String horse = "0";
					int size = -1;
					for (int i = 0; i < 10; i++) {
						Map<Integer, Player> map = gamblerHorseMap.get(i+"");
						if (map != null) {
							if (size < 0) {
								size = map.size();
								horse = i+"";
							} else {
								if (size > map.size()) {
									size = map.size();
									horse = i+"";
								}
							}
						} else {
							horse = i+"";
							size = 0;
						}
					}
					roundHorse = horse;
					Map<Integer, Player> map = gamblerHorseMap.get(horse);
					String info = "";
					if (map != null && map.size() > 0) {
						String tp = "此次"+horse+"号种马获得第一,";
						for (Map.Entry<Integer, Player> entry : map.entrySet()) {
							int bonus =  M_HORSE_REWARD * 10  * 90 /100;
							info = "此次"+horse+"号种马获得第一,奖励你"+bonus;
							tp += entry.getValue().getName() + ",";
							rewardPlayer(entry.getKey(), bonus, true, info);
						}
						tp += "中奖";
						info = tp;
					} else {
						//
						info = "此次"+horse+"号种马获得第一，无人中奖";
					}
					
					if (result != null) {
						result.setVO("info", info);
						result.setStatus("info");					
					}
					gamblerHorseMap.clear();
					expireHorseTime=0;
				}
			} else {
				result.setVO("leftHorseTime", (expireHorseTime - System.currentTimeMillis())/1000);
			}
			if (result != null && maxRoundPoints > 0) {
				result.setVO("roundHorse", roundHorse);
			}
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * five points.
	 */
	public static AtomicInteger fiveTotalMoney = new AtomicInteger();
	public static Map<Integer, GambleEntity> gamblerMap = new ConcurrentHashMap<Integer, GambleEntity>();
	public static long expireTime = 0;
	public static int maxRoundPoints = 0;
	public static String maxPointsStr = "";
	public static int roundTime = 3;
	
	/**
	 * horse.
	 * 
	 */
	public static Map<String, Map<Integer, Player>> gamblerHorseMap = new ConcurrentHashMap<String, Map<Integer, Player>>();
	public static int roundHorseTime = 15;//15
	public static long expireHorseTime = 0;
	public static String roundHorse = null;
	
	public static void checkAndStartRound(Player player) {

		if (gamblerMap.size() == 0) {
			synchronized (gamblerMap) {
				expireTime = System.currentTimeMillis() + roundTime * 60 * 1000;
			}
		}
	}
	
	public static void checkAndEndRound(CommandResult result) {
		try {

			if (expireTime > 0 && (expireTime - System.currentTimeMillis() <= 0)) {
				synchronized (gamblerMap) {
					String name = "";
					if (gamblerMap.size() > 1) {
						List<GambleEntity> list = new ArrayList<GambleEntity>();
						for (Map.Entry<Integer, GambleEntity> entry : gamblerMap.entrySet()) {
							GambleEntity ge = entry.getValue();
							if (ge.getMaxRoundPoints() == null) {
								ge.setMaxRoundPoints(ge.getCurrentRoundPoints());
							}
							list.add(ge);
						}
						Collections.sort(list, new Comparator<GambleEntity>(){

	                        public int compare(GambleEntity o1, GambleEntity o2) {
								if (o1.getMaxPoints() > o2.getMaxPoints()) {
									return -1;
								} else {
									return 1;								
								}
	                        }
						});
						GambleEntity ge = list.get(0);
						maxRoundPoints = ge.getMaxPoints();
						maxPointsStr = ge.convert2Str(ge.getMaxRoundPoints());
						List<GambleEntity> gList = new ArrayList<GambleEntity>();
						gList.add(ge);
						for (int i = 1; i < list.size(); i++) {
							GambleEntity g = list.get(i);
							if (ge.getMaxPoints() == g.getMaxPoints()) {
								gList.add(g);
							}
						}
						int totalBonus = fiveTotalMoney.get() * 90 /100;
						int bonus = (totalBonus)/gList.size();
						for (int i = 0; i < gList.size(); i++) {
							GambleEntity t = gList.get(i);
							String message = "此次开奖结果为："+maxPointsStr+"("+maxRoundPoints+"),恭喜你获得"+bonus+"铜";
							rewardPlayer(t.getPlayer().getId(), bonus, true, message);
							name += t.getPlayer().getName()+" ";
						}
					} else {
						for (Map.Entry<Integer, GambleEntity> entry : gamblerMap.entrySet()) {
							Integer playerId = entry.getKey();
							rewardPlayer(playerId, M_REWARD, false, null);
							name += entry.getValue().getPlayer().getName()+" ";
						}
					}
					String info = "此次开奖结果为："+maxPointsStr+"("+maxRoundPoints+"),恭喜"+name+"获得"+(fiveTotalMoney.get() * 90 /100)+"铜";
					if (result != null) {
						result.setVO("info", info);
						result.setStatus("info");					
					}
					//result.setVO("fiveTotalMoney", fiveTotalMoney.get());
				}
				expireTime = 0;
				fiveTotalMoney.set(0);
				gamblerMap.clear();
			}
			if (result != null && maxRoundPoints > 0) {
				result.setVO("roundMaxPoints", maxRoundPoints);
				result.setVO("roundMaxPointStr", maxPointsStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			checkAndEndHorseRound(result);
		} catch (Exception e) {
			
		}
	}
	
	private static void rewardPlayer(int playerId, int money, boolean flag, String message) {
		Player p = PlayerMG.instance.getOnlinePlayer(playerId);
		if (p == null) {
			p = PlayerMG.instance.getPlayerSimple(playerId);
		}
		p.addCopper(money);
		if (flag) {
			ChatService.sendMessageSystem(playerId, message);
		} else {
			ChatService.sendMessageSystem(playerId, "由于此局没有其他人参赌，系统退还你"+money+"铜");
		}
	}
	
	public static void generateRoundPoints(Player player) throws NoMoneyException {

		if (!player.consumeInCopper(M_REWARD)) {
			throw new NoMoneyException("no money");
		}
		checkAndStartRound(player);
		synchronized (fiveTotalMoney) {
			fiveTotalMoney.addAndGet(M_REWARD);
		}
		
		GambleEntity ge = gamblerMap.get(player.getId());
		if (ge == null)   {
			ge = new GambleEntity();
			ge.setPlayer(player);
			gamblerMap.put(player.getId(), ge);
		}
		int[] arry = openQuotation(5);
		int currentPoints = calResult(arry);
		int[] oldCurr = ge.getCurrentRoundPoints();
		int[] maxRoundPoints = ge.getMaxRoundPoints();
		if (oldCurr != null) {
			if (maxRoundPoints != null && maxRoundPoints.length > 0) {
				int maxPoints = calResult(maxRoundPoints);
				if (currentPoints > maxPoints) {
					ge.setMaxRoundPoints(arry);
				}
			} else {
				int oldPoints = calResult(oldCurr);
				if (currentPoints > oldPoints) {
					ge.setMaxRoundPoints(arry);					
				} else {
					ge.setMaxRoundPoints(oldCurr);
				}
			}
		}
		ge.setCurrentRoundPoints(arry);
	}
	
	public static void viewPoints(Player player, CommandResult result) {
		GambleEntity ge = gamblerMap.get(player.getId());
		result.setVO("num", gamblerMap.size());
		result.setVO("totalMoney", fiveTotalMoney.get()/1000);
		result.setVO("leftTime", (expireTime - System.currentTimeMillis())/1000);
		int[] maxRoundPoints = ge.getMaxRoundPoints();
		if (maxRoundPoints != null) {
			int max = calResult(maxRoundPoints);
			result.setVO("maxPoints", ge.convert2Str(maxRoundPoints)+"("+max+")");		
		}
		int[] arry = ge.getCurrentRoundPoints();
		int currentPoints = calResult(arry);
		result.setVO("currentPoints", ge.convert2Str(arry)+"("+currentPoints+")");
	}
	
	public static GambleEntity getGambleEntity(Player player) {
		return gamblerMap.get(player.getId());
	}
	
	public static List<String> getGambleHorseList(Player player) {
		List<String> list = new ArrayList<String>();
		for (Map.Entry<String, Map<Integer, Player>> entry : gamblerHorseMap.entrySet()) {
			String horse =  entry.getKey();
			Map<Integer, Player> map = entry.getValue();
			if (map != null)   {
				Player p = map.get(player.getId());
				if (p != null) {
					list.add(horse);
				}
			}
		}
		return list;
	}
	
	public static void viewHorsePoints(Player player, CommandResult result) {
		/**
		GambleEntity ge = gamblerHorseMap.get(player.getId());
		result.setVO("num", gamblerHorseMap.size());
		result.setVO("totalMoney", fiveTotalHorseMoney.get()/1000);
		result.setVO("leftTime", (expireHorseTime - System.currentTimeMillis())/1000);
		int[] maxRoundPoints = ge.getMaxRoundPoints();
		if (maxRoundPoints != null) {
			int max = calResult(maxRoundPoints);
			result.setVO("maxPoints", ge.convert2Str(maxRoundPoints)+"("+max+")");		
		}
		int[] arry = ge.getCurrentRoundPoints();
		int currentPoints = calResult(arry);
		result.setVO("currentPoints", ge.convert2Str(arry)+"("+currentPoints+")");
		*/
	}
	
	public static void main(String []args) {
		for (int j = 0; j < 50; j++) {
			int [] result = openQuotation(3);
			int type = publishResult(result);
			for (int i = 0; i < result.length; i++) {
				System.out.print(result[i]);
				System.out.print(" ");
			}
			System.out.print(" : "+type);
			System.out.println();
		}
	}
}
