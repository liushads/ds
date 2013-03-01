package com.ppsea.ds.service;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.Path;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Route;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;

public class TravelService {
	private static final Logger log = Logger.getLogger(TravelService.class);

	public static int TRANSFER_UNIT_FEE = 5;		//单位距离花费铜
	public static int TRANSFER_MIN_FEE = 200;		//最低传送费用10银
	public static int TRANSFER_LEVEL_LIMIT = 1;	//传送等级要求15级
	
	/**
	 * 选择航行目的地,并将路径保存到player中
	 * @param
	 * player:
	 * cityId:目标车城市id
	 * @return
	 * SUCC, ERR_INVALID_CITY
	 * */
	public static ErrorMsg selectCity(Player player, int cityId){
		//已经在旅途中，不能更改目的地
		if(player.getCityFacility() == null){
			return new ErrorMsg(ErrorCode.ERR_SYS, "旅途中不可更改目的地");
		}
		
		City targetCity = MapMG.instance.getCity(cityId);
		if(targetCity == null ){
			return new ErrorMsg(ErrorCode.ERR_INVALID_CITY, "目的地不存在");
		}
		//检查是否在用户地图中			
//		if(player.getWorldMap().get(targetCity.getAreaId()) == null 
//				&& player.getCityFacility().getCity().getAreaId().intValue() != targetCity.getAreaId()){
//			return new ErrorMsg(ErrorCode.ERR_DIRTY_WORD, "你没有该地区地图，目的地不可达");
//		}
		
		//检查是否满足改地区等级要求
		ErrorMsg ret =  checkPlayerLevel(player, targetCity);
		if( ret.code != ErrorCode.SUCC ){
			return ret;
		}
		
		
		//保存目的路径到用户资料中。	
		player.setPath(player.getCityFacility().getCityId(), cityId);
		player.setPathFinish( 0 );
		player.setTotalLeft(player.getPath().getDistance() );

		return Constants.SUCC;
	}
	
	/**
	 * 检查是否满足该地区等级要求
	 * @param player
	 * @param city
	 * @return
	 */
	public static ErrorMsg checkPlayerLevel(Player player, City city){
		Area area = MapMG.instance.getArea(city.getAreaId());
		if(area == null){
			return new ErrorMsg(ErrorCode.ERR_INVALID_CITY, "目的地不存在");
		}
		
		if( player.getLevel() < area.getPlayerLevel()){
			return new ErrorMsg(ErrorCode.ERR_INVALID_CITY, "等级达到"+area.getPlayerLevel()+"级以上的玩家才能进入。");
		}
		return Constants.SUCC;
	}
	
	/**
	 * 城市间旅行
	 * @return
	 * SUCC_GO,SUCC_CITY, SUCC_END, , ERR_SUPPLY, ERR_HAD_ARRVIED
	 * @throws PpseaException 
	 * */
	public static ErrorMsg go(Player player) throws PpseaException{
		try{
		//防止利用传送和保存码头书签，跳过航海
		if(player.getStartCity() == 0 && player.getEndCity() == 0 ){
			return new ErrorMsg(ErrorCode.ERR_SYS, "非法操作");
		}
		
		if(player.getTotalLeft() == 0){
			return new ErrorMsg(ErrorCode.ERR_HAD_ARRVIED, "已经达到目的地");
		}
		
		//海上航行要清除设施信息		
		player.setCityFacility(null);
		
		int finish = player.getPathFinish();		
		//到下一港口剩余距离
		int leftDistance = getDistanceToNextCity(player) - finish;
		//本次应走距离
		int d = 0;
		//如果逆行第一步，为了正向，逆向船只可完全重合，如果一次不能抵达城市，先走余数距离
		int speed = player.getDyn().getSpeed();
		if(finish == 0 && !player.isDirection() && leftDistance > speed){
			d = getDistanceToNextCity(player)%speed;
			if(d == 0){
				d = speed;
			}
		}
		else if(leftDistance > speed ){
			d = speed;
		}
		else{
			d = leftDistance;
		}
	
		int ret;
		if( leftDistance > d  ){	
			//本次航行距离，不能抵达城市
			player.setPathFinish( finish + d);
			player.setTotalLeft(player.getTotalLeft() - d);
			updatePlayerXY(player);
			return new ErrorMsg(ErrorCode.SUCC_GO, null);
		}
		else{	
			//本次航行，可以抵达城市
			arriveNextCity(player);
			
			//是否到达终点
			if( player.getTotalLeft() == 0){
				return new ErrorMsg(ErrorCode.SUCC_END, null);
			}
			else{
				return new ErrorMsg(ErrorCode.SUCC_CITY, null);
			}
		}
		}
		catch (Exception e) {
			log.error("exception", e);
			throw new PpseaException("旅行中出错，重置路线");
		}
	}
	
