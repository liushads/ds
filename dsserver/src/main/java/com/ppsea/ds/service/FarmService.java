package com.ppsea.ds.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.FarmPre;
import com.ppsea.ds.data.model.GetOnce;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerFarm;
import com.ppsea.ds.util.Util;

public class FarmService {
	
	public static final int ZHANHUAN = 5;//当生成战魂是随机
	public static final int SPEED = 100;//加速生长时所需要消耗的金，单位分
	public static final int MAX_LEVEL = 6;
	public static final int LENGTH = 10;//成长间隔，分钟
	public static final int LIMIT_TIMES = 30;//每天最多可以摘取次数
	/**
	 * 检查玩家宝树生长状态
	 * @param player
	 */
	public static void checkSelf(Player player){
		if (player.getPlayerFarm() == null || player.getPlayerFarm().size() <= 0) {
			return;
		}
		TreeMap<Integer, PlayerFarm> farms = player.getPlayerFarm();
		
		int level = 0;
		for (PlayerFarm playerFarm : farms.values()) {
			level = getLevel(playerFarm);
			boolean tmp = false;
			if (playerFarm.getFarmStatus() != level) {
				playerFarm.setFarmStatus(level);
				tmp = true;
			}
			if (playerFarm.getFarmStatus().intValue() != playerFarm
					.getRewardStatus().intValue()) {
				playerFarm.setRewardStatus(playerFarm.getFarmStatus());
				// 当前等级与生成的结果不相同时生成新的结果
				if (playerFarm.getFarmStatus() >= 1
						&& playerFarm.getFarmStatus() <= 5) {
					tmp = true;
					getPre(playerFarm);
				}
			}
			if (tmp) {
				DBService.commit(playerFarm);
			}
		}
		
	}
	
	/**
	 * 初始化玩家宝树，当玩家查看宝树时再进行初始化
	 * @param player
	 * @return
	 */
	public static TreeMap<Integer, PlayerFarm> creatPlayerFarm(Player player){
		TreeMap<Integer, PlayerFarm> farms = player.getPlayerFarm();
		int num  = player.getLevel() / 10 + 1;
		if (num > 10) {// TODO 临时限制，最多每个玩家生成10棵宝树
			num = 10;
		}
		if (farms != null && farms.size() >= num) {
			return farms;
		}
		if (farms == null) {
			farms = new TreeMap<Integer, PlayerFarm>();
		}
		for (int i = farms.size(); i < num; i++) {
			PlayerFarm playerFarm = new PlayerFarm();
			playerFarm = new PlayerFarm();
			playerFarm.setId(GlobalGenerator.instance.getIdForNewObj(playerFarm));
			playerFarm.setPlayerId(player.getId());
			playerFarm.setRewardStatus(0);
			playerFarm.setFarmStatus(0);
			playerFarm.setLastUpgradeTime(new Date());
			playerFarm.setFruitType(0);
			playerFarm.setFruitNum(0);
			playerFarm.setEvent(0);
			farms.put(playerFarm.getId(), playerFarm);
			DBService.commit(playerFarm);
		}
		player.setPlayerFarm(farms);
		
		return farms;
	}
	
	private static int getLevel(PlayerFarm playerFarm){
		long now  = System.currentTimeMillis();
		long last_update = 0;
		int lenght = 0;
		last_update = playerFarm.getLastUpgradeTime().getTime();
		lenght = (int) ((now - last_update) / Constants.ONE_MINUTE);
		int level = lenght / 10;
		if (level > MAX_LEVEL) {
			level = MAX_LEVEL;
		}
		return level;
	}

	/**
	 * 加速果树的生长状态
	 * @param player
	 * @param player_farm_id
	 * @return
	 */
	public static ErrorMsg upSpeed(Player player,int player_farm_id){
		if (player.getPlayerFarm() == null || !player.getPlayerFarm().containsKey(player_farm_id)) {
			return new ErrorMsg(-1, "此宝树不存在，还是加速其他宝树吧！");
		}
		PlayerFarm playerFarm = player.getPlayerFarm().get(player_farm_id);
		//检查是否已经是最高等级了
		int level = getLevel(playerFarm);
		if (level >= MAX_LEVEL) {
			return new ErrorMsg(-1, "此宝树已经成熟了，不需要加速了！");
		}
		if (!player.consumeInAdvGlod(SPEED)) {
			return new ErrorMsg(-1, "水晶不足！");
		}
		Calendar cal = Calendar.getInstance();   
		cal.setTime(playerFarm.getLastUpgradeTime());
		cal.add(Calendar.MINUTE, -LENGTH);
		playerFarm.setLastUpgradeTime(cal.getTime());
		getPre(playerFarm);
		DBService.commit(playerFarm);
		return new ErrorMsg(ErrorCode.SUCC, "加速成功");
	}
	
