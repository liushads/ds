/*
 * 
 */
package com.ppsea.ds.service;

import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.ppsea.ds.command.player.Displayer;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.manager.PlayerMG;

/**
 * @author Administrator
 * @date 2011-1-10
 */
public class DisplayerService {

	private static DisplayerService instance = new DisplayerService();
	
	private static ConcurrentLinkedQueue<Displayer> displayerQueue = new ConcurrentLinkedQueue<Displayer>();
	
	public static DisplayerService getInstance() {
		return instance;
	}
	
	
	public void addDisplayer(Displayer displayer) {
		displayerQueue.add(displayer);
	}
	
	public Displayer createDisplayer(Player player, int displayType, int serverType) {
		Displayer displayer = new Displayer();
		displayer.setPlayer(player);
		displayer.setCreateTime(new Date());
		displayer.setDisplayType(displayType);
		displayer.setServerType(serverType);
		return displayer;
	}
	
	
	public ErrorMsg commitMarryDisplayer(Player player, int mateId, int displayerType) {
		Displayer displayer = createDisplayer(player, displayerType, 2);
		Player matePlayer = PlayerMG.instance.getOnlinePlayer(mateId);
		if (matePlayer == null) {
			return new ErrorMsg(ErrorCode.ERR_USER_NO_EXIST, "对方不在线");
		}
		displayer.setMatePlayer(matePlayer);
		addDisplayer(displayer);
		return new ErrorMsg(ErrorCode.SUCC, matePlayer);
		
	}
	public ErrorMsg commitLeiTaiDisplayer(Player player, int sid, int displayerType) throws NoMoneyException {
		Displayer displayer = createDisplayer(player, displayerType,4);
		Player seller = PlayerMG.instance.getPlayerSimple(sid);
		if (seller == null) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "人物不存在");
		}
		if (displayerType == 1)	 {
			//置顶
			if (!(player.consumeInAdvGlod(50) || player.consumeInGlod(50))) {
				throw new NoMoneyException("no money");
			}
			displayer.setSeller(seller.getName());
			addDisplayer(displayer);
		} else {
			//聊天
			if (!player.consumeInCopper(50*1000)) {
				throw new NoMoneyException("no money");
			}
			ChatService.sayXiaoQ(player.getName()+"战胜了"+seller.getName()+",好牛哦");
		}
		
		return new ErrorMsg(ErrorCode.SUCC, seller);
	}
	public ErrorMsg commitPlayerItemDisplayer(Player player, int pitemId, int displayerType) throws NoMoneyException {
		Displayer displayer = createDisplayer(player, displayerType,1);
		PlayerItem playerItem = player.getAllItems().get(String.valueOf(pitemId));
		if (playerItem == null) {
			return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "物品不存在");
		}
		if (displayerType == 1)	 {
			//置顶
			if (!(player.consumeInAdvGlod(50) || player.consumeInGlod(50))) {
				throw new NoMoneyException("no money");
			}
			displayer.setPlayerItem(playerItem);
			addDisplayer(displayer);
		} else {
			//聊天
			if (!player.consumeInCopper(50*1000)) {
				throw new NoMoneyException("no money");
			}
			ChatService.sayXiaoQ(player.getName()+"展示了他的"+ItemService.getBeautifulName(playerItem)+",好牛的装备哦");
		}
		
		return new ErrorMsg(ErrorCode.SUCC, playerItem);
	}
	
	public Displayer checkAndDisplay() {
		try {
			Displayer displayer = displayerQueue.peek();
			if (displayer != null) {
				long disTime = displayer.getDisplayTime();
				long now = System.currentTimeMillis();
				if (disTime <= 0) {
					displayer.setDisplayTime(now);
				} else {
					long inval = now -displayer.getDisplayTime();
					if (inval >= 10 *1000) {
						displayerQueue.remove(displayer);
						displayer = displayerQueue.peek();
						displayer.setDisplayTime(now);
					}
				}
			}
			return displayer;
		} catch (Exception e) {
		}
		return null;
	}
	
	
	
}

