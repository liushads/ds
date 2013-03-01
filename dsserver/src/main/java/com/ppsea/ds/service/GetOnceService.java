package com.ppsea.ds.service;

import java.util.Map;

import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.GetOnce;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.util.DateUtil;

/**
 * 每天可以做的事件
 * @author small
 *
 */
public class GetOnceService {

	/**
	 * 根据今天完成次数和每天限制次数判断今天是否可以再进行
	 * @param getOnce
	 * @param limitTimes
	 * @return	true 可以正常进行，false 不能进行
	 */
	public static boolean checkToday(GetOnce getOnce,int limitTimes){
		if(getOnce == null && limitTimes > 0){
			return true;
		}
		String getDay[] = getOnce.getGetDay().split("_");
		String last_time = getDay[1];
		int times = getTodayTimes(getOnce);
		try {
			if (DateUtil.isSameDay(DateUtil.getNow(last_time+"000000").getTime(), System.currentTimeMillis())) {
				if (times < limitTimes) {
					return true;
				}
			}else {
				return true;
			}
			
		} catch (Exception e) {
			return false;
		}
		return false;
	}
	
	public static int getTodayTimes(GetOnce getOnce){
		if(getOnce == null ){
			return 0;
		}
		String getDay[] = getOnce.getGetDay().split("_");
		return Integer.parseInt(getDay[2]);
	}
	/**
	 * 根据key获取相应的getOnce
	 * @param player
	 * @param key
	 * @return
	 */
	public static GetOnce getByType(Player player,String key){
		Map<String, GetOnce>  getOnceMap = player.getGetOnce();
		if (getOnceMap != null && getOnceMap.containsKey(key)) {
			return getOnceMap.get(key);
		}
		return null;
	}
	
	/**
	 * 将今天做过的事情加1
	 * @param getOnce
	 * @param key
	 */
	public static GetOnce addGetOnce(Player player,GetOnce getOnce,String key){
		if (getOnce == null) {
			getOnce = create(player, key);
		}else {
			String tmp[] = getOnce.getGetDay().split("_");
			if (key.equals(tmp[0])) {
				int value = Integer.parseInt(tmp[2]);
				String dateDesc = DateUtil.getDateDesc();
				if (dateDesc.equals(tmp[1])) {
					value ++;
				}else {
					value = 1;
				}
				getOnce.setGetDay(getKey(key, dateDesc, value));
				
			}
		}
		DBService.commit(getOnce);
		return getOnce;
	} 
	
	private static String getKey(String key,String dateDesc,int value){
		return key+"_" + dateDesc + "_" + value;
	}
	
	/**
	 * 生成每天限制，并设置已经做过一次
	 * @param player
	 * @param key
	 * @return
	 */
	private static GetOnce create(Player player,String key){
		GetOnce getOnce = new GetOnce();
		getOnce.setId(GlobalGenerator.instance.getIdForNewObj(getOnce));
		getOnce.setPlayerId(player.getId());
		getOnce.setGetDay(getKey(key,DateUtil.getDateDesc(),1));
		return getOnce;
	}
}
