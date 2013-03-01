/*
 * 
 */
package com.ppsea.ds.command.npc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;

/**
 * @author Administrator
 * @date 2011-2-11
 */
public class LuckCmd extends BaseCmd {
	
	
	private static List<Player> menList = new ArrayList<Player>();
	
	private static List<Player> womenList = new ArrayList<Player>();
	
	private static long lastUpdateTime = 0;

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		
		if (ps == null || ps.length == 0) {
			result.setStatus("main");
			return result;
		}
		
		String action = ps[0];
		if ("luck".equals(action)) {
			try {
				List<Player> list = getCacheList(player);
				Random ran = new Random();
				int index = ran.nextInt(list.size());
				Player target = list.get(index);
				if (target != null) {
					result.setVO("targetm", target);
				}
			} catch (Exception e) {
			}
			result.setStatus("luck");
			return result;
		}
		
		return result;
	}

	private List<Player> getCacheList(Player player) {
		if (lastUpdateTime == 0 || (lastUpdateTime > 0 && (System.currentTimeMillis() - lastUpdateTime > 1 * 60 * 1000))) {
			Map<Integer, Player> map = PlayerMG.instance.getPlayerMap();
			synchronized("1") {
				menList.clear();
				womenList.clear();
				for (Map.Entry<Integer, Player> entry : map.entrySet()) {
					Player p = entry.getValue();
					if (p.getSex() == 1) {
						menList.add(p);
					} else {
						womenList.add(p);
					}
				}
				lastUpdateTime = System.currentTimeMillis();
			}
		}
		if (player.getSex() == 1) {
			return womenList;
		} else {
			return menList;
		}
	}
}
