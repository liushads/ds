package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;

public class ListDecomposeCmd extends BaseCmd{

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		// TODO 分页
		result.setVO("label_decompose", ItemService.getPlayerItemDescompose(player));
		return result;
	}

}
