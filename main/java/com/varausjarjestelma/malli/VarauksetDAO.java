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
 * relating to the Varaukset database
 * table.
 * 
 * @author V. Ahlstén, S. Sarviala
 */
public class VarauksetDAO {
	
	private SessionFactory istuntotehdas;
	private StandardServiceRegistry rekisteri;

	/**
	 * A constructor in which the member variables
	 * istuntotehdas and rekisteri are initialized.
	 */
	public VarauksetDAO() {
		
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
	 * Returns every varaukset in the database
	 * as an array.
	 * 
	 * @return array of varaukset
	 */
	public Varaukset[] haeKaikkiVaraukset() {
		Session istunto = null;
		Transaction transaktio = null;
		Varaukset[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<Varaukset> varaukset = istunto.createQuery("from Varaukset").getResultList();
			palautus = new Varaukset[varaukset.size()];

			istunto.getTransaction().commit();
			varaukset.toArray(palautus);

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
	 * Inserts the varaukset passed as an argument
	 * into the database.
	 * 
	 * @param varaus
	 * @return success or failure
	 */
	public boolean lisaaVaraus(Varaukset varaus) {
		Transaction transaktio = null;
		Session istunto = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.save(varaus);
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
	 * Returns a varaukset matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return varaukset matching id
	 */
	public Varaukset etsiVaraus(int id) {
		Varaukset palautus = null;
		Session istunto = null;
		Transaction transaktio = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			palautus = (Varaukset) istunto.createQuery("from Varaukset where id = " + id).getSingleResult();

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
	 * Gets the values in the varaukset
	 * passed as an argument and sets
	 * them to the varaukset matching the
	 * id passed as an argument.
	 * 
	 * @param id
	 * @param varaus
	 * @return success or failure
	 */
	public boolean muokkaaVarausta(int id, Varaukset varaus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			Varaukset muokattava = etsiVaraus(id);

			muokattava.setAlkuAika(varaus.getAlkuAika());
			muokattava.setLoppuAika(varaus.getLoppuAika());

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
	 * Deletes the varaukset passed as an argument
	 * from the database.
	 * 
	 * @param varaus
	 * @return success or failure
	 */
	public boolean poistaVaraus(Varaukset varaus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.delete(varaus);
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
	 * Returns an array of varaukset
	 * relating to the tila passed as an
	 * argument.
	 * 
	 * @param tila
	 * @return array or varaukset
	 */
	public Varaukset[] haeVarauksetTila(Tila tila) {
		Session istunto = null;
		Transaction transaktio = null;
		Varaukset[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Varaukset> varaukset = istunto.createQuery("from Varaukset where tila = :tila").setParameter("tila", tila).getResultList();
			palautus = new Varaukset[varaukset.size()];
			
			transaktio.commit();
			
			varaukset.toArray(palautus);

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
	 * Returns an array of varaukset
	 * relating to the käyttäjä passed as an
	 * argument.
	 * 
	 * @param tila
	 * @return array or varaukset
	 */
	public Varaukset[] haeVaraukset(Käyttäjä käyttäjä) {
		Session istunto = null;
		Transaction transaktio = null;
		Varaukset[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<Varaukset> varaukset = istunto.createQuery("from Varaukset where kayttaja = :kayttaja").setParameter("kayttaja", käyttäjä).getResultList();
			palautus = new Varaukset[varaukset.size()];
			
			transaktio.commit();
			
			varaukset.toArray(palautus);

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
