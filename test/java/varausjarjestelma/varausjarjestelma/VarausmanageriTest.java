package varausjarjestelma.varausjarjestelma;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import org.junit.jupiter.api.*;

import com.varausjarjestelma.kontrolleri.Kontrolleri;
import com.varausjarjestelma.logiikka.Varausmanageri;
import com.varausjarjestelma.malli.*;

import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;

@TestMethodOrder(Alphanumeric.class)
public class VarausmanageriTest {

	private static VarauksetDAO dao;
	private static Kontrolleri kontrolleri;
	private static Varausmanageri manageri;
	private static Varaukset varaus;
	
	@BeforeAll
	public static void setup() {
		kontrolleri = Kontrolleri.haeInstanssi();
		manageri = new Varausmanageri();
		dao = new VarauksetDAO();
		varaus = new Varaukset();
		
		Timestamp alkuaika = new Timestamp(0);
		Timestamp loppuaika = new Timestamp(3600000);
		Tila tila = kontrolleri.haeTilat()[0];
		Käyttäjä käyttäjä = kontrolleri.haeKäyttäjät()[0];
		
		varaus.setKayttaja(käyttäjä);
		varaus.setTila(tila);
		varaus.setAlkuAika(alkuaika);
		varaus.setLoppuAika(loppuaika);
		
		dao.lisaaVaraus(varaus);
	}
	
	@AfterAll
	public static void teardown() {
		dao.poistaVaraus(varaus);
	}
	
	@Test
	public void tilaOnVarattu1() {
		Timestamp alkuaika = new Timestamp(1800000);
		Timestamp loppuaika = new Timestamp(5400000);
		Tila tila = varaus.getTila();
		
		assertFalse( manageri.onkoTilaVapaa(tila, alkuaika, loppuaika) );
	}
	
	@Test
	public void tilaOnVarattu2() {
		Timestamp alkuaika = new Timestamp(-1800000);
		Timestamp loppuaika = new Timestamp(1800000);
		Tila tila = varaus.getTila();
		
		assertFalse( manageri.onkoTilaVapaa(tila, alkuaika, loppuaika) );
	}
	
	@Test
	public void tilaOnVapaaEnnen() {
		Timestamp alkuaika = new Timestamp(-3600000);
		Timestamp loppuaika = new Timestamp(0);
		Tila tila = varaus.getTila();
		
		assertTrue( manageri.onkoTilaVapaa(tila, alkuaika, loppuaika) );
	}
	
	@Test
	public void tilaOnVapaaJälkeen() {
		Timestamp alkuaika = new Timestamp(3600000);
		Timestamp loppuaika = new Timestamp(7200000);
		Tila tila = varaus.getTila();
		
		assertTrue( manageri.onkoTilaVapaa(tila, alkuaika, loppuaika) );
	}
	
	
}