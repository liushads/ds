/*
 * 
 */
package com.ppsea.ds.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
 * @date 2010-10-12
 */
public class ChongYangJieService {

	private static Logger logger = Logger.getLogger("Reward");
	
	public static final int ITEM_ID_JUHUA = 10443; 
	public static final int ITEM_ID_NUOMI = 10444;
	public static final int ITEM_ID_DOUSHA = 10445;
	public static final int ITEM_ID_HETAO = 10446;
	public static final int ITEM_ID_MEIGUI = 10447;
	public static final int ITEM_ID_ZHUFU = 10448;
	public static final int ITEM_ID_JUHUA_JIU = 10449;
	public static final int ITEM_ID_DOUSHA_GAO = 10450;
	public static final int ITEM_ID_MEIGUI_GAO  = 10451;
	public static final int ITEM_ID_HETAO_GAO = 10452;
	public static final int ITEM_ID_JIU  = 10453;
	public static final int ITEM_ID_GAO  = 10454;
	
	public static final String ACTIVE_NAME = "qingmingjie";
	
	/**
	 * 合成礼品.
	 * 
	 * @param player
	 * @param type
	 * @return
	 */
	public static ErrorMsg compose(Player player, int type) {

		int targetId = 0;
		List<Integer> itemIdList = new ArrayList<Integer>();
		switch (type) {
		case 1:
			//清明菊花酒
			itemIdList.add(10654);
			itemIdList.add(10659);
			targetId = 10661;
			break;
		case 2:
			//清明豆沙糕
			itemIdList.add(10655);
			itemIdList.add(10660);
			itemIdList.add(10656);
			targetId = 10662;
			break;
		case 3:
			//请明白玫瑰糕
			itemIdList.add(10655);
			itemIdList.add(10660);
			itemIdList.add(10657);
			targetId = 10663;
			break;
		case 4:
			//清明核桃糕
			itemIdList.add(10655);
			itemIdList.add(10660);
			itemIdList.add(10658);
			targetId = 10664;
			break;
		default:
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "参数无效");

		}
		ErrorMsg msg = checkComponent(player, itemIdList, targetId);
		if (msg.code != ErrorCode.SUCC) {
			//miss material.
			return msg;
		}
		for(int id : itemIdList) {
			msg = ItemService.releasePlayerItem(player, ItemMG.instance.getItem(id), true);
		}
		msg = ItemService.addItem(player, targetId, 1, false);
		return new ErrorMsg(ErrorCode.SUCC,"恭喜你成功合成了"+ItemMG.instance.getItem(targetId).getName());
	}
	
	private static ErrorMsg checkComponent(Player player, List<Integer> itemids, int targetId) {
		String str = "";
		for (int id : itemids) {
			PlayerItem pi = ItemService.findPlayerItem(player, id);
			if (pi == null) {
				str += ItemMG.instance.getItem(id).getName()+",";
			}
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
			if (targetId == 0) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"赠送失败，缺少"+str);
			} else {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,"不能合成"+ItemMG.instance.getItem(targetId).getName()+",缺少材料"+str);
				
			}
		}
		return new ErrorMsg(ErrorCode.SUCC,"");
	}
	
	private static Random rand = new Random();
	//private static Map<Integer, Calendar> giveMap = new ConcurrentHashMap<Integer, Calendar>();
	//private static Map<Integer, Calendar> wishMap = new ConcurrentHashMap<Integer, Calendar>();
	public static ErrorMsg give2Npc(Player player, int type) {
		Calendar cal = Calendar.getInstance();
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		
		if (type == 1) {
			//送糕点
			List<Integer> idList = new ArrayList<Integer>();
			idList.add(10661);
			idList.add(10662);
			idList.add(10663);
			idList.add(10664);
			ErrorMsg msg = checkComponent(player, idList, 0);
			if (msg.code != ErrorCode.SUCC) {
				//miss material.
				return msg;
			}
			Integer pid = player.getId();
			String activeKey = player.getId().toString() + "_"+ACTIVE_NAME + "_" +type;
			PlayerActive pa = player.getPlayerActive().get(activeKey);
			if (pa == null) {
				pa = PlayerMG.instance.createPlayerActive(player, ACTIVE_NAME, type);
				player.getPlayerActive().put(activeKey, pa);
				pa.setTime(1);
				DBService.commit(pa);
			} else {
				int num=0;
				Date date = new Date();
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				num = pa.getTime();
				if (!sf.format(date).equals(sf.format(pa.getJoinDate()))){
					num = 0;
					pa.setJoinDate(date);
				}
				if (num >= 10) {
						return new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,
						"赠送失败，你今天已经赠送过10次请明天再来");
				}
				pa.setTime(num + 1);
				DBService.commit(pa);
				DBService.commit(player);
				pa.setLastCal(cal);
			}
			
			//可以赠送.
			for (int id : idList) {
				ItemService.releasePlayerItem(player, ItemMG.instance.getItem(id), true);
			}
			//奖励
			player.addCopper(10000);
			player.addExp(20000);
			DBService.commit(player);
			//道具赠送.
			int tempId = 0;
			int i = 0;
			
			String str = "恭喜你送糕点成功，奖励你10银,20000经验,";
			String tmp = "";
			while (i < 2) {
				int id = rand.nextInt(itemPool.length);
				
				int amount = 1;
				int itemId = itemPool[id];
				if (tempId == itemId) {
					continue;
				}
				tempId = itemId;
				if (itemId == 10368) {
					amount = 4;
				}
				ItemService.addItem(player, itemId, amount, false);
				str += amount+"X"+ItemMG.instance.getItem(itemId).getName()+",";
				tmp +="|"+itemId;
				i++;
			} 
			str = str.substring(0, str.length() - 1);
			logger.info(LoggerHelper.ZONE_ID+"|qingmingjie|R|"+type+"|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|m|"+10000+"|exp|"+100000+tmp);
			return new ErrorMsg(ErrorCode.SUCC,str);
		} else if (type == 2) {
			//送祝福
			PlayerItem pi = ItemService.findPlayerItem(player, ITEM_ID_ZHUFU);
			if (pi == null) {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "没有准备好祝福语就别来凑热闹");
			}
			//Calendar lastCal = player.getLastWishDate();
			boolean flag = false;
			Integer pid = player.getId();
			String activeKey = player.getId().toString() + "_"+ACTIVE_NAME + "_" +type;
			PlayerActive pa = player.getPlayerActive().get(activeKey);
			if (pa == null) {
				pa = PlayerMG.instance.createPlayerActive(player, ACTIVE_NAME, type);
				player.getPlayerActive().put(activeKey, pa);
				flag = true;
			} else {
				Calendar lastCal = pa.getLastCal();
				int lastDay = lastCal.get(Calendar.DAY_OF_MONTH);
				if (dayOfMonth > lastDay) {
					pa.setLastCal(cal);
					flag = true;
				} 
			}
			if (flag) {
				//可以送祝福.
				ErrorMsg msg = ItemService.releasePlayerItem(player, ItemMG.instance.getItem(ITEM_ID_ZHUFU), true);
				if (msg.code != ErrorCode.SUCC) {
					return msg;
				}
				//奖励.
				player.addExp(20000);
				
				//player.setLastWishDate(cal);
				//wishMap.put(pid, cal);
				DBService.commit(pa);
				DBService.commit(player);
				
				int num = rand.nextInt(2);
				int itemId = 0;
				if (num == 0) {
					//青铜回血符
					itemId = 82;
				} else {
					//青铜凝神符
					itemId = 84;
				}
				ItemService.addItem(player, itemId, 1, false);
				logger.info(LoggerHelper.ZONE_ID+"|chongyangjie|R|"+type+"|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+itemId+"|1|exp|"+20000);
				return new ErrorMsg(ErrorCode.SUCC, "送祝福成功，奖励你经验20000，一个"+ItemMG.instance.getItem(itemId).getName());
			} else {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "每天只能赠送一次，请明天再来");
			}
		} else {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "参数无效");
		}
	}
	
	public static ErrorMsg exchange(Player player,List<Integer> itemIdList) {
		List<Integer> mList = new ArrayList<Integer>();
		ErrorMsg msg = exchange(player, itemIdList, mList);
		if (mList.size() == 0) {
			return msg;
		} else {
			int money = 0;
			for (int m : mList) {
				money += m;
			}
			logger.info(LoggerHelper.ZONE_ID+"|qingmingjie|E|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+money);
			return new ErrorMsg(ErrorCode.SUCC,""+money/1000);
		}
	}
	
	private static ErrorMsg exchange(Player player,List<Integer> itemIdList, List<Integer> mList) {
		int totalAmount = 0;
		for (int id : itemIdList) {
			PlayerItem pi = ItemService.findPlayerItem(player, id);
			if (pi != null&&pi.getBindId()!=null&&pi.getBindId()==0&&pi.getInExchange()==false) {
				totalAmount += pi.getAmount();
			}
		}
		if (totalAmount < 10) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "剩余材料不足10件");
		}
		//开始兑换.
		int amount = 0;
		for (int id : itemIdList) {
			PlayerItem pi = ItemService.findPlayerItem(player, id);
			if (pi != null&&pi.getBindId()!=null&&pi.getBindId()==0&&pi.getInExchange()==false) {

				if (amount + pi.getAmount() < 10) {
					amount += pi.getAmount();
					ItemService.releasePlayerItem(player, pi, pi.getAmount(), true);
				} else {
					int tmp = 10 - amount;
					ItemService.releasePlayerItem(player, pi, tmp, true);
					player.addCopper(1*1000);
					DBService.commit(player);
					amount = 0;
					mList.add(1*1000);
					break;
				}
				
			}
		}
		
		return exchange(player, itemIdList, mList);
	}
	
	private static int[] itemPool = new int[100];
	private static int[][] configuration = {{36,5},{37,5},{31,5},{10312,6},{10327,6},{10317,6},{10322,6},{70,6},{10513,1},{10475,6},{10476,6},{10477,6},{10403,5},{60,5},{10489,1},{10462,5},{10574,5},{10369,5},{10405,5},{10631,5}};
	public static void initItemPool() {
		int pos = 0;
		//36,强化宝石,14.5%
		//31,金刚宝钻,14.5%
		//10346,白银回血符,14.5%
		//37,升星宝石,14.5%
		//10403,宠物粮食,14.5%
		//10275,游龙乌金带 ,0.5%
		//10428,血魔指  ,0.5%
		//10427,混元金手  ,4%
		//10276,游龙玄金腿    ,4%
		//10273,游龙紫金盔  ,4%
		//10368,双倍经验卡,14.5%
		for (int i = 0; i < configuration.length; i++) {
			int itemId = configuration[i][0];
			int count = configuration[i][1];
			for (int j = 0; j < count; j++) {
				itemPool[pos] = itemId;
				pos++;
			}
		}
	}
	
	public static void main(String[] args) {
		initItemPool();
	}
	public static ErrorMsg duihuan(Player player,CommandResult result) {
		String td="";
		PlayerItem juhua= ItemService.findPlayerItem(player, 10654);
		PlayerItem nuomi= ItemService.findPlayerItem(player, 10655);
		PlayerItem dousha= ItemService.findPlayerItem(player, 10656);
		PlayerItem meigui= ItemService.findPlayerItem(player, 10657);
		PlayerItem hetao= ItemService.findPlayerItem(player, 10658);
		if (juhua!=null&&juhua.getAmount()<8) {
			td=td+"缺少清明菊花*"+(8-juhua.getAmount());
		}
		if (nuomi!=null&&nuomi.getAmount()<8) {
			td=td+"缺少清明糯米*"+(8-nuomi.getAmount());
		}
		if (dousha!=null&&dousha.getAmount()<8) {
			td=td+"缺少清明豆沙*"+(8-dousha.getAmount());
		}
		if (meigui!=null&&meigui.getAmount()<8) {
			td=td+"缺少清明白玫瑰*"+(8-meigui.getAmount());
		}
		if (hetao!=null&&hetao.getAmount()<8) {
			td=td+"缺少清明核桃*"+(8-hetao.getAmount());
		}
		if (juhua==null) {
			td=td+"缺少清明菊花*8,";
		}
		if (nuomi==null) {
			td=td+"缺少清明糯米*8,";
		}
		if (dousha==null) {
			td=td+"缺少清明豆沙*8,";
		}
		if (meigui==null) {
			td=td+"缺少清明白玫瑰*8,";
		}
		if (hetao==null) {
			td=td+"缺少清明核桃*8";
		}
		if (td.equals("")) {
			String activeKey = player.getId().toString() + "_"+"datiduihuan" + "_" +1;
			PlayerActive pa = player.getPlayerActive().get(activeKey);
			if (pa == null) {
				pa = PlayerMG.instance.createPlayerActive(player, "datiduihuan", 1);
				player.getPlayerActive().put(activeKey, pa);
			} else {
				Calendar calendar=Calendar.getInstance();
				Calendar car=Calendar.getInstance();
				Date date=new Date();
				calendar.setTime(date);
				car.setTime(pa.getJoinDate());
				long num=12*60-(calendar.getTimeInMillis()-car.getTimeInMillis())/(60*1000);
				if (num>0) {
					return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "距离下次领取还有"+num+"分钟");
				}
				pa.setJoinDate(date);
			}
			DBService.commit(pa);
			ItemService.releasePlayerItem(player, juhua,8, true);
			ItemService.releasePlayerItem(player, nuomi,8, true);
			ItemService.releasePlayerItem(player, meigui,8, true);
			ItemService.releasePlayerItem(player, dousha,8, true);
			ItemService.releasePlayerItem(player, hetao,8, true);
			ItemService.addItem(player, 10653, 1,false);
			td="兑换成功,你获得一个答题卷！";
			result.setVO("td", td);
		}else {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, td);
		}
		return new ErrorMsg(ErrorCode.SUCC,"");
	}
}
