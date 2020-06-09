package it.polito.tdp.borders.model;

public class CoupleCountries {

	private Country c1; 
	private Country c2;
	
	/**
	 * @param c1
	 * @param c2
	 */
	public CoupleCountries(Country c1, Country c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}

	public Country getC1() {
		return c1;
	}

	public Country getC2() {
		return c2;
	} 
	
	
	
}

