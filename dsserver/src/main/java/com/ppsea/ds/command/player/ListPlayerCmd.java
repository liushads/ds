package com.ppsea.ds.command.player;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.TeamPlayer;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.Util;
/**
 * 显示自己装备信息 
 **/
public class ListPlayerCmd extends BaseCmd {
	private static final Logger log = Logger.getLogger(ListPlayerCmd.class);
	
	/**
	 * ps[0]- 玩家列表类型， 0-同设施用户， 1-在线用户 
	 * ps[1]- pageId
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int type = Integer.valueOf(ps[0]);
		int pageId = Integer.valueOf(ps[1]);
		
		List<Player> players = null;
		if( type == 0 ){
//			if (player.isInInstacneCity()) {
//				players = new LinkedList<Player>();
//				Team team = TeamMG.instance.getTeam(player.getTeamId());
//				if (team != null && team.isInstanceTeam()) {
//					for (TeamPlayer tp : team.getMembers()) {
//						if (tp.getPlayerId() != player.getId()) {
//							Player p = PlayerMG.instance.getOnlinePlayer(tp
//							        .getPlayerId());
//							if (p.isOnline()) {
//								players.add(p);
//							}
//						}
//					}
//				}
//			} else {
				players = getNearPlayers(player);

//			}
		}
		else{
			players = new LinkedList<Player>();
				for(Player p:PlayerMG.instance.getPlayerMap().values()){
					if(p.isOnline() && p.getId().intValue() != player.getId()){
						players.add(p);
					}
				}

		}
		
		Util.page(players, pageId, result);
		result.setVO("playerType", type);
		return result;
	}
	
	private List<Player> getNearPlayers(Player player){
		List<Player> players = new LinkedList<Player>();
		if(player.getCityFacility() == null){
			return players;
		}
		
		for(Integer pid:player.getCityFacility().getOnlinePlayers()){
			Player p = PlayerMG.instance.getOnlinePlayer(pid);
			if( p != null && p.getId().intValue() != player.getId()){
				players.add(p);
			}
		}
		return players;
	}
}
