package poo.alberoBinarioRicerca;

import java.util.Iterator;

public abstract class StackAstratto<T> implements Stack<T>{
	
	
	public String toString()
	{	StringBuilder sb=new StringBuilder();
		sb.append("[");
		Iterator<T>it =iterator();
		while(it.hasNext())
		{	sb.append(it.next());
			if(it.hasNext())
				sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o)
	{	if(!(o instanceof StackAstratto))
			return false;
		if(o==this)
			return true;
		StackAstratto<T> s=(StackAstratto<T>)o;
		if(s.size()!=size())
			return false;
		Iterator<T>it = iterator();
		Iterator<T>it2 = s.iterator();
		while(it.hasNext())
		{	if(!it.next().equals(it2.next()))
				return false;
		}
		return true;
	}
	
	public int hashCode()
	{	final int M=43;
		int ret=0;
		Iterator<T>it = iterator();
		while(it.hasNext())
			ret=ret*M+it.next().hashCode();
		return ret;
	
	}

}
