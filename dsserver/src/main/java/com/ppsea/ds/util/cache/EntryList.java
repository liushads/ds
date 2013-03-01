package com.ppsea.ds.util.cache;

final class EntryList<K, V>
{
	CacheEntry<K, V>	head;
	CacheEntry<K, V>	tail;

	public boolean empty()
	{
		return head == null;
	}

	public void remove(CacheEntry<K, V> n)
	{
		if(n.next != null){
			n.next.prev = n.prev;
		}else{
			tail = n.prev;
			if(tail != null)
				tail.next = null;
		}
		if(n.prev != null){
			n.prev.next = n.next;
		}else{
			head = n.next;
			if(head != null)
				head.prev = null;
		}
	}
	
	public void add(CacheEntry<K, V> n)
	{
		if(head == null){
			tail = head = n;
		}else{
			n.next = head;
			head.prev = n;
			head = n;
			head.prev = null;
		}
	}
	
	public void moveToTop(CacheEntry<K, V> n)
	{
		if(head != tail){
			remove(n);
			add(n);
		}
	}
	
	public void clear()
	{
		head = tail = null;
	}
}
