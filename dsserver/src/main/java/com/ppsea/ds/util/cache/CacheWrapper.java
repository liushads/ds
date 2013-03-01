package com.ppsea.ds.util.cache;

@SuppressWarnings("unchecked")
public final class CacheWrapper
{
	private Cache cache;

	private long lastCheckTime = System.currentTimeMillis();
	private long checkInterval = 1000 * 60 * 60;

	public CacheWrapper(Cache cache, long checkInterval)
	{
		this.cache = cache;
		this.checkInterval = checkInterval;
		if(this.checkInterval <= 0)
			this.checkInterval = Long.MAX_VALUE;
	}
	
	public Cache getCache()
	{
		return cache;
	}
	
	void setCache(Cache c)
	{
		cache = c;
	}

	public boolean cleanUp()
	{
		if (System.currentTimeMillis() - this.lastCheckTime < this.checkInterval)
		{
			return false;
		}
		try{
			cache.cleanUp();
		}finally{
			lastCheckTime = System.currentTimeMillis();
		}
		return true;
	}
}
