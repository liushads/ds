package com.ppsea.ds.command.dart;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Dart;
import com.ppsea.ds.data.model.DartAward;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerDart;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DartService;
import com.ppsea.ds.service.ErrorCode;

public class RobDartCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int pid = Integer.parseInt(ps[0]);
		
		if (ps != null && ps.length > 1 && "ok".equals(ps[1])) {//劫镖
			Player enemy = PlayerMG.instance.getOnlinePlayer(pid);
			ErrorMsg msg = DartService.robDart(player, enemy);
			if (msg.code != ErrorCode.SUCC) {
				setFailResult(result, msg);
				return result;
			}
			if (!"fail".equals(msg.text)) {
				result.setVO("label_succ", msg.text);
			}
			StringBuffer sb = new StringBuffer();
			sb.append(enemy.getName()).append("VS你<br/>");
			sb.append("体力：").append(enemy.getDyn().getMaxHp()).append("VS").append(player.getDyn().getMaxHp()).append("<br/>");
			sb.append("攻击：").append(enemy.getDyn().getAttackMin()).append("-").append(enemy.getDyn().getAttackMax());
			sb.append("VS").append(player.getDyn().getAttackMin()).append("-").append(player.getDyn().getAttackMax()).append("<br/>");
			sb.append("防御：").append(enemy.getDyn().getDefence()).append("VS").append(player.getDyn().getDefence()).append("<br/>");
			sb.append("敏捷：").append(enemy.getDyn().getAgility()).append("VS").append(player.getDyn().getAgility()).append("<br/>");
			result.setVO("label_vs", sb.toString());
			result.setStatus("stat_rob");
			return result;
		}else {//查看玩家镖车
			Player enemy = PlayerMG.instance.getOnlinePlayer(pid);
			if (enemy == null) {
				setFailResult(result, new ErrorMsg(-1, "玩家不在线，不能劫镖"));
				return result;
			}
			
			ErrorMsg msg = DartService.getDartAwardPrize(player, enemy);
			if (msg.code != ErrorCode.SUCC) {
				setFailResult(result, msg);
				return result;
			}
			Dart dart = ResourceCache.instance.getDarts().get(enemy.getPlayerDart().getDartId());
			PlayerDart eDart = enemy.getPlayerDart();
			result.setVO("label_name", dart.getName());
			result.setVO("label_limit_times", Constants.DART_ROB_TIME);
			result.setVO("label_rob_times",eDart.getRobed());
			result.setVO("label_less_time", DartService.getLastTime(eDart));
			result.setVO("label_award", (DartAward)msg.obj);
			result.setVO("label_other_pid", enemy.getId());
			result.setVO("label_other_name", enemy.getName());
			result.setVO("label_other_level", enemy.getLevel());
			result.setStatus("stat_vie");
		}
//		CommandRequest newCmd = new CommandRequest();
//		newCmd.setCmd("d_SD");
//		newCmd.setPid(player.getId().toString());
//		newCmd.setPs(new String[] { "in" });
//		result = callOtherCmd(newCmd);
//		result.setVO("label_desc", msg.text);
		return result;
	}

}
