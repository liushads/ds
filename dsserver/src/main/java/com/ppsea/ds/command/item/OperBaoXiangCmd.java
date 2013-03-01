package com.ppsea.ds.command.item;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ppsea.ds.command.BaseCmd;
import com.ppsea.ds.command.CommandResult;
import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.ItemDecompose;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.exception.PpseaException;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class OperBaoXiangCmd extends BaseCmd {
	@Override
	protected CommandResult done(Player player, String[] ps)
			throws PpseaException {
		List<Item> list=new LinkedList<Item>();	
		
		// TODO Auto-generated method stub
		CommandResult result = new CommandResult(STATUS_SUCC);
		int itemid=Integer.parseInt(ps[0]);
		PlayerItem playerItem=ItemService.findPlayerItem(player, itemid);
		if (playerItem==null) {
			setResult(result,STATUS_FAIL,ErrorCode.ITEM_ERR_BASE,"你没有该道具");
			return result;
		}
		int biaoshi=1;
		list.clear();
		Item item=new Item();
		int ack=0;
		Random ran = new Random();
		int num=ran.nextInt(1000);
		if (itemid==10526) {
			PlayerItem playerItem2=ItemService.findPlayerItem(player, 10529);
			if (playerItem2==null) {
				setResult(result,STATUS_FAIL,ErrorCode.ITEM_ERR_BASE,"你没有普通宝箱钥匙");
				return result;
			}
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
			
			if (ack!=10347) {
				item=ItemMG.instance.getItem(10347);
				list.add(item);
			}
			if (ack!=10402) {
				item=ItemMG.instance.getItem(10402);
				list.add(item);
			}
			if (ack!=10462) {
				item=ItemMG.instance.getItem(10462);
				list.add(item);
			}
			if (ack!=10386) {
				item=ItemMG.instance.getItem(10386);
				list.add(item);
			}
			if (ack!=10475) {
				item=ItemMG.instance.getItem(10475);
				list.add(item);
			}
			if (ack!=10403) {
				item=ItemMG.instance.getItem(10403);
				list.add(item);
			}
			if (ack!=10536) {
				item=ItemMG.instance.getItem(10536);
				list.add(item);
			}
			if (ack!=10272) {
				item=ItemMG.instance.getItem(10272);
				list.add(item);
			}
			if (ack!=10407) {
				item=ItemMG.instance.getItem(10407);
				list.add(item);
			}
			item=ItemMG.instance.getItem(ack);
			ErrorMsg msg=ItemService.releasePlayerItem(player, playerItem, true);
			if(msg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,msg);
				return result;
			}
			ErrorMsg errorMsg=ItemService.releasePlayerItem(player, playerItem2, true);
			if(errorMsg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,errorMsg);
				return result;
			}
			biaoshi=1;
			
		}
		if (itemid==10527) {
			PlayerItem playerItem2=ItemService.findPlayerItem(player, 10530);
			if (playerItem2==null) {
				setResult(result,STATUS_FAIL,ErrorCode.ITEM_ERR_BASE,"你没有白银宝箱钥匙");
				return result;
			}
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
			
			if (ack!=10346) {
				item=ItemMG.instance.getItem(10346);
				list.add(item);
			}
			if (ack!=36) {
				item=ItemMG.instance.getItem(36);
				list.add(item);
			}
			if (ack!=10477) {
				item=ItemMG.instance.getItem(10477);
				list.add(item);
			}
			if (ack!=60) {
				item=ItemMG.instance.getItem(60);
				list.add(item);
			}
			if (ack!=10532) {
				item=ItemMG.instance.getItem(10532);
				list.add(item);
			}
			if (ack!=31) {
				item=ItemMG.instance.getItem(31);
				list.add(item);
			}
			if (ack!=10504) {
				item=ItemMG.instance.getItem(10504);
				list.add(item);
			}
			if (ack!=10272) {
				item=ItemMG.instance.getItem(10272);
				list.add(item);
			}
			if (ack!=10407) {
				item=ItemMG.instance.getItem(10407);
				list.add(item);
			}
			item=ItemMG.instance.getItem(ack);
			ErrorMsg msg=ItemService.releasePlayerItem(player, playerItem, true);
			if(msg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,msg);
				return result;
			}
			ErrorMsg errorMsg=ItemService.releasePlayerItem(player, playerItem2, true);
			if(errorMsg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,errorMsg);
				return result;
			}
			biaoshi=2;
		}
		if (itemid==10528) {
			PlayerItem playerItem2=ItemService.findPlayerItem(player, 10531);
			if (playerItem2==null) {
				setResult(result,STATUS_FAIL,ErrorCode.ITEM_ERR_BASE,"你没有黄金宝箱钥匙");
				return result;
			}
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
			
			if (ack!=10513) {
				item=ItemMG.instance.getItem(10513);
				list.add(item);
			}
			if (ack!=10525) {
				item=ItemMG.instance.getItem(10525);
				list.add(item);
			}
			if (ack!=10428) {
				item=ItemMG.instance.getItem(10428);
				list.add(item);
			}
			if (ack!=10457) {
				item=ItemMG.instance.getItem(10457);
				list.add(item);
			}
			if (ack!=10511) {
				item=ItemMG.instance.getItem(10511);
				list.add(item);
			}
			if (ack!=36) {
				item=ItemMG.instance.getItem(36);
				list.add(item);
			}
			if (ack!=10272) {
				item=ItemMG.instance.getItem(10272);
				list.add(item);
			}
			if (ack!=10407) {
				item=ItemMG.instance.getItem(10407);
				list.add(item);
			}
			if (ack!=10476) {
				item=ItemMG.instance.getItem(10476);
				list.add(item);
			}
			item=ItemMG.instance.getItem(ack);
			ErrorMsg msg=ItemService.releasePlayerItem(player, playerItem, true);
			if(msg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,msg);
				return result;
			}
			ErrorMsg errorMsg=ItemService.releasePlayerItem(player, playerItem2, true);
			if(errorMsg.code != ErrorCode.SUCC){
				setResult(result,STATUS_FAIL,errorMsg);
				return result;
			}
			biaoshi=3;
		}
		player.setOpenxiang(true);
		result.setVO(TAG_PLAYER_ITEM, itemid);
		result.setVO(TAG_NUM, biaoshi);
		result.setVO(TAG_ITEMS, list);
		result.setVO(TAG_ITEM, item);
		return result;
	}
}
