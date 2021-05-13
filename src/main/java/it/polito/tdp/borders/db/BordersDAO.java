package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public void loadAllCountries(Map<Integer,Country> idMap,int anno) {

		String sql = "SELECT DISTINCT StateAbb,CCode,StateNme\r\n"
				+ "FROM contiguity AS cont, country AS coun\r\n"
				+ "WHERE YEAR<=? AND conttype=1 AND (cont.state1no = coun.CCode OR cont.state2no = coun.CCode)";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("CCode"))) {
					Country c=new Country( rs.getString("StateAbb"),rs.getInt("CCode"), rs.getString("StateNme"));
					idMap.put(rs.getInt("CCode"),c);
				}
			}
			
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {
		
		String sql = "SELECT state1no,state2no\r\n"
				+ "FROM contiguity\r\n"
				+ "WHERE YEAR<=? AND conttype=1\r\n"
				+ "GROUP BY dyad";

		List<Border> result = new LinkedList<>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border b=new Border(rs.getInt("state1no"),rs.getInt("state2no"));
				result.add(b);
			}

			conn.close();
			
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
