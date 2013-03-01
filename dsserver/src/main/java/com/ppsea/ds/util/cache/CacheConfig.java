package com.ppsea.ds.util.cache;

public class CacheConfig {
	public String name;
    public int size;
    public int timeout;
    public int recycleRate;
    public String recycleHandler;
    public boolean recycleByAccess;
    public int checkInterval;
    public boolean forceClean;

    public CacheConfig()
    {
    	name = "";
    	timeout = 86400;
    	recycleRate = 30;
    	recycleHandler = null;
    	recycleByAccess = true;
    	checkInterval = 60;
    	forceClean = false;
    }

    public CacheConfig(String name,
    		int size,
    		int timeout,
    		int recycleRate,
    		String recycleHandler,
    		boolean recycleByAccess,
    		int checkInterval)
    {
    	this.name = name;
    	this.size = size;
    	this.timeout = timeout;
    	this.recycleRate = recycleRate;
    	this.recycleHandler = recycleHandler;
    	this.recycleByAccess = recycleByAccess;
    	this.checkInterval = checkInterval;
    	this.forceClean = false;
    }
    public CacheConfig(String name,
    		int size,
    		int timeout,
    		int recycleRate,
    		String recycleHandler,
    		boolean recycleByAccess,
    		int checkInterval,
    		boolean forceClean)
    {
    	this.name = name;
    	this.size = size;
    	this.timeout = timeout;
    	this.recycleRate = recycleRate;
    	this.recycleHandler = recycleHandler;
    	this.recycleByAccess = recycleByAccess;
    	this.checkInterval = checkInterval;
    	this.forceClean = forceClean;
    }

    public String toString()
    {
		StringBuilder sb = new StringBuilder();
		sb.append("[Cache ").append(name).append(" :");
		sb.append(size).append(" ,");
		sb.append(timeout).append(" ,");
		sb.append(recycleRate).append(" ,");
		sb.append(recycleByAccess).append(" ,");
		sb.append(forceClean).append(" ,");
		sb.append((recycleHandler==null?"null":recycleHandler)).append("]");
		return sb.toString();	    	
    }
}
