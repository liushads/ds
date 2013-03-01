package com.ppsea.ds.command.skill;

import java.util.ArrayList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Skill;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.SkillService;

public class StartIndexCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<Skill> learnSkills = new ArrayList<Skill>();
		List<Skill> no_learnSkills = new ArrayList<Skill>();
		SkillService.getAllSkillType(player, learnSkills, no_learnSkills);
		result.setVO("label_learn", learnSkills);
		result.setVO("label_no_learn", no_learnSkills);
		return result;
	}

}
