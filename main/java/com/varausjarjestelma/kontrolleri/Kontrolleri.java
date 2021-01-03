package com.varausjarjestelma.kontrolleri;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import com.varausjarjestelma.malli.*;

/**
 * The controller class. Serves as an intermediary
 * between the UI and the database. This class
 * is a singleton, so there is only one instance
 * of it over the program's runtime.
 * 
 * @author V. Ahlstén, E. Niemi, S. Sarviala
 *
 */
public class Kontrolleri {

	private static Kontrolleri instanssi = null;

	private TestConnection tc;
	private TilaDAO tilaDAO;
	private TilanOminaisuusDAO toDAO;
	private OminaisuusDAO ominaisuusDAO;
	private KäyttäjäDAO käyttäjäDAO;
	private VarauksetDAO varauksetDAO;

	private Kontrolleri() {
		tc = new TestConnection();
		tilaDAO = new TilaDAO();
		käyttäjäDAO = new KäyttäjäDAO();
		varauksetDAO = new VarauksetDAO();
		toDAO = new TilanOminaisuusDAO();
		ominaisuusDAO = new OminaisuusDAO();
	}

	/**
	 * Provides an instance of Kontrolleri.
	 * 
	 * @return instance of this class
	 */
	public static Kontrolleri haeInstanssi() {
		if (instanssi == null)
			instanssi = new Kontrolleri();

		return instanssi;
	}

	/**
	 * Provides an array filled with all
	 * the rooms in the database.
	 * 
	 * @return array of rooms
	 */
	public Tila[] haeTilat() {
		return tilaDAO.haeKaikkiTilat();
	}

	/**
	 * Provides an array filled with all
	 * the room attributes in the database.
	 * 
	 * @return array of attributes
	 */
	public Ominaisuus[] haeOminaisuudet() {
		return ominaisuusDAO.haeKaikkiOminaisuudet();
	}

	/**
	 * Provides an array filled with all
	 * the users in the database. Note:
	 * there is only one user in the
	 * database, used as an default.
	 * 
	 * @return array of users
	 */
	public Käyttäjä[] haeKäyttäjät() {
		return käyttäjäDAO.haeKaikkiKayttajat();
	}

	/**
	 * Returns a room (Tila) matching
	 * the id passed as an argument.
	 * 
	 * @param id
	 * @return room matching id
	 */
	public Tila etsiTila(int id) {
		return tilaDAO.etsiTila(id);
	}

	/**
	 * Inserts the room (Tila) passed
	 * as an argument into the database.
	 * Returns an boolean to signal
	 * success or failure.
	 * 
	 * @param tila
	 * @return success or failure
	 */
	public boolean lisääTila(Tila tila) {
		return tilaDAO.lisaaTila(tila);
	}

	/**
	 * Tells how many rooms there are
	 * in the database.
	 * 
	 * @return number of rooms
	 */
	public int haeTilatMaara() {
		Tila[] tilat = tilaDAO.haeKaikkiTilat();

		return tilat.length;
	}

	/**
	 * Returns an array with example data.
	 * 
	 * @return example data
	 * @throws SQLException
	 */
	public Object[] tilojenTiedotTaulukkona() throws SQLException {

		ResultSet rs = tc.haeEsimerkkiData();
		ArrayList<String> rivitListana = new ArrayList<String>();
		int riveja = 0;
		while (rs.next()) {
			riveja++;
			int id = rs.getInt("id");
			String nimi = rs.getString("nimi");
			String kuvaus = rs.getString("kuvaus");
			rivitListana.add(id + " " + nimi + " " + kuvaus);
		}
		Object[] rivitTaulukkona = rivitListana.toArray();
		for (int i = 0; i < rivitTaulukkona.length; i++) {
			System.out.println(rivitTaulukkona[i]);
		}
		System.out.println("(rivejä yhteensä: " + riveja + ")");
		return rivitTaulukkona;
	}

	/**
	 * Creates a formatted string out of the attributes
	 * of the room (Tila) matching the tilaID passed
	 * as an argument.
	 * 
	 * @param tilaID
	 * @return room attributes as formatted string
	 */
	public String näytäTilanOminaisuudetStringinä(int tilaID) {

		Kontrolleri kontrolleri = Kontrolleri.haeInstanssi();
		Tila kyseinenTila = kontrolleri.etsiTila(tilaID);
		TilanOminaisuus[] ominaisuusArray = kontrolleri.etsiTilanOminaisuudet(kyseinenTila);
		ArrayList<String> ominaisuudetListattuna = new ArrayList<String>();

		for (int i = 0; i <= ominaisuusArray.length - 1; i++) {
			TilanOminaisuus o = ominaisuusArray[i];
			String nimi = o.getOminaisuus().getNimi();
			String kuvaus = o.getOminaisuus().getKuvaus();
			ominaisuudetListattuna.add(nimi + " (" + kuvaus + ")");
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ominaisuudetListattuna.size(); i++) {
			sb.append("\n \t" + ominaisuudetListattuna.get(i));
		}
		return sb.toString();
	}

