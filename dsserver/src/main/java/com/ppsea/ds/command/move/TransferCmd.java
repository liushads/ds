package com.ppsea.ds.command.move;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.MissionService;
import com.ppsea.ds.service.PlayerService;
import com.ppsea.ds.service.TravelService;

public class TransferCmd extends BaseCmd{
	private static final Logger log = Logger.getLogger(TransferCmd.class);
	
	/**
	 * @ps : ps[0] = targetcity, ps[1] = paytype, siliver(0) or gold(1)
	 * */
	@Override
	public CommandResult done(Player player, String[] ps) throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		
		//判断等级
		if(player.getLevel() <= TravelService.TRANSFER_LEVEL_LIMIT){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL,"传送需要大于"+TravelService.TRANSFER_LEVEL_LIMIT+"级"));
			return result;
		}
		if(MissionService.hasRewardMission(player)){
			//result.setStatus(STATUS_FAIL);
			result.setName("mv_TrD");
			result.setVO("cityId", ps[0]);
			result.setVO("payType", ps[1]);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_SYS,"你有押镖任务，不能传送。"));
			return result;
		}
		
		int cityId = Integer.valueOf(ps[0]);
		int payType = 0;
		if( ps.length > 1){
			payType = Integer.valueOf(ps[1]);
		}
		
		City targetCity = MapMG.instance.getCity(cityId);
		//扣费
		int cost = TravelService.transferFee(player, targetCity);
		if( cost < 0 ){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_INVALID_CITY,"传送目的地不可达"));
			return result;
		}
		
		//用传送珠传送
		if(payType == 0 ){
			PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.ITEM_CITY_CHUANSONG);
			if (playerItem == null) {
				result.setStatus(Command.STATUS_FAIL);
				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "您还没有传送珠，请您到主城驿站传送使者处免费领取一个再来传送，也可以选择出城骑马抵达或者使用0.1金进行传送."));
				return result;
			}
			if (playerItem.getCurrHp() <= 0) {
				result.setStatus(Command.STATUS_FAIL);
				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "您的传送珠次数为0，请您到主城驿站传送使者处更换传送珠再来传送，也可以选择出城骑马抵达或者使用0.1金进行传送."));
				return result;
			}
			playerItem.setCurrHp(playerItem.getCurrHp() - 1);
			DBService.commit(playerItem);
		} else if ( payType == 1) {
			//0.1金票
			if( !player.consumeInAdvGlod(10)){
				throw new NoMoneyException("no money");
			}
		} else if (payType == 2) {
			if( !player.consumeInGlod(10)){
				throw new NoMoneyException("no money");
			}
		} else {
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_INVALID_CITY,"传送目的地不可达"));
			return result;
		}
		
		//将用户在城市中未捡起的道具清除
		player.getTempItems().clear();
		
		//清除上次对话npc
		player.setLastNpcId(0);
		
		//移动到目标城市的驿站
		CityFacility target = targetCity.getSpecialFacilityMap().get(Constants.STATION_ID);
		ErrorMsg ret = PlayerService.move(player, target.getId(), false);
		//player.setFromCityFacilityId(target.getId());
		
		if(ret.code != ErrorCode.SUCC){
			result.setStatus(STATUS_FAIL);
			result.setVO(TAG_ERR_MSG, ret);
			return result;
		}
		
		result = callOtherCmd(COMMAND_WALK, player, target.getId().toString());
		return result;
	}
}
