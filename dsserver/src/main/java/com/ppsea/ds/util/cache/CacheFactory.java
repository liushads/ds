package com.ppsea.ds.util.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cache工厂
 */
@SuppressWarnings("unchecked")
public class CacheFactory
{
	private static Map<String, CacheWrapper> caches = new ConcurrentHashMap<String, CacheWrapper>();

	private static Thread cleanUpThread = new Thread()
	{
		public void run()
		{
			cleanUp();
		}
	};

	public static void init(ArrayList<CacheConfig> cfgs) throws Exception
	{
		for(int i=0;i<cfgs.size();i++) {
			CacheConfig cfg = cfgs.get(i);
			Cache c = new CacheProxy();
			caches.put(cfg.name, new CacheWrapper(c, cfg.checkInterval));
		}
		for(int i=0;i<cfgs.size();i++) {
			CacheConfig cfg = cfgs.get(i);
			Cache c = new CacheImp(cfg);
			CacheWrapper cw = caches.get(cfg.name);
			if(cw == null) {
				throw new Exception("cache init fail: get "+cfg.name+" is null");
			}
			CacheProxy co = (CacheProxy) cw.getCache();
			co.c = c;
			cw.setCache(c);		// 替换cache
		}

		cleanUpThread.start();
	}

	public static <K, T> Cache<K, T> getCache(String name)
	{
		CacheWrapper cw = caches.get(name);
		if(cw == null)
			return null;
		return cw.getCache();
	}

	private static void cleanUp()
	{
		while (true)
		{
			try
			{
				Collection<CacheWrapper> vs = caches.values();
				for (CacheWrapper o : vs)
				{
					try{
						o.cleanUp();
					}catch(Exception e){
					}
				}
				Thread.sleep(10000);
			}
			catch (Exception e)
			{
			}
		}
	}

	/**
	 * @return String[]
	 */
	public static String[] getAllCacheName()
	{
		return caches.keySet().toArray(new String[0]);
	}
	
}
