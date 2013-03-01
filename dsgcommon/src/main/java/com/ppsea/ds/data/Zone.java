package com.ppsea.ds.data;


public class Zone extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7506164683423686373L;
	private String id;
	private String name;
	private String desc;
	private String host;
	private Integer port;
	private Integer adminPort;
	private Integer sortId;
	private Integer onlinePlayer;
	
	private String proxyIp;
	private Integer proxyPort;
	private String proxyKey;
	
	private Integer currOnlineUserNum;
	private Integer currCachedUserNum;
	
	private String descDefault;
	private Integer descFullNum;
	private Integer descBusyNum;
	
	//note info
	private String noteInfo;
	
	/**
     * @return the noteInfo
     */
    public String getNoteInfo() {
    	return noteInfo;
    }
	/**
     * @param noteInfo the noteInfo to set
     */
    public void setNoteInfo(String noteInfo) {
    	this.noteInfo = noteInfo;
    }
	public String getDescDefault() {
		return descDefault;
	}
	public void setDescDefault(String descDefault) {
		this.descDefault = descDefault;
	}
	public Integer getDescFullNum() {
		return descFullNum;
	}
	public void setDescFullNum(Integer descFullNum) {
		this.descFullNum = descFullNum;
	}
	public Integer getDescBusyNum() {
		return descBusyNum;
	}
	public void setDescBusyNum(Integer descBusyNum) {
		this.descBusyNum = descBusyNum;
	}
	public String getProxyKey() {
		return proxyKey;
	}
	public void setProxyKey(String proxyKey) {
		this.proxyKey = proxyKey;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProxyIp() {
		return proxyIp;
	}
	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}
	public Integer getProxyPort() {
		return proxyPort;
	}
	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}

	public Integer getSortId() {
		return sortId;
	}
	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}
	public Integer getOnlinePlayer() {
		return onlinePlayer;
	}
	public void setOnlinePlayer(Integer onlinePlayer) {
		this.onlinePlayer = onlinePlayer;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public Integer getAdminPort() {
		return adminPort;
	}
	public void setAdminPort(Integer adminPort) {
		this.adminPort = adminPort;
	}
	
	public String toPrint(){
		return name+"[id="+id+",sordId="+sortId+",online="+currOnlineUserNum+",cached=" + currCachedUserNum+"]";
	}
	public void setCurrOnlineUserNum(Integer currOnlineUserNum) {
		this.currOnlineUserNum = currOnlineUserNum;
	}
	public Integer getCurrOnlineUserNum() {
		return currOnlineUserNum;
	}
	public void setCurrCachedUserNum(Integer currCachedUserNum) {
		this.currCachedUserNum = currCachedUserNum;
	}
	public Integer getCurrCachedUserNum() {
		return currCachedUserNum;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
}