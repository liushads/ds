package com.ppsea.ds.command.admin;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.data.Constants;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;
//import com.ppsea.ds.service.MemberService;
//import com.ppsea.ds.service.PromotionService;
//import com.tencent.wapgame.netgame.NetGameApp;

public class AddGoldCmd extends AdminCmd{
	private static final Logger log = Logger.getLogger(AddGoldCmd.class);
	
	@Override
	public boolean isResponsible(AdminRequest req) {
		if(req.cmd.equals("addGold") && req.param.size() > 0){
			adminReq =  req;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject process() throws JSONException {
		JSONObject json = new JSONObject();
		try{
			int pid = Integer.parseInt(adminReq.param.get(0));
			int gold= Integer.parseInt(adminReq.param.get(1));
			log.info("pid="+pid+"|gold="+gold);
			if(pid==0){
				json.put("result", "false");
				return json;				
			}
			Player player = PlayerMG.instance.getPlayerSimple(pid);
			String payType = "";
			if (adminReq.param.size()>2){
				log.info("pid="+pid+"|gold="+gold+"|userid="+adminReq.param.get(2));
				String uid = adminReq.param.get(2);
//				String encodeUid = NetGameApp.getEncodeUid(uid);
				log.info("pid="+pid+"|gold="+gold+"|uid="+adminReq.param.get(2)+"|encodeUid="+player.getUserId());
				if(!player.getUserId().equals(uid)){
					json.put("result", "false");
					return json;
				}
				payType = adminReq.param.get(3);
				log.info("pid="+pid+"|gold="+gold+"|userid="+adminReq.param.get(2)+"|paytype="+payType);
			}
			
			//log.warn("pid"+pid+"-gold:"+gold+"-userId:"+adminReq.param.get(2)+"-payType:"+payType);
			gold = gold*100;
			
			String name = Constants.GOLD_NAME;
			Player p = PlayerMG.instance.getPlayerFromCache(player.getId());
//			int memberPoints = 0;
			if(Constants.PAY_QB.equals(payType)){
				player.addAdvGold(gold);
				name  = Constants.ADV_GOLD_NAME;
//				memberPoints = gold * MemberService.M_POINT_BASE_QB/100;
			}
			else if(Constants.PAY_SHENZHOUFU.equals(payType)) {
				player.addAdvGold(gold);
				name  = Constants.ADV_GOLD_NAME;
//				memberPoints = gold * MemberService.M_POINT_BASE_SHENZHOUDU/100;
			}
			else if(Constants.PAY_SMS.equals(payType)) {
				player.addGold(gold);
			}
			else if(Constants.PAY_LBS.equals(payType)) {
				player.addGold(gold);
			}
			else if(Constants.PAY_WAP.equals(payType)) {
				player.addGold(gold);
			}	
			else{
				name  = Constants.ADV_GOLD_NAME;
				player.addAdvGold(gold);
			}
//			if (memberPoints > 0) {
//				//MemberService.addMemberPointValue(player, memberPoints, true);
//				player.addRewardPoints(memberPoints);
//			}
			if (p == null) {
				
				DBService.commitNoCacheUpdate(player);
			} else {
				
				DBService.commit(player);
			}
//			try {
//				PromotionService.playerPrepaid(player, gold);					
//			} catch (Exception e) {
//				log.error("add gold to promote player failed.", e);
//			}
			// 发送消息
			ChatService.sendMessageSystem(player.getId(), gold/100 + name+"已充入您的帐户！", false);
			json.put("result", "true");
		}
		catch(Exception e){
			json.put("result", "false");
			log.error("金币支付失败 addGoldFailed.",e);
		}
		return json;
	}

	@Override
	public String desc() {
		return "addMoney payerId money: addMoney for player";
	}
	
}
