package com.ppsea.ds.command.move;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityTong;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ErrorCode;

public class ListCityCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(ListCityCmd.class);

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//用户已经在旅途中
		if(player.getCityFacility() == null){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_SYS, "你已经在旅途中不能重新选择目的地！");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		
		//判断是否有坐骑装备，没有则看是否有可装备的坐骑，有则提示。
		List<PlayerItem> usedArms = player.getUsedArms(String.valueOf(Item.POS_MOUNTS));
		if (usedArms == null || usedArms.size() == 0) {
			List<PlayerItem> unusedArms = player.getArms(String.valueOf(Item.POS_MOUNTS));
			if (unusedArms != null && unusedArms.size() >0) {
				result.setStatus("ride_note");
				result.setVO("note_info", "山高路远，步行十分艰难。你还没有坐骑，请找到并点击：状态-装备-坐骑栏，装配坐骑后再出城吧！");
				return result;
			}
		}
		
		City currCity = player.getCityFacility().getCity();
		int areaId = 0;
		if(ps == null || ps.length == 0){
			areaId = currCity.getAreaId();
		}
		else{
			areaId = Integer.valueOf(ps[0]);
		}
//		//用户拥有该区域海图，则显示该区域全部城市， 否则显示该区域，用户可直达城市
//		Area area = player.getWorldMap().get(areaId);
//		List<City> cities = new LinkedList<City>();
//		if( area != null ){
//			for(City city:area.getCityList()){
//				//过滤掉特殊城市和本城
//				if(city.getLevel() == 0 || city.getId().intValue() == currCity.getId()){
//					continue;
//				}
//				cities.add(city);
//			}
//			
//		}
//		else if( areaId == currCity.getAreaId()){
//			for(City city:currCity.getNearCityList()){
//				if( city.getAreaId() == areaId){
//					cities.add(city);
//				}
//			}
//		}
		Area area = MapMG.instance.getArea(areaId);
		List<City> cities = new LinkedList<City>();
		for(City city:area.getCityList()){
			//过滤掉特殊城市和本城
			if(city.getLevel() == 0 || city.getId().intValue() == currCity.getId()){
				continue;
			}
			cities.add(city);
		}
		
		for (City city : cities) {
			CityTong cityTong = MapMG.instance.getCityTong(city.getId());
			String tongName = "";
			Integer tongId = null;
		}
		result.setVO(TAG_CITIES, cities);
		result.setVO(TAG_AREAS, MapMG.instance.getAreaList());
		result.setVO(TAG_AREA, areaId);
		result.setVO(TAG_CITY, player.getCityFacility().getCityId());
		return result;
	}
}
