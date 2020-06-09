package it.polito.tdp.borders.db;


import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CoupleCountries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public void loadAllCountries(Map<Integer,Country> countriesMap) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			//List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				if(countriesMap.get(rs.getInt("ccode")) == null){
				
					Country c = new Country(
							rs.getInt("ccode"),
							rs.getString("StateAbb"), 
							rs.getString("StateNme")) ;
					countriesMap.put(c.getcCode(), c);
					//list.add(c);
				} //else 
					//list.add(countriesMap.get(rs.getInt("ccode")));
			}
			
			conn.close() ;
			
			//return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return null ;
	}

	/**
	 * Lista di {@code Country} confinanti via terra prima dell'anno indicato
	 * @param year
	 * @return
	 */
	public List<CoupleCountries> getCouples(int year, Map<Integer, Country> idMap) {
		String sql="SELECT state1no, state2no " + 
				"FROM contiguity " + 
				"WHERE conttype=?  AND YEAR<=? " + 
				"AND state1no>state2no " + 
				"group BY state1no, state2no ";  //group by e' superfluo
	
		List <CoupleCountries> lista= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection() ;
            PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, 1); //sempre per noi
			st.setInt(2, year);
			ResultSet rs = st.executeQuery() ;
			
			while( rs.next() ) {
				Country c1= idMap.get(rs.getInt("state1no")); 
				Country c2= idMap.get(rs.getInt("state2no"));
				
				CoupleCountries cc = new CoupleCountries(c1, c2); 
				lista.add(cc); 
			}
			conn.close() ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
	
	
}
