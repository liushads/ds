package com.ppsea.ds.command.item;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;

public class TestBuffRandCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int num = Integer.parseInt(ps[0]);
//		List<BuffRand> buffRands = ItemService.getBuffRand(num);
//		result.setVO("label_buff", buffRands);
		return result;
	}

}
