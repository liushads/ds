package com.ppsea.ds.command;

import java.io.Serializable;

public class CommandRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cmd;
	private String pid;
	private String uid;
	private String sid;
	private String sSid;
	private String[] ps;
	private String proxyMd5Key;
	private String realIp;
	private String verifyKey = null;
	private String verifyStr = null;
	
	public CommandRequest(){}
	
	public CommandRequest(String cmd) {
		super();
		this.cmd = cmd;
	}
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String[] getPs() {
		return ps;
	}
	public void setPs(String[] ps) {
		this.ps = ps;
	}	

	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("cmd=").append(cmd)
			.append("&uid=").append(uid)
			.append("&pid=").append(pid)
			.append("&key=").append(proxyMd5Key)
			.append("&ps=");
		if(ps == null || ps.length == 0){
			sb.append("null");
		}
		else{
			for(int i=0; i<ps.length; i++){
				sb.append(ps[i]+"/");
			}
		}
		
		return sb.toString();
	}

	public void setProxyMd5Key(String proxyMd5Key) {
		this.proxyMd5Key = proxyMd5Key;
	}

	public String getProxyMd5Key() {
		return proxyMd5Key;
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getVerifyKey() {
		return verifyKey;
	}

	public void setVerifyKey(String verifyKey) {
		this.verifyKey = verifyKey;
	}

	public String getVerifyStr() {
		return verifyStr;
	}

	public void setVerifyStr(String verifyStr) {
		this.verifyStr = verifyStr;
	}
	
	/**
     * @return the sSid
     */
    public String getSSid() {
    	return sSid;
    }

	/**
     * @param sid the sSid to set
     */
    public void setSSid(String sid) {
    	sSid = sid;
    }
	
}
