package com.ppsea.ds.util.serial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 循环队列
 * @param <E>
 */
@SuppressWarnings("serial")
public final class LoopQ<E> implements Serializable
{
	private ArrayList<E> queue;	
	private int header;
	private int size;

	public LoopQ()
	{
	}

	public LoopQ(List<E> data)
	{
		init(data);
	}

	public void init(List<E> data)
	{
		size = data.size();
		if(size > 0)
			header = size - 1;
		queue = new ArrayList<E>(size);
		for(int i = data.size() - 1; i >= 0; --i)
			queue.add(data.get(i));
	}

	public LoopQ(List<E> data, int maxSize)
	{
		if(data.size() > maxSize)
		{
			List<E> l = data.subList(0, maxSize);
			init(l);
		}
		else if(data.size() == maxSize)
		{
			init(data);
		}
		else
		{
			size = data.size();
			if(size > 0)
				header = size - 1;
			queue = new ArrayList<E>(maxSize);

			for(int i = data.size() - 1; i >= 0; --i)
				queue.add(data.get(i));
			for (int i = 0; i < maxSize - size; i++) 
			{
				queue.add(null);
			}
		}
	}

	/**
	 * 获节点个数
	 * @return
	 */
	public int size()
	{
		return size;
	}

	public int maxSize()
	{
		return queue.size();
	}

	/**
	 * 取第n个节点
	 */
	public E get(int n)
	{
		if (n < 0 || n >= size)
			throw new IndexOutOfBoundsException("LoopQ index=" + n
					+ " ,size=" + size);
		int idx = getIdx(n);
		return queue.get(idx);
	}

	/**
	 * 添加节点，如果超过最大值，剔除最早的节点
	 * @return	被删掉的节点
	 */
	public E add(E e)
	{
		header = (header + 1) % queue.size();
		if (size < queue.size())
			++size;
		return queue.set(header, e);
	}

	public E add(int n, E e)
	{
		header = (header + 1) % queue.size();
		if (size < queue.size())
			++size;
		int i = 1;
		for (; i <= n; ++i)
		{
			E o = get(i);
			int idx = getIdx(i - 1);
			queue.set(idx, o);
		}
		int idx = getIdx(i - 1);
		return queue.set(idx, e);
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	/**
	 */
	public boolean contains(E e)
	{
		for (int i = 0; i < size; ++i)
		{
			E o = get(i);
			if ((e == null && o == null) || (e != null && e.equals(o)))
				return true;
		}
		return false;
	}

	/**
	 * 删除一个节点
	 */
	public E remove(int n)
	{
		if (n < 0 || n >= size)
			throw new IndexOutOfBoundsException("current index: " + n
					+ ", size: " + size);
		E o = get(n);
		int i = n + 1;
		for (; i < size; ++i)
		{
			int idx1 = getIdx(i - 1);
			int idx2 = getIdx(i);
			queue.set(idx1, queue.get(idx2));
		}
		int idx = getIdx(--size);
		queue.set(idx, null);
		//--size;
		return o;
	}

	/**
	 * 删除节点
	 */
	public E remove(E e)
	{
		for (int i = 0; i < size; ++i)
		{
			E o = get(i);
			if ((e == null && o == null) || (e != null && e.equals(o)))
			{
				return remove(i);
			}
		}
		return null;
	}

	private int getIdx(int i)
	{
		int idx = header - i;
		idx = idx < 0 ? idx + queue.size() : idx;
		return idx;
	}

	/**
	 * 将第n个节点提前到顶端
	 */
	public void moveToTop(int n)
	{
		E o = get(n);
		remove(n);
		add(o);
	}

	/**
	 * 将某个节点提前到顶端
	 */
	public E moveToTop(E e)
	{
		for (int i = 0; i < size; ++i)
		{
			E o = get(i);
			if ((e == null && o == null) || (e != null && e.equals(o)))
			{
				remove(i);
				add(o);
				return null;
			}
		}
		return add(e);
	}

	/**
	 * 移动节点到某个位置
	 */
	public void moveTo(int srcPos, int destPos)
	{
		if(srcPos <= destPos && destPos > 0)
			--destPos;
		E o = remove(srcPos);
		if(o != null)
			add(destPos, o);
	}

	/**
	 * 将节点移动到某个位置
	 */
	public void moveTo(E src, int destPos)
	{
		int srcPos = find(src);
		if(srcPos != -1)
			moveTo(srcPos, destPos);
		else
			add(destPos, src);
	}

	/**
	 * 清除所有节点
	 */
	public void clear()
	{
		size = 0;
	}

	public ArrayList<E> toArrayList()
	{
		ArrayList<E> l = new ArrayList<E>();
		l.ensureCapacity(size);
		for(int i = 0; i < size; ++i)
			l.add(get(i));
		return l;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{ size: ").append(size).append(" [");
		for(int i = 0; i < size; ++i)
		{
			sb.append(String.valueOf(get(i)));
			if(i != size)
				sb.append(", ");
		}
		sb.append("] }");
		return sb.toString();
	}
	
	private int find(E e)
	{
		for (int i = 0; i < size; ++i)
		{
			E o = get(i);
			if ((e == null && o == null) || (e != null && e.equals(o)))
			{
				return i;
			}
		}
		return -1;
	}

	public E findElement(E e)
	{
		for (int i = 0; i < size; ++i)
		{
			E o = get(i);
			if ((e == null && o == null) || (e != null && e.equals(o)))
			{
				return o;
			}
		}
		return null;
	}

	public static void main(String[] argv)
	{
		ArrayList<String> ll = new ArrayList<String>();
		ll.add("a");
		ll.add("b");
		ll.add("c");
		LoopQ<String> l = new LoopQ<String>(
				ll, 4);
		for(int i = 0; i < l.size(); ++i){
			System.out.println(l.get(i));
		}
		l.remove("b");
		for(int i = 0; i < l.size(); ++i){
			System.out.println(l.get(i));
		}
		
		System.out.println(Arrays.toString(l.toArrayList().toArray()));
	}
}
