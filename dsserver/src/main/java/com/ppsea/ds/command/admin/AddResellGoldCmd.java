package com.ppsea.ds.command.admin;

import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerResell;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;

public class AddResellGoldCmd extends AdminCmd {

	@Override
	public boolean isResponsible(AdminRequest req) {
		if(req.cmd.equals("addResellGold") && req.param.size() > 0){
			adminReq =  req;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject process() throws JSONException {
		JSONObject json = new JSONObject();
		int pid = Integer.parseInt(adminReq.param.get(0));
		int num = Integer.parseInt(adminReq.param.get(1));
		
		try {
			Player player = PlayerMG.instance.getPlayer(pid,true);
			if(player != null){
				PlayerResell playerResell = player.getPlayerResell();
				int oldResellGold = playerResell.getResellGold();
				player.getPlayerResell().setResellGold(oldResellGold+num);
				//DBService.commit(player);
				DBService.commit(playerResell);
				ChatService.sendMessageSystem(player.getId(), "系统赠送了"+num+"个倒卖币!", false);
				json.put("result", "ok");
				json.put("msg", "倒卖币+"+num);
			}
			else{
				json.put("result", "erorr");
				json.put("msg", "player不存在");
			}
			return json;
		}
		catch(Exception e){
			json.put("result", "erorr");
			json.put("errid", e.getMessage());
			return json;
		}
	}

	@Override
	public String desc() {
		return "addResellGold|playerId|num: addResellGold for player";
		}

}
