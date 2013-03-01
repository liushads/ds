package com.ppsea.ds.command.dart;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Dart;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerDart;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DartService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.GetOnceService;

public class StartDartCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String action = ps[0];
		if ("in".equals(action)) {
			PlayerDart pd = player.getPlayerDart();
			result.setStatus("stat_in");
			result.setVO("label_today_time", DartService.getTodayTime(pd));
			result.setVO("label_today_all", DartService.DART_TIMES);
			result.setVO("label_time",DartService.getLastTime(pd));
			if (pd != null) {
				Dart dart = ResourceCache.instance.getDarts().get(pd.getDartId());
				result.setVO("label_dart_name", dart.getName());
			}
			result.setVO("label_today_rob", GetOnceService.getTodayTimes(GetOnceService.getByType(player, Constants.DART_ROBE)));
			result.setVO("label_today_rob_all", Constants.DART_TIME);
			result.setVO("label_dartvo", DartService.getDartVos(player,3));
			result.setVO("label_msg_list", DartService.getMsgList(3));
		}
		if ("bg".equals(action)) {
			ErrorMsg msg = null;
			if (ps.length == 2 && "g".equals(ps[1])) {
				msg = DartService.getTmpPlayerDart(player, DartService.REF_ONE_GOLD);
				if (msg.code != ErrorCode.SUCC) {
					setResult(result, STATUS_FAIL, msg);
					return result;
				}
				result.setVO("label_player_dart", msg.obj);
			}else {
				msg = DartService.getTmpPlayerDart(player, 0);
				result.setVO("label_player_dart", msg.obj);
			}
			result.setVO("label_dart", DartService.getDart());
			result.setStatus("stat_bg");
		}
		if ("st".equals(action)) {//开始押镖
			ErrorMsg msg = DartService.begin(player);
			
			if (msg.code != ErrorCode.SUCC) {
				setResult(result, STATUS_FAIL, msg);
				return result;
			}
			PlayerDart pd = player.getPlayerDart();
			result.setVO("label_day_time", pd.getDayTimes());
			long res = DartService.getLastTime(pd);
			if (res < 0) {
				setResult(result, STATUS_FAIL, new ErrorMsg(-1, "押镖失败！"));
				return result;
			}
			result.setVO("label_time", res);
			result.setStatus("stat_st");
		}
		
		return result;
	}

}
