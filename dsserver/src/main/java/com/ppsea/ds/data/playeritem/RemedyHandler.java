package com.ppsea.ds.data.playeritem;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerActive;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;


public class RemedyHandler implements PlayerItemHandler{
	private static final Logger log = Logger.getLogger(RemedyHandler.class);
	
	public static PlayerItemHandler handler;
	
	public synchronized static PlayerItemHandler getInstance() {
		if (handler == null) {
			handler = new RemedyHandler();
		}
		return handler;
	}
	
	public ErrorMsg unuse(PlayerItem playerItem) {
		log.error(playerItem.getItem().getName()+"不支持unuse");
		return new ErrorMsg(ErrorCode.ERR_SYS, "药品类不支持unuse");
	}

	public ErrorMsg use(PlayerItem playerItem) {
		Player player = PlayerMG.instance.getOnlinePlayer(playerItem.getPlayerId());
		if(player == null ){
			return new ErrorMsg(ErrorCode.ERR_OFFLINE,"离线用户不能使用道具");
		}
//		if (playerItem.getItem().getId() == 10575 || playerItem.getItem().getId() == 10574) {
//			return useExpStone(player, playerItem);
//		}
		return Constants.SUCC;
	}


	/**
	 * 使用道具恢复HP
	 * =0成功: obj[0]=实际补充HP, obj[1]=实际补充MP
	 * <0失败: ERR_SYS,ERR_NO_ITEM
	 * */
//	private ErrorMsg restoreHpMp(Player player, PlayerItem playerItem) {
//		if (playerItem.getAmount() == 0) {
//			return new ErrorMsg(ErrorCode.ERR_NO_ITEM,"道具不存在");
//		}
//		//检查使用等级
//		if (player.getLevel() < playerItem.getItem().getLevel()) {
//			return new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL,"等级不够，暂时不能使用该道具");
//		}
//		
//		//增加玩家加体力
//		int hp = player.addHp(playerItem.getItem().getHp());
//		int mp = player.addMp(playerItem.getItem().getMp());
//		if(hp > 0 || mp > 0){
//			//销毁物品
//			ErrorMsg ret = ItemService.releasePlayerItem(player, playerItem, true);
//			if (ret.code != ErrorCode.SUCC) {
//				return ret;
//			}
//			DBService.commit(player);
//		} 		
//		return new ErrorMsg(ErrorCode.SUCC,null, new Integer[]{hp, mp});
//	}
	
//	private ErrorMsg useExpStone(Player player, PlayerItem playerItem) {
//		//经验丹,经验果.
//		String key = player.getId().toString() + "_" + "ExpStone"
//		+ "_" + playerItem.getItem().getId().toString();
//		PlayerActive pa = player.getPlayerActive().get(key);
//		if (pa == null) {
//			ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem, true);
//			if (msg.code != ErrorCode.SUCC) {
//				return msg;
//			}
//			pa = PlayerMG.instance.createPlayerActive(player, "ExpStone", playerItem.getItem().getId().intValue());
//			pa.setTime(1);
//			player.getPlayerActive().put(key, pa);
//			DBService.commit(pa);
//			int exp = playerItem.getItem().getExp();
//			player.addExp(exp);
//			int copper = playerItem.getItem().getLife();
//			player.addCopper(copper);
//			return new ErrorMsg(ErrorCode.SUCC,null, new Integer[]{exp, copper});
//		} else {
//			Calendar today = Calendar.getInstance();
//			int nday = today.get(Calendar.DAY_OF_MONTH);
//			Calendar cal = pa.getLastCal();
//			int day = cal.get(Calendar.DAY_OF_MONTH);
//			if (nday != day) {
//				ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem, true);
//				if (msg.code != ErrorCode.SUCC) {
//					return msg;
//				}
//				pa.setTime(pa.getTime() + 1);
//				pa.setJoinDate(new Date());
//				DBService.commit(pa);
//				int exp = playerItem.getItem().getExp();
//				player.addExp(exp);
//				int copper = playerItem.getItem().getLife();
//				player.addCopper(copper);
//				return new ErrorMsg(ErrorCode.SUCC,null, new Integer[]{exp, copper});
//			} else {
//				return new ErrorMsg(ErrorCode.ERR_ITEM_IN_USE,"加经验物品每天只能使用一次");
//			}
//		}
//		
//	}
}
