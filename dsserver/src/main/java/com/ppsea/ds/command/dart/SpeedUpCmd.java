package com.ppsea.ds.command.dart;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Dart;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerDart;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DartService;
import com.ppsea.ds.service.ErrorCode;

public class SpeedUpCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int type = Integer.parseInt(ps[0]);
		if (ps != null && ps.length > 1 && "ok".equals(ps[1])) {
			ErrorMsg msg = DartService.speedUp(player, type);
			if (msg.code != ErrorCode.SUCC) {
				setResult(result, STATUS_FAIL, msg);
				return result;
			}
			CommandRequest newCmd = new CommandRequest();
			newCmd.setCmd("d_SD");
			newCmd.setPid(player.getId().toString());
			newCmd.setPs(new String[] { "in" });
			result = callOtherCmd(newCmd);
			result.setVO("label_desc", msg.text);
		}else {
			PlayerDart pd = player.getPlayerDart();
			if (pd == null) {
				setFailResult(result, new ErrorMsg(-1, "没有镖车，不能加速"));
			}
			Dart dart = ResourceCache.instance.getDarts().get(pd.getDartId());
			if (type == 0) {
				result.setVO("label_go_gold", dart.getGold());
				result.setVO("label_go_time", dart.getGoTime());
			}
			if (type == 1) {
				result.setVO("label_go_total_gold", dart.getTotalGold());
			}
		}
		
		return result;
	}

}
