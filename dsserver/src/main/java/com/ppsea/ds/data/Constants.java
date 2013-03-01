package com.ppsea.ds.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ppsea.ds.util.GlobalConfig;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Config;
import com.ppsea.ds.data.model.Notice;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.service.ErrorCode;

/**
 * ClassName:Constants
 *
 * @author   Daniel Hao
 * @version  
 * @since    Ver 1.0
 * @Date	 2009	Feb 9, 2009		3:40:15 PM
 *
 * @see 	 
 */
public class Constants {
	private static final Logger log = Logger.getLogger(Constants.class);
	
	/**
	 * 百分比，以万为单位
	 */
	public static final int BASE_PRE = 10000;
	
	public static String TEXT_MONSTER = "怪物";
	public static String TEXT_OFFLINE = "离线";
	public static String DEFAULT_DIALOG = "";
	
	public static String DEFAULT_LOCATION = "旅途中";
	
	public static String GOLD_NAME = "金锭";
	public static String ADV_GOLD_NAME = "金票";
	
	public static String PAY_QB = "qbpay";
	public static String PAY_SHENZHOUFU = "shenzhoufuPay";
	public static String PAY_SMS = "smsPay";
	public static String PAY_LBS = "lbsExchange";
	public static String PAY_WAP = "wapPay";
	
	public static int EXCHANGE_SILVER_COPPER = 1000;
	public static int EXCHANGE_GOLD_SILVER = 100;
	
	//用户状态
    public static byte STATUS_OFFLINE = 0;	
	public static byte STATUS_ONLINE = 1;	
	public static byte STATUS_LOCKED = 2;	
	
	public static int PAGE_SIZE = 10;
	public static int PLAYER_CACHE_LIMIT = 3000;	//player的缓存容量3000个
	public static int IDLE_TIME = 15 * 60 * 1000;			//发呆超时离线时间15分钟		
	public static int SCAN_INTERVAL = 5*1000;		//扫描离线用户时间间隔5秒
	public static int REPORT_INTERVAL = 10*60*1000;	//3分钟上报次用户
	
	public static int MAX_PLAYER_NUM = 0;			//注册角色数限制，0标示不限制
	public static int CURR_PLAYER_NUM = 0;			//当前总角色数
	public static int MAX_PLAYER_IN_USER = 3; 		//每个用户最多3个角色
	
	public static int BORN_CITY_ID;
	public static int BORN_FACILITY_ID;
	public static int CENTER_ID; //广场的设施ID	
	public static int YIGUAN_ID;	//医馆设施id

	public static int TAX_ADD_TIME = 0;
	
	public static int STATION_ID;	//驿站id
	public static CityFacility BORN_LOCATION = null;
	
	public static int NPC_CHUCHENG_ID;	//出城npcid
	public static int NPC_CHUANSONG_ID;	//传送npcid
	
	public static int SEND_PUBLIC_INTERVAL = 10; // 发言最低间隔（秒）
	public static int PUBLIC_INTERVAL = 2 * 60; // 发公聊时间间隔（秒）
	
	public static String PREFIX_MONSTER = "monster";
	public static String PREFIX_NPC = "npc";
	public static String PREFIX_QUESTION = "question";
	
	public static ErrorMsg SUCC = new ErrorMsg(ErrorCode.SUCC, Integer.valueOf(0));
	public static ErrorMsg ERR_SYS = new ErrorMsg(ErrorCode.ERR_SYS, "系统忙，请稍后再试");
	public static List<Zone> ZONES = new LinkedList<Zone>();
	
	// 快速进行任务消耗的物品
	public static int ITEM_MISSION_GUIDE;	//遁地符
	
	public static int HUNT_BONUS_GOLD = 0;
	public static int MISSION_HUNT_ID = 0;
	public static int HUNT_LEVEL_MIN = 10;
	
	
	public static int ITEM_XILIAN_ID;	//洗练石id
	public static int ITEM_XILIAN_GOLD; //洗练所需要人民币
	public static int ITEM_XILIAN_COPPER;//洗练所需要游戏币
	
	public static int ITEM_WAKONG;//挖空时所需要消耗的道具
	
	public static int ITEM_FORTUNE;//幸运石
	
	public static final long ONE_MINUTE = 60 * 1000;
	
	
	/************************ player 身上临时key begin *******************************/
	public static String AUTO_TIME = "auto_time_";//自动回血时间，打架时回血用
	public static String COLL_TIME = "coll_time";//打劫冷却时间
	
	/************************ player 身上临时key  end  *******************************/
	
	/***************************** 玩家每天规定次数限制 key******************************/
	public static final String DART_ROBE = "dartRobe";//玩家每天劫镖次数
	public static final String GET_FRAM = "getFram";//玩家每天摘取次数
	public static int DART_TIME = 10;//玩家每天劫镖次数上限
	public static int DART_ROB_TIME = 2;//玩家每天被劫镖次数上限
	/***************************** 玩家每天规定次数限制 key******************************/
	
