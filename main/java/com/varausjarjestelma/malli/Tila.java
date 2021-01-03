package com.varausjarjestelma.malli;

import javax.persistence.*;

/**
 * A hibernate entity used to map a row
 * in the room (Tila) table in the
 * database.
 * 
 * @author V. Ahlstén
 *
 */
@Entity
@Table(name = "Tila")
public class Tila {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "nimi", nullable = false)
	private String nimi;

	@Column(name = "kuvaus", length = 500)
	private String kuvaus;

	@Column(name = "osoite", nullable = false)
	private String osoite;

	@Column(name = "hlomaara", nullable = false)
	private int hlomaara;

	@Column(name = "nakyvyys", nullable = false)
	private boolean nakyvyys;

	@Column(name = "kaupunki", nullable = false)
	private String kaupunki;
	
	/**
	 * Constructor.
	 */
	public Tila() {
	}
	
	/**
	 * Constructor with parameters for id and name (nimi).
	 * 
	 * @param id
	 * @param n
	 */
	public Tila(int id, String n ) {
		this.id = id;
		this.nimi = n;
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
	 * variable nimi.
	 * 
	 * @return nimi
	 */
	public String getNimi() {
		return nimi;
	}

	/**
	 * Sets a new value to the member
	 * variable nimi.
	 * 
	 * @param nimi
	 */
	public void setNimi(String nimi) {
		this.nimi = nimi;
	}

	/**
	 * Returns the value in the member
	 * variable kuvaus.
	 * 
	 * @return kuvaus
	 */
	public String getKuvaus() {
		return kuvaus;
	}

	/**
	 * Sets a new value to the member
	 * variable kuvaus.
	 * 
	 * @param kuvaus
	 */
	public void setKuvaus(String kuvaus) {
		this.kuvaus = kuvaus;
	}

	/**
	 * Returns the value in the member
	 * variable hlomaara.
	 * 
	 * @return hlomaara
	 */
	public int getHlomaara() {
		return hlomaara;
	}

	/**
	 * Sets a new value to the member
	 * variable hlomaara.
	 * 
	 * @param hlomaara
	 */
	public void setHlomaara(int hlomaara) {
		this.hlomaara = hlomaara;
	}

	/**
	 * Returns the value in the member
	 * variable kaupunki.
	 * 
	 * @return kaupunki
	 */
	public String getKaupunki() {
		return kaupunki;
	}

	/**
	 * Sets a new value to the member
	 * variable kaupunki.
	 * 
	 * @param kaupunki
	 */
	public void setKaupunki(String kaupunki) {
		this.kaupunki = kaupunki;
	}

	/**
	 * Returns the value in the member
	 * variable osoite.
	 * 
	 * @return osoite
	 */
	public String getOsoite() {
		return osoite;
	}

	/**
	 * Sets a new value to the member
	 * variable osoite.
	 * 
	 * @param osoite
	 */
	public void setOsoite(String osoite) {
		this.osoite = osoite;
	}

	/**
	 * Returns the value in the member
	 * variable nakyvyys.
	 * 
	 * @return nakyvyys
	 */
	public boolean getNakyvyys() {
		return nakyvyys;
	}

	/**
	 * Sets a new value to the member
	 * variable nakyvyys.
	 * 
	 * @param nakyvyys
	 */
	public void setNakyvyys(boolean nakyvyys) {
		this.nakyvyys = nakyvyys;
	}

}
