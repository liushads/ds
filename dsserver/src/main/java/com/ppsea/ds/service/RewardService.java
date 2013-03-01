/*
 * 
 */
package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.PetTalent;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerPet;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2010-9-19
 */
public class RewardService {

	private static Logger logger = Logger.getLogger("Reward");
	
	/**
	 * 中秋活动数据.
	 */
	private static int activeDay;//当前活动日
	private static Integer dayCounter = 0;//每天取得Q币的人数，不能超过六个
	private static List<Integer> allPlayerList = new LinkedList<Integer>();//参与过的玩家列表
	
	/**
	 * 中秋节活动9月22-24日.
	 * <@a href="/p?n_re/${pid}/m"/>领取中秋活动大奖</a><br/>
	 */
	public static ErrorMsg middleAutumn(Player player) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		if (month != Calendar.SEPTEMBER) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "活动不在当前月内，活动所在月份为九月");
		}
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth < 22 || dayOfMonth > 24) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "活动不在有效日期内，活动有效日期为9月22-9月24");
		}
		if (allPlayerList.contains(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "你已经参加过该活动，不能重复参加");
		}
		/**
		 * 玩家达到活动条件.
		 */
		String msg = "恭喜你赢得中秋大奖,";
		/**
		synchronized (dayCounter) {
			if (dayOfMonth != activeDay) {
				//活动按天进行，每天清空前一天的计数
				dayCounter = 0;
				activeDay = dayOfMonth;
			}
			if (dayCounter < 6) {
				//当日送出Q币人数没达到6人，送Q币，否则只送宠物礼包(10野果,五银)
				logger.info(LoggerHelper.ZONE_ID+"|mid-autumn|Q|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|10|"+dayCounter);
				dayCounter++;
				msg += "10Q币,";
			}
		}
		*/
		allPlayerList.add(player.getId());
		player.addCopper(5 * 1000);
		ItemService.addItem(player, 10353, 10, false);//野果
		msg += "5银，10个野果!";
		logger.info(LoggerHelper.ZONE_ID+"|mid-autumn|R|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|10353|10|5");
		return new ErrorMsg(ErrorCode.SUCC, msg);
	}
	

	/**
	 * 国庆活动10月1日~10月10日.
	 */
	public final static int ITEM_ID_LPKA = 10430;
	
	/**
	 * 国庆任务令.
	 * OCT_ITEM_A对应给银的任务.
	 * OCT_ITEM_B对应给经验的任务.
	 */
	public static final int OCT_ITEM_A = 10432;
	
	public static final int OCT_ITEM_B = 10433;
	
	/**
	 * 国庆活动兑换.
	 * <@a href="/p?n_re/${pid}/o"/>国庆礼品卡兑换</a><br/>
	 * @param player
	 * @param itemId 目标道具id
	 * @param amount 礼品卡的数量
	 * @return
	 */
	public static ErrorMsg octoberFestival(Player player, int type) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		
		
		if (month != Calendar.OCTOBER) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "活动不在当前月内，活动所在月份为十月");
		}
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth > 10) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "活动不在有效日期内，活动有效日期为10月1日-10月10日");
		}
		
		
		List<Integer> itemIdList = new ArrayList<Integer>();
		int amount = 0;
		int toGiveAmount = 1;
		switch(type) {
		case 1:
			itemIdList.add(10318);//朱雀神石
			/**
			itemIdList.add(10313);//青龙神石
			itemIdList.add(10323);//玄武神石
			itemIdList.add(10328);//白虎神石
			*/
			amount = 1000;
			break;
		case 2:
			itemIdList.add(82);//青铜回血符
			amount = 200;
			break;
			/**
		case 3:
			itemIdList.add(84);//青铜凝神符
			amount = 100;
			break;
			*/
		case 4:
			itemIdList.add(10281);
			amount = 100;//宝莲灯(100)
			break;
		case 5:
			itemIdList.add(10333);
			amount = 100;//小行囊
			break;
		case 6:
			itemIdList.add(10346);//白银回血符
			amount = 900;
			break;
		case 7:
			itemIdList.add(36);
			amount = 500;//强化宝石
			break;
		case 8:
			itemIdList.add(10387);
			amount = 500;//升星石
			break;
		case 9:
			itemIdList.add(10392);
			toGiveAmount = 20;
			amount = 1;//追心箭
			break;
		case 10:
			itemIdList.add(10347);//白银凝神符
			amount = 900;
			break;
			default:
		}
		if (itemIdList.size() <= 0) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_TYPE,"类型错误");
		}
		PlayerItem lpKa = ItemService.findPlayerItem(player, ITEM_ID_LPKA);
		if (lpKa == null || lpKa.getAmount() < amount) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "国庆礼品卡不存在，或者数量不足");
		}
		ErrorMsg msg = ItemService.releasePlayerItem(player, lpKa, amount, true);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		String str = "";
		for (int id : itemIdList) {
			msg = ItemService.addItem(player, id, toGiveAmount, false);
			if (msg.code == ErrorCode.SUCC) {
				str += "|"+toGiveAmount+"|"+id;
			}
		}
		logger.info(LoggerHelper.ZONE_ID+"|oct-1|R|"+player.getUserId()+"|"+player.getId()+"|"+player.getLevel()+"|"+type+"|"+amount+"|10430"+str);
		str = "";
		for (int id : itemIdList) {
			Item kpKa = ItemMG.instance.getItem(id);
			str += toGiveAmount+"个"+kpKa.getName()+",";
		}
		str = str.substring(0, str.length() - 1);
		msg.setText("恭喜你用"+amount+"张国庆礼品卡成功兑换"+str+",剩余"+(lpKa == null?0:lpKa.getAmount())+"张礼品卡");
		
		return msg;
	}
	
	public static final String ACTIVE_NAME_JIANGHULING = "jianghuling_monster";
	public static final String ACTIVE_NAME_JIANGHULING_1202 = "jianghuling_1202";
	public static ErrorMsg bufaJiangHuLing(Player player) {
		if(player.getLevel() < 70) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "不满足赠送条件，必须 70以上，包含70级");
		}
		String activeKey = player.getId().toString() + "_"+ACTIVE_NAME_JIANGHULING_1202 + "_" +0;
		PlayerActive pa = player.getPlayerActive().get(activeKey);
		if (pa == null) {
			pa = PlayerMG.instance.createPlayerActive(player, ACTIVE_NAME_JIANGHULING_1202, 0);
			pa.setTime(player.getLevel());
			player.getPlayerActive().put(activeKey, pa);
			ItemService.addItem(player, 10358, 20, false);//3个斗神令
			DBService.commit(pa);
			return new ErrorMsg(ErrorCode.SUCC, "领取成功，系统赠送你20个斗神令");
		} else {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "你已经成功领取过斗神令，不能重复参与");
		}
	}
	
	
	private static long startTime = 0;
	
	public synchronized static void invokeDoubleExp(Player player) {
		if (!LoggerHelper.ZONE_ID.equals("10")) {
			return;
		}
		if (startTime == 0) {
			if (player.getLevel() == 40) {
				startTime = System.currentTimeMillis();
				logger.info(LoggerHelper.ZONE_ID + "|DoubleExpTime|S|" + player.getUserId() + "|" + player.getId() + "|" + player.getLevel());
				ChatService.sayXiaoQ("全区荣耀时刻，有你而精彩,恭喜" + player.getName()
				        + "第一个升级到40级，触发系统开启2小时的打怪额外双倍经验");
			}
		}
	}
	
	public static boolean isDoubleExpForNewZone() {
		
		// 活动结束.
		if (!LoggerHelper.ZONE_ID.equals("10")) {
			return false;
		}
		long currTime = System.currentTimeMillis();
		if (startTime > 0) {
			//活动两个小时
			if (currTime - startTime > 2*60*60*1000) {// 2*60*60*1000
				startTime = -1;
				logger.info(LoggerHelper.ZONE_ID+"|DoubleExpTime|E|");
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isDoubleExpForFightMonster() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (month == Calendar.JANUARY && dayOfMonth == 1) {
			return true;
		}
		return false;
		
	}
}
