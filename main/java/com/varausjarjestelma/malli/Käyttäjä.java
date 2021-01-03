package com.varausjarjestelma.malli;

import javax.persistence.*;

/**
 * A hibernate entity used to map a row
 * in the user (Kayttaja) table in the
 * database.
 * 
 * @author V. Ahlstén
 *
 */
@Entity
@Table(name = "Kayttaja")
public class Käyttäjä {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "etunimi", nullable = false)
	private String etunimi;

	@Column(name = "sukunimi", nullable = false)
	private String sukunimi;

	@Column(name = "sahkoposti", nullable = false)
	private String sposti;

	@Column(name = "hallinnoija", nullable = false)
	private boolean hallinnoija;
	
	@Column(name = "kieli", nullable = false)
	private String kieli;

	/**
	 * Constructor.
	 */
	public Käyttäjä() {
	}
	
	/**
	 * Constructor with parameters for id,
	 * first name and last name.
	 * 
	 * @param id
	 * @param n
	 * @param m
	 */
	public Käyttäjä(int id, String n, String m) {
		this.id = id;
		this.etunimi = n;
		this.sukunimi = m;
	}

	/**
	 * Returns the value in the member
	 * variable id.
	 * 
	 * @return id
	 */
	public int getID() {
		return id;
	}

	/**
	 * Sets a new value to the member
	 * variable id.
	 * 
	 * @param id
	 */
	public void setID(int id) {
		this.id = id;
	}

	/**
	 * Returns the value in the member
	 * variable etunimi.
	 * 
	 * @return etunimi
	 */
	public String getEtunimi() {
		return etunimi;
	}

	/**
	 * Sets a new value to the member
	 * variable for first name (etunimi).
	 * 
	 * @param first name
	 */
	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	/**
	 * Returns the value in the member
	 * variable sukunimi.
	 * 
	 * @return sukunimi
	 */
	public String getSukunimi() {
		return sukunimi;
	}

	/**
	 * Sets a new value to the member
	 * variable for last name (sukunimi).
	 * 
	 * @param last name
	 */
	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}
	
	/**
	 * Returns the value in the member
	 * variable sposti.
	 * 
	 * @return sposti
	 */
	public String getSposti() {
		return sposti;
	}

	/**
	 * Sets a new value to the member
	 * variable for email (sposti).
	 * 
	 * @param email
	 */
	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	/**
	 * Returns the value in the member
	 * variable hallinnoija.
	 * 
	 * @return hallinnoija
	 */
	public boolean getOikeudet() {
		return hallinnoija;
	}

	/**
	 * Sets a new value to the member
	 * variable that determines whether
	 * the user (Käyttäjä) is an administrator
	 * (hallinnoija).
	 * 
	 * @param is administrator
	 */
	public void setOikeudet(boolean hallinnoija) {
		this.hallinnoija = hallinnoija;
	}
	
	/**
	 * Returns the value in the member
	 * variable kieli.
	 * 
	 * @return kieli
	 */
	public String getKieli() {
		return kieli;
	}

	/**
	 * Sets a new value to the member
	 * variable kieli.
	 * 
	 * @param kieli
	 */
	public void setKieli(String kieli) {
		this.kieli = kieli;
	}

}
