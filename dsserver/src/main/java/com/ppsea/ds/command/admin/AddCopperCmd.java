package com.ppsea.ds.command.admin;

import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.DBService;

public class AddCopperCmd extends AdminCmd{
//	private static final Logger log = Logger.getLogger(AddCopperCmd.class);
	
	@Override
	public boolean isResponsible(AdminRequest req) {
		if(req.cmd.equals("addCopper") && req.param.size() > 0){
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
				player.addCopper(num);
				DBService.commit(player);
				ChatService.sendMessageSystem(player.getId(), "系统赠送了"+num+"个铜币!", false);
				json.put("result", "ok");
				json.put("msg", "铜贝+"+num);
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
		return "addItem|payerId|itemId|num: addItem for player";
	}
	
}
