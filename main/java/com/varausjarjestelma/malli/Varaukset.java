package com.varausjarjestelma.malli;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A hibernate entity used to map a row
 * in the reservation (Varaukset) table in the
 * database.
 * 
 * @author V. Ahlstén
 *
 */
@Entity
@Table(name = "Varaukset")
public class Varaukset {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="kayttaja", nullable=false)
	private Käyttäjä kayttaja;

	@ManyToOne
	@JoinColumn(name="tila", nullable=false)
	private Tila tila;
	
	@Column(name = "alkuaika", nullable = false)
	private java.sql.Timestamp alkuaika;

	@Column(name = "loppuaika", nullable = false)
	private java.sql.Timestamp loppuaika;

	/**
	 * Constructor.
	 */
	public Varaukset() {
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
	 * variable kayttaja.
	 * 
	 * @return kayttaja
	 */
	public Käyttäjä getKayttaja() {
		return kayttaja;
	}

	/**
	 * Sets a new value to the member
	 * variable kayttaja.
	 * 
	 * @param kayttaja
	 */
	public void setKayttaja(Käyttäjä kayttaja) {
		this.kayttaja = kayttaja;
	}

	/**
	 * Returns the value in the member
	 * variable tila.
	 * 
	 * @return tila
	 */
	public Tila getTila() {
		return tila;
	}

	/**
	 * Sets a new value to the member
	 * variable tila.
	 * 
	 * @param tila
	 */
	public void setTila(Tila tila) {
		this.tila = tila;
	}

	/**
	 * Returns the value in the member
	 * variable alkuaika.
	 * 
	 * @return alkuaika
	 */
	public Timestamp getAlkuAika() {
		return alkuaika;
	}

	/**
	 * Sets a new value to the member
	 * variable alkuaika.
	 * 
	 * @param alkuaika
	 */
	public void setAlkuAika(Timestamp alku) {
		this.alkuaika = alku;
	}
	
	/**
	 * Returns the value in the member
	 * variable loppuaika.
	 * 
	 * @return loppuaika
	 */
	public Timestamp getLoppuAika() {
		return loppuaika;
	}

	/**
	 * Sets a new value to the member
	 * variable loppuaika.
	 * 
	 * @param loppuaika
	 */
	public void setLoppuAika(Timestamp loppu) {
		this.loppuaika = loppu;
	}

}