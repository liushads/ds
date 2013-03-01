package com.ppsea.ds.command.admin;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.GameServer;


public class TerminateCmd extends AdminCmd{
	private static final Logger log = Logger.getLogger(TerminateCmd.class);
	@Override
	public boolean isResponsible(AdminRequest req) {
		if(req.cmd.equals("exit")){
			adminReq = req;
			return true;
		}
		return false;
	}

	@Override
	public JSONObject process() throws JSONException {
		JSONObject json = new JSONObject();
		try
		{
			if (GameServer.terminated == false) {
	    		GameServer.terminated = true;
	    		json.put("result", "result:[start terminate]...");
	    		GameServer.doTerminate();
	    		log.debug("***********system exit************");
				System.exit(0);
			}
			else {
				json.put("result", "result:[terminate is running, please wait]");
			}
		}
		catch (Exception e){
			log.error("exception:", e);
			json.put("result", "result:[terminate exception]");
		}
     	return json;
	}

	@Override
	public String desc() {
		return "exit: terminate game server.";
	}
	
}
