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
 * relating to the Ominaisuus database
 * table.
 * 
 * @author V. Ahlstén, S. Sarviala
 *
 */
public class OminaisuusDAO {

	private SessionFactory istuntotehdas;
	private StandardServiceRegistry rekisteri;

	/**
	 * A constructor in which the member variables
	 * istuntotehdas and rekisteri are initialized.
	 */
	public OminaisuusDAO() {
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
	 * Returns every ominaisuus in the database
	 * as an array.
	 * 
	 * @return array of ominaisuus
	 */
	public Ominaisuus[] haeKaikkiOminaisuudet() {
		Session istunto = null;
		Transaction transaktio = null;
		Ominaisuus[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Ominaisuus> ominaisuudet = istunto.createQuery("from Ominaisuus").getResultList();
			palautus = new Ominaisuus[ominaisuudet.size()];

			istunto.getTransaction().commit();
			ominaisuudet.toArray(palautus);

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
	 * Inserts the ominaisuus passed as an argument
	 * into the database.
	 * 
	 * @param ominaisuus
	 * @return success or failure
	 */
	public boolean lisaaOminaisuus(Ominaisuus ominaisuus) {
		Transaction transaktio = null;
		Session istunto = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.save(ominaisuus);
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
	 * Returns an ominaisuus matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return ominaisuus matching id
	 */
	public Ominaisuus etsiOminaisuus(int id) {
		Ominaisuus palautus = null;
		Session istunto = null;
		Transaction transaktio = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			palautus = (Ominaisuus) istunto.createQuery("from Ominaisuus where id = " + id).getSingleResult();

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
	 * Gets the values in the ominaisuus
	 * passed as an argument and sets
	 * them to the ominaisuus matching the
	 * id passed as an argument.
	 * 
	 * @param id
	 * @param ominaisuus
	 * @return success or failure
	 */
	public boolean muokkaaOminaisuutta(int id, Ominaisuus ominaisuus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Ominaisuus muokattava = etsiOminaisuus(id);

			muokattava.setKuvaus(ominaisuus.getKuvaus());
			muokattava.setNimi(ominaisuus.getNimi());

			istunto.update(muokattava);
			transaktio.commit();
			
			palautus = true;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.out.println("Ominaisuuden muokkaus epäonnistui");
			e.printStackTrace();

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Deletes the ominaisuus passed as an argument
	 * from the database.
	 * 
	 * @param ominaisuus
	 * @return success or failure
	 */
	public boolean poistaOminaisuus(Ominaisuus ominaisuus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.delete(ominaisuus);
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

