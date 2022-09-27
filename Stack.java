package poo.alberoBinarioRicerca;

import java.util.Iterator;

public interface Stack<T>extends Iterable<T> {
	
	default int size()
	{	int ret=0;
		Iterator<T>it=iterator();
		while(it.hasNext())
		{	it.next();
			ret++;
		}
		return ret;
	}
	
	default void clear()
	{	Iterator<T>it=iterator();
		while(it.hasNext())
		{	it.next();
			it.remove();
		}
	}
	
	void push(T x);
	
	default T pop()
	{	Iterator<T>it=iterator();
		T x=it.next();
		it.remove();
		return x;
	}
	
	
	default T peek()
	{	Iterator<T>it=iterator();
		T x=it.next();
		return x;
	}
	
	default boolean isEmpty()
	{	return size()==0;
	}
	
	default boolean isFull()
	{	return false;
	}
	
	
}//Stack
