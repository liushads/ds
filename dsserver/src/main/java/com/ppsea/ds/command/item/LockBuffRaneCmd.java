package com.ppsea.ds.command.item;

import java.util.TreeMap;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.BuffRand;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.ItemService;

public class LockBuffRaneCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		try {
			int buff_type_id = Integer.parseInt(ps[1]);
			int buff_id = Integer.parseInt(ps[2]);
			PlayerItem playerItem = player.getAllItems().get(ps[0]);
			TreeMap<Integer, BuffRand> buffRands = ResourceCache.instance.getBuffRandMap().get(buff_type_id);
			BuffRand buff = buffRands.get(buff_id);
			String desc  = "";
			if (ItemService.lockBuffRand(playerItem, buff)) {
				desc = "锁定成功！";
			}else {
				desc = "锁定失败！";
			}
			result.setVO("label_desc", desc);
		} catch (Exception e) {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_DESC, "书签错误");
		}
		return result;
	}

}
