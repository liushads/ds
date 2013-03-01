package com.ppsea.ds.command.admin;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class AdminCmd {
	protected AdminRequest adminReq;
	public abstract boolean isResponsible(AdminRequest req);
	public abstract JSONObject process()  throws JSONException;
	public abstract String desc();
}
