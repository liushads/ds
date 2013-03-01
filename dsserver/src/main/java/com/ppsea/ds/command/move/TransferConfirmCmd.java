package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.TravelService;

public class TransferConfirmCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(TransferConfirmCmd.class);
	
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//判断等级
		if(player.getLevel() <= TravelService.TRANSFER_LEVEL_LIMIT){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL, "等级不够，传送需要大于"+TravelService.TRANSFER_LEVEL_LIMIT+"级"));
			return result;
		}
		
		int cityId = Integer.valueOf(ps[0]);
		City city = MapMG.instance.getCity(cityId);
		//检查是否满足改地区等级要求
		ErrorMsg ret =  TravelService.checkPlayerLevel(player, city);
		if( ret.code != ErrorCode.SUCC ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		
		int cost = TravelService.transferFee(player, city);
		PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.ITEM_CITY_CHUANSONG);
		result.setVO("chuanSongZhu", playerItem);
		result.setVO(TAG_CITY, city);		
		result.setVO(TAG_AMOUNT, cost);
		return result;
	}
}
