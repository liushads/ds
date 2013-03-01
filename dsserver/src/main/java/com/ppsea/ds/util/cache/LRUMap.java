package com.ppsea.ds.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LRUMap<K, V> extends LinkedHashMap<K, V>
{
	private static final long serialVersionUID = 1L;
	private static int MAX_ENTRIES = 1024;
	public int getMaxSize() {
		return MAX_ENTRIES;
	}
	public void setMaxSize(int max_size) {
		MAX_ENTRIES = max_size;
	}

	public LRUMap() {
		super(MAX_ENTRIES,0.75f,true);
	}

	protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
		return size() > MAX_ENTRIES;
	}

	public static void main(String[] argv)
	{
		LRUMap<String,Integer> lm = new LRUMap<String,Integer>();
		lm.setMaxSize(4);
		lm.put("1", 1);
		lm.put("2", 2);
		lm.put("3", 3);
		lm.get("1");
		lm.put("4", 4);
		lm.put("5", 5);
		lm.put("2",2);
		lm.put("6", 6);

		Set<Map.Entry<String,Integer>> se = lm.entrySet();

		System.out.println(se.toArray());

	}	
}
