package com.ppsea.ds.command.mall;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
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
public class BuyGoodsNumCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(BuyGoodsNumCmd.class);
	
	/**
	 * @param ps ps[0] = goods_id
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int goodsId = Integer.parseInt(ps[0]);
		Goods goods = GoodsMG.instance.getGoods(goodsId);
		//判断道具是否存在
		if(goods == null){
			result.setStatus(STATUS_FAIL);
			ErrorMsg errMsg = new ErrorMsg(ErrorCode.ERR_NO_ITEM, "道具不存在!");
			result.setVO(TAG_ERR_MSG, errMsg);
			return result;
		}

		result.setVO(TAG_GOODS, goods );
		result.setVO(TAG_PLAYER, player);
		return result;
	}
}
