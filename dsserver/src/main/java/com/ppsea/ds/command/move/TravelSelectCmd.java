package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.TravelService;

public class TravelSelectCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(TravelSelectCmd.class);
	
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int cityId = Integer.valueOf(ps[0]);
		ErrorMsg ret = TravelService.selectCity(player, cityId);
		
		if( ret.code != ErrorCode.SUCC ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
	
		result.setVO(TAG_CITY, MapMG.instance.getCity(cityId).getName());
		result.setVO(TAG_DISTANCE, player.getPath().getDistance());
		log.info(player.getPath().toString());
		
		return result;
	}
}
