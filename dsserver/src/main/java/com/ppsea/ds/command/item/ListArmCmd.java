package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ResourceCache;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
/**
 * 显示玩家所以武器类装备
 **/
public class ListArmCmd extends BaseCmd {	
	
	/**
	 * @param ps[0] 装备位置
	 * @param ps[1] 页数
	 **/
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int type = 1;
		try{
			type = Integer.parseInt(ps[0]);
		}
		catch(Exception e){
			type = 1;
		}
		List<PlayerItem> objs = player.getArms().get(ps[0]);		
		int pageId = 0;
		try{
			pageId = Integer.parseInt(ps[1]);
		}
		catch (Exception e) {
			pageId = 0;
		}
		//对装备列表进行分页处理
		if(objs == null) objs = new LinkedList<PlayerItem>();//may be null
		ItemService.sortByLevelDesc(objs);
		Util.page(objs, pageId, result);
		result.setVO(TAG_RETURN_TYPE, String.valueOf(type));
		return result;
	}
}
