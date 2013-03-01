package com.ppsea.ds.data.playeritem;

import java.util.LinkedList;
import java.util.List;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.ItemPosition;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.DBService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PlayerService;


public class ArmHandler implements PlayerItemHandler{

	public static PlayerItemHandler handler;
	
	public synchronized static PlayerItemHandler getInstance() {
		if (handler == null) {
			handler = new ArmHandler();
		}
		return handler;
	}
	
	public ErrorMsg unuse(PlayerItem playerItem) {
		Player player = PlayerMG.instance.getOnlinePlayer(playerItem.getPlayerId());
		if(player == null ){
			return new ErrorMsg(ErrorCode.ERR_OFFLINE,"离线用户不能装备道具");
		}
		
		if (player == null || playerItem == null) {
			return Constants.SUCC;
		}
		List<PlayerItem> ls = player.getUsedArms().get(playerItem.getItem().getPositionStr());
		if (ls == null || !ls.remove(playerItem)) {
			//数据不一致保护: 设置了使用位，但没有在使用map里
			if (playerItem.getIsUse() == 1) {
				playerItem.setIsUse(0);
				DBService.commit(playerItem);
			}
			return Constants.SUCC;
		}

		//更新道具属性, 并提交到数据库
		playerItem.setIsUse(0);
		DBService.commit(playerItem);

		//检查套装
		ItemService.checkItemSuit(player);
		
		//改变用户属性	
		PlayerService.reloadPlayerDyn(player);

		//检查用户体力是否越界
		player.checkHp();
		return Constants.SUCC;
	}

	public ErrorMsg use(PlayerItem playerItem) {
		Player player = PlayerMG.instance.getOnlinePlayer(playerItem.getPlayerId());
		if(player == null ){
			return new ErrorMsg(ErrorCode.ERR_OFFLINE,"离线用户不能装备道具");
		}
		
		//如果道具已经在使用，则直接返回成功
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_NO_ITEM,"装备已经不存在了");
		}
		
		if (playerItem.getIsUse() == 1) {
			return Constants.SUCC;
		}
		
		//检查Player是否拥有该Item
		if (player.getId().intValue() != playerItem.getPlayerId().intValue()) {
			return new ErrorMsg(ErrorCode.ERR_BIND,"绑定关系错误");
		}
		//检查道具使用级别
		if (player.getLevel() < playerItem.getItem().getLevel()) {
			return new ErrorMsg(ErrorCode.ERR_PLAYER_LEVEL,"级别不够，暂时不能使用该道具");
		}
		//检查有耐久属性的道具，当前耐久是否为0
//		if (playerItem.getItem().getEndure() > 0 && playerItem.getCurrEndure() == 0) {
//			return new ErrorMsg(ErrorCode.ERR_ENDURE,"道具耐久为0,将无法为您带来装备属性");
//		}
		//是否绑定了别人		
		if (playerItem.checkBindOther(player.getId())) {
			return new ErrorMsg(ErrorCode.ERR_BIND,"绑定关系错误");
		}
		//道具是否在交易中
		if (playerItem.getExchangeAmount() > 0){
			return new ErrorMsg(ErrorCode.ERR_EX_IN,"交易中的装备暂时不能使用");
		}
		
		//如果超过位置容量，则提示用户先卸下装备留出空间	
		ItemPosition itemPosition = ItemMG.instance.getItemPostion(playerItem.getItem().getPosition().intValue());
		String position = playerItem.getItem().getPositionStr();
		List<PlayerItem> pis = player.getUsedArms().get(position);
		if (pis == null) {
			pis = new LinkedList<PlayerItem>();
			player.getUsedArms().put(position, pis);
		}
		if (pis.size() >= itemPosition.getRoom()) {
			return new ErrorMsg(ErrorCode.ERR_EQUIP_FULL,"装备位置已满");
		}		
		//上新装备
		pis.add(playerItem);		
				
		//更新道具属性
		playerItem.setIsUse(1);
		DBService.commit(playerItem);
		
		//检查套装
		ItemService.checkItemSuit(player);
		
		//改变用户属性	
		PlayerService.reloadPlayerDyn(player);

		return Constants.SUCC;
	}
	
	
}
