package poo.alberoBinarioRicerca;



import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;




public class AlberoBinarioDiRicerca<T extends Comparable<? super T>> implements CollezioneOrdinata<T>
{	
	public class Nodo<E>
	{	E info;
		Nodo<E>fS,fD;		
		int cfs,cfd; //cardinalita sotto albero
		
		public String toString()
		{	return ""+info;
		}
		
	}
	
	private Nodo<T>radice=null;
	private Stack<Nodo<T>>sbilanciati=new StackConcatenato<>();
	
	private int modCounder;
	
	
	
	public int size()
	{	return size(radice);
	}
	private int size(Nodo<T> radice)
	{	if(radice==null)
			return 0;
		return 1+size(radice.fS)+size(radice.fD);
	}
	
	public void clear()
	{	radice=null;
		modCounder++;
	}
	
	public boolean contains(T elem)
	{	return contains(radice,elem);
	}
	
	private boolean contains(Nodo<T>radice,T elem)
	{	if(radice==null)
			return false;
		if(radice.info.equals(elem))
			return true;
		if(radice.info.compareTo(elem)>0)
			return contains(radice.fS, elem);
		return contains(radice.fD, elem);
	}
	
	public void add(T x)
	{	radice=add(radice,x);
		modCounder++;
	}
	
	private Nodo<T>add(Nodo<T>radice,T x)
	{	
		if(radice==null)				
		{	radice=new Nodo<>();
			radice.info=x;
			radice.fD=null;radice.fS=null;
			return radice;
		}
		
		if(radice.info.compareTo(x)>=0)
		{	radice.fS=add(radice.fS,x);
			radice.cfs=radice.cfs+1;		
			if(!bilanciato())
			{	sbilanciati.push(radice);
				radice=bilancia(sbilanciati);
			}
			return radice;
		}
		radice.fD=add(radice.fD,x);
		radice.cfd=radice.cfd+1;
		if(!bilanciato())
		{	sbilanciati.push(radice);
			radice=bilancia(sbilanciati);
		}
		return radice;
	}
	
	
	public Nodo<T> bilancia(Stack<Nodo<T>>stack)
	{	List<T>a=new LinkedList<>();
		Nodo<T>n=stack.pop();
		inOrder(a,n);
		n.fD=null;n.fS=null;
		n.cfd=0;n.cfd=0;
		n=inserisciOrd(a,n,0,a.size()-1);
		return n;
	}
	
	
	private Nodo<T> inserisciOrd(List<T> a, Nodo<T> radice,int inf,int sup)
	{	if(inf>sup)
			return null;
		Iterator<T>it=a.iterator();
		int mid=(inf+sup)/2;
		for(int i=0;i<mid;i++)
			it.next();
		T mediano=it.next();
		Nodo<T>n=new Nodo<>();
		n.info=mediano;
		radice=n;
		radice.fS=inserisciOrd(a, radice.fS, inf, mid-1);
		radice.cfs=size(radice.fS);
		radice.fD=inserisciOrd(a, radice.fD, mid+1, sup);
		radice.cfd=size(radice.fD);
		return radice;
	}
	
		
	public void remove( T x ) {
		radice=remove( radice, x );		
		modCounder++;
	}//remove
	private Nodo<T> remove( Nodo<T> radice, T x )
	{	if(radice==null)
			return null;
		if(radice.info.compareTo(x)>0)
		{	radice.fS=remove(radice.fS, x);
			radice.cfs--;
			if(!bilanciato())
			{	sbilanciati.push(radice);
				radice=bilancia(sbilanciati);
			}
			return radice;
		}
		if(radice.info.compareTo(x)<0)
		{	radice.fD=remove(radice.fD, x);
			radice.cfd--;
			if(!bilanciato())
			{	sbilanciati.push(radice);
				radice=bilancia(sbilanciati);
			}

			return radice;
		}
		//l'elem è in radice.info
		
		if(radice.fD==null && radice.fS==null)
			return null;
		
		if( radice.fS==null ) //nodo col solo figlio destro
			return radice.fD;	
		if( radice.fD==null )  //nodo col solo figlio sinistro
			return radice.fS;
		//radice con entrambi i figli
		if(radice.fD.fS==null)//1 caso
		{	radice.info=radice.fD.info;
			radice.fD=radice.fD.fD;
			return radice;
		}
		Nodo<T>padre=radice.fD;
		Nodo<T>figlio=padre.fS;
		while(figlio.fS!=null)
		{	padre=figlio;
			figlio=figlio.fS;
		}
		radice.info=figlio.info;
		padre.fS=figlio.fD;
		return radice;	
	}
	
	public boolean bilanciato()
	{	if(radice!=null)
			return Math.abs(radice.cfd-radice.cfs)<=1;
		return true;
	}


	
	public void inOrder(List<T>l)
	{	inOrder(l,radice);
	}
	
	private void inOrder(List<T>l,Nodo<T>radice)
	{	if(radice!=null)
		{	inOrder(l,radice.fS);
			l.addLast(radice.info);
			inOrder(l, radice.fD);
		}
			
	}
	public void preOrder( List<T> lis ) {//visita in ordine anticipato
		preOrder(radice,lis);
	}
	private void preOrder( Nodo<T> radice, List<T> lis ) {
		if( radice!=null ) {
			lis.addLast(radice.info); //prima la radice
			preOrder( radice.fS, lis );
			preOrder( radice.fD, lis );
		}
	}
	
	public void postOrder( List<T> lis ) {//visita in ordine posticipato
		postOrder( radice, lis );
	}
	private void postOrder( Nodo<T> radice, List<T> lis ) {
		if( radice!=null ) {
			postOrder( radice.fS, lis );
			postOrder( radice.fD, lis );
			lis.addLast( radice.info );
		}
	}
	

	
	public T get(T x)
	{	return get(radice,x);
	}
		
