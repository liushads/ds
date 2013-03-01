/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GambleEntity;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.GamblerService;

/**
 * @author Administrator
 * @date 2011-1-24
 */
public class GamblerXiaoBaoCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		if (ps == null || ps.length == 0) {
			result.setStatus("main");
			return result;
		}
		String action = ps[0];
		if ("ru".equals(action)) {
			result.setStatus("rule");
		} else if ("ch".equals(action)) {
			if (ps.length == 1) {
				result.setStatus("channel");				
			} else {
				String type = ps[1];
				if ("th".equals(type)) {
					//三个子
					if (ps.length == 2) {
						result.setStatus("three");
					} else {
						String request = ps[2];
						if ("r".equals(request)) {
							result.setStatus("three_rule");
						} else if ("g".equals(request)) {
							if (ps.length == 3) {
								result.setStatus("three_gamble");								
							} else {
								String mtype = ps[3];
								result.setVO("mtype", mtype);
								result.setStatus("three_conf");
							}
						} else if ("c".equals(request)) {
							String mtype = ps[3];
							GamblerService.gamble(player, 3, mtype, result);
							result.setStatus("info");
						}
					}
				} else if ("hos".equals(type)) {
					//赛马
					if (ps.length == 2) {
						List<String>  list = GamblerService.getGambleHorseList(player);
						result.setStatus("horse_gamble");
						if (list != null && list.size() > 0) {
							result.setVO("horseList", list);
						} 
					} else {
						String request = ps[2];
						if ("r".equals(request)) {
							result.setStatus("horse_rule");
						} else if ("g".equals(request)) {
							if (ps.length == 3) {
								result.setStatus("horse_gamble");								
							} else {
								String mtype = ps[3];
								result.setVO("mtype", mtype);
								ErrorMsg msg = GamblerService.horse(player, mtype, result);
								if (msg.code != ErrorCode.SUCC) {
									result.setStatus(STATUS_FAIL);
									result.setVO(TAG_ERR_MSG, msg);
									return result;
								}
								List<String>  list = GamblerService.getGambleHorseList(player);
								result.setStatus("horse_gamble");
								if (list != null && list.size() > 0) {
									result.setVO("horseList", list);
								} 
								//result.setStatus("horse_conf");
							}
						} else if ("c".equals(request)) {
							String mtype = ps[3];
							GamblerService.horse(player, mtype, result);
							result.setStatus("info");
						}
					} 
					result.setVO("roundHorse", GamblerService.roundHorse);
					GamblerService.checkAndEndHorseRound(result);
				} else if ("fv".equals(type)) {
					//五个子
					if (ps.length == 2) {
						GambleEntity ge = GamblerService.getGambleEntity(player);
						if (ge == null) {
							result.setStatus("five");
						} else {
							GamblerService.viewPoints(player, result);
							result.setStatus("five_gamble");							
						}
					} else {
						String request = ps[2];
						if ("r".equals(request)) {
							result.setStatus("five_rule");
						} else if ("g".equals(request)) {
							if (ps.length == 3) {
								GamblerService.generateRoundPoints(player);
								GamblerService.viewPoints(player, result);
								result.setStatus("five_gamble");					
							} else {
								String mtype = ps[3];
								result.setVO("mtype", mtype);
								result.setStatus("five_conf");
							}
						} else if ("c".equals(request)) {
							String mtype = ps[3];
							result.setStatus("info");
						} else if ("v".equals(request)) {
							GambleEntity ge = GamblerService.getGambleEntity(player);
							if (ge == null) {
								result.setStatus("five");
							} else {
								GamblerService.viewPoints(player, result);
								result.setStatus("five_gamble");								
							}
						}
					} 
					result.setVO("roundMaxPoints", GamblerService.maxRoundPoints);
					result.setVO("roundMaxPointStr", GamblerService.maxPointsStr);
					GamblerService.checkAndEndRound(result);
				}
			}
		} else {
			result.setStatus("main");
		}
		
		return result;
	}

}
