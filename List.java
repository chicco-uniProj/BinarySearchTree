package poo.alberoBinarioRicerca;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

public interface List<T> extends Iterable<T>{
	@SuppressWarnings("unused")
	default int size()
	{	int c=0;
		for(T x:this)
			c++;
		return c;
	}
	
	default void clear()
	{	Iterator<T>it=this.iterator();
		while(it.hasNext())
		{	it.next();
			it.remove();
		}
	}
	
	default void addFirst(T e)
	{	ListIterator<T>lit=this.listIterator();
		lit.add(e);
	}
	default void addLast(T e)
	{	ListIterator<T>lit=this.listIterator(size());
		lit.add(e);
	}
	
	default T getFirst()
	{	return this.listIterator().next();
	}
	default T getLast()
	{	return this.listIterator(size()).previous();
	}
	
	default T removeFirst()
	{	ListIterator<T>lit=this.listIterator();
		T x=lit.next();
		lit.remove();
		return x;
	}
	
	
	default T removeLast()
	{	ListIterator<T>lit=this.listIterator(size());
		T x=lit.previous();
		lit.remove();
		return x;
	}
	
	default boolean isEmpty()
	{	return !listIterator().hasNext();
	}
	
	
	default boolean isFull()
	{	return false;
	}
	
	static <T>void sort(List<T>lista,Comparator<? super T>c)
	{	if(lista.size()<=1)
			return;
		boolean scambi=true;
		int limite=lista.size();
		int pus=0;
		while(scambi)
		{	ListIterator<T>lit=lista.listIterator();
			T x=lit.next(), y=null;
			int pos=1;scambi=false;
			while(pos<limite)
			{	y=lit.next();
				if(c.compare(x, y)>0)
				{	lit.set(x);
					lit.previous();lit.previous(); //devo tornare indientro due volte  fai nu schemino se non capisci
					lit.set(y);
					lit.next();lit.next(); // la seconda next equivale a : x=lit.next();
					scambi=true;pus=pos;
				}
				else 
					x=y;
				pos++;
			}//while int
			limite=pus;
		}//while ext
	}
	
	
	ListIterator<T>listIterator();
	ListIterator<T>listIterator(int pos);

	

}
