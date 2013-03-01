package com.ppsea.ds.util.cache;

import java.util.Iterator;
import java.util.Map;

final class CacheIterator<K, V> implements Iterator<Map.Entry<K, V>>
{
	private CacheImp<K, V> fc;
	private CacheEntry<K, V> next;

	private static class Entry<Key, Value> implements Map.Entry<Key, Value>
	{
		private Key k;
		private Value v;
		
		public Entry(Key k, Value v)
		{
			this.k = k;
			this.v = v;
		}

		public Key getKey()
		{
			return k;
		}

		public Value getValue()
		{
			return v;
		}

		public Value setValue(Value v)
		{
			Value o = this.v;
			this.v = v;
			return o;
		}
	}
	
	public CacheIterator(CacheImp<K, V> fc)
	{
		this.fc = fc;
		fc.l.lock();
		try{
			this.next = fc.nl.head;
		}finally{
			fc.l.unlock();
		}
	}

	public boolean hasNext()
	{
		return next != null;
	}
	
	public CacheEntry<K, V> nextNode()
	{
		CacheEntry<K, V> n = next;
		fc.l.lock();
		try{
			next = next.next;
		}finally{
			fc.l.unlock();
		}
		return n;
	}

	public Entry<K, V> next()
	{
		CacheEntry<K, V> n = nextNode();
		return new Entry<K, V>(n.key, n.value);
	}

	public void remove()
	{
		fc.remove(next);
	}
}
