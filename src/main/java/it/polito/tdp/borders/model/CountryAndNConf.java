package it.polito.tdp.borders.model;

public class CountryAndNConf implements Comparable<CountryAndNConf>{

	private Country c; 
	private Integer n; //numero di stati con cou confina 
	
	/**
	 * @param c
	 * @param n
	 */
	public CountryAndNConf(Country c, int n) {
		super();
		this.c = c;
		this.n = n;
	}

	public Country getC() {
		return c;
	}

	public int getN() {
		return n;
	}

	//per n decrescente 
	@Override
	public int compareTo(CountryAndNConf o) {
		
		return o.n.compareTo(this.n);
	}

	@Override
	public String toString() {
		return c.getStateAbb()+" "+c.getStateName()+" "+n+" countries confinanti";
	}
	
	
	
}
