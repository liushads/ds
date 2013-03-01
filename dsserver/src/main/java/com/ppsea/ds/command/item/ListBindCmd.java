package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.util.Util;
/**
 * 显示已经绑定了的装备列表
 **/
public class ListBindCmd extends BaseCmd {
	
	/**
	 * Log 
	 **/
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ListBindCmd.class);
	
	/**
	 * @param ps[0] 当前页
	 **/	
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		List<PlayerItem> binds = new LinkedList<PlayerItem>();
		for(PlayerItem pi:player.getAllItems().values()){
			if(pi.checkBindOther(player.getId())){
				binds.add(pi);
			}
		}
		int page = 0;
		try{
			page = Integer.parseInt(ps[0]);
		}
		catch(Exception e){ page = 0; }
		//分页
		Util.page(binds, page, result);
		return result;
	}
}
