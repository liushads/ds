/*
 * 
 */
package com.ppsea.ds.command.admin;

import org.json.JSONException;
import org.json.JSONObject;

import com.ppsea.ds.manager.PlayerMG;

/**
 * @author Administrator
 * @date 2010-10-29
 */
public class ReloadPlayerCmd extends AdminCmd {

	/**
	 * @see com.ppsea.jh.command.admin.AdminCmd#desc()
	 */
	@Override
	public String desc() {
		// 
		return "reload player";
	}

	/**
	 * @see com.ppsea.jh.command.admin.AdminCmd#isResponsible(com.ppsea.jh.command.admin.AdminRequest)
	 */
	@Override
	public boolean isResponsible(AdminRequest req) {
		if (req.cmd.equals("reloadPlayer") && req.param.size() > 0) {
			adminReq = req;
			return true;
		}
		return false;
	}

	/**
	 * @see com.ppsea.jh.command.admin.AdminCmd#process()
	 */
	@Override
	public JSONObject process() throws JSONException {
		
		JSONObject json = new JSONObject();

		try {
			int pid = Integer.parseInt(adminReq.param.get(0));
			PlayerMG.instance.loadPlayer(pid, true);
			
        } catch (Exception e) {
	        json.put("result", "failed");
        }
		json.put("result", "ok");
		
		return json;
	}

}
