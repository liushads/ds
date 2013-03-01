package com.ppsea.ds.command.resell;

import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.SellPriceVo;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.PlayerResellService;
import com.ppsea.ds.util.Util;

/**
 * 倒卖主页
 * @author xl
 *
 */
public class ResellMainCmd extends BaseCmd {


	@SuppressWarnings("unchecked")
    protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int goodsType = 1;//1、低；2、中；3、高；4、奢
	    int optType = 1;   //1、买；2、卖；3.排行榜
		int pageId = 1;
		if(ps.length==3){
			optType = Integer.valueOf(ps[0]);
			goodsType = Integer.valueOf(ps[1]);
			pageId = Integer.valueOf(ps[2]);
		}
		result.setVO(TAG_GOODS_TYPE, goodsType);
		result.setVO(TAG_OPT_TYPE, optType);
		//资产总额（现金总额、货物价值）
		PlayerResell playerResell = player.getPlayerResell();
		if(playerResell==null){//上线操作从DB载入后还是空，则执行初始化
			playerResell = PlayerResellService.initPrice(player);
		}
		result.setVO(TAG_GOODS_VALUE, playerResell.getGoodsValue());
		result.setVO(TAG_RESELL_GOLD, playerResell.getResellGold());
		//倒计时间、刷新次数
		long overTime = playerResell.getFlushTime()+PlayerResellService.REFRESH_TIME_SPACE;//过期时间 
		Long remainTime = (overTime-System.currentTimeMillis())/1000;
		int secondSpace = remainTime.intValue();
		
		result.setVO("space_time", secondSpace);
		result.setVO(TAG_REFRESH_LIMIT, PlayerResellService.REFRESH_COST_GOLD_MAX_TIMES);
		result.setVO(TAG_REFRESH_REMAIN,PlayerResellService.REFRESH_COST_GOLD_MAX_TIMES-playerResell.getFlushTimes());
		
		if(optType == 1){////1、买；
			//自动触发系统刷新
			if(secondSpace<=0){
				PlayerResellService.refreshPrice(player,false);
			}
			
			List<SellPriceVo> priceList =  player.getPriceList(goodsType);
			Util.page(priceList, pageId, result);
			
		}else if(optType == 2){//2、卖；
			List<BoughtPriceVo> boughtList =  player.getBoughtGoods();
			Util.page(boughtList, pageId, result);
			
		}else if(optType == 3){//3.排行榜
			Util.page(null, pageId, result);
		}
		return result;
	}
}
