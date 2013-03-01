package com.ppsea.ds.command.item;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.ItemDecompose;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class DecomposeItemCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		try {
			String action = ps[1];
			if ("ok".equals(action)) {
				PlayerItem playerItem = player.getAllItems().get(ps[0]);
				ErrorMsg msg = ItemService.doItemDescompose(player, playerItem);
				if (msg.code != ErrorCode.SUCC) {
					result.setVO(TAG_DESC, msg.getText());
					result.setStatus(STATUS_FAIL);
					return result;
				}
				result.setVO("label_descompose", (ItemDecompose)msg.getObj());
			}
		} catch (Exception e) {
			result.setVO(TAG_DESC, "书签错误！");
			result.setStatus(STATUS_FAIL);
		}
		return result;
	}

}
