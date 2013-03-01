package com.ppsea.ds.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CommandResult implements Serializable  {
	private static final long serialVersionUID = 1L;

	public static String UNKNOWN = "UNKNOWN"; 
	private String status = UNKNOWN;
	private String name = UNKNOWN;
	private HashMap<String, Object> data = new HashMap<String, Object>();
	public static final String NULL = "null";
	
	private transient StringBuffer logMsg;
	private transient StringBuffer extMsg;
	private transient String playerName;
	
	public CommandResult(){}
	public CommandResult(String status){
		this.status = status;
	}
	//////////////////////////////////////////////////////////////////	
	public class ResultItem implements Serializable {
		private String name = "null";
		private String id = "null";
		public ResultItem(String n, String i) {
			name = n;
			id = i;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
	}
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 */
	public void clear() {
		status = "UNKNOWN";
		name = "UNKNOWN";
		data.clear();
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */
	public HashMap<String, Object> getData() {
		return data;
	}
	
	public void setData(HashMap<String, Object> data){
		this.data = data;
	}
	/**
	 * 
	 * @param label
	 * @param text
	 */
	public void setText(String label, Object id) {
		data.put(label, String.valueOf(id));
	}
	
	
	public void setVO(String label, Object vo){
		data.put(label, vo);
	}
	
	public Object getVO(String label){
		return data.get(label);
	}
	/**
	 * 
	 * @param label
	 * @param name
	 * @param id
	 */
	public void setHref(String label, String name, Object id) {
		data.put(label, new ResultItem(name, String.valueOf(id)));
	}
	/**
	 * 
	 * @param label
	 * @param name
	 * @param id
	 */
	public void setList(String label, String name, String id, String...items) {
		if (!data.containsKey(label)) {
			data.put(label, new ArrayList<ArrayList<ResultItem>>());
		}
		ArrayList<ResultItem> list = new ArrayList<ResultItem>();
		list.add(new ResultItem(name, id));
		List<String> ls = Arrays.asList(items);
		int size = ls.size();
		int i = 0;
		while (i < size) {
			if (i + 1 < size) {
				list.add(new ResultItem(ls.get(i), ls.get(i+1)));
			}
			i += 2;
		}
		((List)data.get(label)).add(list);
	}
	/**
	 * 
	 */
	public String toString() {
		Set<String> set = data.keySet();
		StringBuffer ret = new StringBuffer();
		ret.append("[");
		ret.append("name=" + this.name + ",");
		ret.append("stat=" + this.status + ",");
		for (String s : set)
		{
			if (s != null && data.get(s) != null) {
				ret.append(s + "=" + data.get(s) + ",");
			}
		}
		ret.append("]");
		return ret.toString();
	}
	/**
	 * @return the logMsg
	 */
	public StringBuffer getLogMsg() {
		return logMsg;
	}
	/**
	 * @param logMsg the logMsg to set
	 */
	public void setLogMsg(StringBuffer logMsg) {
		this.logMsg = logMsg;
	}

	public StringBuffer getExtMsg() {
		return extMsg;
	}
	public void setExtMsg(StringBuffer extMsg) {
		this.extMsg = extMsg;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPlayerName() {
		return playerName;
	}
}