	private T get(Nodo<T> radice, T x) {
		if(radice==null)
			return null;
		if(radice.info.equals(x))
			return radice.info;
		if(radice.info.compareTo(x)>0)
			return get(radice.fS, x);
		return get(radice.fD, x);
	}
	
	public boolean isEmpty() { return radice==null; }
	public boolean isFull() { return false; }

	

	public String toString() {
		StringBuilder sb=new StringBuilder(100);
		sb.append('[');
		Iterator<T> it=this.iterator();
		while( it.hasNext() ) {
			sb.append( it.next() );
			if( it.hasNext() ) sb.append(", ");
		}
		sb.append(']');
		return sb.toString();
	}//toString
	
	@SuppressWarnings("unchecked")
	public boolean equals(Object o)
	{	if(!(o instanceof AlberoBinarioDiRicerca))
			return false;
		if(o==this)
			return true;
		AlberoBinarioDiRicerca<T>a=(AlberoBinarioDiRicerca<T>)o;
		return equals(a.radice,radice);
	}
	private boolean equals(Nodo<T> r1, Nodo<T> r2) 
	{	if(r1==null && r2==null)
			return true;
		if(r1==null || r2==null)
			return false;
		if( !r1.info.equals(r2.info) ) 
			return false;
		return equals(r1.fD,r2.fD) && equals(r1.fS,r2.fS);
	}

	public int hashCode()
	{	return toString().hashCode();
	}

	public AlberoBinarioDiRicerca<T> copy()
	{	AlberoBinarioDiRicerca<T>ret=new AlberoBinarioDiRicerca<>();
		Iterator<T>it=this.iterator();
		while(it.hasNext())
		{	T info=it.next();
			ret.add(info);
		}
		return ret;
		
	}//copy
	
	public void build( T[] a ) {
		clear();
		//crea l'albero a partire dal contenuto di a, supposto ordinato per valori crescenti
		//ci si aspetta che l'albero costruito sia bilanciato
		AlberoBinarioDiRicerca<T>ret=new AlberoBinarioDiRicerca<>();
		ret.radice=inserisciOrd(a, radice, 0, a.length-1);
	}
	private Nodo<T> inserisciOrd(T[] a,Nodo<T> radice, int inf,int sup)
	{	if(inf>sup)
			return  null;
		int mid=(inf+sup)/2;
		T mediano=a[mid];
		Nodo<T>n=new Nodo<>();
		n.info=mediano;
		radice=n;
		radice.fS=inserisciOrd(a, radice.fS, inf, mid-1);
		radice.cfs=size(radice.fS);
		radice.fD=inserisciOrd(a, radice.fD, mid+1, sup);
		radice.cfs=size(radice.fD);
		return radice;
		
	}
	public int altezza() {
		return altezza(radice);
	}//altezza
	
	private int altezza(Nodo<T>radice)
	{	if(radice==null || radice.fD==null && radice.fS==null)
			return 0;
		else 
		{	int hS=altezza(radice.fS);
			int hD=altezza(radice.fD);
			if(hS>hD)
				return 1+hS;
			else
				return 1+hD;
		}
	}
	

	public void visitaPerLivelli(List<T>visitati)
	{	if(radice==null)
			return;
		LinkedList<Nodo<T>>coda=new LinkedList<>();
		coda.addLast(radice);
		while(!coda.isEmpty())
		{	Nodo<T>n=coda.removeFirst();
			visitati.addLast(n.info);
			if(n.fS!=null)
				coda.addLast(n.fS);
			if(n.fD!=null)
				coda.addLast(n.fD);
		}
	}
	@Override
	public Iterator<T> iterator()
	{	
		return new ABRIterator();
	}

	private class ABRIterator implements Iterator<T>
	{	
		private Nodo<T>corrente=null;
		private Stack<Nodo<T>>stackIt=new StackConcatenato<>();
		private int modCunterIt=modCounder;
		
		public ABRIterator()
		{	Nodo<T>cor=radice;
			while(cor!=null)
			{	stackIt.push(cor);
				cor=cor.fS;
			}
		}
	
		public boolean hasNext()
		{	return !stackIt.isEmpty();
		}
		
		public T next()
		{	if(modCounder!=modCunterIt)
				throw new ConcurrentModificationException();
			if(!hasNext())
				throw new NoSuchElementException();
			Nodo<T>n=stackIt.pop();
			corrente=n;
			if(n.fD!=null)
			{	stackIt.push(n.fD);
				Nodo<T>corSx=n.fD.fS;
				while(corSx!=null)
				{	stackIt.push(corSx);
					corSx=corSx.fS;
				}
			}	
			return n.info;
		}
		
		public void remove()
		{	if(modCounder!=modCunterIt)
				throw new ConcurrentModificationException();
			if(corrente==null)
				throw new NoSuchElementException();
			modCunterIt++;
			T x=corrente.info;
			AlberoBinarioDiRicerca.this.remove(x);
		}
	}
	
	
	public static void main(String...args)
	{	AlberoBinarioDiRicerca<Integer>abr=new AlberoBinarioDiRicerca<>();
		abr.add(12); 
		abr.add(-2); 
		abr.add(34); 
		abr.add(-4); 
		abr.add(-6); 
		abr.add(5); 
		abr.add(1); 
		abr.add(7); 
		abr.add(8);
		abr.add(38); 		
		System.out.println(abr.bilanciato());
		
		System.out.println(abr.bilanciato());
		


//		List<Integer>a=new LinkedList<Integer>();
//		abr.inOrder(a);
		
	//	abr.remove(-2);abr.remove(-4);abr.remove(8);
		

	}
	
	
	
	
	
	
	
	
}
