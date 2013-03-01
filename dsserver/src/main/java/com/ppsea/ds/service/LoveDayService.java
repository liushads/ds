/*
 * 
 */
package com.ppsea.ds.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.Command;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.command.player.Displayer;
import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.util.LoggerHelper;

/**
 * @author Administrator
 * @date 2011-2-11
 */
public class LoveDayService {

	private static Logger logger = Logger.getLogger("Reward");
	private static Logger logsys = Logger.getLogger(LoveDayService.class);
	
	private static List<Displayer> wishesList = Collections.synchronizedList(new ArrayList<Displayer>());
	
	private static List<Player> flowerList = Collections.synchronizedList(new ArrayList<Player>());
	
	public static List<Displayer> getWishesList() {
		return wishesList;
	}
	
	public static List<Player> getFlowerList() {
		return flowerList;
	}
	
	public static void removeFlower(int pid) {
		try {
			for (Player p : flowerList) {
				if (p.getId() == pid) {
					flowerList.remove(p);
				}
			}
		} catch (Exception e) {}
	}
	
	private static boolean checkExistedWishes(Player player) {
		for (Displayer d : wishesList) {
			if (player.getId().equals(d.getPlayer().getId()) && d.getServerType() == 3) {
				return true;
			}
		}
		return false;
	}
	
	public static Displayer findDisplayerPlayer(int playerId) {
		
		for (Displayer d : wishesList) {
			if (d.getPlayer().getId() == playerId && d.getServerType() == 3) {
				return d;
			}
		}
		return null;
	}
	
	public static ErrorMsg publishWishes(Player player, String content, String type) throws NoMoneyException {
		boolean flag = checkExistedWishes(player);
		if (flag) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "你有未完成的愿望，请等待别人的祝福");
		}
		if ("1".equals(type)) {
			if (!(player.consumeInAdvGlod(50) || player.consumeInGlod(50))) {
				throw new NoMoneyException("no money");
			}
		} else {
			PlayerItem pi = ItemService.findPlayerItem(player, 10600);//许愿结
			if (pi == null || pi.getAmount() <= 0) {
				return new ErrorMsg(ErrorCode.ERR_ITEM_EXIST, "没有许愿结");
			}
			ErrorMsg msg = ItemService.releasePlayerItem(player, pi, 1, true);
			if (msg.code != ErrorCode.SUCC) {
				return msg;
			}
		}
		Displayer displayer = DisplayerService.getInstance().createDisplayer(player, 1, 3);
		displayer.setWishes(content);
		wishesList.add(displayer);
		DisplayerService.getInstance().addDisplayer(displayer);
		logger.info(LoggerHelper.ZONE_ID+"|loveDay|"+"publishWishes|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|");
		return Constants.SUCC;
	}
	
	public static void removeWish(Player player, int targetId) {
		try {
			for (Displayer d : wishesList) {
				if (d.getPlayer().getId() == targetId && d.getServerType() == 3) {
					wishesList.remove(d);
				}
			}
		} catch (Exception e) {
			logsys.error("error removeWish", e);
		}
		
	}
	
	public static void sendWishes(Player player, int type, int targetId, CommandResult result) throws NoMoneyException {
		Player target = PlayerMG.instance.getOnlinePlayer(targetId);
		if (target == null) {
			target = PlayerMG.instance.getPlayerSimple(targetId);
		}
		Displayer dis = findDisplayerPlayer(targetId);
		if (dis == null) {
			result.setStatus(Command.STATUS_FAIL);
			result.setVO(Command.TAG_ERR_MSG, new ErrorMsg(ErrorCode.ERR_ACTIVE_DONE, "已经达到最大祝福人数!"));
			return ;
		}
		String info = "送祝福成功,赠送你";
		String tp = "";
		switch(type) {
		case 1:
			if (!(player.consumeInAdvGlod(200) || player.consumeInGlod(200))) {
				throw new NoMoneyException("no money");
			}
			target.addGold(200);
			ItemService.addItem(player, 10598, 1, false);//浓情玫瑰
			ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
			ItemService.addItem(player, 10389, 2, false);//神石脆片
			info += "浓情玫瑰x1,巧克力玫瑰x1,神石碎片x2";
			tp = "2金";
			break;
		case 2:
			if (!(player.consumeInAdvGlod(100) || player.consumeInGlod(100))) {
				throw new NoMoneyException("no money");
			}
			target.addGold(100);
			ItemService.addItem(player, 10598, 1, false);//浓情玫瑰
			ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
			ItemService.addItem(player, 10389, 1, false);//神石脆片
			info += "浓情玫瑰x1,巧克力玫瑰x1,,神石碎片x1";
			tp = "1金";
			break;
		case 3:
			if (!(player.consumeInCopper(1000 * 1000))) {
				throw new NoMoneyException("no money");
			}
			target.addCopper(1000 * 1000);
			ItemService.addItem(player, 10598, 1, false);//浓情玫瑰
			ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
			info += "浓情玫瑰x1,巧克力玫瑰x1";
			tp = "1000银";
			break;
		case 4:
			if (!(player.consumeInCopper(500 * 1000))) {
				throw new NoMoneyException("no money");
			}
			target.addCopper(500 * 1000);
			ItemService.addItem(player, 10598, 1, false);//浓情玫瑰
			ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
			info += "浓情玫瑰x1,巧克力玫瑰x1";
			tp = "500银";
			break;
		case 5:
			if (!(player.consumeInCopper(100 * 1000))) {
				throw new NoMoneyException("no money");
			}
			target.addCopper(100 * 1000);
			ItemService.addItem(player, 10599, 1, false);//巧克力玫瑰
			info += "巧克力玫瑰x1";
			tp = "100银";
			break;
		}
		removeWish(player, targetId);
		ChatService.sendMessageSystem(targetId, player.getName()+"给你送了祝福，收获"+tp);
		result.setVO("info", info);
		result.setStatus("info");
		logger.info(LoggerHelper.ZONE_ID+"|loveDay|"+"sendWishes|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+targetId+"|"+type);
		
	}
	
	public static ErrorMsg forge(Player player) throws NoMoneyException {
		PlayerItem pi = null;
		if (player.getSex() == 1) {
			pi = ItemService.findPlayerItem(player,10598);
		} else {
			pi = ItemService.findPlayerItem(player,10599);
		}
		if (pi == null || pi.getAmount() < 99) {
			return new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "你身上没有玫瑰，或者数量不足");
		}
		PlayerItem qingrenfu = ItemService.findPlayerItem(player,10601);
		if (qingrenfu == null || qingrenfu.getAmount() <= 0) {
			return  new ErrorMsg(ErrorCode.ERR_ACTIVE_INVALID, "没有情人符");
		}
		if (!player.consumeInCopper(1000 * 1000)) {
			throw new NoMoneyException("No money");
		}
		ErrorMsg msg = ItemService.releasePlayerItem(player, pi, 99, true);
		if (msg.code != ErrorCode.SUCC) {
			 return msg;
		}
		msg = ItemService.releasePlayerItem(player, qingrenfu, 1, true);
		if (msg.code != ErrorCode.SUCC) {
			 return msg;
		}
		ItemService.addItem(player, 10602, 1, false);
		logger.info(LoggerHelper.ZONE_ID+"|loveDay|"+"forge|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|");
		ChatService.sayAll(player, "恭喜"+player.getName()+"获得情人手镯", ChatService.CHAT_WORLD);
		return Constants.SUCC;
	}
}
