/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemForge;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.Question;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MissionMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.QuestionService;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2011-2-15
 */
public class LanternDayCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player,
	 *      java.lang.String[])
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
		if ("out".equals(action)) {
			int city_facility_id = 7414;
			ErrorMsg ret = PlayerService.move(player, city_facility_id, false);
			if (ret.code != ErrorCode.SUCC) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}

			return callOtherCmd(COMMAND_WALK, player, city_facility_id + "");
		} 
//			else if ("move".equals(action)) {
//
//			// 每天早上10：00-11：00,下午14：00-16：00,晚上：20：00-22：00
//			int hour = cal.get(Calendar.HOUR_OF_DAY);
//			if (!((hour >= 10 && hour < 11) || (hour >= 14 && hour < 16) || (hour >= 20 && hour < 22))) {
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, new ErrorMsg(
//						ErrorCode.ERR_ACTIVE_INVALID, " 活动时间无效"));
//				return result;
//			}
//			int city_facility_id = 8574;
//			ErrorMsg ret = PlayerService.move(player, city_facility_id, false);
//			if (ret.code != ErrorCode.SUCC) {
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, ret);
//				return result;
//			}
//
//			return callOtherCmd(COMMAND_WALK, player, city_facility_id + "");
//		} 
	else if ("rule".equals(action)) {
			result.setStatus("rule");
			return result;
		} 
