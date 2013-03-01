package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Area;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.Event;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.EventService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.TravelService;


public class GoCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(GoCmd.class);
	
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		ErrorMsg ret = TravelService.go(player);
		
		if(ret.code < 0 ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		
		if(ret.code == ErrorCode.SUCC_GO){
			result.setVO(TAG_FACILITY_PALYER, PlayerService.listPlayersByCityFacility(player));
			result.setVO(TAG_AREA, TravelService.getPlayerArea(player).getName());
			City city = MapMG.instance.getCity(player.getEndCity());
			result.setVO(TAG_CITY, city.getName());
			result.setVO(TAG_DISTANCE_LEFT, player.getTotalLeft());			
			result.setVO(TAG_FLAG, ret.code);	
			
			// 发生随机事件
			result.setVO(TAG_DIALOG, EventService.checkRouteEvent(player, Event.TYPE_SAIL));
			
			//页面消息
			result.setVO(TAG_PAGE_OBJS, ChatService.queryTop2PublicMessages(ChatService.CHAT_WORLD));
			
			//显示私聊消息.
			PlayerService.showPrivateMsg(player, result);
		}
		else if(ret.code == ErrorCode.SUCC_CITY){
			City city = TravelService.getPreCity(player);
			Area area = MapMG.instance.getArea(city.getAreaId());
			result.setVO(TAG_AREA, area.getName());
			result.setVO(TAG_CITY, city.getName());
			result.setVO(TAG_FLAG, ret.code);
		}
		//到达终点，进入码头
		else{
			result =  callOtherCmd(COMMAND_WALK, player, "0");
		}
		return result;
	}
	
}
