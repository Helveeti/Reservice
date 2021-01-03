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
 * relating to the TilanOminaisuus 
 * database table.
 * 
 * @author V. Ahlstén, S. Sarviala
 */
public class TilanOminaisuusDAO {

	private SessionFactory istuntotehdas;
	private StandardServiceRegistry rekisteri;

	/**
	 * A constructor in which the member variables
	 * istuntotehdas and rekisteri are initialized.
	 */
	public TilanOminaisuusDAO() {
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
	 * Returns every tilanOminaisuus in the database
	 * as an array.
	 * 
	 * @return array of tilanOminaisuus
	 */
	public TilanOminaisuus[] haeKaikkiTilanOminaisuudet() {
		Session istunto = null;
		Transaction transaktio = null;
		TilanOminaisuus[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<TilanOminaisuus> tilanOminaisuudet = istunto.createQuery("from TilanOminaisuus").getResultList();
			palautus = new TilanOminaisuus[tilanOminaisuudet.size()];

			istunto.getTransaction().commit();
			tilanOminaisuudet.toArray(palautus);

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
	 * Inserts the tilanOminaisuus passed as an argument
	 * into the database.
	 * 
	 * @param tilanOminaisuus
	 * @return success or failure
	 */
	public boolean lisaaTilanOminaisuus(TilanOminaisuus tilanOminaisuus) {
		Transaction transaktio = null;
		Session istunto = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.save(tilanOminaisuus);
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
	 * Returns a tilanOminaisuus matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return tilanOminaisuus matching id
	 */
	public TilanOminaisuus etsiTilanOminaisuus(int id) {
		TilanOminaisuus palautus = null;
		Session istunto = null;
		Transaction transaktio = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			palautus = (TilanOminaisuus) istunto.createQuery("from TilanOminaisuus where id = " + id).getSingleResult();

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
	 * Gets the values in the tilanOminaisuus
	 * passed as an argument and sets
	 * them to the tilanOminaisuus matching the
	 * id passed as an argument.
	 * 
	 * @param id
	 * @param tilanOminaisuus
	 * @return success or failure
	 */
	public boolean muokkaaTilanOminaisuutta(int id, TilanOminaisuus tilanOminaisuus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();
			TilanOminaisuus muokattava = etsiTilanOminaisuus(id);

			muokattava.setLisatiedot(tilanOminaisuus.getLisatiedot());
			muokattava.setTila(tilanOminaisuus.getTila());
			muokattava.setOminaisuus(tilanOminaisuus.getOminaisuus());

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
	 * Deletes the tilanOminaisuus passed as an argument
	 * from the database.
	 * 
	 * @param tilanOminaisuus
	 * @return success or failure
	 */
	public boolean poistaTilanOminaisuus(TilanOminaisuus tilanOminaisuus) {
		Session istunto = null;
		Transaction transaktio = null;
		boolean palautus = false;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			istunto.delete(tilanOminaisuus);
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
	 * Returns an array of tilanOminaisuus
	 * relating to the tila passed as an
	 * argument.
	 * 
	 * @param tila
	 * @return array or tilanOminaisuus
	 */
	public TilanOminaisuus[] etsiTilaanLiittyvätTilanOminaisuudet(Tila tila) {
		Session istunto = null;
		Transaction transaktio = null;
		TilanOminaisuus[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<TilanOminaisuus> tilanOminaisuudet = istunto.createQuery("from TilanOminaisuus where tila = :tila")
					.setParameter("tila", tila).getResultList();
			palautus = new TilanOminaisuus[tilanOminaisuudet.size()];

			istunto.getTransaction().commit();
			tilanOminaisuudet.toArray(palautus);

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
	 * Returns an array of tilanOminaisuus
	 * relating to the ominaisuus passed as an
	 * argument.
	 * 
	 * @param ominaisuus
	 * @return array or tilanOminaisuus
	 */
	public TilanOminaisuus[] etsiOminaisuuteenLiittyvätTilanOminaisuudet(Ominaisuus ominaisuus) {
		Session istunto = null;
		Transaction transaktio = null;
		TilanOminaisuus[] palautus = null;

		// try-with-resources ei ole tarjolla. JRE-versio-ongelma.
		try {
			istunto = istuntotehdas.openSession();
			transaktio = istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			List<TilanOminaisuus> tilanOminaisuudet = istunto
					.createQuery("from TilanOminaisuus where ominaisuus = :ominaisuus")
					.setParameter("ominaisuus", ominaisuus).getResultList();
			palautus = new TilanOminaisuus[tilanOminaisuudet.size()];

			istunto.getTransaction().commit();
			tilanOminaisuudet.toArray(palautus);

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
