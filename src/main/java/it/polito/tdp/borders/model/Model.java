package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Graph<Country, DefaultEdge> graph ;
	private Map<Integer,Country> countriesMap ;
	
	private Simulator sim; 
	
	public Model() {
		this.countriesMap = new HashMap<>() ;
		this.sim= new Simulator();

	}
	
	public void creaGrafo(int anno) {
		
		this.graph = new SimpleGraph<>(DefaultEdge.class) ;

		BordersDAO dao = new BordersDAO() ;
		
		//vertici
		dao.getCountriesFromYear(anno,this.countriesMap) ;
		Graphs.addAllVertices(graph, this.countriesMap.values()) ;
		
		// archi
		List<Adiacenza> archi = dao.getCoppieAdiacenti(anno) ;
		for( Adiacenza c: archi) {
			graph.addEdge(this.countriesMap.get(c.getState1no()), 
					this.countriesMap.get(c.getState2no())) ;
			
		}
	}
	
	public List<CountryAndNumber> getCountryAndNumber() {
		List<CountryAndNumber> list = new ArrayList<>() ;
		
		for(Country c: graph.vertexSet()) {
			list.add(new CountryAndNumber(c, graph.degreeOf(c))) ;
		}
		Collections.sort(list);
		return list ;
	}

	public void simula(Country partenza) {
		
		//controllo che la simulazione venga fatta solo a grafo creato
		if (this.graph!=null) {
		 
		
		sim.inizializzazione(partenza, this.graph);
		sim.run();
		}
		
	}
	
	public Integer getPasso() {
		
		return this.sim.getPassi(); 
	}
	
	public List<CountryAndNumber> getStanziali(){
		Map<Country, Integer> stanziali= this.sim.getStanziali(); 
		List<CountryAndNumber> risultato= new ArrayList<>(); 
		
		for (Country c : stanziali.keySet()) {
			CountryAndNumber nuovo = new CountryAndNumber(c,  stanziali.get(c)); 
			risultato.add(nuovo); 
		} 
		Collections.sort(risultato); //gia' implementato per utilita' del grafo
		
		return risultato; 
	}
	
	public List<Country> getCountries() {
		List<Country> countries = new ArrayList<>(this.graph.vertexSet()); 
		Collections.sort(countries);
		return countries; 
		
	}
}
