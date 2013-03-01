package com.ppsea.ds.command.fight;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.PlayerDyn;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerService;

public class ChalLengeCmd extends BaseCmd {
	public static List<Player> chu = new ArrayList<Player>();
	public static List<Player> zhong = new ArrayList<Player>();
	public static List<Player> gao = new ArrayList<Player>();

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_INDEX);
		if (ps == null || ps.length == 0) {
			result.setStatus("index");
			return result;
		}
		String action = ps[0];
		if ("guize".equals(action)) {
			result.setStatus("guize");
			return result;
		}
		if ("bishi".equals(action)) {
			result.setStatus("bishi");
			return result;
		}
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if ("chu".equals(action)) {
			String type = ps[1];
			if (type.equals("jin")) {
			}
			if (type.equals("she")) {
				if (player.getLeitaiDate() == null) {
					if (player.consumeInCopper(20 * 1000)) {
						player.setLeitaiDate(calendar);
						chu.add(player);
						player.setLeitype(1);
						long num = 300 - (calendar.getTimeInMillis() - player
								.getLeitaiDate().getTimeInMillis()) / 1000;
						result.setVO(TAG_NUM, num);
						result.setVO(TAG_TYPE, "chuji");
						result.setStatus("leitai");
						return result;
					} else {
						result.setStatus(STATUS_FAIL);
						result.setVO(TAG_ERR_MSG, new ErrorMsg(
								ErrorCode.ERR_MISSION_BASE, "金钱不足，不能创建擂台"));
						return result;
					}

				} else {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "你已经拥有了一个擂台，不能创建擂台"));
					return result;
				}
			}
			result.setVO("seller", player);
			result.setVO("chulist", chu);
			result.setStatus("chu");
			return result;

		}
		if ("zhong".equals(action)) {
			String type = ps[1];
			if (type.equals("jin")) {
			}
			if (type.equals("she")) {
				if (player.getLeitaiDate() == null) {
					if (player.consumeInCopper(50 * 1000)) {
						player.setLeitaiDate(calendar);
						player.setLeitype(2);
						zhong.add(player);
						long num = 300 - (calendar.getTimeInMillis() - player
								.getLeitaiDate().getTimeInMillis()) / 1000;
						result.setVO(TAG_NUM, num);
						result.setVO(TAG_TYPE, "zhongji");
						result.setStatus("leitai");
						return result;
					} else {
						result.setStatus(STATUS_FAIL);
						result.setVO(TAG_ERR_MSG, new ErrorMsg(
								ErrorCode.ERR_MISSION_BASE, "金钱不足，不能创建擂台"));
						return result;
					}

				} else {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "你已经拥有了一个擂台，不能创建擂台"));
					return result;
				}
			}
			result.setVO("seller", player);
			result.setVO("zhonglist", zhong);
			result.setStatus("zhong");
			return result;
		}
		if ("gao".equals(action)) {
			String type = ps[1];
			if (type.equals("jin")) {
			}
			if (type.equals("she")) {
				if (player.getLeitaiDate() == null) {
					if (player.consumeInCopper(100 * 1000)) {
						player.setLeitaiDate(calendar);
						player.setLeitype(3);
						gao.add(player);
						long num = 300 - (calendar.getTimeInMillis() - player
								.getLeitaiDate().getTimeInMillis()) / 1000;
						result.setVO(TAG_NUM, num);
						result.setVO(TAG_TYPE, "gaoji");
						result.setStatus("leitai");
						return result;
					} else {
						result.setStatus(STATUS_FAIL);
						result.setVO(TAG_ERR_MSG, new ErrorMsg(
								ErrorCode.ERR_MISSION_BASE, "金钱不足，不能创建擂台"));
						return result;
					}

				} else {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "你已经拥有了一个擂台，不能创建擂台"));
					return result;
				}
			}
			result.setVO("seller", player);
			result.setVO("gaolist", gao);
			result.setStatus("gao");
			return result;
		}
		if ("gong".equals(action)) {
			int id = Integer.valueOf(ps[1]);
			String dengji = ps[2];
			if (player.getLeitaiDate()!=null) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_MISSION_BASE, "你已经有了一个擂台,不能挑战其他擂主"));
				return result;
			}
			if (player.getId() == id) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_MISSION_BASE, "自己不能攻自己的擂台"));
				return result;
			}
			Player seller = PlayerMG.instance.getOnlinePlayer(id);
			if (seller == null) {
				String td = "由于你下线所以擂台取消，金额返回";
				if (dengji.equals("chuji")) {
					chu.remove(seller);
					seller.addCopper(20 * 1000);
					seller.setLeitaiDate(null);
					ChatService.sendMessageSystem(seller.getId(), td, true);
				}
				if (dengji.equals("zhongji")) {
					zhong.remove(seller);
					seller.addCopper(50 * 1000);
					seller.setLeitaiDate(null);
					ChatService.sendMessageSystem(seller.getId(), td, true);
				}
				if (dengji.equals("gaoji")) {
					gao.remove(seller);
					seller.addCopper(100 * 1000);
					seller.setLeitaiDate(null);
					ChatService.sendMessageSystem(seller.getId(), td, true);
				}
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_MISSION_BASE, "擂主不在线不能与他PK"));
				return result;
			}
			if (seller.getLevel() > player.getLevel()) {
				if ((seller.getLevel() - player.getLevel()) > 5) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "你与擂主相差大于5级,请重新选择擂台"));
					return result;
				}
			} else {
				if ((player.getLevel() - seller.getLevel()) > 5) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "你与擂主相差大于5级,请重新选择擂台"));
					return result;
				}
			}
			if (dengji.equals("chuji")) {
				if (!chu.contains(seller)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "擂台取消或者擂台正在被人攻擂"));
					return result;
				}
				if (!player.consumeInCopper(20 * 1000)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "金钱不足，不能攻打擂台"));
					return result;
				}
				chu.remove(seller);
			}
			if (dengji.equals("zhongji")) {
				if (!zhong.contains(seller)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "擂台取消或者擂台正在被人攻擂"));
					return result;
				}
				if (!player.consumeInCopper(50 * 1000)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "金钱不足，不能攻打擂台"));
					return result;
				}
				zhong.remove(seller);
			}
			if (dengji.equals("gaoji")) {
				if (!gao.contains(seller)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "擂台取消或者擂台正在被人攻擂"));
					return result;
				}
				if (!player.consumeInCopper(100 * 1000)) {
					result.setStatus(STATUS_FAIL);
					result.setVO(TAG_ERR_MSG, new ErrorMsg(
							ErrorCode.ERR_MISSION_BASE, "金钱不足，不能攻打擂台"));
					return result;
				}
				gao.remove(seller);
			}
			if (dengji.equals("chuji")) {
				player.setFightPlayerStatus(1);
				seller.setFightPlayerStatus(1);
			}
		
			if (dengji.equals("zhongji")) {
				player.setFightPlayerStatus(2);
				seller.setFightPlayerStatus(2);
			}
			if (dengji.equals("gaoji")) {
				player.setFightPlayerStatus(3);
				seller.setFightPlayerStatus(3);
			}
			player.setLeitaiDate(null);
			seller.setLeitaiDate(null);
			DBService.commit(player);
			DBService.commit(seller);
			if (player.getFightPlayerStatus()>0&&seller.getFightPlayerStatus()>0) {
				CommandRequest newCmd = new CommandRequest();
				newCmd.setCmd("f_M");
				newCmd.setPid(player.getId().toString());
				newCmd.setPs(new String[] { seller.getId().toString() });
				result = callOtherCmd(newCmd);
				return result;
			}
		
		}
		if ("shua".equals(action)) {
			Date datetime = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
		
			if (player.getLeitaiDate()==null) {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(
						ErrorCode.ERR_MISSION_BASE, "你没有创建擂台，请不要捣乱"));
				return result;
			}
			long num = 300 - (cal.getTimeInMillis() - player.getLeitaiDate()
					.getTimeInMillis()) / 1000;
			String dengji = ps[1];
			if (num <= 0) {
				String td = null;
				if (dengji.equals("chuji")) {
					chu.remove(player);
					player.setLeitype(0);
					player.addCopper(20 * 1000);
					player.setLeitaiDate(null);
					td = "你的擂台无人敢挑战，20银币全额返还";
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (dengji.equals("zhongji")) {
					zhong.remove(player);
					player.setLeitaiDate(null);
					player.setLeitype(0);
					player.addCopper(50 * 1000);
					td = "你的擂台无人敢挑战，50银币全额返还";
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				if (dengji.equals("gaoji")) {
					gao.remove(player);
					player.setLeitaiDate(null);
					player.setLeitype(0);
					player.addCopper(100 * 1000);
					td = "你的擂台无人敢挑战，100银币全额返还";
					ChatService.sendMessageSystem(player.getId(), td, true);
				}
				result.setVO(TAG_TIQIU_DIANQIU, td);
				result.setStatus("chenggong");
				return result;
			}
			result.setVO(TAG_TYPE, dengji);
			result.setVO(TAG_NUM, num);
			result.setStatus("leitai");
			return result;
		}
		return result;
	}

}
