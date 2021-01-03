package com.varausjarjestelma.käyttöliittymä.tilojenselaus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.varausjarjestelma.i18n.I18n;
import com.varausjarjestelma.kontrolleri.Kontrolleri;
import com.varausjarjestelma.malli.Tila;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A view for browsing and reserving spaces.
 * 
 * @author E. Niemi, S. Sarviala
 *
 */
public class TilojenSelaus extends BorderPane {

	private final Kontrolleri kontrolleri;
	private final List<Pane> tilaPanet;
	private GridPane korostettu;

	/**
	 * Constructor. Fills this border pane with desired content.
	 */
	public TilojenSelaus() {
		kontrolleri = Kontrolleri.haeInstanssi();
		tilaPanet = new ArrayList<Pane>();
		korostettu = null;
		final TilePane tausta = new TilePane();
		Tila[] tilat = kontrolleri.haeTilat();

		tausta.setOnMouseClicked(new EventHandler<Event>() {

			public void handle(Event event) {
				if (event.getTarget().equals(tausta)) {
					setRight(null);
					setBottom(null);
					poistaKorostus();
				}
			}
		});

		for (final Tila tila : tilat) {
			final GridPane tilaPaneeli = new GridPane();

			Text tilannimi = I18n.stringForText("tilannimi");
			Text nimi = new Text(" " + tila.getNimi());
			Text tilankaupunki = I18n.stringForText("tilankaupunki");
			Text kaupunki = new Text(" " + tila.getKaupunki());
			Text henkilömäärä = I18n.stringForText("henkilötmäärä");
			Text hlömäärä = new Text(" " + tila.getHlomaara());
			tilaPanet.add(tilaPaneeli);

			tilaPaneeli.add(tilannimi, 0, 0);
			tilaPaneeli.add(tilankaupunki, 0, 1);
			tilaPaneeli.add(henkilömäärä, 0, 2);
			tilaPaneeli.add(nimi, 1, 0);
			tilaPaneeli.add(kaupunki, 1, 1);
			tilaPaneeli.add(hlömäärä, 1, 2);

			tilaPaneeli.setOnMouseClicked(new EventHandler<Event>() {

				public void handle(Event event) {
					korosta(tilaPaneeli);

					ScrollPane selausikkuna = new ScrollPane();
					VBox tietoikkunanSisältö = new VBox();
					Text[] otsikot = { I18n.stringForText("nimi"), I18n.stringForText("kaupunki"),
							I18n.stringForText("osoite"), I18n.stringForText("henkilömäärä"),
							I18n.stringForText("kuvaus"), I18n.stringForText("ominaisuudet") };
					String[] arvot = { tila.getNimi(), tila.getKaupunki(), tila.getOsoite(), "" + tila.getHlomaara(),
							tila.getKuvaus(), kontrolleri.näytäTilanOminaisuudetStringinä(tila.getID()) };

					tietoikkunanSisältö.setPadding(new Insets(0, 15, 0, 15));
					tietoikkunanSisältö.setSpacing(10);

					for (int i = 0; i < otsikot.length && i < arvot.length; i++) {
						VBox tiedonKappale = new VBox();
						Text otsikko = otsikot[i];
						double fonttikoko = otsikko.getFont().getSize();
						otsikko.setFont(Font.font(null, FontWeight.BOLD, fonttikoko));
						String arvo = arvot[i];
						if (arvo != null && !arvo.isEmpty()) {
							tiedonKappale.getChildren().add(otsikko);
							tiedonKappale.getChildren().add(new Text(arvo));
							tietoikkunanSisältö.getChildren().add(tiedonKappale);
						}
					}

					selausikkuna.setContent(tietoikkunanSisältö);
					selausikkuna.setMinWidth(225);
					selausikkuna.setMaxWidth(225);

					setRight(selausikkuna);

					// StackPane n = new StackPane();
					Pane varauslomake = new TilojenSelausVarauslomake().getKokoVarausLomake(tila);

					varauslomake.setPadding(new Insets(30));

					setBottom(varauslomake);

				}

			});
			tausta.getChildren().add(tilaPaneeli);
			tilaPaneeli.setPadding(new Insets(5));
			tilaPaneeli.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		}

		setCenter(tausta);
		tausta.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, null, null)));
		tausta.setHgap(15);
		tausta.setVgap(10);
		tausta.setPadding(new Insets(20));

	}

	/**
	 * Adds a golden color to the given grid pane. This is meant to communicate to
	 * the user which grid pane representing a space is selected. The golden color
	 * is removed from any previously selected grid pane.
	 * 
	 * @param korostettava
	 */
	private void korosta(GridPane korostettava) {
		poistaKorostus();

		if (korostettava != null)
			korostettava.setBackground(new Background(new BackgroundFill(Color.GOLD, null, null)));

		korostettu = korostettava;
	}

	/**
	 * Deselects the selected grid pane and removes the golden color from it.
	 */
	private void poistaKorostus() {
		if (korostettu != null) {
			korostettu.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
			korostettu = null;
		}
	}

	private void switchLanguage(Locale locale) {
		I18n.setLocale(locale);
	}

	/**
	 * Creates a horizontal box containing buttons for
	 * each supported language. Buttons are equipped with
	 * on-action event handlers that switch the UI language
	 * to the language that corresponds with the clicked button.
	 * 
	 * @return horizontal box with language options
	 */
	public HBox kieliboxi() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(5, 5, 5, 5));
		hbox.setSpacing(5);
		Button buttonEnglish = I18n.buttonForKey("button.english");
		buttonEnglish.setTooltip(I18n.tooltipForKey("button.english.tooltip"));
		buttonEnglish.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switchLanguage(Locale.ENGLISH);
			}

		});
		hbox.getChildren().add(buttonEnglish);
		Button buttonFinnish = I18n.buttonForKey("button.finnish");
		buttonFinnish.setTooltip(I18n.tooltipForKey("button.finnish.tooltip"));
		buttonFinnish.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switchLanguage(I18n.FINNISH);
			}

		});
		hbox.getChildren().add(buttonFinnish);
		return hbox;
	}

}
