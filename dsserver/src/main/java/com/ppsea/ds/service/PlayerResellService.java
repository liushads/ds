package com.ppsea.ds.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.SellPriceVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.data.model.ResellPrice;
import com.ppsea.ds.manager.DBManager;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.ResellPriceMG;

public class PlayerResellService {
	private static final Logger log = Logger.getLogger(PlayerService.class);
	
	public final static int BUYABLE_GOODS_MAX_NUM = 10;
	public final static int REFRESH_PRICE_COST_GOLD = 1;
	public final static int REFRESH_COST_GOLD_MAX_TIMES = 50;//花钱每日最大刷新次数
	public final static long REFRESH_TIME_SPACE = 10*60*1000l;        //10分钟时间间隔 （单位：毫秒）
	public final static int EXCHANGE_GOLD_START = 10*10000;//10W游戏币起兑
	public final static int EXCHANGE_RATE = 10000;//兑换比率 1：10000
	public final static int EXCHANGE_GOLD_MAX = 100*10000;//游戏币最大兑换限额   暂定100W???
	
	
	private final static int RANDOM_MAX_CODE = 1000000;
	private static final Random RND = new Random();
	
	/**
	 * 物价刷新  isCost是否花费Gold
	 * @param player
	 * @param isCost
	 */
	public static void refreshPrice(Player player,Boolean isCost){
		List<ResellPrice> rpList = ResellPriceMG.instance.getAllPrice();
		PlayerResell prObj = player.getPlayerResell();
		//update "price_list" field
		String prStr = prObj.getPriceList();
		prObj.setPriceList(refreshPrice(rpList, prStr));//价格列表
		
		if(isCost){
			prObj.setFlushTimes(prObj.getFlushTimes()+1);//次数++
		}
		prObj.setFlushTime(System.currentTimeMillis());//刷新时间 
		//update this object to player &DBSerice commit this object
		player.setPlayerResell(prObj);
		DBService.commit(prObj);
	}
	
	/**
	 * make new price order by flush
	 * string formate like this： id|price|sign,
	 * @param rpList
	 * @param prStr
	 * @return
	 */
	public static String refreshPrice(List<ResellPrice> rpList,String prStr){
		StringBuilder sb = new StringBuilder();
		if(prStr == null){
			//player first entry resell function,  init with min price
			for (ResellPrice rp : rpList) { //(id|price|sign,)
				sb.append(rp.getId()).append("|").append(rp.getMinPrice()).append("|").append("0").append(",");
			}
		}else{
			//convert self curr price from string to map
			Map<Integer,Integer> currMap = new HashMap<Integer, Integer>();
			String [] elements = prStr.split("\\,");
			for (String str : elements) {
				String [] ele = str.split("\\|");
				currMap.put(Integer.valueOf(ele[0]), Integer.valueOf(ele[1]));
			}
			for (ResellPrice rp : rpList) {
				sb.append(rp.getId()).append("|");
				int rdm = RND.nextInt(100);
				int floatPriceLast = currMap.get(rp.getId());
				int floatPrice = floatPriceLast;
				int sign = 0;
				if(rdm<=rp.getEffectRate()){
					floatPrice = floatPrice(floatPriceLast,rp.getMinPrice(), rp.getMaxPrice(), rp.getFloatPrice());
				}
				if(floatPrice>floatPriceLast){
					sign = 1;
				}
				if(floatPrice<floatPriceLast){
					sign = -1;
				}
				sb.append(floatPrice).append("|").append(sign).append(",");
			}
		}
		String result = sb.toString();
		return result.substring(0,result.length()-1);
	}
	
	public static int floatPrice(int currPrice,int minPrice,int maxPrice,int floatVol){
		int optType = RND.nextInt(2);
		if(optType >0){
			currPrice += floatVol;
		}else{
			currPrice -=floatVol;
		}
		if(currPrice <minPrice){
			currPrice = minPrice;
		}
		if(currPrice > maxPrice){
			currPrice = maxPrice;
		}
		return currPrice;
	}
	
	/**
	 * player first entry resell function
	 * init "player_resell" table,make new record
	 * @param player
	 */
	public static PlayerResell initPrice(Player player){
		PlayerResell prObj = new PlayerResell();
		
		prObj.setPlayerId(player.getId());//非空字段给初始值
		prObj.setResellGold(0);
		prObj.setGoodsValue(0);
		prObj.setExchangeGold(0);
		prObj.setFlushTime(0l);
		prObj.setFlushTimes(0);
		prObj.setGetTime(0l);
		prObj.setResetTime(0l);
		
		prObj.setBoughtGoods("");//防止第一次初始化更新内存时拆分字符串时空指针
		
		//set default value to other fileds,and set "price_list" 
		List<ResellPrice> rpList = ResellPriceMG.instance.getAllPrice();
		String prStr = refreshPrice(rpList, null);
		prObj.setPriceList(prStr);
		//update this object to player &DBSerice commit this object
		player.setPlayerResell(prObj);
		DBService.commit(prObj);
		return prObj;
	}
	

	/**
	 * Get Item Name For Page Desc
	 * @param spv
	 */
	public static void updateSellPriceVoName(SellPriceVo spv){
			//spv.setName(ItemMG.instance.getItem(spv.getId()).getName());
			spv.setName(ResellPriceMG.instance.getResellPrice(spv.getId()).getItemName());
	}
	
	public static void updateBoughtPriceVoName(List<BoughtPriceVo> priceList){
		for (BoughtPriceVo priceVo : priceList) {
			priceVo.setName(ItemMG.instance.getItem(priceVo.getId()).getName());
		}
	}
	
	
	/**
	 * update Current Price for page describe
	 * @param priceList
	 * @param idPriceMap
	 */
	public static void updateCurrPrice(List<BoughtPriceVo> priceList,Map<Integer,SellPriceVo> idPriceMap){
		for (BoughtPriceVo priceVo : priceList) {
			priceVo.setPrice(idPriceMap.get(priceVo.getId()).getPrice());
		}
	}
	
	public static int getRandomCode(){
		return RND.nextInt(RANDOM_MAX_CODE);
	}
}
