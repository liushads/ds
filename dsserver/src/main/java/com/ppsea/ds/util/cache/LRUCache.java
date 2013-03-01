package com.ppsea.ds.util.cache;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unchecked")
public class LRUCache<K, V> implements Cache<K, V> {
	private ConcurrentHashMap<K, CacheEntry<K, V>> map = new ConcurrentHashMap<K, CacheEntry<K, V>>();
	EntryList<K, V> nl = new EntryList<K, V>();

	Lock l = new ReentrantLock();

	private AtomicInteger queryCount = new AtomicInteger();
	private AtomicInteger hitCount = new AtomicInteger();

	private int maxSize = 10000;
	private RecycleHandler<K, V> handler = null;

	public int getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(int max_size) {
		this.maxSize = max_size;
	}

	public LRUCache() {
	}
	public LRUCache(int maxSize) {
		this.maxSize = maxSize;
	}

	public LRUCache(int maxSize, RecycleHandler<K, V> handler) {
		this.maxSize = maxSize;
		this.handler = handler;
	}

	public LRUCache(int maxSize, String recycleHandler) {
		if (recycleHandler != null) {
			try {
				RecycleHandler<K, V> h = (RecycleHandler<K, V>) createHandler(recycleHandler);
				if (h != null)
					handler = h;
			} catch (Throwable e) {
			}
		}
	}

	public LRUCache(CacheConfig cfg) {
		this.maxSize = cfg.size;
		if (cfg.recycleHandler != null) {
			try {
				RecycleHandler<K, V> h = (RecycleHandler<K, V>) createHandler(cfg.recycleHandler);
				if (h != null)
					handler = h;
			} catch (Throwable e) {
			}
		}
	}

	private Object createHandler(String className) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Class c = Class.forName(className);
		if (c == null)
			return null;
		Constructor con = c.getConstructor();
		if (con == null)
			return null;
		return con.newInstance();
	}

	protected void setRecycleHander(RecycleHandler<K, V> h) {
		handler = h;
	}

	public V get(K key) {
		queryCount.incrementAndGet();
		l.lock();
		try {
			CacheEntry<K, V> n = map.get(key);
			if (n != null) {
				hitCount.incrementAndGet();
				nl.moveToTop(n);
				return n.value;
			}
		} finally {
			l.unlock();
		}
		return null;
	}

	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	public V set(K key, V value) {
		CacheEntry<K, V> n = new CacheEntry<K, V>(key, value);
		n = set(n);
		return n != null ? n.value : null;
	}
	public V put(K key, V value) {
		CacheEntry<K, V> n = new CacheEntry<K, V>(key, value);
		n = set(n);
		return n != null ? n.value : null;
	}

	private CacheEntry<K, V> set(CacheEntry<K, V> n) {
		l.lock();
		try {
			CacheEntry<K, V> old = map.put(n.key, n);
			nl.add(n);
			if (old != null) {
				nl.remove(old);
			} else {
				cleanOne(handler);				
			}
			return old;
		} finally {
			l.unlock();
		}
	}

	public void clear() {
		l.lock();
		try {
			map.clear();
			nl.clear();
		} finally {
			l.unlock();
		}
	}

	public V remove(K key) {
		l.lock();
		try {
			CacheEntry<K, V> n = map.remove(key);
			if (n != null) {
				nl.remove(n);
				return n.value;
			}
			return null;
		} finally {
			l.unlock();
		}
	}

	V remove(CacheEntry<K, V> n) {
		l.lock();
		try {
			CacheEntry<K, V> n1 = map.get(n.key);
			if (n1 == n) {
				map.remove(n.key);
				nl.remove(n);
				return n.value;
			}
			return null;
		} finally {
			l.unlock();
		}
	}

	public int size() {
		return map.size();
	}

	private void cleanOne(RecycleHandler<K, V> h) {
		try {
			int n = size();
			if (n > maxSize) {
				CacheEntry<K, V> node = nl.tail;
				if (node == null)
					return;
				if (h == null) {
					remove(node);
				} else if(h.done(node, false)) {
					remove(node);
				}
			}
		} catch (Exception e) {
		}
	}

	public int cleanUp() {
		return 0;
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public int getQueryCount() {
		return queryCount.get();
	}

	public int getHitCount() {
		return hitCount.get();
	}

	public Iterator<Entry<K, V>> iterator() {
		return new LRUCacheIterator<K, V>(this);
	}

	public static void main(String[] argv)
	{
		LRUCache<String,Integer> lm = new LRUCache<String,Integer>(4);
		lm.set("1", 1);
		lm.set("2", 2);
		lm.set("3", 3);
		lm.get("1");
		lm.set("4", 4);
		lm.set("5", 5);
		lm.set("2",2);
		lm.set("6", 6);

		Iterator<Entry<String,Integer>> ie = lm.iterator();
		while(ie.hasNext()) {
			Entry<String,Integer> e = ie.next();
			System.out.println(e.getKey()+":"+e.getValue());
		}
	}	

}
