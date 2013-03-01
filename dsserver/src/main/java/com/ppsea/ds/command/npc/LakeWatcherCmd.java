/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.ArrayList;
import java.util.Calendar;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerFishing;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.LakeWatcherService;

/**
 * @author Administrator
 * @date 2010-11-5
 */
public class LakeWatcherCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		boolean isValid = false;
		/**
		if (month != Calendar.NOVEMBER) {
			isValid = false;
		}*/
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
			isValid = true;
		}
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if ((month == Calendar.JANUARY && dayOfMonth >= 20)) {
			if (hourOfDay <=9 && hourOfDay >=8) {
				isValid = true;
			}
		}
		if (month == Calendar.FEBRUARY && dayOfMonth <= 10) {
			if (hourOfDay <=9 && hourOfDay >=8) {
				isValid = true;
			}
		}
		
		
//		if (!isValid) {
//			result.setStatus(STATUS_FAIL);
//			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "活动时间无效，固定开放时间为每周六和周日及春节活动时间"));
//			return result;
//		}
		if (ps == null || ps.length == 0) {
			result.setStatus("main");
		} else {
			String action = ps[0];
			
			if ("g".equals(action)) {
				//领取每日钓鱼福利
				ErrorMsg msg = LakeWatcherService.getFishingGivings(player);
				if (msg.code != ErrorCode.SUCC) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setStatus("info");
				result.setVO("info", msg.getText());
			} else if ("f".equals(action)) {
				//钓鱼
				PlayerItem pi = ItemService.findPlayerItem(player, 10473);//是否有鱼竿
				if (pi == null || pi.getAmount() <= 0) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "你还没有鱼竿，不能钓鱼，你可以到守湖人处免费领取"));
					return result;
				}
				if (ps.length == 1) {
					
					//result.setVO("lastLureId", player.getLastLureId());
					if (player.getLastLureId() > 0) {
						result.setStatus("fishing");
						result.setVO("fishingWishes", new ArrayList<PlayerFishing>(player.getFishWishes().values()));
					} else {
						result.setStatus("fish");
					}
				} else {
					String type = ps[1];
					if ("l".equals(type)) {
						//选择鱼饵.
						result.setStatus("lure");
					} else {
						int lureId = Integer.parseInt(type);
						ErrorMsg msg = LakeWatcherService.setFishingLure(player, lureId);
						if (msg.code != ErrorCode.SUCC) {
							result.setStatus(STATUS_FAIL);
							result.setVO(TAG_ERR_MSG, msg);
							return result;
						}
						result.setStatus("fishing");
						result.setVO("lure_note", msg.getText());
						result.setVO("fishingWishes", new ArrayList<PlayerFishing>(player.getFishWishes().values()));
					}
					
				}
			} else if ("l".equals(action)) {
				//lift up
				ErrorMsg msg = LakeWatcherService.liftUp(player, result);
				if (msg.code != ErrorCode.SUCC) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setStatus("info");
				result.setVO("info", "恭喜你钓到了"+msg.getText());
				//result.setVO("itemId", msg.getObj().toString());
				result.setVO("fish", true);
			} else if ("w".equals(action)) {
				//go watching.
				String note = LakeWatcherService.gonaWatch(player);
				result.setStatus("fishing");
				result.setVO("fish_note", note);
				result.setVO("fishingWishes", new ArrayList<PlayerFishing>(player.getFishWishes().values()));
			} else if ("tw".equals(action)) {
				//throw fish wish.
				if (ps.length == 1) {
					PlayerItem pi = ItemService.findPlayerItem(player, 10472);//许愿瓶
					if (pi == null || pi.getAmount() == 0) {
						result.setStatus(STATUS_FAIL);
						result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有许愿瓶，不能许愿"));
						return result;
					}
					result.setStatus("wish");
				} else {
					String content = ps[1];
					ErrorMsg msg = LakeWatcherService.throwFishWishes(player, content);
					if (msg.code != ErrorCode.SUCC) {
						result.setStatus(STATUS_FAIL);
						result.setVO(TAG_ERR_MSG, msg);
						return result;
					}
					result.setStatus("info");
					result.setVO("t_wish","tw");
				}
			} else if ("o".equals(action)) {
				//open fish wishes.
				int wishId = Integer.parseInt(ps[1]);
				PlayerFishing pf = player.getFishWishes().get(wishId);
				result.setVO("info", "匿名："+pf.getContent());
				result.setVO("replyWish", "true");
				result.setVO("wishId2", pf.getId());
				result.setStatus("info");
			} else if ("input".equals(action)) {
				//replay input.
				int wishId = Integer.parseInt(ps[1]);
				PlayerFishing pf = player.getFishWishes().get(wishId);
				result.setVO("pf", pf);
				result.setVO("wishId", wishId);
				result.setStatus("input");
				
			} else if ("reply".equals(action)) {
				//reply
				int wishId = Integer.parseInt(ps[1]);
				String content = ps[2];
				ErrorMsg msg = LakeWatcherService.replyFishWishes(player, wishId, content);
				if (msg.code != ErrorCode.SUCC) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, msg);
					return result;
				}
				result.setStatus("info");
				result.setVO("info", "回复成功");
				result.setVO("fish", "true");
			} else {
				result.setStatus("rule");
			}
			
		}
		return result;
	}

}
