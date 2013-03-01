/*
 * 
 */
package com.ppsea.ds.command.move;

import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.ChuansongItemConfig;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.MoveMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

/**
 * @author Administrator
 * @date 2010-8-5
 */
public class ChuanSongSheZheCmd extends BaseCmd {
	
	//5 银.
	private final static int COST_FOR_REPAIR = 5000;
	
    private static Logger logChiShu = Logger.getLogger("CHUANSONG");
	
	
	/**
	 * @see com.ppsea.ds.command.BaseCmd#done(com.ppsea.ds.data.model.Player, java.lang.String[])
	 */
	@Override
	protected CommandResult done(Player player, String[] ps)
	        throws PpseaException {
		
		CommandResult result = new CommandResult(Command.STATUS_SUCC);
		String action = "";
		try {
			action = ps[0];			
		} catch (Exception e) {
			
		}
		if (action.equals("f")) {
			int cityId = 0;
			try {
				cityId = Integer.parseInt(ps[1]);				
			} catch (Exception e) {
				
			}
			fetchChuanSongItem(player, result);
			if (cityId > 0) {
				result.setVO("cityId", cityId);
			}
			return result;
		} else if ("i".equals(action)) {
			result.setStatus("info");
			return result;
		} else if ("m".equals(action)) {
			repaireChuanSongItem(player, result);
			return result;
		} else if ("l".equals(action)) {
			PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.ITEM_CITY_CHUANSONG);
			if (playerItem == null) {
				result.setStatus(Command.STATUS_FAIL);
				result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "您还没有传送珠，无法更换."));
				return result;
			}
			result.setVO("pitem", playerItem);
			result.setStatus("reok");
			return result;
		}
		return result;
	}
	
	public void repaireChuanSongItem(Player player, CommandResult result) throws NoMoneyException {
		PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.ITEM_CITY_CHUANSONG);
		if (playerItem == null) {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "您还没有传送珠，无法更换."));
			return ;
		}
		boolean flag = player.consumeInCopper(COST_FOR_REPAIR);
		if (!flag) {
			throw new NoMoneyException("no money");
		}
		DBService.commit(player);
		
		int cishu = getRandomPoints();
		
		if (cishu > 0) {
			String description = getRepairDescription(cishu);
			result.setVO("cishu", cishu);
			result.setVO("description", description);
			playerItem.setCurrHp(cishu);
			logChiShu.info("succ|"+player.getId().toString()+"|"+cishu);
			DBService.commit(playerItem);
		} else {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "系统异常"));
			return ;
		}
		result.setStatus("repair");
	}
	
	public static int getRandomPoints() {
		int length = MoveMG.pointPool.length;
		Random random = new Random();
		try {
			int pos = random.nextInt(length);
			return MoveMG.pointPool[pos];			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	private String getRepairDescription(int useNum) {
		if (MoveMG.chuansongList != null) {
			for (ChuansongItemConfig cs : MoveMG.chuansongList) {
				if (cs.getUseTimes() == useNum) {
					return cs.getDescription();
				}
			}
		}
		return null;
	}
	
	public void fetchChuanSongItem(Player player, CommandResult result) {
		PlayerItem playerItem = ItemService.findPlayerItem(player, ItemMG.ITEM_CITY_CHUANSONG);
		if (playerItem != null) {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "您已经拥有一个"+ItemMG.ITEM_CITY_CHUANSONG.getName()+"，不能重复领取"));
			return ;
		}
		
		ErrorMsg msg = ItemService.addItem(player, ItemMG.ITEM_CITY_CHUANSONG.getId(), 1, false);
		if (msg.code != ErrorCode.SUCC) {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, msg);
			return ;
		}
		result.setStatus("fetch");
	}
}
