package com.ppsea.ds.util.cache;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简单cache封装，线程安全
 * 只支持简单对象，对Value里存在类似map/list之类数据时不保证安全
 */
public class SimpleThreadSafeCache<K, V>
{
	private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();	// 锁
	private final Lock r = rwLock.readLock();				// 读锁
	private final Lock w = rwLock.writeLock();				// 写锁
	private Cache<K,V> cache = null;

	public void init(String name) {
		cache = CacheFactory.getCache(name);
	}

	public V get(K k){
		r.lock();
		try{
			return cache.get(k);
		}finally{
			r.unlock();
		}
	}
 
	public void put(K k,V v){
		try{
			w.lock();
			cache.set(k,v);
		}
		finally {
			w.unlock();
		}
	}

	public int size(){
		r.lock();
		try{
			return cache.size();
		}finally{
			r.unlock();
		}
	}
	
}
