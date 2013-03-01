package com.ppsea.ds.command.mall;

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
 * 商城首页
 * */
public class MallIndexCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(MallIndexCmd.class);
	
	/**
	 * @param ps ps[0] = item_id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		String type = ps[0];
		
		int pageId = 0;
		try {
			pageId = Integer.parseInt(ps[1]);
			pageId = pageId>10?0:pageId;
		} catch (Exception e) {
		}
		List<Goods> goods = GoodsMG.instance.getMallGoods(Integer.parseInt(type));
		Util.page(goods, pageId, result);
		
		result.setVO(TAG_PLAYER, player);
		result.setVO(TAG_TYPE, type);
		result.setVO(TAG_TODAY_SILVER, Constants.getIntValue("mall.today.sliver.price"));
		
		/*
		int dis = ResourceCache.instance.getIntValue("member.superqq.discount");
		result.setVO(CommandResult.LABEL_DISCOUNT, dis );

		result.setVO(CommandResult.LABEL_PLAYER, player);
		result.setVO(CommandResult.LABEL_EXCHANGE_GOLD, Constants.EXCHANGE_RATE_GOLD );
		result.setVO(CommandResult.LABEL_MEMBER_EXCHANGE_GOLD, Constants.MEMBER_EXCHANGE_RATE_GOLD );
		result.setVO(CommandResult.LABEL_RETURN, type);
		
		//列出永乐船
		ShipType ship = ResourceCache.instance.getShipType(Constants.SHIP_YL_ID);
		result.setVO(CommandResult.LABEL_SHIP, ship);
		result.setVO(CommandResult.LABEL_SHIP_CURR, Constants.SHIP_YL_CURR);
		*/
		return result;
	}
}
