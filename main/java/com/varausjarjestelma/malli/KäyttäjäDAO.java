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
 * relating to the Kayttaja database
 * table.
 * 
 * @author V. Ahlstén, S. Sarviala
 *
 */
public class KäyttäjäDAO {
	
	private SessionFactory istuntotehdas;
	private StandardServiceRegistry rekisteri;

	/**
	 * A constructor in which the member variables
	 * istuntotehdas and rekisteri are initialized.
	 */
	public KäyttäjäDAO() {
		
		istuntotehdas = null;
		rekisteri = null;
		
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
			rekisteri = new StandardServiceRegistryBuilder().configure().build();
		} catch (Exception e) {
			System.out.println("KäyttäjäDAO - istuntotehtaan luonti epäonnistui");
			istuntotehdas.close();
			StandardServiceRegistryBuilder.destroy(rekisteri);
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Returns an array containing all the
	 * Käyttäjä entities in the database.
	 * 
	 * @return array of users
	 */
	public Käyttäjä[] haeKaikkiKayttajat() {
		Session istunto = null;
		Transaction transaktio = null;
		Käyttäjä[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Käyttäjä> käyttäjät = istunto.createQuery("from Käyttäjä").getResultList();
			palautus = new Käyttäjä[käyttäjät.size()];

			istunto.getTransaction().commit();
			käyttäjät.toArray(palautus);

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
	 * Inserts the käyttäjä passed as an argument
	 * into the database.
	 * 
	 * @param käyttäjä
	 * @return success or failure
	 */
	public boolean lisaaKayttaja(Käyttäjä käyttäjä) {
		Transaction transaktio = null;
		Session istunto = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.save(käyttäjä);
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
	 * Returns a Käyttäjä matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return user matching id
	 */
	public Käyttäjä etsiKayttaja(int id) {
		Käyttäjä palautus = null;
		Session istunto = null;
		Transaction transaktio = null;

		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			palautus = (Käyttäjä) istunto.createQuery("from Käyttäjä where id = " + id).getSingleResult();

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
	 * Alters a Käyttäjä in the database.
	 * Gets the values in the käyttäjä
	 * passed as an argument and sets
	 * them to the käyttäjä matching the
	 * id passed as an argument.
	 * 
	 * @param id
	 * @param käyttäjä
	 * @return success or failure
	 */
	public boolean muokkaaKayttaja(int id, Käyttäjä käyttäjä) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Käyttäjä muokattava = etsiKayttaja(id);

			muokattava.setEtunimi(käyttäjä.getEtunimi());
			muokattava.setSukunimi(käyttäjä.getSukunimi());
			muokattava.setSposti(käyttäjä.getSposti());

			istunto.update(muokattava);
			transaktio.commit();
			
			palautus = true;

		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			System.out.println("Käyttäjän muokkaus epäonnistui");
			e.printStackTrace();

		} finally {
			if (istunto != null)
				istunto.close();
		}

		return palautus;
	}

	/**
	 * Deletes the käyttäjä passed as an argument
	 * from the database.
	 * 
	 * @param käyttäjä
	 * @return success or failure
	 */
	public boolean poistaKayttaja(Käyttäjä käyttäjä) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.delete(käyttäjä);
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
