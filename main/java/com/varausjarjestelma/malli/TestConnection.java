package com.varausjarjestelma.malli;

import java.sql.*;

/**
 * A class for tests.
 * 
 * @author V. Ahlstén, E. Niemi
 *
 */
public class TestConnection {
	
	private Connection db;

	/**
	 * A constructor in which the database connection
	 * is established.
	 */
	public TestConnection() {
		db = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			final String URL = "jdbc:mysql://10.114.32.32:3306/tilanvaraus";
			final String USERNAME = "team7";
			final String PASSWORD = "team7";
			db = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		} catch (ClassNotFoundException e) {
			System.err.println("JDBC-ajurin lataus epäonnistui");
			System.exit(-1); // lopetus heti virheen vuoksi
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Returns example data.
	 * 
	 * @return example data
	 */
	public ResultSet haeEsimerkkiData() {
		
		try {
			Statement stmt = db.createStatement();
			String query = "SELECT * FROM Tila";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
