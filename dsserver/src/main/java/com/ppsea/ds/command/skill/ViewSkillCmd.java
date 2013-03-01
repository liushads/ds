package com.ppsea.ds.command.skill;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Skill;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.SkillService;

public class ViewSkillCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int id = Integer.parseInt(ps[0]);
		Skill skill = SkillService.getSkill(id);
		if (skill != null) {
			result.setVO("label_skill", skill);
		}
		return result;
	}

}