	/**
	 * 得到到下个城市的距离，已经到达终点则返回0
	 * */
	public static int getDistanceToNextCity(Player player) {
		if(player.getTotalLeft() == 0){
			return 0;
		}
		int currCityId = player.getPath().getCityId(player.getPathIndex());
		int nextCityId = getNextCity(player).getId();
		return MapMG.instance.getPath(currCityId, nextCityId).getDistance();
	}
	
	/**
	 * 得到下一个城市，已经到达终点择返回终点城市
	 * */
	public static City getNextCity(Player player){
		if( player.getTotalLeft() == 0){
			return MapMG.instance.getCity(player.getEndCity());
		}
		
		int nextCityId = 0;
		if(player.isDirection()){
			nextCityId = player.getPath().getCityId(player.getPathIndex()+1);
		}
		else{
			nextCityId = player.getPath().getCityId(player.getPathIndex()-1);
		}	
		return MapMG.instance.getCity(nextCityId);
	}
	
	/**
	 * 得到上一个城市
	 * */
	public static City getPreCity(Player player){
		int preCityId = player.getPath().getCityId(player.getPathIndex());
		return MapMG.instance.getCity(preCityId);
	}
	
	/**
	 * 更新用户坐标
	 * */
	private static void updatePlayerXY(Player player){
		City c1 = getPreCity(player);
		City c2 = getNextCity(player);
		double x1 = c1.getX();
		double y1 = c1.getY();
		double x2 = c2.getX();
		double y2 = c2.getY();
		
		double finish = player.getPathFinish();
		double nextDistance = getDistanceToNextCity(player);
		
		int x =(int)( x1 + (x2-x1)* finish / nextDistance );
		int y =(int)( y1 + (y2-y1)* finish / nextDistance);
		player.setX(x);
		player.setY(y);
	}
	
	/**
	 * 抵达下一个城市
	 * 
	 * @return
	 * 抵达的城市
	 * */
	private static City arriveNextCity(Player player){
		City nextCity = null;
		//已经到达终点，则直接返回终点城市
		if( player.getTotalLeft() == 0){
			nextCity = MapMG.instance.getCity(player.getEndCity());
			return nextCity;
		}
		
		if(player.isDirection()){
			player.setPathIndex( player.getPathIndex()+1);
		}
		else{
			player.setPathIndex( player.getPathIndex()-1);
		}
		player.setPathFinish(0);
		//判断是否到达终点城市
		int nextCityId = player.getPath().getCityId(player.getPathIndex());		
		if( nextCityId == player.getEndCity() ){
			player.setTotalLeft(0);
		}
		else{
			int d = MapMG.instance.getPath(nextCityId, player.getEndCity()).getDistance();
			player.setTotalLeft(d);
		}
		return MapMG.instance.getCity(nextCityId);
	}
	
	
	/**获得用户所在地区，如果用户在城市则取city的地区，
	 * 如果在航道则取航道上最近城市的地区
	*/
	public static Area getPlayerArea(Player player){
		if( player.getCityFacility() != null){
			return MapMG.instance.getArea(player.getCityFacility().getCity().getAreaId());
		}
		int areaId;
		if(player.getPathFinish() <= getDistanceToNextCity(player)/2){
			areaId = getPreCity(player).getAreaId();
		}
		else{
			areaId = getNextCity(player).getAreaId();
		}
		return MapMG.instance.getArea(areaId);
	}
	
	/**
	 * 计算传送花费
	 * */
	public static int transferFee(Player player, City city) {
		City currCity = player.getCityFacility().getCity();
		Path path = MapMG.instance.getPath(currCity.getId(), city.getId());
		if (path == null) {
			log.error("不存在路线:"+currCity.getName()+"<->"+city.getName());
			return -1;
		}
		
		int cost = path.getDistance() * TRANSFER_UNIT_FEE;
		if (cost < TRANSFER_MIN_FEE) {
			cost = TRANSFER_MIN_FEE;
		}
		return cost;
	}
	
	/**
	 * 获取当前航段
	 * */
	public static Route getRoute(Player player){
		String key = MapMG.instance.getKey(getPreCity(player).getId(), getNextCity(player).getId());
		return MapMG.instance.getRoute(key);
	}
}