	// Varaamiseen tarvittavat metodit

	/**
	 * Returns the user matching the id
	 * passed as an argument.
	 * 
	 * @param id
	 * @return user matching id
	 */
	public Käyttäjä etsiKäyttäjä(int id) {
		return käyttäjäDAO.etsiKayttaja(id);
	}

	/**
	 * Forms a reservation (Varaukset) out of the arguments
	 * and inserts it into the database.
	 * 
	 * @param käyttäjänID
	 * @param tilaID
	 * @param alku
	 * @param loppu
	 * @return success or failure
	 */
	public boolean asetaVaraus(int käyttäjänID, int tilaID, Timestamp alku, Timestamp loppu) {
		Varaukset varaus = new Varaukset();
		Käyttäjä kayttaja = etsiKäyttäjä(käyttäjänID);
		Tila tila = etsiTila(tilaID);

		varaus.setKayttaja(kayttaja);
		varaus.setTila(tila);
		varaus.setAlkuAika(alku);
		varaus.setLoppuAika(loppu);

		return varauksetDAO.lisaaVaraus(varaus);
	}

	// Varauksen muokkaus

	
	/**
	 * Returns the reservation (Varaukset)
	 * matching the id passed as an
	 * argument.
	 * 
	 * @param id
	 * @return reservation matching id
	 */
	public Varaukset etsiVaraus(int id) {
		return varauksetDAO.etsiVaraus(id);
	}

	/**
	 * Unused operation for altering a reservation's
	 * start and end time.
	 * 
	 * @param varausID
	 * @param alku
	 * @param loppu
	 * @return success or failure
	 */
	public boolean muokkaaVarausta(int varausID, Timestamp alku, Timestamp loppu) {
		Varaukset varaus = etsiVaraus(varausID);

		varaus.setAlkuAika(alku);
		varaus.setLoppuAika(loppu);

		return varauksetDAO.muokkaaVarausta(varausID, varaus);
	}

	// Varauksen poistaminen

	/**
	 * Deletes the reservation (Varaukset)
	 * matching the id passed as an argument.
	 * 
	 * @param varausID
	 * @return success or failure
	 */
	public boolean poistaVaraus(int varausID) {
		Varaukset varaus = etsiVaraus(varausID);

		return varauksetDAO.poistaVaraus(varaus);
	}

	// Varauksien haku

	/**
	 * Returns an array of reservations (Varaukset)
	 * relating to the room (Tila) passed as an
	 * argument.
	 * 
	 * @param tila
	 * @return array of reservations
	 */
	public Varaukset[] haeVarauksetTila(Tila tila) {
		return varauksetDAO.haeVarauksetTila(tila);
	}

	/**
	 * Returns an array of reservations (Varaukset)
	 * relating to the user (Käyttäjä) passed as an
	 * argument.
	 * 
	 * @param käyttäjä
	 * @return array of reservations
	 */
	public Varaukset[] haeVaraukset(Käyttäjä käyttäjä) {
		return varauksetDAO.haeVaraukset(käyttäjä);
	}

	/**
	 * Returns an array containing all the
	 * reservations (Varaukset) in the database.
	 * 
	 * @return array of reservations
	 */
	public Varaukset[] haeKaikkiVaraukset() {
		return varauksetDAO.haeKaikkiVaraukset();
	}

	// TilanOminaisuuksien haku

	/**
	 * Returns an array of connectors (TilanOminaisuus)
	 * between a room (Tila) and an attribute (Ominaisuus)
	 * relating to the room passed as an argument.
	 * 
	 * @param tila
	 * @return array of room-attribute connectors
	 */
	public TilanOminaisuus[] etsiTilanOminaisuudet(Tila tila) {
		return toDAO.etsiTilaanLiittyvätTilanOminaisuudet(tila);
	}

	/**
	 * Returns an array of connectors (TilanOminaisuus)
	 * between a room (Tila) and an attribute (Ominaisuus)
	 * relating to the attribute passed as an argument.
	 * 
	 * @param ominaisuus
	 * @return array of room-attribute connectors
	 */
	public TilanOminaisuus[] etsiTilanOminaisuudet(Ominaisuus ominaisuus) {
		return toDAO.etsiOminaisuuteenLiittyvätTilanOminaisuudet(ominaisuus);
	}

}
