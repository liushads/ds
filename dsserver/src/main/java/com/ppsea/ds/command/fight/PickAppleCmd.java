package com.ppsea.ds.command.fight;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.FightService;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;
import com.ppsea.ds.util.LoggerHelper;

public class PickAppleCmd extends BaseCmd {
	private static Logger logger = Logger.getLogger("Reward");
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		if (player.getHongbaoDate()!=null) {
			if ((calendar.getTimeInMillis()-player.getHongbaoDate().getTimeInMillis())<60*60*1000*12) {
				setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"12小时只能捡取一张答题卷");
				return result;
			}
		}
		if (player.getLevel()<40) {
			setResult(result,STATUS_FAIL,ErrorCode.ERR_NO_ITEM,"小于40级不能拾取");
			return result;
		}
		String mapkey=ps[0];
		String apple=PlayerMG.instance.getApplemap().get(mapkey);
		PlayerMG.instance.getApplemap().remove(mapkey);
		String td=null;
		int itemid=0;
		if (apple==null) {
			 td="你下手太慢了,答题卷被捡了";
			result.setVO(TAG_TIQIU_DIANQIU, td);
			return result;
		}
		else {
			player.setHongbaoDate(calendar);
			int orangesid=0;
			if (apple.equals("答题卷")) {
				orangesid=10653;
			}
			else {
				orangesid=10653;
			}
		
			ItemService.addItem(player, orangesid, 1,false);
			 td="恭喜你获得了"+apple;
			result.setVO(TAG_TIQIU_DIANQIU, td);
			ChatService.sendMessageSystem(player.getId(), td);
			logger.info(LoggerHelper.ZONE_ID+"|qingming|D|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+itemid);
		}
		
		return result;
	}

}
