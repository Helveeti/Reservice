package com.varausjarjestelma.malli;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Data Access Object for operations
 * relating to the Tila database
 * table.
 * 
 * @author V. Ahlstén, S. Sarviala
 */
public class TilaDAO {

	private SessionFactory istuntotehdas;
	private StandardServiceRegistry rekisteri;

	/**
	 * A constructor in which the member variables
	 * istuntotehdas and rekisteri are initialized.
	 */
	public TilaDAO() {
		istuntotehdas = null;
		rekisteri = null;
		
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
			rekisteri = new StandardServiceRegistryBuilder().configure().build();
		} catch (Exception e) {
			System.out.println("TilaDAO - istuntotehtaan luonti epäonnistui");
			istuntotehdas.close();
			StandardServiceRegistryBuilder.destroy(rekisteri);
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Returns every tila in the database
	 * as an array.
	 * 
	 * @return array of tila
	 */
	public Tila[] haeKaikkiTilat() {
		Session istunto = null;
		Transaction transaktio = null;
		Tila[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Tila> tilat = istunto.createQuery("from Tila").getResultList();
			palautus = new Tila[tilat.size()];

			istunto.getTransaction().commit();
			tilat.toArray(palautus);

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println(e.getMessage());

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Inserts the tila passed as an argument
	 * into the database.
	 * 
	 * @param tila
	 * @return success or failure
	 */
	public boolean lisaaTila(Tila tila) {
		Transaction transaktio = null;
		Session istunto = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.save(tila);
			transaktio.commit();
			
			palautus = true;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			e.printStackTrace();
		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Returns a tila matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return tila matching id
	 */
	public Tila etsiTila(int id) {
		Tila palautus = null;
		Session istunto = null;
		Transaction transaktio = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			palautus = (Tila) istunto.createQuery("from Tila where id = " + id).getSingleResult();

			istunto.getTransaction().commit();

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println(e.getMessage());

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Gets the values in the tila
	 * passed as an argument and sets
	 * them to the tila matching the
	 * id passed as an argument.
	 * 
	 * @param id
	 * @param tila
	 * @return success or failure
	 */
	public boolean muokkaaTilaa(int id, Tila tila) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Tila muokattava = etsiTila(id);

			muokattava.setHlomaara(tila.getHlomaara());
			muokattava.setKaupunki(tila.getKaupunki());
			muokattava.setKuvaus(tila.getKuvaus());
			muokattava.setNakyvyys(tila.getNakyvyys());
			muokattava.setNimi(tila.getNimi());
			muokattava.setOsoite(tila.getOsoite());

			istunto.update(muokattava);
			transaktio.commit();
			
			palautus = true;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.out.println("Tilan muokkaus epäonnistui");
			e.printStackTrace();

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Deletes the tila passed as an argument
	 * from the database.
	 * 
	 * @param tila
	 * @return success or failure
	 */
	public boolean poistaTila(Tila tila) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.delete(tila);
			transaktio.commit();
			
			palautus = true;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.err.println(e.getMessage());

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Closes istuntotehdas and destroys rekisteri
	 * when the application closes.
	 */
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		if (istuntotehdas != null)
			istuntotehdas.close();
		
		if (rekisteri != null)
			StandardServiceRegistryBuilder.destroy(rekisteri);
	}

}
