package com.ppsea.ds.command.mall;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Goods;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.util.Util;
import com.ppsea.ds.manager.GoodsMG;
import com.ppsea.ds.data.Constants;

/**
 * 输入买入个数
 * */
public class BuySilverNumCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(BuySilverNumCmd.class);
	
	/**
	 * @param ps ps[0] = item_id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_TODAY_SILVER, Constants.getIntValue("mall.today.sliver.price"));
		
		return result;
	}
}
