package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private Map<Integer, Country> idMapCountries; 
	private BordersDAO dao; 
	private Graph<Country, DefaultEdge> graph; 
	
	public Model() {
		this.idMapCountries= new HashMap<>(); 
		this.dao= new BordersDAO(); 
		dao.loadAllCountries(idMapCountries);
	}
	
	public void creaGrafo(int year) {
		
		this.graph= new SimpleGraph<>(DefaultEdge.class); 
		
		//vertex and archi 
		for(CoupleCountries c : dao.getCouples(year, idMapCountries)) {
			
			// mi chiedo se ci sono quei vertex
			if (!this.graph.containsVertex(c.getC1())) {
				this.graph.addVertex(c.getC1()); 
			}
			if (!this.graph.containsVertex(c.getC2())) {
				this.graph.addVertex(c.getC2()); 
			}
			
			this.graph.addEdge(c.getC1(), c.getC2()); 
		}
	}
	
	public int nVertex() {
		return this.graph.vertexSet().size(); 
	}
	public int nArchi() {
		return this.graph.edgeSet().size(); 
	}
	
	/**
	 * Lista di stati confinanti con il numero di stati con cui confinano
	 * @return
	 */
	public List<CountryAndNConf> getConfinanti(){
		List<CountryAndNConf> lista= new ArrayList<>(); 
		
		for (Country c : this.graph.vertexSet()) {
			int nConf= this.graph.degreeOf(c); 
			lista.add(new CountryAndNConf(c, nConf)); 
		}
		
		Collections.sort(lista);
		return lista; 
	}
}
