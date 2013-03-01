package com.ppsea.ds.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.IntimateScoreRank;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * 活动数据管理器类
 **/
public class PlayMG {
	/** logger writer. */
	private static Logger logger = Logger.getLogger(PlayMG.class);
	public static final Logger  dispateLog = Logger.getLogger("QIXI");
	
	public static PlayMG instance = new PlayMG();
	
	private static int[] ITEM_SUIT = {10273,10274,10276,10277};						//游龙套装ID列表,不包括腰带
	private static int ITEM_SUIT_YD_ID = 10275;										//腰带ID
	private static long UPDATE_INTERVAL = 1000*60*10;								//更新内存数据间隔24小时
	private static int GIFT_DISPATE_HOUR = 20;										//每天礼品发送时间
	private static long GIFT_DISPATE_INTERVAL = 24*3600*1000;						//礼品发放间隔隔24小时
	private static int FREQUENCY = 1000;											//后台线程执行频率
	
	private Background background = null;
	private List<IntimateScoreRank> topLst = new ArrayList<IntimateScoreRank>(10);	//缓存排名前10的玩家
	private List<IntimateScoreRank> rankLst = new ArrayList<IntimateScoreRank>(90);	//缓存排名前10的玩家
	private long lastUpdate = 0;													//最近一次更新
	private long lastDispate = 0;													//最近一次发放礼品
	private Random RAND = new Random();												//随机对象	
	
	/**
	 * 七夕活动 
	 **/	
	private PlayMG (){
		background = new Background();
	}
	
	/**
	 * 初始化 
	 **/
	public void init(){		
		background.start();//启动后台线程
		dispateLog.debug("Play Manager start.");
	}
	
	/**
	 * 返回排名前10的玩家 
	 **/
	public List<IntimateScoreRank> getTopRankLst(){				
		return topLst;
	}
	
	/**
	 * 随机返回游龙套装 
	 **/
	public int getRandomItemID(){
		//先判断腰带是否开出来
		if(RAND.nextInt(100) < 5){//5%腰带几率
			return ITEM_SUIT_YD_ID;
		}
		int index = RAND.nextInt(ITEM_SUIT.length);
		return ITEM_SUIT[index];
	}
	
	/**
	 * 逻辑处理 
	 **/
	private void work(){
		long time = System.currentTimeMillis();
		if(time - lastUpdate >= UPDATE_INTERVAL){
			//更新内存排行数据
			update();
		}
		/*//是否发放礼品
		Calendar calen = Calendar.getInstance();
		calen.setTimeInMillis(time);
		int hour = calen.get(Calendar.HOUR_OF_DAY);
		if(hour == GIFT_DISPATE_HOUR && (time-lastDispate) >= GIFT_DISPATE_INTERVAL && (topLst.size() > 0 || rankLst.size() > 0)){		
			if(!hasUpdated)update();
			dispate();			
		}	*/	
	}
	
	/**
	 * 更新内存数据 
	 **/
	public void update(){
		try {
			List<IntimateScoreRank> lst = DBManager.queryAllIntimateScoreRank();
			topLst.clear();
			for(int i=0;i<lst.size() && i<10;i++){
				topLst.add(lst.get(i));
			}
			rankLst.clear();
			for(int j=10;j<lst.size();j++){
				rankLst.add(lst.get(j));
			}
			lst = null;
		}catch(Exception e){
			logger.error("update exception.", e);
		}		
		lastUpdate = System.currentTimeMillis();
	}
	
	
	/**
	 * 该类负责玩家礼品的发放
	 **/
	class Background implements Runnable {
		Thread thread = null;
		boolean terminate = false;
		Background(){
			thread = new Thread(this,"Background");
			thread.setDaemon(true);
		}
		public void run(){
			while(!terminate){
				try {
					work();
					Thread.sleep(FREQUENCY);
				}
				catch(Exception e){
					logger.error("Play manager occur some exception.", e);
				}
			}
		}		
		void start(){
			thread.start();
			logger.info("PlayMG background thread started.");
		}
		void stop(){
			terminate = true;
		}
	}
	
	/**
	 * 测试
	 **/
	public static void main(String[] args){
		
	}
}
