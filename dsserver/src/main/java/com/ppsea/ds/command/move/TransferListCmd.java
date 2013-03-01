package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.Path;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityTong;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.TravelService;

public class TransferListCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(TransferListCmd.class);
	
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//用户已经在旅途中
		if(player.getCityFacility() == null){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_SYS, "你已经在旅途中不能重新选择目的地！");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}
		
		City currCity = player.getCityFacility().getCity();
		int areaId = 0;
		if(ps == null || ps.length == 0){
			areaId = currCity.getAreaId();
		}
		else{
			areaId = Integer.valueOf(ps[0]);
		}
		Area area = MapMG.instance.getArea(areaId);		
		//根据距离确定传送价格
		if( area != null ){
			for( City city:area.getCityList()){
				try {
					//过滤掉特殊城市和本城
					if(city.getLevel() == 0 || city.getId().intValue() == currCity.getId()){
						continue;
					}
					int cost = TravelService.transferFee(player, city);
					if(cost <= 0 ){
						continue;
					}
					Path path = MapMG.instance.getPath(currCity.getId(), city.getId());
					int distance = 20;
					if (path != null) {
						distance = path.getDistance();
					}
					CityTong cityTong = MapMG.instance.getCityTong(city.getId());
					String tongName = "";
					String tongId = "";
					result.setList(TAG_OBJS, city.getName(), String.valueOf(city.getId())
							, tongName, tongId
							, String.valueOf(distance),"");
				} catch (Exception e) {
					log.error("TransferListCmd failed.", e);
				}
			}
		}

		result.setVO(TAG_AREAS, MapMG.instance.getAreaList());
		result.setVO(TAG_AREA, areaId);
		result.setVO(TAG_CITY, player.getCityFacility().getCityId());
		return result;
	}
}
