package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{
	
	//unico event, ARRIVO_MIGRANTI, non sto nemmeno a fare enum eventType
	
	private Integer time; 
	private Country country; //country in cui arrivano
	private int quanti; //numeor Migranti che arrivano
	
	
	/**
	 * @param time
	 * @param country
	 * @param quanti
	 */
	public Event(int time, Country country, int quanti) {
		super();
		this.time = time;
		this.country = country;
		this.quanti = quanti;
	}


	public int getTime() {
		return time;
	}


	public Country getCountry() {
		return country;
	}


	public int getQuanti() {
		return quanti;
	}


	//time 
	@Override
	public int compareTo(Event o) {
		
		return this.time.compareTo(o.time);
	}
	
	
	

	
}
