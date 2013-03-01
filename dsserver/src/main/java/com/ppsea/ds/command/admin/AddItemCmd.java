package com.ppsea.ds.command.admin;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.data.ErrorMsg;
import com.ppsea.ds.data.model.Item;
import com.ppsea.ds.data.model.Player;
import com.ppsea.ds.data.model.PlayerItem;
import com.ppsea.ds.manager.ItemMG;
import com.ppsea.ds.manager.PlayerMG;
import com.ppsea.ds.service.ChatService;
import com.ppsea.ds.service.ErrorCode;
import com.ppsea.ds.service.ItemService;

public class AddItemCmd extends AdminCmd{
	private static final Logger log = Logger.getLogger(AddItemCmd.class);
	
	@Override
	public boolean isResponsible(AdminRequest req) {
		if(req.cmd.equals("addItem") && req.param.size() > 0){
			adminReq =  req;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject process() throws JSONException {
		JSONObject json = new JSONObject();
		int pid = Integer.parseInt(adminReq.param.get(0));
		int itemId= Integer.parseInt(adminReq.param.get(1));
		int num = Integer.parseInt(adminReq.param.get(2));
		
		try {
			Player player = PlayerMG.instance.getPlayer(pid,true);
			ErrorMsg ret = null;
			if(adminReq.param.size()==3){
				log.error("additem begin");
				ret = ItemService.addItem(player, itemId, num, false);
				log.error("additem end" + ret.toString());
			}
			else{
				//TODO 给用户增加已经强化过的道具 和 升星过的道具
			}
			if(ret.code == ErrorCode.SUCC ){
				Item item = ((PlayerItem)ret.obj).getItem();
				ChatService.sendMessageSystem(player.getId(), "系统赠送了"+num+"个【"+item.getName()+ "】给你！");
				json.put("result", "ok");
				json.put("msg", item.getName()+"x"+num);
			}
			else{
				json.put("result", "erorr");
				json.put("msg", ret);
			}
			return json;
		}
		catch(Exception e){
			json.put("result", "erorr");
			json.put("errid", e.getMessage());
			log.error("additem error:" +e.getMessage(),e);
			return json;
		}
	}

	@Override
	public String desc() {
		return "addItem|payerId|itemId|num: addItem for player";
	}
	
}
