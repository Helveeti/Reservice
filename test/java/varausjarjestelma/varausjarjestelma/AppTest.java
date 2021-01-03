package varausjarjestelma.varausjarjestelma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;

import com.varausjarjestelma.malli.*;

@TestMethodOrder(Alphanumeric.class)
public class AppTest 
{
	private static TilaDAO tilaDAO;
	private static Tila testiTila;
	private static OminaisuusDAO ominaisuusDAO;
	private static Ominaisuus testiOminaisuus;
	private static TilanOminaisuusDAO tilanOminaisuusDAO;
	private static TilanOminaisuus testiTilanOminaisuus;
	private static VarauksetDAO varauksetDAO;
	private static Varaukset testiVaraukset;
	private static KäyttäjäDAO käyttäjäDAO;
	private static Käyttäjä testiKäyttäjä;

	@BeforeAll
	static void setup() {
		tilaDAO = new TilaDAO();
		testiTila = new Tila();

		testiTila.setNimi("Aurora");
		testiTila.setKuvaus("Helsingin yliopiston aula");
		testiTila.setKaupunki("Helsinki");
		testiTila.setOsoite("Siltavuorenpenger 10");
		testiTila.setHlomaara(15);
		testiTila.setNakyvyys(true);
		
		ominaisuusDAO = new OminaisuusDAO();
		testiOminaisuus = new Ominaisuus();
		
		testiOminaisuus.setNimi("Videotykki");
		testiOminaisuus.setKuvaus("Voit heijastaa tietokoneella olevaa tietoa valkotaululle.");
		
		tilanOminaisuusDAO = new TilanOminaisuusDAO();
		testiTilanOminaisuus = new TilanOminaisuus();
		
		testiTilanOminaisuus.setOminaisuus(testiOminaisuus);
		testiTilanOminaisuus.setTila(testiTila);
		testiTilanOminaisuus.setLisatiedot("Löytyy kaapistosta työpöydän oikealta puolelta.");
		
		käyttäjäDAO = new KäyttäjäDAO();
		testiKäyttäjä = new Käyttäjä();
		
		testiKäyttäjä.setEtunimi("Matti");
		testiKäyttäjä.setSukunimi("Meikäläinen");
		testiKäyttäjä.setSposti("matti.meikalainen@gmail.com");
		testiKäyttäjä.setOikeudet(false);
		testiKäyttäjä.setKieli("FIN");
		
		varauksetDAO = new VarauksetDAO();
		testiVaraukset = new Varaukset();
		
		Date date = new Date();
		Timestamp alku = new Timestamp(date.getTime());
		Timestamp loppu = new Timestamp(date.getTime() + 2);
		
		System.out.println("Alku: " + alku + "\nLoppu: " + loppu);
		
		testiVaraukset.setKayttaja(testiKäyttäjä);
		testiVaraukset.setTila(testiTila);
		testiVaraukset.setAlkuAika(alku);
		testiVaraukset.setLoppuAika(loppu);
	}

	@Test
	public void testALisaaTila() {
		boolean result = tilaDAO.lisaaTila(testiTila);
		assertTrue(result, "Lisäys ei onnistunut.");
	}
	
	@Test
	public void testALisaaOminaisuus() {
		boolean result = ominaisuusDAO.lisaaOminaisuus(testiOminaisuus);
		assertTrue(result, "Lisäys ei onnistunut.");
	}
	
	@Test
	public void testALisaaTilanOminaisuus() {
		boolean result = tilanOminaisuusDAO.lisaaTilanOminaisuus(testiTilanOminaisuus);
		assertTrue(result, "Lisäys ei onnistunut.");
	}
	
	@Test
	public void testALuoKäyttäjä() {
		boolean result = käyttäjäDAO.lisaaKayttaja(testiKäyttäjä);
		assertTrue(result, "Käyttäjän lisääminen ei onnistunut.");
	}

	@Test
	public void testBEtsiTila() {
		Tila tila = tilaDAO.etsiTila(testiTila.getID());
		assertEquals(testiTila.getID(), tila.getID(), "ID ei täsmännyt.");
	}
	