	/**
	 * 升级时随机生成一个结果
	 * @param playerFarm
	 */
	private static void getPre(PlayerFarm playerFarm){
		// 只有第一季以上才行
		Map<String, Integer> pre = new HashMap<String, Integer>();
		Map<Integer, FarmPre> map = ResourceCache.instance
				.getFarmPars().get(
						playerFarm.getFarmStatus().intValue());
		for (FarmPre fp : map.values()) {
			pre.put(fp.getId() + "", fp.getPre());
		}
		String key = Util.getPreKey(pre);
		int farmPre_id = Integer.parseInt(key);

		FarmPre fp = map.get(farmPre_id);
		playerFarm.setFruitType(fp.getRewardType());
		if (farmPre_id == ZHANHUAN) {
			// TODO 临时处理
			int num = ResourceCache.instance.random.nextInt(10) + 1;
			playerFarm.setFruitNum(num);
		} else {
			playerFarm.setFruitNum(fp.getValue());
		}
	}
	
	
	public static ErrorMsg drop(Player player,int player_farm_id){
		if (player.getPlayerFarm() == null || !player.getPlayerFarm().containsKey(player_farm_id)) {
			return new ErrorMsg(-1, "此宝树不存在！");
		}
		PlayerFarm playerFarm = player.getPlayerFarm().get(player_farm_id);
		playerFarm.setFruitType(0);
		playerFarm.setFruitNum(0);
		DBService.commit(playerFarm);
		return new ErrorMsg(ErrorCode.SUCC, "成功丢弃！");
	}
	
	/**
	 * 摘取果实
	 * @param player
	 * @param player_farm_id
	 * @param isBuff 是否使用金，增加20%收入
	 * @return
	 */
	public static ErrorMsg getResult(Player player,int player_farm_id,boolean isBuff){
		GetOnce getOnce = GetOnceService.getByType(player, Constants.GET_FRAM);
		if (!GetOnceService.checkToday(getOnce, LIMIT_TIMES)) {
			return new ErrorMsg(-1, "今天你已经收获次" + LIMIT_TIMES + "了，还是明天再来吧");
		}
		if (player.getPlayerFarm() == null || !player.getPlayerFarm().containsKey(player_farm_id)) {
			return new ErrorMsg(-1, "此宝树不存在，还是看看其他宝树吧！");
		}
		PlayerFarm playerFarm = player.getPlayerFarm().get(player_farm_id);
		int type = playerFarm.getFruitType();
		int num  = playerFarm.getFruitNum();
		if (type <= 0 || num <= 0) {
			return new ErrorMsg(-1, "此宝树已经被摘过了，还是看看其他宝树吧！");
		}
		int buff = 0;
		if (isBuff) {
			if (!player.consumeInAdvGlod(SPEED)) {
				return new ErrorMsg(-1, "水晶不足！");
			}
			buff = 20;
		}
		num  = num + num * buff / 100;
		
		//增加今天摘取次数
		GetOnceService.addGetOnce(player, getOnce, Constants.GET_FRAM);
		switch (type) {
		case 1://经验
		case 2://经验
			player.addExp(num);
			DBService.commit(player);
			return new ErrorMsg(ErrorCode.SUCC, "恭喜您获得了"+num+"经验");
		case 3://金币
		case 4://金币
			player.addCopper(num);
			DBService.commit(player);
			return new ErrorMsg(ErrorCode.SUCC, "恭喜您获得了"+num+"金币");
		case 5://战魂
			player.addEternal(num);
			DBService.commit(player);
			return new ErrorMsg(ErrorCode.SUCC, "恭喜您获得了"+num+"战魂");
		}
		return new ErrorMsg(ErrorCode.SUCC, "很遗憾，什么都没得到！请联系管理员");
	}
	
	/**
	 * 获取今天已经摘取了多少次了
	 * @param player
	 * @return
	 */
	public static int getTodayTimes(Player player){
		GetOnce getOnce = GetOnceService.getByType(player, Constants.GET_FRAM);
		return GetOnceService.getTodayTimes(getOnce);
	}
}
