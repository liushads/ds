package com.ppsea.ds.command.skill;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerSkill;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.SkillService;

public class LearnSkillCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int skill_id = Integer.parseInt(ps[0]);
		ErrorMsg msg = SkillService.learnSkill(player, skill_id);
		if (msg.getCode() != ErrorCode.SUCC) {
			setFailResult(result, msg);
			return result;
		}
		result.setVO("label_player_skill", (PlayerSkill)msg.getObj());
		return result;
	}

}
