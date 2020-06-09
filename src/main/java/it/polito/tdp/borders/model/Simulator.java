package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;



public class Simulator {

	
	//parametri di simulazione
	private Country partenza=null; 
	private int migrantiTot=0;
	private Graph<Country, DefaultEdge> graph; 
	
	
	public void setPartenza(Country partenza) {
		this.partenza = partenza;
	}
	public void setMigrantiTot(int migrantiTot) {
		this.migrantiTot = migrantiTot;
	} 
	
	
	
	//modello del mondo
	
	private Map<Country, Integer> countryNumeroPersone; 
	
	
	//output
	int time=0; 

	public Map<Country, Integer> getCountryNumeroPersone() {
		return countryNumeroPersone;
	}

	public int getTime() {
		return time;
	}



	//coda degli eventi 
	private PriorityQueue<Event> queue; 
	
	public void init(Country country,Graph<Country, DefaultEdge> graph ) {
		this.queue= new PriorityQueue<>(); //pulita
		
		//imposto le condizioni iniziali
		this.partenza=country; 
		this.migrantiTot=1000; //scelgo io da testo 
		this.graph=graph; 
		
		//creo la mappa
		this.countryNumeroPersone= new HashMap<>(); 
		for (Country c : graph.vertexSet()) {
			countryNumeroPersone.put(c, 0); //all'inizio nessuno in ogni stato
		}
		
		time=1; 
		Event e= new Event(time, country,migrantiTot);
		this.queue.add(e); 
		
		
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll(); 
			processEvent(e); 
		}
	}
	private void processEvent(Event e) {
		
		// dell'evento qual e' il country ed il numero di Migranti e dil tempo corrente
		Country c = e.getCountry(); 
		int persone= e.getQuanti(); 
		time= e.getTime(); 
		
		//il 50% si vuole spostare 
		
		//countries confinanti
		List<Country> confinanti= Graphs.neighborListOf(graph, c); 
		int perCountry = (persone/2) / confinanti.size(); 
		
		if (perCountry > confinanti.size()) {
			//li posso far muovere 
			for (Country confine : confinanti) {
				Event eNuovo = new Event(time+1, confine, perCountry); 
				this.queue.add(eNuovo); 
			}
		
			
		}
		//non posso muoverli, chi si ferma (la meta' + quelli che non possono muoversi)
		int fermi= persone - perCountry*confinanti.size(); 
		countryNumeroPersone.put(c, countryNumeroPersone.get(c)+fermi); //aggiungo non sovrascrivo dato che posso anche tornare indietro
		
		
		
	}
	
	
}
