package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;

public class ListFacilityCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(ListFacilityCmd.class);

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//列出可快速抵达设施
		if( player.getCityFacility() == null ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS,"你正在旅途中，非法操作"));
			return result;
		} 
		
		for(CityFacility cityFacility : player.getCityFacility().getCity().getFacilityList()){
			if(cityFacility.getFacility().getDirect()){
				result.setList(TAG_DIRECT, cityFacility.getAlias(), String.valueOf(cityFacility.getId()));
			}
		}
		return result;
	}
}
