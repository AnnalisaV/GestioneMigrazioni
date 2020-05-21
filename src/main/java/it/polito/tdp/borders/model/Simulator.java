package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {

	
	
	//Modello -> stato del sistema ad ogni passo
	private Graph<Country, DefaultEdge> graph; 
	
	//Tipi di evento -> da inserire nella queue prioritaria
	private PriorityQueue<Event> queue; 
	
	
	//Parametri di simulazione
	private int N_MIGRANTI=1000; 
	private Country partenza; //da che stato partono le migrazioni 
	
	
	//Valori di output
	private int passi=-1; //quanti passi sono stati simulati
	private Map<Country, Integer> stanziali;
	
	public int getPassi() {
		return passi;
	}
	public Map<Country, Integer> getStanziali() {
		return stanziali;
	}
	
	
	//Simulazione vera 
	
	public void inizializzazione(Country country, Graph<Country, DefaultEdge> graph) {
		this.partenza= country; 
		this.graph= graph; 
		
		//impostazione dello stato iniziale 
		this.passi=1; 
		this.stanziali= new HashMap<>(); 
		
		for (Country c : this.graph.vertexSet()) {
			this.stanziali.put(c, 0); //preventivamente dico che ci sono 0 persone in ogni stato, poi aggiorno se necessario
		}
		
		this.queue= new PriorityQueue<>(); 
		//inserisco il primo evento
		Event e = new Event(passi, country, N_MIGRANTI);
		this.queue.add(e); 
		
		
	}
	
	public void run() {
		
		// finche' la coda non e' vuota, estraggo un evento per volta e lo eseguo
		Event e; 
		while( (e=this.queue.poll()) != null ) {
			this.passi= e.getT(); 
			//eseguo l'evento ( non mi interessa sapere quale perche' c'e' solo un tipo di Event qui)
			int nPersone= e.getN(); 
			Country stato= e.getStato(); 
		
			//quali sono i confini dello stato su cui distribuire la meta' dei migranti?
			List<Country> confini= Graphs.neighborListOf(this.graph, stato); 
			int quantiPerStato= (nPersone/2) / confini.size(); /*arrotondamento per difetto automatico*/
			                                           
			if (quantiPerStato>0) {
				// si possono muovere 
				for (Country c : confini) {
					                    //il tempo dell'evento corrente+1
					Event ev = new Event(e.getT()+1, c, quantiPerStato); 
					this.queue.add(ev); 
				}
			}
			
			int stanziali= nPersone - quantiPerStato*confini.size();
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali); // così anche chi torna indietro e' contato
			
			
		}
	}
	
	
}
