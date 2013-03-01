package com.ppsea.ds.util.cache;

public final class CacheEntry<K, V>
{
	K	key;
	V	value;
	long t;
	CacheEntry<K, V>	next;
	CacheEntry<K, V>	prev;

	CacheEntry(K k, V v)
	{
		this(k, v, System.currentTimeMillis());
	}

	CacheEntry(K k, V v, long t)
	{
		this.key = k;
		this.value = v;
		this.t = t;
	}
	
	public K getKey()
	{
		return key;
	}

	public V getValue()
	{
		return value;
	}

	public void setValue(V value)
	{
		t = System.currentTimeMillis();
		this.value = value;
	}

	public long getTime()
	{
		return t;
	}

	public void setTime(long t)
	{
		this.t = t;
	}
}