	@Test
	public void testBEtsiOminaisuus() {
		Ominaisuus ominaisuus = ominaisuusDAO.etsiOminaisuus(testiOminaisuus.getID());
		assertEquals(testiOminaisuus.getID(), ominaisuus.getID(), "ID ei täsmännyt.");
	}
	
	@Test
	public void testBEtsiTilanOminaisuus() {
		TilanOminaisuus tilanOminaisuus = tilanOminaisuusDAO.etsiTilanOminaisuus(testiTilanOminaisuus.getID());
		assertEquals(testiTilanOminaisuus.getID(), tilanOminaisuus.getID(), "ID ei täsmännyt.");
	}

	@Test
	public void testCMuokkaaTila() {
		testiTila.setHlomaara(10);
		boolean result = tilaDAO.muokkaaTilaa(testiTila.getID(), testiTila);
		assertTrue(result, "Muokkaus ei onnistunut.");
	}
	
	@Test
	public void testCMuokkaaOminaisuus() {
		testiOminaisuus.setKuvaus("Videotykillä voit heijastaa tietoa valkokankaalle.");
		boolean result = ominaisuusDAO.muokkaaOminaisuutta(testiOminaisuus.getID(), testiOminaisuus);
		assertTrue(result, "Muokkaus ei onnistunut.");
	}
	
	@Test
	public void testCMuokkaaTilanOminaisuus() {
		testiTilanOminaisuus.setLisatiedot("Videotykki on näkyvillä työpöydällä, mutta mikäli se ei ole siinä, on se mitä luultavimmin kaapistossa vasemmalla puolella.");
		boolean result = tilanOminaisuusDAO.muokkaaTilanOminaisuutta(testiTilanOminaisuus.getID(), testiTilanOminaisuus);
		assertTrue(result, "Muokkaus ei onnistunut.");
	}
	
	@Test
	public void testCMuokkaaKäyttäjää() {
		testiKäyttäjä.setEtunimi("Marko");
		boolean result = käyttäjäDAO.muokkaaKayttaja(testiKäyttäjä.getID(), testiKäyttäjä);
		assertTrue(result, "Käyttäjän muokkaus ei onnistunut.");
	}
	
	@Test
	public void testDLisääVaraus() {
		boolean result = varauksetDAO.lisaaVaraus(testiVaraukset);
		assertTrue(result, "Varauksen lisäys ei onnistunut.");
	}
	
	@Test
	public void testEMuokkaaVarausta() {
		Date date = new Date();
		Timestamp uusiAlku = new Timestamp(date.getTime() + 1);
		Timestamp uusiLoppu = new Timestamp(date.getTime() + 3);
		testiVaraukset.setAlkuAika(uusiAlku);
		testiVaraukset.setLoppuAika(uusiLoppu);
		
		boolean result = varauksetDAO.muokkaaVarausta(testiVaraukset.getID(), testiVaraukset);
		assertTrue(result, "Varauksen muokkaaminen ei onnistunut.");
	}
	
	@Test
	public void testFPoistaVaraus() {
		boolean result = varauksetDAO.poistaVaraus(testiVaraukset);
		assertTrue(result, "Varauksen poistaminen ei onnistunut.");
	}
	
	@Test
	public void testGPoistaKäyttäjä() {
		boolean result = käyttäjäDAO.poistaKayttaja(testiKäyttäjä);
		assertTrue(result, "Poistaminen ei onnistunut.");
	}

	@Test
	public void testGPoistaTila() {
		boolean result = tilaDAO.poistaTila(testiTila);
		assertTrue(result, "Poistaminen ei onnistunut.");
	}
	
	@Test
	public void testGPoistaOminaisuus() {
		boolean result = ominaisuusDAO.poistaOminaisuus(testiOminaisuus);
		assertTrue(result, "Poistaminen ei onnistunut.");
	}
	
	@Test
	public void testGPoistaTilanOminaisuus() {
		boolean result = tilanOminaisuusDAO.poistaTilanOminaisuus(testiTilanOminaisuus);
		assertTrue(result, "Poistaminen ei onnistunut.");
	}
}
