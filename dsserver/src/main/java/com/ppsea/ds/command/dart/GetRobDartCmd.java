package com.ppsea.ds.command.dart;

import java.util.ArrayList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.Page;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerDartPrize;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DartService;

public class GetRobDartCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		if (ps == null || ps.length <= 1) {//查看所有
			List<PlayerDartPrize> list = new ArrayList<PlayerDartPrize>();
			list.addAll(player.getPlayerDartPrize().values());
			result.setVO("label_prize", list);
			result.setStatus("stat_ind");
			return result;
		}
		if (ps != null && ps.length > 2) {
			
			if (ps != null && "de".equals(ps[1])) {//查看指定
				int get_id = Integer.parseInt(ps[2]);
				PlayerDartPrize pdp = player.getPlayerDartPrize().get(get_id);
				if (pdp != null) {
					result.setVO("label_award", pdp.getDartAward());
				}
				result.setStatus("stat_desc");
				result.setVO("player_dart_prize_id", get_id);
				return result;
			}
			if (ps != null && "gt".equals(ps[1])) {//领取
				int get_id = Integer.parseInt(ps[2]);
				PlayerDartPrize pdp = player.getPlayerDartPrize().get(get_id);
				if (pdp != null) {
					result.setVO("label_award_prize", DartService.getPlayerDartPrize(player, pdp));
				}else {
					setFailResult(result, new ErrorMsg(-1, "已经领取了，不能重复领取"));
					return result;
				}
				result.setStatus("stat_get");
				return result;
			}
		}
		return result;
	}

}
