/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.player.Displayer;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.LoveDayService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.util.LoggerHelper;
import com.ppsea.ds.util.Util;

/**
 * @author Administrator
 * @date 2011-2-11
 */
public class LoveDayCmd extends BaseCmd {

	private static Logger logger = Logger.getLogger("Reward");
	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
//		
//		Calendar cal = Calendar.getInstance();
//		int month = cal.get(Calendar.MONTH);
//		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
//		if (month != Calendar.FEBRUARY) {
//			result.setStatus(Command.STATUS_FAIL);
//			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID," 活动时间无效"));
//			return result;
//		}
//		if (!(dayOfMonth >= 14 && dayOfMonth <= 24)) {
//			result.setStatus(Command.STATUS_FAIL);
//			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID," 活动时间无效"));
//			return result;
//		}
//		
//		if (ps == null || ps.length == 0) {
//			result.setStatus("main");
//			return result;
//		}
//		
//		String action = ps[0];
//		if ("tree".equals(action)) {
//			result.setStatus("tree");
//			return result;
//		}else if ("wish".equals(action)) {
//			String type = ps[2];
//			String content = ps[1];
//			ErrorMsg msg = LoveDayService.publishWishes(player, content, type);
//			if (msg.code != ErrorCode.SUCC) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, msg);
//				return result;
//			}
//			result.setVO("wish", "许愿成功,你还可以继续许愿,或者去送送祝福");
//			result.setStatus("tree");
//			return result;
//		} else if ("give".equals(action)) {
//			int pageId = Integer.parseInt(ps[1]);
//			if (ps.length == 2) {
//				List<Displayer> list = LoveDayService.getWishesList();
//				Util.page(list, pageId, result);
//				result.setStatus("give");
//				return result;
//			}
//		} else if ("choose".equals(action)) {
//			int otherId = Integer.parseInt(ps[1]);
//			Displayer displayer = LoveDayService.findDisplayerPlayer(otherId);
//			if (displayer == null) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "已经达到最大祝福人数"));
//				return result;
//			}
//			result.setVO("otherPlayer", displayer.getPlayer());
//			result.setVO("otherId", otherId);
//			result.setStatus("choose");
//			return result;
//		} else if ("send".equals(action)) {
//			int targetId = Integer.parseInt(ps[1]);
//			if (targetId == player.getId()) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "自己不能给自己送祝福"));
//				return result;
//			}
//			int type = Integer.parseInt(ps[2]);
//			LoveDayService.sendWishes(player,type, targetId, result);
//		} else if ("flower".equals(action)) {
//			result.setStatus("flower");
//		} else if ("day".equals(action)) {
//			if (player.getLevel() < 40) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "等级不够，40级才可以参与"));
//				return result;
//			}
//			PlayerItem pi = null;
//			if (player.getSex() == 1) {
//				pi = ItemService.findPlayerItem(player,10597);//水桶
//			} else {
//				pi = ItemService.findPlayerItem(player,10596);//花苗
//			}
//			if (pi != null) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "你身上有水桶或者花苗，请使用完后再来领取"));
//				return result;
//			}
//			String key = player.getId().toString() + "_" + "flower" + "_" + 1;
//			PlayerActive pa = player.getPlayerActive().get(key);
//			boolean flag = false;
//			if (pa == null) {
//				pa = PlayerMG.instance.createPlayerActive(player, "flower", 1);
//				pa.setTime(1);
//				player.getPlayerActive().put(key, pa);
//				DBService.commit(pa);
//				flag = true;
//			} else {
//				Calendar lastCal = pa.getLastCal();
//				int lastDay = lastCal.get(Calendar.DAY_OF_MONTH);
//				if (dayOfMonth != lastDay) {
//					pa.setTime(1);
//					pa.setLastCal(cal);
//					DBService.commit(pa);
//					flag = true;
//				} else {
//					//同一天
//					if (pa.getTime() < 2) {
//						pa.setTime(pa.getTime() + 1);
//						DBService.commit(pa);
//						flag = true;
//					} else {
//						//不能领取.
//						result.setStatus(Command.STATUS_FAIL);
//						result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "你今天的领取次数用完，请明天再来"));
//						return result;
//					}
//				}
//			}
//			String info = "领取成功，送你一个";
//			if (player.getSex() == 1) {
//				ItemService.addItem(player, 10597, 1, false);//水桶
//				info += "水桶";
//			} else {
//				ItemService.addItem(player, 10596, 1, false);//花苗
//				info += "花苗";
//			}
//			result.setStatus("info");
//			result.setVO("info", info);
//			return result;
//		} else if ("plant".equals(action)) {
//			PlayerItem pi = ItemService.findPlayerItem(player, 10596);
//			if (pi == null) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "没有花苗"));
//				return result;
//			}
//			if (pi.isPlant()) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "已经种植，不能重复种植"));
//				return result;
//			}
//			if (player.getCity().getId()!=135 && player.getCity().getId()!=140&& player.getCity().getId()!=141&& player.getCity().getId()!=142) {
//				/**
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "必须到情人岛才能种植"));
//				return result;
//				*/
//			} 
//			pi.setPlant(true);
//			LoveDayService.getFlowerList().add(player);
//			result.setVO("info", "种植成功，邀请好友来替你浇水吧");
//			result.setStatus("info");
//		} else if ("water".equals(action)) {
//			int targetId = Integer.parseInt(ps[1]);
//			PlayerItem pi = ItemService.findPlayerItem(player, 10597);
//			if (pi == null || pi.getCurrEndure() > 9) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "没有水桶，或者使用次数用完"));
//				return result;
//			}
//			
//			Player target = PlayerMG.instance.getOnlinePlayer(targetId);
//			if (target == null) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "对方不在线，不能浇水"));
//				return result;
//			}
//			PlayerItem pflower = ItemService.findPlayerItem(target, 10596);
//			if (pflower == null) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "没有花苗"));
//				LoveDayService.removeFlower(targetId);
//				return result;
//			}
//			if (!(pflower.getPlantTime() == 0 || (System.currentTimeMillis() - pflower.getPlantTime()  > 3 * 1000))) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "请等待3秒后浇水"));
//				return result;
//			}
//			pflower.setCurrEndure(pflower.getCurrEndure() + 1);
//			pflower.setPlantTime(System.currentTimeMillis());
//			if (pflower.getCurrEndure() >= 9) {
//				ItemService.addItem(target, 10598, 1, false);//浓情玫瑰
//				target.addCopper(9999);
//				target.addExp(9999);
//				ChatService.sendMessageSystem(targetId, "花苗成熟，获得奖励，浓情玫瑰一朵，9999经验，9999铜");
//				ItemService.releasePlayerItem(target, pflower, 1, true);
//				LoveDayService.removeFlower(targetId);
//				logger.info(LoggerHelper.ZONE_ID+"|loveDay|"+"flower|"+target.getUserId()+"|"+target.getId()+"|"+target.getName()+"|"+target.getLevel()+"|");
//			} else {
//				DBService.commit(pflower);
//			}
//			
//			int cur = pi.getCurrEndure() == null? 0: pi.getCurrEndure();
//			pi.setCurrEndure(cur+1);
//			
//			if (pi.getCurrEndure()>=9) {
//				ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
//				player.addCopper(9999);
//				player.addExp(9999);
//				ChatService.sendMessageSystem(player.getId(), "浇水完成，获得奖励，巧克力玫瑰一朵，9999经验，9999铜");
//				ItemService.releasePlayerItem(player, pi, 1, true);
//				result.setStatus("info");
//				result.setVO("info", "浇水完成，获得奖励，巧克力玫瑰一朵，9999经验，9999铜");
//				logger.info(LoggerHelper.ZONE_ID+"|loveDay|"+"water|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|");
//				return result;
//			} else {
//				DBService.commit(pi);
//				result.setVO("info", "浇水成功,还需要浇水"+(9-pi.getCurrEndure())+"次才能领到奖励");
//				result.setStatus("info");
//				return result;
//			}
//		} else if ("view".equals(action)) {
//			int pageId = Integer.parseInt(ps[1]);
//			List<Player> list = LoveDayService.getFlowerList();
//			Util.page(list, pageId, result);
//			result.setStatus("view");
//			return result;
//		} else if ("rule".equals(action)) {
//			result.setStatus("rule");
//		} else if ("move".equals(action)) {
//			int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
//			if (!((hourOfDay >= 10 && hourOfDay < 11) || (hourOfDay >= 14 && hourOfDay < 16) || (hourOfDay >= 20 && hourOfDay < 22))) {
//				result.setStatus(Command.STATUS_FAIL);
//				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "时间无效"));
//				return result;
//			}
//			int city_facility_id = Integer.parseInt(ps[1]);
//			int level = player.getLevel();
//			if (level <= 40) {
//				city_facility_id = 8555;
//			} else if (level<= 60) {
//				city_facility_id = 8561;
//			} else if (level <= 80) {
//				city_facility_id =  8565;
//			} else {
//				city_facility_id =  8572;
//			}
//			
//			ErrorMsg ret = PlayerService.move(player, city_facility_id, false);
//			if(ret.code != ErrorCode.SUCC){
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, ret);
//				return result;
//			}
//			
//			return callOtherCmd(COMMAND_WALK, player, city_facility_id+"");
//		} else if ("out".equals(action)) {
//			int city_facility_id = 3968;
//			ErrorMsg ret = PlayerService.move(player, city_facility_id, false);
//			if(ret.code != ErrorCode.SUCC){
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, ret);
//				return result;
//			}
//			return callOtherCmd(COMMAND_WALK, player, city_facility_id+"");
//		} else if ("forge".equals(action)) {
//			if (ps.length == 1) {
//				result.setStatus("forge");
//				return result;
//			} else {
//				ErrorMsg msg = LoveDayService.forge(player);
//				if (msg.code != ErrorCode.SUCC) {
//					result.setStatus(STATUS_FAIL);
//					result.setVO(TAG_ERR_MSG, msg);
//					return result;
//				}
//				result.setVO("info", "合成成功，恭喜你获得情人手镯");
//				result.setStatus("info");
//				return result;
//			}
//		} else if ("show".equals(action)) {
//			result.setStatus("show");
//		}
		return result;
	}

	public static boolean valiate() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (month != Calendar.FEBRUARY) {
			return false;
		}
		if (!(dayOfMonth >= 14 && dayOfMonth <= 24)) {
			return false;
		}
		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		if (!((hourOfDay >= 10 && hourOfDay < 11) || (hourOfDay >= 14 && hourOfDay < 16) || (hourOfDay >= 20 && hourOfDay < 22))) {
			return false;
		} 
		return true;
	}
}
