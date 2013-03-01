package com.ppsea.ds.util.serial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ppsea.ds.util.serial.LoopQ;

@SuppressWarnings("serial")
public final class SplitLoopQ<E> implements Serializable
{
	private LoopQ<E> lq;			// 循环队列

	public SplitLoopQ()
	{
	}
	public SplitLoopQ(List<E> data, int maxSize)
	{
		init(data,maxSize);
	}
	public SplitLoopQ(List<E> data)
	{
		init(data,data.size());
	}

	public void init(List<E> data, int maxSize)
	{
		this.lq = new LoopQ<E>(data, maxSize);
	}

	public ArrayList<E> subList(int pageNo,int pageSize)
	{
		int begin = pageNo * pageSize;
		int end = begin + pageSize;
		if(end > lq.size())
			end = lq.size();
		if(begin > lq.size())
			return null;

		//ArrayList<E> l = lq.toArrayList();
		//return new ArrayList<E>(l.subList(begin, end));
		ArrayList<E> l = new ArrayList<E>();
		for (int i = begin; i < end; ++i)
		{
			E e = get(i);
			l.add(e);
		}
		return l;
	}

	public boolean contains(E e)
	{
		return lq.contains(e);
	}

	public boolean equals(Object arg0)
	{
		return lq.equals(arg0);
	}

	public E findElement(E e)
	{
		return lq.findElement(e);
	}

	public E get(int n)
	{
		return lq.get(n);
	}

	public int hashCode()
	{
		return lq.hashCode();
	}

	public boolean isEmpty()
	{
		return lq.isEmpty();
	}

	public int maxSize()
	{
		return lq.maxSize();
	}

	public void moveTo(E src, int destPos)
	{
		lq.moveTo(src, destPos);
	}

	public void moveTo(int srcPos, int destPos)
	{
		lq.moveTo(srcPos, destPos);
	}

	public E moveToTop(E e)
	{
		return lq.moveToTop(e);
	}

	public void moveToTop(int n)
	{
		lq.moveToTop(n);
	}

	public E add(E e)
	{
		return lq.add(e);
	}

	public E add(int n, E e)
	{
		return lq.add(n, e);
	}

	public E remove(E e)
	{
		return lq.remove(e);
	}

	public E remove(int n)
	{
		return lq.remove(n);
	}

	public void clear()
	{
		lq.clear();
	}

	public int size()
	{
		return lq.size();
	}

	public ArrayList<E> toArrayList()
	{
		return lq.toArrayList();
	}

	public String toString()
	{
		return lq.toString();
	}	
}
