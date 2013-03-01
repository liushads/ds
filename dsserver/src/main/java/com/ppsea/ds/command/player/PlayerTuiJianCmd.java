package com.ppsea.ds.command.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerPromote;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.Util;

public class PlayerTuiJianCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String action = ps[0];
		
		if ("index".equals(action)) {
			Map<Integer, PlayerPromote> map=player.getPlayerPromoteMap();
			List<Player> playerlist=  new ArrayList<Player>();
			for (Map.Entry<Integer, PlayerPromote> entry : map.entrySet()) {
				PlayerPromote pp = entry.getValue();
				Player p = PlayerMG.instance.getOnlinePlayer(pp.getPlayerId());
				if (p == null) {
					p = PlayerMG.instance.getPlayerSimple(pp.getPlayerId());
				}
				playerlist.add(p);
			}
			int pageId = 0;
			try{
				pageId = Integer.parseInt(ps[1]);
			}
			catch(Exception e){
				pageId = 0;
			}
			Util.page(playerlist, pageId, result);
			result.setStatus("index");
		}
		if ("jiang".equals(action)){
			int id=Integer.valueOf(ps[1]);
			Player p = PlayerMG.instance.getOnlinePlayer(id);
			if (p == null) {
				p = PlayerMG.instance.getPlayerSimple(id);
			}
			result.setVO("tuijian", p);
			result.setStatus("jiang");
		}
		return result;
	}

}
