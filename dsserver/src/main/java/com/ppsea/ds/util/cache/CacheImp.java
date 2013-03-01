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
public class CacheImp<K, V> implements Cache<K, V> {
	private ConcurrentHashMap<K, CacheEntry<K, V>> map = new ConcurrentHashMap<K, CacheEntry<K, V>>();
	EntryList<K, V> nl = new EntryList<K, V>();

	Lock l = new ReentrantLock();

	private AtomicInteger queryCount = new AtomicInteger();
	private AtomicInteger hitCount = new AtomicInteger();

	private long timeout = 60 * 60 * 1000;
	private int maxSize = 10000;
	private double recycleRate = 0.3;
	private boolean force_clean = false; //
	private boolean recycleByAccess = true;

	private RecycleHandler<K, V> handler = null;

	public CacheImp(long timeout, int maxSize, int recycleRate) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
	}
	public CacheImp(long timeout, int maxSize, int recycleRate,boolean force_clean,boolean recycleByAccess) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
		this.force_clean = force_clean;
		this.recycleByAccess = recycleByAccess;
	}

	public CacheImp(long timeout, int maxSize, int recycleRate, RecycleHandler<K, V> handler) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
		this.handler = handler;
	}
	public CacheImp(long timeout, int maxSize, int recycleRate,boolean force_clean,boolean recycleByAccess, RecycleHandler<K, V> handler) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
		this.handler = handler;
		this.force_clean = force_clean;
		this.recycleByAccess = recycleByAccess;
	}

	public CacheImp(long timeout, int maxSize, int recycleRate, String recycleHandler) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
		if (recycleHandler != null) {
			try {
				RecycleHandler<K, V> h = (RecycleHandler<K, V>) createHandler(recycleHandler);
				if (h != null)
					handler = h;
			} catch (Throwable e) {
			}
		}
	}
	public CacheImp(long timeout, int maxSize, int recycleRate,boolean force_clean,boolean recycleByAccess, String recycleHandler) {
		this.timeout = ((long) timeout) * 1000;
		this.maxSize = maxSize;
		this.recycleRate = ((double) recycleRate) / 100;
		this.force_clean = force_clean;
		this.recycleByAccess = recycleByAccess;
		if (recycleHandler != null) {
			try {
				RecycleHandler<K, V> h = (RecycleHandler<K, V>) createHandler(recycleHandler);
				if (h != null)
					handler = h;
			} catch (Throwable e) {
			}
		}
	}

	public CacheImp(CacheConfig cfg) {
		this.timeout = ((long) cfg.timeout) * 1000;
		this.maxSize = cfg.size;
		this.recycleRate = ((double) cfg.recycleRate) / 100;
		this.force_clean = cfg.forceClean;
		this.recycleByAccess = cfg.recycleByAccess;
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
				if(recycleByAccess)
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

	private CacheEntry<K, V> set(CacheEntry<K, V> n) {
		l.lock();
		try {
			CacheEntry<K, V> old = map.put(n.key, n);
			nl.add(n);
			if (old != null)
				nl.remove(old);
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

	public int cleanUp(RecycleHandler<K, V> h) {
		// recycle_by_update
/*
		int c1 = 0;
		try {
			int newSize = (int) (((double) size()) * (1.0 - recycleRate));
			if (timeout > 0) {
				Set<Map.Entry<K, CacheEntry<K, V>>> entrySet = map.entrySet();
				Iterator<Map.Entry<K, CacheEntry<K, V>>> it = entrySet.iterator();
				while (it.hasNext() && size() >= newSize) {
					Map.Entry<K, CacheEntry<K, V>> n = it.next();
					CacheEntry<K, V> node = n.getValue();
					long now = System.currentTimeMillis();
					if (now - node.t >= timeout) {
						if (h != null && h.done(node, true)) {
							remove(node);
						}
						++c1;
					} 
				}
			}
		} catch (Exception e) {
		}
*/
		// recycle_by_access
		int c1 = 0;
		int c2 = 0;
		try {
			if(force_clean) {
				for (;;) {
					CacheEntry<K, V> node = nl.tail;
					if (node == null)
						break;
					long now = System.currentTimeMillis();
					if (now - node.t < timeout)
						break;
					if (h == null) {
						remove(node);
						c1++;						
					} else if(h.done(node, true)) {
						remove(node);
						c1++;
					}
				}
			}
			int n = size();
			if (n > maxSize) {
				final int newSize = (int) (((double) maxSize) * (1.0 - recycleRate));
				for (; n - c2 > newSize; ++c2) {
					CacheEntry<K, V> node = nl.tail;
					if (node == null)
						break;
					if (h == null) {
						remove(node);
					} else if(h.done(node, false)) {
						remove(node);
					}
				}
			}
		} catch (Exception e) {
		}
		return c1+c2;
	}

	public int cleanUp() {
		return cleanUp(handler);
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
		return new CacheIterator<K, V>(this);
	}

}
