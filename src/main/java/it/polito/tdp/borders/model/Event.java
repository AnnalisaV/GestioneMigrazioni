package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	//questo evento e' solo lui, unico -> ARRIVO DEI MIGRANTI 
	
	private int t;  //tempo di arrivo
	private Country stato; //stato in cui arrivano i migranti al tempo t 
	private int n; //numero di migranti che arrivano e vanno divisi fra stanziali e non
	
	
	/**
	 * @param t
	 * @param stato
	 * @param n
	 */
	public Event(int t, Country stato, int n) {
		super();
		this.t = t;
		this.stato = stato;
		this.n = n;
	}


	public int getT() {
		return t;
	}


	public void setT(int t) {
		this.t = t;
	}


	public Country getStato() {
		return stato;
	}


	public void setStato(Country stato) {
		this.stato = stato;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}


	// per tempo
	@Override
	public int compareTo(Event other) {
		
		return this.t-other.t; //ok perche' sono 'tipi' primitivi 
	}
	
	
	
	
}
