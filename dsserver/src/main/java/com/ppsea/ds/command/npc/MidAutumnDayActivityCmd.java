package com.ppsea.ds.command.npc;

import java.util.Calendar;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * 中秋节活动
 * 
 * @author EddyZhou
 * @date 2011-8-23
 */
public class MidAutumnDayActivityCmd extends BaseCmd {

	/** 活动结束时间 */
	private static long END_TIME;

	/** 活动开始时间 */
	private static long START_TIME;

	static {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONDAY, Calendar.SEPTEMBER);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		START_TIME = c.getTimeInMillis();

		c.set(Calendar.MONDAY, Calendar.SEPTEMBER);
		c.set(Calendar.DAY_OF_MONTH, 16);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		END_TIME = c.getTimeInMillis();
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if (ps != null && "index".equals(ps[0])) {
			result.setStatus("index");
			return result;
		}

		long now = System.currentTimeMillis();
		if (now >= END_TIME || now < START_TIME) {
			this.setFailResult(result, new ErrorMsg(
					ErrorCode.ERR_ACTIVE_INVALID, "活动时间无效，有效时间为：9月1日-9月15日"));
			return result;
		}

		String type = ps[0];

		boolean opSuc = exchangePrizePkg(player, type);
		if (opSuc) {
			result.setStatus(STATUS_SUCC);
			result.setVO(TAG_DESC, "兑换礼包成功!");
			return result;
		} else {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_LOW,
					"领取失败，没有齐集兑换的道具."));
			return result;
		}

	}

	private boolean exchangePrizePkg(Player p, String type) {
		if ("small".equals(type)) {
			return this.exchangeSmallPrizePkg(p);
		} else if ("big".equals(type)) {
			return this.exchangeBigPrizePkg(p);
		} else if ("luxury".endsWith(type)) {
			return this.exchangeLuxuryPrizePkg(p);
		} else {
			throw new IllegalArgumentException("type[" + type + "] not valid.");
		}
	}

	private boolean exchangeSmallPrizePkg(Player player) {
		PlayerItem playerItem1 = null;
		PlayerItem playerItem2 = null;
		PlayerItem playerItem3 = null;
		PlayerItem playerItem4 = null;

		playerItem1 = ItemService.findPlayerItem(player, 10415); // 【欢】
		if (playerItem1 == null || playerItem1.getAmount() < 1) {
			return false;
		}

		playerItem2 = ItemService.findPlayerItem(player, 10416); // 【乐】
		if (playerItem2 == null || playerItem2.getAmount() < 1) {
			return false;
		}

		playerItem3 = ItemService.findPlayerItem(player, 10417); // 【中】
		if (playerItem3 == null || playerItem3.getAmount() < 1) {
			return false;
		}

		playerItem4 = ItemService.findPlayerItem(player, 10418); // 【秋】
		if (playerItem4 == null || playerItem4.getAmount() < 1) {
			return false;
		}

		ItemService.releasePlayerItem(player, playerItem1, 1, true);
		ItemService.releasePlayerItem(player, playerItem2, 1, true);
		ItemService.releasePlayerItem(player, playerItem3, 1, true);
		ItemService.releasePlayerItem(player, playerItem4, 1, true);

		ItemService.addItem(player, 10423, 1, false); // 中秋小礼包

		return true;
	}

	private boolean exchangeBigPrizePkg(Player player) {
		PlayerItem playerItem1 = null;
		PlayerItem playerItem2 = null;
		PlayerItem playerItem3 = null;
		PlayerItem playerItem4 = null;

		playerItem1 = ItemService.findPlayerItem(player, 10419); // 【共】
		if (playerItem1 == null || playerItem1.getAmount() < 1) {
			return false;
		}

		playerItem2 = ItemService.findPlayerItem(player, 10420); // 【度】
		if (playerItem2 == null || playerItem2.getAmount() < 1) {
			return false;
		}

		playerItem3 = ItemService.findPlayerItem(player, 10421); // 【佳】
		if (playerItem3 == null || playerItem3.getAmount() < 1) {
			return false;
		}

		playerItem4 = ItemService.findPlayerItem(player, 10422); // 【节】
		if (playerItem4 == null || playerItem4.getAmount() < 1) {
			return false;
		}

		ItemService.releasePlayerItem(player, playerItem1, 1, true);
		ItemService.releasePlayerItem(player, playerItem2, 1, true);
		ItemService.releasePlayerItem(player, playerItem3, 1, true);
		ItemService.releasePlayerItem(player, playerItem4, 1, true);

		ItemService.addItem(player, 10424, 1, false); // 中秋大礼包

		return true;
	}

	private boolean exchangeLuxuryPrizePkg(Player player) {
		PlayerItem playerItem1 = null;
		PlayerItem playerItem2 = null;
		PlayerItem playerItem3 = null;
		PlayerItem playerItem4 = null;
		PlayerItem playerItem5 = null;
		PlayerItem playerItem6 = null;
		PlayerItem playerItem7 = null;
		PlayerItem playerItem8 = null;

		playerItem1 = ItemService.findPlayerItem(player, 10415); // 【欢】
		if (playerItem1 == null || playerItem1.getAmount() < 1) {
			return false;
		}

		playerItem2 = ItemService.findPlayerItem(player, 10416); // 【乐】
		if (playerItem2 == null || playerItem2.getAmount() < 1) {
			return false;
		}

		playerItem3 = ItemService.findPlayerItem(player, 10417); // 【中】
		if (playerItem3 == null || playerItem3.getAmount() < 1) {
			return false;
		}

		playerItem4 = ItemService.findPlayerItem(player, 10418); // 【秋】
		if (playerItem4 == null || playerItem4.getAmount() < 1) {
			return false;
		}

		playerItem5 = ItemService.findPlayerItem(player, 10419); // 【共】
		if (playerItem5 == null || playerItem5.getAmount() < 1) {
			return false;
		}

		playerItem6 = ItemService.findPlayerItem(player, 10420); // 【度】
		if (playerItem6 == null || playerItem6.getAmount() < 1) {
			return false;
		}

		playerItem7 = ItemService.findPlayerItem(player, 10421); // 【佳】
		if (playerItem7 == null || playerItem7.getAmount() < 1) {
			return false;
		}

		playerItem8 = ItemService.findPlayerItem(player, 10422); // 【节】
		if (playerItem8 == null || playerItem8.getAmount() < 1) {
			return false;
		}

		ItemService.releasePlayerItem(player, playerItem1, 1, true);
		ItemService.releasePlayerItem(player, playerItem2, 1, true);
		ItemService.releasePlayerItem(player, playerItem3, 1, true);
		ItemService.releasePlayerItem(player, playerItem4, 1, true);
		ItemService.releasePlayerItem(player, playerItem5, 1, true);
		ItemService.releasePlayerItem(player, playerItem6, 1, true);
		ItemService.releasePlayerItem(player, playerItem7, 1, true);
		ItemService.releasePlayerItem(player, playerItem8, 1, true);

		ItemService.addItem(player, 10425, 1, false); // 中秋豪华礼包

		return true;
	}

}
