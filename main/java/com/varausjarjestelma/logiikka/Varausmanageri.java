package com.varausjarjestelma.logiikka;

import java.sql.Timestamp;
import java.time.LocalDate;

import com.varausjarjestelma.kontrolleri.Kontrolleri;
import com.varausjarjestelma.malli.Tila;
import com.varausjarjestelma.malli.Varaukset;

import javafx.scene.control.ChoiceBox;

/**
 * A singleton class providing operations for
 * reservation validation.
 * 
 * @author V. Ahlstén, E. Niemi, O. Närhi, S. Sarviala
 *
 */
public class Varausmanageri {

	private final Kontrolleri kontrolleri;
	static Timestamp aloitusaika;
	static String varatuntilannimi;

	/**
	 * Constructor.
	 */
	public Varausmanageri() {
		kontrolleri = Kontrolleri.haeInstanssi();
	}

	public boolean onkoTilaVapaa(Tila tila, Timestamp alku, Timestamp loppu) {
		Varaukset[] varaukset = kontrolleri.haeVarauksetTila(tila);

		for (Varaukset v : varaukset) {
			Timestamp vAlku = v.getAlkuAika();
			Timestamp vLoppu = v.getLoppuAika();

			if ((vAlku.compareTo(alku) >= 0 && vAlku.compareTo(loppu) < 0)
					|| (vLoppu.compareTo(alku) > 0 && vLoppu.compareTo(loppu) <= 0)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Edits the values passed as arguments and
	 * passes them on the controller (Kontrolleri)
	 * for further processing. End result is a new
	 * reservation inserted into the database.
	 * 
	 * @param alkuTuntiPicker
	 * @param loppuTuntiPicker
	 * @param varauksenAloitusPäivä
	 * @param varauksenLopetusPäivä
	 * @param tilanId
	 */
	public void varaaTila(ChoiceBox<Integer> alkuTuntiPicker, ChoiceBox<Integer> loppuTuntiPicker,
			LocalDate varauksenAloitusPäivä, LocalDate varauksenLopetusPäivä, int tilanId) {

		int alkuTunti = alkuTuntiPicker.getValue();
		int loppuTunti = loppuTuntiPicker.getValue();

		LocalDate alkuInit = varauksenAloitusPäivä;
		LocalDate loppuInit = varauksenLopetusPäivä;

		Timestamp alkuTs = Timestamp.valueOf(alkuInit.atTime(alkuTunti, 0));
		Timestamp loppuTs = Timestamp.valueOf(loppuInit.atTime(loppuTunti, 0));
		System.out.println(alkuTs);
		System.out.println(loppuTs);
		aloitusaika = alkuTs;
		varatuntilannimi = kontrolleri.etsiTila(tilanId).getNimi();
		System.out.println(aloitusaika + " " + varatuntilannimi);

		kontrolleri.asetaVaraus(4, tilanId, alkuTs, loppuTs);

	}

	/**
	 * Formats an email containing the values in the
	 * member variable varatuntilannimi and aloitusaika.
	 * 
	 * @return email in string form
	 */
	public String varaustiedotMailiin() {
		String returni = "" + varatuntilannimi + ", on varattu teille alkaen " + aloitusaika + ".";
		return returni;

	}

}
