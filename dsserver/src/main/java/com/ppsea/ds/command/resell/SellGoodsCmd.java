package com.ppsea.ds.command.resell;

import java.util.Iterator;
import java.util.List;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.BoughtPriceVo;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;

/**
 * 卖货物 1.扣货物 2.加钱
 * 以player为基准，同时更新DB 
 * 一次卖指定的一个,指定下标；一次卖全部，卖出相同ID的全部货物
 * @author {xl}
 *
 */
public class SellGoodsCmd extends BaseCmd {

	@Override
	protected CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		PlayerResell playerResell = player.getPlayerResell();//倒卖对象
		List<BoughtPriceVo> boughtList = player.getBoughtGoods();  //已购买对象
		int sellGold = 0;    														//收入
		if(ps.length !=3){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!"));
			return result;
		}
		if(boughtList == null || boughtList.size()==0){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "没有货物可卖,快去买货吧!"));
			return result;
		}
		
		int sellType = Integer.valueOf(ps[0]);//卖类型
		String goodsName = "";
		int goodsNumber = 1;
		if(sellType == 1){//卖出单个货物--下标标志出单个--------------------------------------------------------------------
			int goodsIndex = Integer.valueOf(ps[1]);//下标
			int randomCode = Integer.valueOf(ps[2]);//randomCode
			//检查物品是否存在
			BoughtPriceVo currPriceVo = player.getBoughtPriceVo(goodsIndex);
			if(currPriceVo == null){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "参数错误!"));
				return result;
			}
			//校验随机码
			if(currPriceVo.getRandomCode() != randomCode){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "请不要重复操作"));
				return result;
			}
			
			sellGold = currPriceVo.getPrice();
			if((playerResell.getResellGold()+sellGold)>=Constants.INT_MAX_VALUE){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "倒卖币即将达到最大限额,先消耗后再来吧"));
				return result;
			}
			goodsName = currPriceVo.getName();
			//更新已购买货物
			boughtList.remove(currPriceVo);//单独测试OK
			player.setBoughtGoods(boughtList);
			
		}else if(sellType == 2){//卖出全部  此类型的货物--------------------------------------------------------------------
			int goodsId = Integer.valueOf(ps[1]);//货物ID
			for (BoughtPriceVo bpv : boughtList) {
				sellGold += bpv.getPrice();
			}
			if((playerResell.getResellGold()+sellGold)>=Constants.INT_MAX_VALUE){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS, "倒卖币即将达到最大限额,先消耗后再来吧"));
				return result;
			}
			Iterator<BoughtPriceVo> iter = boughtList.iterator();
			while(iter.hasNext()){
				BoughtPriceVo bvo = iter.next();
				if(bvo.getId()==goodsId){
					
					goodsName = bvo.getName();
					goodsNumber ++;
					iter.remove();
				}
			}
			//更新已购买货物
			player.setBoughtGoods(boughtList);
			
		}
		//更新player对象里的playerResell对象“倒卖币”字段
		playerResell.setResellGold(playerResell.getResellGold()+sellGold);
		//更新DB
		DBService.commit(playerResell);
		
		result.setVO("sell_gold", sellGold);
		result.setVO("goods_name", goodsName);
		result.setVO("goods_number", goodsNumber);
		return result;
	}

}
