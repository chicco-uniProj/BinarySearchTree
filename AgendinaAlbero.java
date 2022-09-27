package poo.alberoBinarioRicerca;

import java.util.Iterator;

import poo.agendina.AgendinaAstratta;
import poo.agendina.AgendinaSet;
import poo.agendina.Nominativo;


public class AgendinaAlbero extends AgendinaAstratta
{	AlberoBinarioDiRicerca<Nominativo>tabella;


	public AgendinaAlbero() {
		tabella=new AlberoBinarioDiRicerca<>();
	}

	@Override
	public void aggiungi(Nominativo n)
	{	tabella.add(n);
	}

	@Override	
	public Nominativo cerca(Nominativo n)
	{	return tabella.get(n);		
	}
	
	public boolean eBilanciato()
	{	return tabella.bilanciato();
	}
	
	public Iterator<Nominativo> iterator() 
	{	return tabella.iterator();
	}
	
	public static void main(String[]args)
	{	Nominativo n=new Nominativo("Greco","Mariasole", "39", "311879685");
		Nominativo n1=new Nominativo("Arcuri","Martina", "39", "3897545135");
		Nominativo n2=new Nominativo("Perri","Vittoria", "39", "3154757223");
		Nominativo n3=new Nominativo("Caputo","Miriam", "39", "3697423254");
		Nominativo n4=new Nominativo("Ferraro","Dora", "39", "3456648635");
		Nominativo n5=new Nominativo("DeBastiani","Annagiulia", "39", "314564532");
		Nominativo n6=new Nominativo("Venneri","Gregorio", "39", "389743972");
		Nominativo n7=new Nominativo("Toscano","Rossella", "39", "314564878");
		Nominativo n8=new Nominativo("Vernioli","Agnese", "39", "3798654341");
		Nominativo n9=new Nominativo("Zumpano","Francesco", "39", "45648913");
		Nominativo n10=new Nominativo("Bagadya","Jenil", "91", "00456484893");
		Nominativo n11=new Nominativo("Apolito","Alessandro", "39", "348951651");
		Nominativo n12=new Nominativo("Nudo","Chiara", "39", "321568546");
		
		AgendinaAlbero abr=new AgendinaAlbero();
		AgendinaSet as=new AgendinaSet();
		abr.aggiungi(n);				as.aggiungi(n);
		abr.aggiungi(n1);			as.aggiungi(n1);
		abr.aggiungi(n2);			as.aggiungi(n2);
		abr.aggiungi(n3);			as.aggiungi(n3);
		abr.aggiungi(n4);			as.aggiungi(n4);	
		abr.aggiungi(n5);			as.aggiungi(n5);
		abr.aggiungi(n6);			as.aggiungi(n6);
		abr.aggiungi(n7);			as.aggiungi(n7);
		abr.aggiungi(n8);			as.aggiungi(n8);
		abr.aggiungi(n9);			as.aggiungi(n9);
		abr.aggiungi(n10);			as.aggiungi(n10);
		abr.aggiungi(n11);			as.aggiungi(n11);
		abr.aggiungi(n12);			as.aggiungi(n12);
		
		System.out.println(abr);
		System.out.println("Tempo di esecuzione con albero "+System.nanoTime());
		System.out.println("L'albero è bilanciato? " + abr.eBilanciato());
		System.out.println();
		System.out.println();
		System.out.println(as);
		System.out.println("Tempo di esecuzione con set "+System.nanoTime());
		
		System.out.println(abr.cerca(n8));				
		System.out.println(System.nanoTime());
		
		System.out.println(as.cerca(n8));
		System.out.println(System.nanoTime());
		
		
		
	}



}
