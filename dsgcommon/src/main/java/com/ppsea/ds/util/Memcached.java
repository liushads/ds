package com.ppsea.ds.util;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

public class Memcached {
	
	private static final Logger log = Logger.getLogger(Memcached.class);
	
	static{
		String[] servers = { GlobalConfig.global.attributeValue("memcached_server") };
		//String[] servers = { "127.0.0.1:11211" };
		// server权重
		Integer[] weights = {3}; 
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers( servers );
		
		pool.setFailover( true );
		// 连接池设置
		pool.setInitConn( 2 ); 
		pool.setMinConn( 2 );
		pool.setMaxConn( 5 );
		//pool.setMaintSleep( 10 );
		pool.setNagle( false );
		// 超时设置
		pool.setSocketTO( 500 );
		pool.setAliveCheck( true );
		pool.initialize();
	}
	
	public static void set(String key, String value){
		set(key,value,-1);
	}
	
	public static void set(String key, String value, int minute){
		MemCachedClient memCachedClient = new MemCachedClient();
		// minute 为 -1时候表示时间不限
		if( minute==-1 ) {
			memCachedClient.set(key, value);
		}
		else{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MINUTE,minute);
			memCachedClient.set(key, value,calendar.getTime());
		}
	}
	
	public static Object get(String key) {

		if(key==null || "".equals(key)) return null;
		// 从memcached中取得缓存的值		
		MemCachedClient memCachedClient = new MemCachedClient();
		if(memCachedClient.keyExists(key)){
			Object object = memCachedClient.get(key);
			return object;
		}
		else{
			return null;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		/*
		Memcached.set("key", "1",-1);
		System.out.print(Memcached.get("key"));
		Memcached.set("key", "2",-1);
		System.out.print(Memcached.get("key"));
		// Thread.sleep(1000*30);
		Memcached.set("key1", "1",-1);
		System.out.print(Memcached.get("key1"));
		Memcached.set("key2", "2",-1);
		System.out.print(Memcached.get("key2"));
		*/
		Memcached.set("key2", "2",-1);
		System.out.print(Memcached.get("11748"));
	}

}
