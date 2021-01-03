package com.varausjarjestelma.malli;
import javax.persistence.*;

/**
 * A hibernate entity used to map a row
 * in the room-attribute connector 
 * (TilanOminaisuus) table in the database.
 * 
 * @author V. Ahlstén
 *
 */
@Entity
@Table(name="TilanOminaisuus")
public class TilanOminaisuus {
	@Id
	@GeneratedValue	
	@Column(name="id", nullable=false)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="tila", nullable=false)
	private Tila tila;
	
	@ManyToOne
	@JoinColumn(name="ominaisuus", nullable=false)
	private Ominaisuus ominaisuus;
	
	@Column(name="lisatietoa", length=500)
	private String lisatietoa;
	
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
	 * variable ominaisuus.
	 * 
	 * @return ominaisuus
	 */
	public Ominaisuus getOminaisuus() {
		return ominaisuus;
	}

	/**
	 * Sets a new value to the member
	 * variable ominaisuus.
	 * 
	 * @param ominaisuus
	 */
	public void setOminaisuus(Ominaisuus ominaisuus) {
		this.ominaisuus = ominaisuus;
	}

	/**
	 * Returns the value in the member
	 * variable lisatietoa.
	 * 
	 * @return lisatietoa
	 */
	public String getLisatiedot() {
		return lisatietoa;
	}

	/**
	 * Sets a new value to the member
	 * variable lisatietoa.
	 * 
	 * @param lisatietoa
	 */
	public void setLisatiedot(String lisatietoa) {
		this.lisatietoa = lisatietoa;
	}
	
}
