package com.ppsea.ds.util.cache;

/**
 * 淘汰处理
 */
public interface RecycleHandler<K, V>
{
	/**
	 * @return	是否需要删除
	 */
	boolean done(CacheEntry<K, V> n, boolean isTimeout);
}
