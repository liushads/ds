package com.ppsea.ds.command.user;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Sect;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.manager.SectMG;

public class CreatePlayerCmd extends BaseCmd {
	private static final Logger log = Logger.getLogger(CreatePlayerCmd.class);

	@SuppressWarnings("unchecked")
	@Override
	public CommandResult execute(CommandRequest cmdReq) {
		CommandResult result = new CommandResult(STATUS_SUCC);

		// 是否达到最大在线人数
		if (PlayerMG.instance.getOnlineNum() >= Constants.PLAYER_CACHE_LIMIT) {
			log.error("cache full!");
			result.setStatus(STATUS_FULL);
			return result;
		}
		// 是否达到该区限制人数
		if (Constants.MAX_PLAYER_NUM > 0
				&& Constants.CURR_PLAYER_NUM > Constants.MAX_PLAYER_NUM) {
			log.error("max player limit!");
			result.setStatus(STATUS_FULL);
			return result;
		}

		String uid = cmdReq.getUid();
		List<Sect> sects = SectMG.instance.getSectList();
		Collections.sort(sects, new Comparator() {
			public int compare(final Object a, final Object b) {
				Sect s1 = (Sect)a;
				Sect s2 = (Sect)b;
				return s1.getId() - s2.getId();
			}
		});

		result.setVO(TAG_UID, uid);
		result.setVO(TAG_SECTS, sects);
		StringBuffer sb = new StringBuffer();
		sb.append(uid).append("|").append(0).append("|").append(0).append("|")
				.append(0).append("|").append(0);
		result.setLogMsg(sb);
		return result;
	}

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		return null;
	}
}
