package poo.alberoBinarioRicerca;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList<T> extends AbstractList<T>{
	
	private static class Nodo<E>{
		E info;
		Nodo<E> next, prior;
	}
	
	private enum Move { UNKNOWN, FORWARD, BACKWARD }
	
	private Nodo<T> first=null, last=null;
	private int size=0;
	private int modCounter=0;
	
	public int size() { return size; }
	
	public void clear() 
	{ first=null; last=null; size=0;
		modCounter++;
	}
	
	public void addFirst( T x ) {
		Nodo<T> n=new Nodo<>();
		n.info=x;
		n.next=first;
		if( first!=null ) first.prior=n;
		else last=n;
		first=n;
		size++;
		modCounter++;
	}//addFirst
	
	public void addLast( T x ) 
	{	Nodo<T>n=new Nodo<>();
		n.info=x;n.next=null;
		if(first==null)
			first=n;
		else 
		{	last.next=n;
			n.prior=last;
		}
		last=n;
		size++;
		modCounter++;
	}//addLast
	
	public T removeFirst() {
		if( first==null )
			throw new NoSuchElementException();
		T x=first.info;
		first=first.next;
		if( first!=null ) first.prior=null;
		else last=null;
		size--;
		modCounter++;
		return x;
	}//removeFirst
	
	public T removeLast()
	{	if(first==null)
			throw new NoSuchElementException();
		T ret=last.info;
		last=last.prior;
		if(last!=null)
			last.next=null;
		else 
			first=null;
		size--;
		modCounter++;
		return ret;
	}//removeLast
	
	public T getFirst()
	{	if(first==null)
			throw new NoSuchElementException();
		return first.info;
	}
	public T getLast()
	{	if(last==null)
			throw new NoSuchElementException();
		return last.info;
	}
	
	//Esercizi TODO: re-implementare getFirst(), getLast(), removeLast()
	//utilizzando i puntatori
	
	
	public Iterator<T> iterator(){ return new ListIteratore(); }
	public ListIterator<T> listIterator(){ return new ListIteratore(); }
	public ListIterator<T> listIterator( int pos ) { return new ListIteratore(pos); }
	
	private class ListIteratore implements ListIterator<T>{
		
		private Nodo<T> pre=null, cor=null;
		//la freccia del list iterator e' in mezzo tra pre e cor
		//dopo un next(), il nodo corrente è puntato da pre
		//dopo un previous(), il nodo corrente è puntato da cor
		
		private Move lastMove=Move.UNKNOWN;
		private int modCounterMirror=modCounter;
		
		public ListIteratore() {
			pre=null; cor=first;
			
		}
		public ListIteratore( int pos ) {
			if( pos<0 || pos>size ) throw new IllegalArgumentException();
			pre=null; cor=first;
			for( int i=0; i<pos; ++i ) {
				pre=cor; cor=cor.next;
			}
			
			//quando pos==size, allora pre si ferma su last, e cor diventa null
			//come si deve quando la freccia va messa subito dopo l'ultimo nodo
		}
		
		public boolean hasNext() {
			return cor!=null;
		}//hasNext
		
		public T next() {
			if(modCounterMirror!=modCounter)
				throw new ConcurrentModificationException();
			if( !hasNext() ) throw new NoSuchElementException();
			lastMove=Move.FORWARD;
			pre=cor; cor=cor.next;
			return pre.info;
		}//next
		
		public void remove() {
			if(modCounterMirror!=modCounter)
				throw new ConcurrentModificationException();
			
			if( lastMove==Move.UNKNOWN ) throw new IllegalStateException();
			Nodo<T> r=null; //nodo da r(imuove
			if( lastMove==Move.FORWARD )
				r=pre;
			else
				r=cor;
			if( r==first ) {
				first=first.next;
				if( first==null ) last=null;
				else first.prior=null;
			}
			else if( r==last ) {
				last=last.prior;
				last.next=null;
			}
			else {//r è un nodo intermedio - occorrono due bypass
				r.prior.next=r.next;
				r.next.prior=r.prior;
			}
			//riposizionare la freccia dell'iterator
			if( lastMove==Move.FORWARD )
				pre=r.prior;
			else 
				cor=r.next;
			size--;
			lastMove=Move.UNKNOWN;
			modCounter++;
			modCounterMirror++;
		}//remove
		
		public boolean hasPrevious() { 
			return pre!=null;
		}//hasPrevious
		
		public T previous() { 
			if(modCounterMirror!=modCounter)
				throw new ConcurrentModificationException();
			
			if( !hasPrevious() ) throw new NoSuchElementException();
			lastMove=Move.BACKWARD;
			cor=pre; pre=pre.prior;
			return  cor.info;
		}//previous
		
		public void add( T e ) {
			Nodo<T>n=new Nodo<>();
			n.info=e;
			n.next=cor; // n messo prima di cor
			n.prior=pre;//n deve seguire pre
			if(cor!=null)
				cor.prior=n;
			if(pre !=null)
				pre.next=n;
			//aggiunstare posizione iteratore
			pre=n;
			
			if(n.next==first)
				first=n;
			if(n.prior==last)
				last=n;
			lastMove=Move.UNKNOWN;
			
			
		}
		
		public void set( T e ) {
			if(modCounterMirror!=modCounter)
				throw new ConcurrentModificationException();
			
			if(lastMove==Move.UNKNOWN)
				throw new IllegalStateException();
			if(lastMove==Move.FORWARD)
				pre.info=e;
			else
				cor.info=e;
		}
		
		public int nextIndex() { throw new UnsupportedOperationException(); }
		public int previousIndex() { throw new UnsupportedOperationException(); }
	}//ListIterator
	




	public static void main(String[]args)
	{	LinkedList<Integer>l=new LinkedList<>();
		l.addLast(12);l.addLast(2);l.addLast(5);l.addLast(7);
		System.out.println(l);
		
		System.out.println(l.removeFirst());
		System.out.println(l.removeLast());
		System.out.println(l.removeLast());
		System.out.println(l.removeLast());
		System.out.println(l.getFirst());
		System.out.println(l.getLast());
		System.out.println(l);
		//	
		
	//	List.sort(l, (i1,i2)->{return i1-i2;});
	//	System.out.println(l);
		
		//ConcurrentModificationexceptio
		
/*		ListIterator<Integer>lit=l.listIterator();
		int x=lit.next();
		
		l.addLast(x);
		int y=lit.next(); //dovrebbe Sollevare
		l.addFirst(y);						*/
	}
}//LinkedList
