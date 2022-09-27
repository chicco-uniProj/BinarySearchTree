package poo.alberoBinarioRicerca;

import java.util.Iterator;

public interface CollezioneOrdinata<T extends Comparable<? super T>>extends Iterable<T> {
	default int size()
	{	int ret=0;
		Iterator<T>it=iterator();
		while(it.hasNext())
			ret++;
		return ret;
	}
	
	default void clear()
	{	Iterator<T>it=iterator();
		while(it.hasNext())
		{	it.next();
			it.remove();
		}
	}
	
	default boolean contains(T x)
	{	Iterator<T>it=iterator();
		while(it.hasNext())
		{	if(it.next().equals(x))
				return true;
		}
		return false;
	}
	void add(T x);// impplementare qui tutti tranne add e iterator
	
	default void remove(T x)
	{	Iterator<T>it=iterator();
		while(it.hasNext())
		{	if(it.next().equals(x))
				it.remove();
		}
	}
	default T get(T x)
	{	Iterator<T>it=iterator();
		while(it.hasNext())
		{	T prox=it.next();
			if(prox.equals(x))
				return prox;
		}
		return null;
	}
	default boolean isEmpty()
	{	Iterator<T>it=iterator();
		return !it.hasNext();
	}
	default boolean isFull()
	{	return false;
	}
}