//	else if ("make".equals(action)) {
//			List<ItemForge> itemList = ItemService.listComposeItemsByType(3);
//			result.setVO("itemList", itemList);
//			result.setStatus("make");
//			return result;
//		} else if ("fire".equals(action)) {
//			int itemId = Integer.parseInt(ps[1]);
//			ErrorMsg msg = fireLantern(player, itemId);
//			if (msg.code != ErrorCode.SUCC) {
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, msg);
//				return result;
//			}
//			result.setStatus("info");
//			result.setVO("info",
//					"燃放成功，赠送你：九折卡1张，双倍经验卡1张，强化石1个，升星石1个，金刚钻1个，凤血石1个，青龙珠1个");
//			return result;
//		} else if ("exchange".equals(action)) {
//			if (ps.length == 1) {
//				result.setStatus("exchgelist");
//				return result;
//			}
//			int itemId = Integer.parseInt(ps[1]);
//			ErrorMsg msg = exchange(player, itemId);
//			if (msg.code != ErrorCode.SUCC) {
//				result.setStatus(STATUS_FAIL);
//				result.setVO(TAG_ERR_MSG, msg);
//				return result;
//			}
//			result.setStatus("info");
//			result.setVO("info", "兑换成功，恭喜你获得"
//					+ ItemMG.instance.getItem(itemId).getName());
//		}
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
			if (month != Calendar.APRIL) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月1日-10日"));
			return result;
		}
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (dayOfMonth < 1 || dayOfMonth > 10)	 {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW, "活动时间无效,有效时间为4月1日-10日"));
			return result;
		}

		 if ("cai".equals(action)) {
			
			ErrorMsg msg = caidengmi(player);
			if (msg.code != ErrorCode.SUCC) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, msg);
				return result;
			}
			Question question = QuestionService.chouti(player);
			String[] da = question.getAnswer().split("/");
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < da.length; j++) {
				list.add(da[j]);
			}
			result.setVO(TAG_QUESTION, question);
			result.setVO(TAG_QUESTIONLIST, list);
			result.setStatus("caiti");
			return result;
		} else if ("dati".equals(action)) {
			ErrorMsg msg = findAnswer(player);
			if (msg.code != ErrorCode.SUCC) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, msg);
				return result;
			}
			int qid = Integer.parseInt(ps[1]);
			int type = Integer.parseInt(ps[2]);
			Question question = MissionMG.instance.getQuestion(qid);
			if (question.getCorrect() != type) {
				int itemid = Answerjiang(player);
				Item item = ItemMG.instance.getItem(itemid);
				result.setVO(TAG_ITEM, item);
				result.setStatus("dati");
				return result;
			} else {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_ACTIVE_INVALID, " 答案错误，请下次再来"));
				return result;
			}
		} else if ("tishi".equals(action)) {
			result.setStatus("tishi");
			return result;
		} else if ("open".equals(action)) {
			int itemid = Integer.parseInt(ps[1]);
			PlayerItem playerItem = ItemService.findPlayerItem(player, itemid);
			if (playerItem == null) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_ACTIVE_INVALID, " 你没有该道具"));
				return result;
			}
			String miaoshu=null;
			Random random = new Random();
			int num = random.nextInt(2);
			ErrorMsg msg =openYuanXiao(player, playerItem, num);
			if (msg.code != ErrorCode.SUCC) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, msg);
				return result;
			}
			if (itemid==10624) {
				if (num==0) {
					miaoshu = "10银，青铜回血符1个，青铜凝神符1个，灯谜票1张，桂花元宵1个";
				}else {
					miaoshu = "10银，青铜回血符1个，青铜凝神符1个，灯谜票1张，三鲜元宵1个";
				}
			}
			if (itemid==10625) {
				if (num==0) {
					miaoshu = "50银，青铜回血符2个，青铜凝神符2个，灯谜票2张，桂花元宵1个";
				}else {
					miaoshu = "50银，青铜回血符2个，青铜凝神符2个，灯谜票2张，三鲜元宵1个";
				}
			}
			if (itemid==10626) {
				if (num==0) {
					miaoshu = "100银，白银回血符1个，白银凝神符1个，灯谜票3张，桂花元宵2个";
				}else {
					miaoshu = "100银，白银回血符1个，白银凝神符1个，灯谜票3张，三鲜元宵2个";
				}
			}
			result.setVO("miaoshu", miaoshu);
			result.setStatus("open");
			return result;
			
		}

		return result;
	}

	private static Logger logger = Logger.getLogger("Reward");

	private static ErrorMsg fireLantern(Player player, int itemId) {
		PlayerItem pitem = ItemService.findPlayerItem(player, itemId);
		if (pitem == null || pitem.getAmount() <= 0) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有龙灯");
		}

		String key = player.getId().toString() + "_" + "lantern" + "_" + 1;
		PlayerActive pa = player.getPlayerActive().get(key);
		if (pa == null) {
			pa = PlayerMG.instance.createPlayerActive(player, "lantern", 1);
			pa.setTime(1);
			DBService.commit(pa);
			player.getPlayerActive().put(key, pa);
		} else {
			Calendar lastCal = pa.getLastCal();
			Calendar cal = Calendar.getInstance();
			int lastDay = lastCal.get(Calendar.DAY_OF_MONTH);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (lastDay != day) {
				pa.setLastCal(cal);
				pa.setTime(pa.getTime() + 1);
				DBService.commit(pa);
			} else {
				return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "每天只能燃放一次");
			}

		}

		ErrorMsg msg = ItemService.releasePlayerItem(player, pitem, 1, true);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}

		// 九折卡1张，双倍经验卡1张，强化石1个，升星石1个，金刚钻1个，凤血石1个，青龙珠1个
		ItemService.addItem(player, 10504, 1, false);
		ItemService.addItem(player, 10368, 1, false);
		ItemService.addItem(player, 10386, 1, false);
		ItemService.addItem(player, 10387, 1, false);
		ItemService.addItem(player, 10385, 1, false);
		ItemService.addItem(player, 10511, 1, false);
		ItemService.addItem(player, 10410, 1, false);
		logger.info(LoggerHelper.ZONE_ID + "|lantern|" + "fire|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|");
		return Constants.SUCC;
	}

	private static ErrorMsg exchange(Player player, int itemId) {
		int qiaokeliId = 10599;
		int nongqingId = 10598;
		PlayerItem qiaokeli = ItemService.findPlayerItem(player, qiaokeliId);
		PlayerItem nongqing = ItemService.findPlayerItem(player, nongqingId);
		ErrorMsg msg = null;
		switch (itemId) {
		case 10624:
			// 一心一意（15朵玫瑰）兑换元宵包
			msg = release(player, qiaokeli, nongqing, 15);
			break;
		case 10625:
			// 温馨浪漫（45朵玫瑰）兑换元宵盒
			msg = release(player, qiaokeli, nongqing, 45);
			break;
		case 10626:
			// 天长地久（99朵玫瑰）兑换元宵篮
			msg = release(player, qiaokeli, nongqing, 99);
			break;
		default:
			msg = Constants.ERR_SYS;
		}
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		ItemService.addItem(player, itemId, 1, false);
		logger.info(LoggerHelper.ZONE_ID + "|lantern|" + "exchange|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|" + itemId);
		return Constants.SUCC;
	}

	private static ErrorMsg release(Player player, PlayerItem qiaokeli,
			PlayerItem nongqing, int amount) {
		int totalAmount = 0;
		if (qiaokeli != null && qiaokeli.getAmount() > 0) {
			totalAmount += qiaokeli.getAmount();
		}
		if (nongqing != null && nongqing.getAmount() > 0) {
			totalAmount += nongqing.getAmount();
		}
		if (totalAmount < amount) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "玫瑰数量不足");
		}
		if (qiaokeli != null) {
			int tp = amount - qiaokeli.getAmount();
			if (tp > 0) {
				if (nongqing != null) {
					ErrorMsg msg = ItemService.releasePlayerItem(player,
							nongqing, tp, true);
					if (msg.code != ErrorCode.SUCC) {
						return msg;
					}
					msg = ItemService.releasePlayerItem(player, qiaokeli,
							qiaokeli.getAmount(), true);
					if (msg.code != ErrorCode.SUCC) {
						return msg;
					}
					// succ
					return Constants.SUCC;
				} else {
					// error....
					return Constants.ERR_SYS;
				}
			} else {
				ErrorMsg msg = ItemService.releasePlayerItem(player, qiaokeli,
						amount, true);
				if (msg.code != ErrorCode.SUCC) {
					return msg;
				}
				// succ
				return Constants.SUCC;
			}
		}

		if (nongqing != null) {
			int tp = amount - nongqing.getAmount();
			if (tp > 0) {
				// error
				return Constants.ERR_SYS;
			} else {
				ErrorMsg msg = ItemService.releasePlayerItem(player, nongqing,
						amount, true);
				if (msg.code != ErrorCode.SUCC) {
					return msg;
				}
				// succ
				return Constants.SUCC;
			}
		}

		return Constants.ERR_SYS;
	}

	private static ErrorMsg caidengmi(Player player) {
		PlayerItem playerItem = ItemService.findPlayerItem(player, 10653);
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "你没有答题卷");
		}
		String key = player.getId().toString() + "_" + "dengmi" + "_" + 1;
		PlayerActive pa = player.getPlayerActive().get(key);
		Date date = new Date();
		if (pa != null) {
			ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem,
					true);
			if (msg.code != ErrorCode.SUCC) {
				return msg;
			}
			pa.setTime(1);
			pa.setJoinDate(date);
			DBService.commit(pa);
			DBService.commit(player);

		} else {
			pa = PlayerMG.instance.createPlayerActive(player, "dengmi", 1);
			pa.setTime(1);
			ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem,
					true);
			if (msg.code != ErrorCode.SUCC) {
				return msg;
			}
			player.getPlayerActive().put(key, pa);
			DBService.commit(pa);
			DBService.commit(player);
		}

		return Constants.SUCC;
	}

	private static ErrorMsg findAnswer(Player player) {
		String key = player.getId().toString() + "_" + "dengmi" + "_" + 1;
		PlayerActive pa = player.getPlayerActive().get(key);
		Date date = new Date();
		if (pa != null) {
			if (pa.getTime() == 2) {
				return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "你已经答过题目了");
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(pa.getJoinDate());
			if ((calendar.getTimeInMillis() - cal.getTimeInMillis()) / 1000 > 30) {
				return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "你的答题超过了30秒");
			}
			pa.setTime(2);
			DBService.commit(pa);
			DBService.commit(player);
		} else {
			return new ErrorMsg(ErrorCode.ERR_ITEM_AMOUNT, "非正确途径答题");
		}
		return Constants.SUCC;
	}

	private static int Answerjiang(Player player) {
		Random random = new Random();
		int num = random.nextInt(100);
		int itemid = 0;
		String name = null;
		if (num < 4) {
			itemid = 10273;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (4 <= num && num < 8) {
			itemid = 10274;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (8 <= num && num < 12) {
			itemid = 10276;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (12 <= num && num < 16) {
			itemid = 10277;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (16 <= num && num < 20) {
			itemid = 10375;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (20 <= num && num < 24) {
			itemid = 10376;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (24 <= num && num < 28) {
			itemid = 10377;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (28 <= num && num < 32) {
			itemid = 10378;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (32 <= num && num < 36) {
			itemid = 10379;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (36 <= num && num < 40) {
			itemid = 10386;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (40 <= num && num < 44) {
			itemid = 10387;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (44 <= num && num < 48) {
			itemid = 10385;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (48 <= num && num < 52) {
			itemid = 10412;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (52 <= num && num < 56) {
			itemid = 10413;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (56 <= num && num < 60) {
			itemid = 10511;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (60 <= num && num < 64) {
			itemid = 10368;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (64 <= num && num < 68) {
			itemid = 82;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (68 <= num && num < 72) {
			itemid = 84;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (72 <= num && num < 74) {
			itemid = 10346;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (74 <= num && num < 76) {
			itemid = 10347;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (76 <= num && num < 77) {
			itemid = 10534;
			ItemService.addItem(player, itemid, 1, false);
			name = "【" + player.getName() + "】在快问乱答活动中获得了【黄金回血符】";
			ChatService.sayXiaoQ(name);
		}
		if (77 <= num && num < 78) {
			itemid = 10536;
			ItemService.addItem(player, itemid, 1, false);
			name = "【" + player.getName() + "】在快问乱答活动中获得了【黄金凝神符】";
			ChatService.sayXiaoQ(name);

		}
		if (78 <= num && num < 79) {
			itemid = 10532;
			ItemService.addItem(player, itemid, 1, false);
			name = "【" + player.getName() + "】在快问乱答活动中获得了【大修复神水】";
			ChatService.sayXiaoQ(name);
		}
		if (79 <= num && num < 80) {
			itemid = 10457;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (80 <= num && num < 85) {
			itemid = 10504;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (85 <= num && num < 89) {
			itemid = 10525;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (89 <= num && num < 90) {
			itemid = 10529;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (90 <= num && num < 91) {
			itemid = 10530;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (91 <= num && num < 92) {
			itemid = 10531;
			ItemService.addItem(player, itemid, 1, false);
			name = "【" + player.getName() + "】在快问乱答活动中获得了【黄金宝箱钥匙】";
			ChatService.sayXiaoQ(name);

		}
		if (92 <= num && num < 95) {
			itemid = 10414;
			ItemService.addItem(player, itemid, 1, false);
		}
		if (95 <= num && num < 96) {
			itemid = 10370;
			ItemService.addItem(player, itemid, 1, false);
			name = "【" + player.getName() + "】在快问乱答活动中获得了【豪华礼包】";
			ChatService.sayXiaoQ(name);

		}
		if (96 <= num && num < 100) {
			itemid = 10389;
			ItemService.addItem(player, itemid, 1, false);

		}
		logger.info(LoggerHelper.ZONE_ID + "|lantern|" + "Answerjiang|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|" + itemid);
		return itemid;
	}

	private static ErrorMsg openYuanXiao(Player player, PlayerItem playerItem,
			int num) {
		int itemid = playerItem.getItemId();
		ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem, false);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}
		if (itemid == 10624) {
			player.addCopper(10 * 1000);
			ItemService.addItem(player, 82, 1, false);
			ItemService.addItem(player, 84, 1, false);
			ItemService.addItem(player, 10630, 1, false);
			if (num == 0) {
				ItemService.addItem(player, 10604, 1, false);
			} else {
				ItemService.addItem(player, 10607, 1, false);
			}
		}
		if (itemid == 10625) {
			player.addCopper(50 * 1000);
			ItemService.addItem(player, 82, 2, false);
			ItemService.addItem(player, 84, 2, false);
			ItemService.addItem(player, 10630, 2, false);
			if (num == 0) {
				ItemService.addItem(player, 10604, 1, false);
			} else {
				ItemService.addItem(player, 10607, 1, false);
			}
		}
		if (itemid == 10626) {
			player.addCopper(100 * 1000);
			ItemService.addItem(player, 10346, 1, false);
			ItemService.addItem(player, 10347, 1, false);
			ItemService.addItem(player, 10630, 3, false);
			if (num == 0) {
				ItemService.addItem(player, 10604, 2, false);
			} else {
				ItemService.addItem(player, 10607, 2, false);

			}
		}
		logger.info(LoggerHelper.ZONE_ID + "|lantern|" + "yuanxiaobao|"
				+ player.getUserId() + "|" + player.getId() + "|"
				+ player.getName() + "|" + player.getLevel() + "|" + itemid);
		return Constants.SUCC;
	}

	public static boolean validate() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		if (month != Calendar.FEBRUARY) {
			return false;
		}
		if (!(dayOfMonth >= 17 && dayOfMonth <= 28)) {
			return false;
		}
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (!((hour >= 10 && hour < 11) || (hour >= 14 && hour < 16) || (hour >= 20 && hour < 22))) {
			return false;
		}
		return true;
	}
}
