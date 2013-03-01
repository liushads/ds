package com.ppsea.ds.command.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.Rank;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.RankMG;

public class RankCmd extends BaseCmd{
	
	private static final Logger log = Logger.getLogger(RankCmd.class);
	
	/**
	 * 排行榜
	 * @param ps
	 *  
	 * */
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		String type = ps[0];
		
		// 首页
		if("index".equals(type)){
			result.setStatus(STATUS_INDEX);
			return result;
		}
		
		List<Rank> ranks = RankMG.instance.getRank(type);
		if(ranks!=null){
			// 按照num排序
			Collections.sort(ranks, new Comparator<Rank>() {
				public int compare(Rank a, Rank b) {
					return b.getNum().intValue() - a.getNum().intValue();
				}
			});
		}
		result.setVO(TAG_TYPE, type);
		result.setVO(TAG_RANKS, ranks);
		return result;
	}
	
}
