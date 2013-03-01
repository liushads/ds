package com.ppsea.ds.command.item;

import java.util.Random;

import org.apache.log4j.Logger;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandRequest;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.NoMoneyException;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;
import com.ppsea.ds.service.PresentService;
import com.ppsea.ds.util.LoggerHelper;

public class HeavyTurnChestsCmd extends BaseCmd {
	private static Logger logger = Logger.getLogger("Reward");
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		CommandResult result = new CommandResult(STATUS_SUCC);
		int ack=0;
		String td=null;
		int itemid=Integer.valueOf(ps[0]);
		Item item=new Item();
		if (!player.isOpenxiang()) {
			CommandRequest newCmd = new CommandRequest();
			newCmd.setCmd("i_L");
			newCmd.setPid(player.getId().toString());
			newCmd.setPs(new String[]{"1"});
			result = callOtherCmd(newCmd);
			return result;
		}
		player.setOpenxiang(false);
		if (player.consumeInGlod(100)||player.consumeInAdvGlod(100)) {
		} 
		else {
			int playeritemid=Integer.valueOf(ps[1]);
			 item=ItemMG.instance.getItem(playeritemid);
			if (playeritemid==10407) {
				player.addCopper(100*1000);
				td="你金钱不够,恭喜你获得了【"+100+"银币】";
			}
			else if (playeritemid==10272) {
				player.addExp(1000*itemid);
				td="你金钱不够,恭喜你获得了【"+5000*itemid+"经验】";
			}
			else {
				ItemService.addItem(player, item, 1,false);
				td="你金钱不够,恭喜你获得了【"+item.getName()+"】";
			}
			result.setVO(TAG_TIQIU_DIANQIU, td);
			logger.info(LoggerHelper.ZONE_ID+"|baoxiang|O|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+item.getId()+"|"+ps[2]);
			return result;
		}
		int playeritemid = Integer.valueOf(ps[1]);
		item = ItemMG.instance.getItem(playeritemid);
		if (playeritemid == 10407) {
			player.addCopper(100* 1000);
			td = "恭喜你获得了【" + 100+"银币】";
		} else if (playeritemid == 10272) {
			player.addExp(5000 * itemid);
			td = "恭喜你获得了【" + 5000 * itemid + "经验】";
		} else {
			ItemService.addItem(player, item, 1, false);
			td = "恭喜你获得了【" + item.getName() + "】";
		}
		Random ran = new Random();
		int num=ran.nextInt(1000);
		if (itemid==1) {
			if (num<50) {
				ack=10347;
			}
			if (num>=50&&num<190) {
				ack=10402;
			}
			if (num>=190&&num<350) {
				ack=10462;
			}
			if (num>=350&&num<490) {
				ack=10386;
			}
			if (num>=490&&num<640) {
				ack=10475;
			}
			if (num>=640&&num<690) {
				ack=10403;
			}
			if (num>=690&&num<700) {
				ack=10536;
			}
			if (num>=700&&num<860) {
				ack=10272;
			}
			if (num>=860&&num<1000) {
				ack=10407;
			}
			 item=ItemMG.instance.getItem(ack);
		}
		if (itemid==2) {
			if (num<150) {
				ack=10346;
			}
			if (num>=150&&num<310) {
				ack=36;
			}
			if (num>=310&&num<360) {
				ack=10477;
			}
			if (num>=360&&num<510) {
				ack=60;
			}
			if (num>=510&&num<530) {
				ack=10532;
			}
			if (num>=530&&num<630) {
				ack=31;
			}
			if (num>=630&&num<780) {
				ack=10504;
			}
			if (num>=780&&num<880) {
				ack=10272;
			}
			if (num>=880&&num<1000) {
				ack=10407;
			}
			item=ItemMG.instance.getItem(ack);
		}
		if (itemid==3) {
			if (num<5) {
				ack=10513;
			}
			if (num>=5&&num<155) {
				ack=10525;
			}
			if (num>=155&&num<165) {
				ack=10428;
			}
			if (num>=165&&num<285) {
				ack=10457;
			}
			if (num>=285&&num<435) {
				ack=10511;
			}
			if (num>=435&&num<585) {
				ack=36;
			}
			if (num>=585&&num<705) {
				ack=10476;
			}
			if (num>=705&&num<855) {
				ack=10272;
			}
			if (num>=855&&num<1000) {
				ack=10407;
			}
			item=ItemMG.instance.getItem(ack);
		}
		if (ack==10407) {
			player.addCopper(100*1000);
			td=td+",【"+100+"银币】";
		}
		else if (ack==10272) {
			player.addExp(5000*itemid);
			td=td+",【"+5000*itemid+"经验】";
		}
		else {
			ItemService.addItem(player, item, 1,false);
			td=td+",【"+item.getName()+"】";
		}
		result.setVO(TAG_TIQIU_DIANQIU, td);
		logger.info(LoggerHelper.ZONE_ID+"|baoxiang|O|"+player.getUserId()+"|"+player.getId()+"|"+player.getName()+"|"+player.getLevel()+"|"+item.getId()+"|"+ps[2]+"|"+playeritemid);
		return result;
	}

}
