package com.varausjarjestelma.malli;

import javax.persistence.*;

/**
 * A hibernate entity used to map a row
 * in the attribute (Ominaisuus) table in the
 * database.
 * 
 * @author V. Ahlstén
 *
 */
@Entity
@Table(name="Ominaisuus")
public class Ominaisuus {
	@Id
	@GeneratedValue	
	@Column(name="id", nullable=false)
	private int id;
	
	@Column(name="nimi", nullable=false)
	private String nimi;
	
	@Column(name="kuvaus", length=500)
	private String kuvaus;
	
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

}
