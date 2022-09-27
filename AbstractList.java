package poo.alberoBinarioRicerca;

import java.util.Iterator;

public abstract class AbstractList<T> implements List<T>{
	
	//mettere equals e hashCode
	
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
		}//toString
		
		@SuppressWarnings("unchecked")
		public boolean equals(Object o)
		{	if(!(o instanceof List))
				return false;
			if(o==this)
				return true;
			List<T> l=(List<T>)o;
			if(l.size()!=size())
				return false;
			Iterator<T>it=iterator();
			Iterator<T>it2=l.iterator();
			while(it.hasNext())
				if(!it.next().equals(it2.next()))
					return false;
			return true;
		}
		
		public int hashCode()
		{	final int primo=43;
			int ret=0;
			for(T x:this)
				ret=ret*primo+x.hashCode();
			return ret;
		}

}
