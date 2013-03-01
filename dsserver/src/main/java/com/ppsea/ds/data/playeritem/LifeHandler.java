/*
 * 
 */
package com.ppsea.ds.data.playeritem;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.GlobalGenerator;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.data.model.PlayerItemUsing;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;

/**
 * @author Administrator
 * @date 2010-5-11
 */
public class LifeHandler implements PlayerItemHandler {

	public static PlayerItemHandler handler;
	
	public synchronized static PlayerItemHandler getInstance() {
		if (handler == null) {
			handler = new LifeHandler();
		}
		return handler;
	}
	
	/**
	 * @see com.ppsea.ds.data.playeritem.PlayerItemHandler#unuse(com.ppsea.ds.data.model.PlayerItem)
	 */
	public ErrorMsg unuse(PlayerItem playerItem) {
		throw new UnsupportedOperationException("method unimplemented yet!");
	}

	/**
	 * @see com.ppsea.ds.data.playeritem.PlayerItemHandler#use(com.ppsea.ds.data.model.PlayerItem)
	 */
	public ErrorMsg use(PlayerItem playerItem) {
		
		Player player = PlayerMG.instance.getOnlinePlayer(playerItem.getPlayerId());
		if (player == null) {
			return new ErrorMsg(ErrorCode.ERR_OFFLINE, "离线用户不能使用时效道具");
		}

		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM, "装备已经不存在了");
		}

		// 道具是否在交易中.
		if (playerItem.getExchangeAmount() > 0) {
			return new ErrorMsg(ErrorCode.ERR_EX_IN, "交易中的装备暂时不能使用");
		}

		Item lifeItem = playerItem.getItem();
		int itemLevel = lifeItem.getLevel();

		int playerLevel = player.getLevel();
		if (playerLevel < itemLevel) {
			return new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL, "用户等级不够，暂时不能使用该道具");
		}
		
		boolean isGroup = ItemMG.instance.isGroupMember(player, lifeItem.getId());
		if (isGroup) {
			return new ErrorMsg(ErrorCode.ERR_GROUP_USING, "你已经有同组道具在使用中.");
		}
		
//		if (lifeItem.getLife() <= 0) {
//			return new ErrorMsg(ErrorCode.ERR_ITEM_IMPROVE_FAIL, "药品已经失效");
//		}

		// release item number of this player.
		ErrorMsg msg = ItemService.releasePlayerItem(player, playerItem, true);
		if (msg.code != ErrorCode.SUCC) {
			return msg;
		}

		// commit to DB.
		PlayerItemUsing itemUsing = new PlayerItemUsing();
		itemUsing.setItemId(playerItem.getItemId());
		itemUsing.setPlayerId(player.getId());
		itemUsing.setExpireTime(playerItem.getItem().getLife() + (int)(System.currentTimeMillis() / 1000));
		itemUsing.setId(GlobalGenerator.instance
				.getIdForNewObj(itemUsing));
		DBService.commit(itemUsing);

		itemUsing.setItem(lifeItem);
		// load to player.
		player.addUsingItem(itemUsing);
		PlayerService.reloadPlayerDyn(player);

		return Constants.SUCC;
	}

}