	public static void init() throws PpseaException{
		
		Constants.MAX_PLAYER_NUM = Integer.parseInt(GlobalConfig.zone.attributeValue("maxPlayerNum"));
		Constants.CURR_PLAYER_NUM = DBManager.queryTotalPlayer();
		
		Constants.PLAYER_CACHE_LIMIT = Integer.parseInt(GlobalConfig.zone.attributeValue("playerCacheLimit"));
		
		loadConfig();
		STATION_ID = getIntValue("sys.station.id"); 
		BORN_CITY_ID = getIntValue("sys.born.city");
		BORN_FACILITY_ID = getIntValue("sys.born.facility");
		NPC_CHUCHENG_ID = getIntValue("sys.npc.chucheng"); 
		NPC_CHUANSONG_ID = getIntValue("sys.npc.chuansong"); 
		
		CENTER_ID = getIntValue("sys.center.id");
		YIGUAN_ID = getIntValue("sys.yiguan.id");
		

		TAX_ADD_TIME = getIntValue("sys.tax.addtime");
//
		ITEM_MISSION_GUIDE = getIntValue("item.mission.guide");
		//========================
		HUNT_BONUS_GOLD = getIntValue("hunt.bonus.gold");
		HUNT_LEVEL_MIN = getIntValue("hunt.level.min");
		//========================
		
		
		ITEM_XILIAN_ID = getIntValue("item.xilian.item");
		ITEM_XILIAN_GOLD = getIntValue("item.xilian.gold");
		ITEM_XILIAN_COPPER = getIntValue("item.xilian.copper");
		ITEM_WAKONG = getIntValue("item.wakongfu");
		ITEM_FORTUNE = getIntValue("item.improve.fortune");
		DART_TIME = getIntValue("sys.dart.time");
				
				
		initZones();
		
	}
	
	public static void initZones(){
		//获得每个区信息
		ZONES.clear();
		List<Element> nodes = GlobalConfig.root.elements("zone");
        for (Iterator<Element> it = nodes.iterator(); it.hasNext();) {
           Element elm = it.next();
           Zone zone = new Zone();
           zone.setId(elm.attributeValue("id"));
           zone.setName(elm.attributeValue("name"));
           zone.setHost(elm.attributeValue("host"));
           zone.setPort(Integer.valueOf(elm.attributeValue("port")));
           zone.setAdminPort(Integer.valueOf(elm.attributeValue("adminPort")));
           zone.setSortId( Integer.valueOf(elm.attributeValue("sortId")));
           zone.setOnlinePlayer(0);
           
           Element desc = elm.element("desc");
           zone.setDescDefault(desc.attributeValue("default"));
           zone.setDescFullNum(Integer.valueOf(desc.attributeValue("fullNum")));
           zone.setDescBusyNum(Integer.valueOf(desc.attributeValue("busyNum")));
           
           Element proxy = elm.element("proxy");
           zone.setProxyIp(proxy.attributeValue("host"));
           zone.setProxyPort(Integer.valueOf(proxy.attributeValue("port")));
           zone.setProxyKey(proxy.attributeValue("md5Key"));
           
           ZONES.add(zone);
        }
        Collections.sort(ZONES,new Comparator<Zone>(){
			public int compare(Zone o1, Zone o2) {
				return o1.getSortId() - o2.getSortId();
			}  
        });
	}
	
	/******************************************************************************************
	 * 系统参数
	 * ****************************************************************************************/
	private static Map<String, String> configCache = new HashMap<String, String>();

	public static void loadConfig() throws PpseaException {
		List<Config> list = DBManager.queryAllConfig();
		for(Config cf:list){
			configCache.put(cf.getName(), cf.getValue());
		}
		log.info("MAX_PLAYER_NUM=" + Constants.MAX_PLAYER_NUM + " ;CURR_PLAYER_NUM=" + Constants.CURR_PLAYER_NUM);
	}

	public static String getValue(String key) {
		return configCache.get(key);
	}

	public static int getIntValue(String key) throws PpseaException {
		String value = configCache.get(key);
		if (value == null)
			throw new PpseaException("no value for " + key);
		return Integer.valueOf(value);
	}
	
	public static int getIntValue(String key, int value){
		int ret = value;
		
		try {
			ret = getIntValue(key);
		} catch (PpseaException e) {
		}
		
		return ret;
	}
	
	/******************************************************************************************
	 * 公告
	 * ****************************************************************************************/
	private static Map<Integer,Notice> noticeMap = new HashMap<Integer,Notice>();
	public static void loadAllNotice() {
		List<Notice> notices = DBManager.queryAllNotice();
		for(Notice no:notices){
			noticeMap.put(no.getId(),no);
		}
	}
	public static Map<Integer,Notice> getNoticeMap() {
		return noticeMap;
	}
	public static Notice getNotice(int noId){
		return noticeMap.get(noId);
	}
	
	/**
	 * life item definition.
	 */
	public static final String LI_EXPIRED = "即将过期";
	public static final String LI_HOUR = "小时";
	public static final String LI_MINUTE = "分钟";
	public static final String LI_SECOND = "秒";
	public static final String LI_OTHER = "后过期";
	
	//倒卖系统
	public static final int INT_MAX_VALUE = 2000000000;//int最大范围2147483647
}
