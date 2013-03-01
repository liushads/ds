package com.ppsea.ds.command.item;

import java.util.HashMap;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;

public class UseArmAllCmd extends BaseCmd{

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
//		CommandResult result =  new CommandResult(STATUS_SUCC);
		Map<String,PlayerItem> map = player.getAllItems();
		Map<String, PlayerItem> playerItemMap = new HashMap<String, PlayerItem>();
		PlayerItem p = null;
		for (PlayerItem pi : map.values()) {
			if (pi.getItem().getType() == Item.ARM_TYPE 
					&& pi.getPlayerId().intValue() == player.getId().intValue()
					&& pi.getItem().getLevel() <= player.getLevel()) {
				p = playerItemMap.get(pi.getItem().getPositionStr());
				if (p != null) {
					if (p.getItem().getLevel() < pi.getItem().getLevel()) {
						p = pi;
					}
				}else {
					p = pi;
				}
				playerItemMap.put(p.getItem().getPositionStr(), p);
			}
		}
		if (playerItemMap.size() > 0) {
			for (PlayerItem pi : playerItemMap.values()) {
				pi.use();
			}
		}
		
		
		return callOtherCmd("p_LA",player);
	}

}
