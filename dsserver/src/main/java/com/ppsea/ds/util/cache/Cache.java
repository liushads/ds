package com.ppsea.ds.util.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface Cache<K, V>
{
	public static final int recycle_by_update = 0;
	public static final int recycle_by_access = 1;

	boolean containsKey(K key);

	V get(K key);
	/**
	 * 如果cache存在，返回之前的数据
	 * 如果不存在，返回null
	 */
	V set(K key, V value);

	V remove(K key);
	Set<K> keySet();

	int size();
	void clear();
	int cleanUp();

	int getQueryCount();
	int getHitCount();

	Iterator<Map.Entry<K, V>> iterator();
}
