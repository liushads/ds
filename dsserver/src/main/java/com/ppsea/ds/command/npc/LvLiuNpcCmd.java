/*
 * 
 */
package com.ppsea.ds.command.npc;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.City;
import com.ppsea.ds.data.model.CityFacility;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.MapMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.PlayerService;

/**
 * @author Administrator
 * @date 2010-9-17
 */
public class LvLiuNpcCmd extends BaseCmd {

	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String action = ps[0];
		if ("in".equals(action)) {
			result.setStatus("in");
			return result;
		} else if ("out".equals(action)) {
			result.setStatus("out");
			return result;
		} else if ("g".equals(action)) {
			int type = Integer.parseInt(ps[1]);
			if (type == 1) {
				//10银 
				if (!player.consumeInCopper(10 * 1000)) {
					throw new NoMoneyException("no money");
				}
			} else if (type == 2) {
				//0.1金
				int cost = 10;
				if (!(player.consumeInAdvGlod(cost) || player.consumeInGlod(cost))) {
					throw new NoMoneyException("no money");
				}
			} else {
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_PAY,"支付类型错误"));
				return result;
			}
			City targetCity = MapMG.instance.getCity(122);//绿柳庄城市ID
			CityFacility target = targetCity.getCityFacilityMap().get(6443);//绿柳庄庄园中央。城市设置ID
			
			ErrorMsg ret = PlayerService.move(player, target.getId(), false);
			if(ret.code != ErrorCode.SUCC){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}
			
			return callOtherCmd(COMMAND_WALK, player, target.getId().toString());
		} else if ("l".equals(action)) {
			if (player.getCityFacilityId() != 6443) {
				return callOtherCmd(COMMAND_WALK, player, player.getCityFacilityId().toString());
			}
			//Integer fromCityFacilityId = player.getFromCityFacilityId();
			ErrorMsg ret = PlayerService.move(player, 3523, false);//凤凰集校场，城市设置ID.
			if(ret.code != ErrorCode.SUCC){
				result.setStatus(STATUS_FAIL);
				result.setVO(TAG_ERR_MSG, ret);
				return result;
			}
			
			return callOtherCmd(COMMAND_WALK, player, "3523");
			
		} else {
			return result;
		}

	}

}
