package com.varausjarjestelma.käyttöliittymä.tilanlisäys;

import com.varausjarjestelma.i18n.I18n;
import com.varausjarjestelma.kontrolleri.Kontrolleri;
import com.varausjarjestelma.malli.Tila;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * A view containing a form for inserting
 * a new space to the database.
 * 
 * @author Sebastian
 *
 */
public class TilanLisäyslomake extends HBox {

	private final Kontrolleri kontrolleri;

	/**
	 * A constructor which builds the view.
	 */
	public TilanLisäyslomake() {
		kontrolleri = Kontrolleri.haeInstanssi();

		setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, null, null)));

		VBox kolumni1 = new VBox(), kolumni2 = new VBox(), nimiBox = new VBox(), osoiteBox = new VBox(),
				kaupunkiBox = new VBox(), hlömääräBox = new VBox(), kuvausBox = new VBox();

		Label nimiLabel = I18n.stringForLabel("nimi", null, null),
				osoiteLabel = I18n.stringForLabel("osoite", null, null),
				kaupunkiLabel = I18n.stringForLabel("kaupunki", null, null),
				hlömääräLabel = I18n.stringForLabel("henkilömäärä", null, null),
				kuvausLabel = I18n.stringForLabel("kuvaus", null, null);

		nimiLabel.setTextFill(Color.WHITE);
		osoiteLabel.setTextFill(Color.WHITE);
		kaupunkiLabel.setTextFill(Color.WHITE);
		hlömääräLabel.setTextFill(Color.WHITE);
		kuvausLabel.setTextFill(Color.WHITE);

		final TextField nimi = new TextField(), osoite = new TextField(), kaupunki = new TextField();
		final TextArea kuvaus = new TextArea();
		final ObservableList<Integer> hlömääräVaihtoehdot = FXCollections.observableArrayList();

		for (int i = 1; i < 100; i++)
			hlömääräVaihtoehdot.add(i);

		final ComboBox<Integer> hlömäärä = new ComboBox<Integer>(hlömääräVaihtoehdot);
		Button vahvista = I18n.buttonForKey("button.vahvistatilanlisäys", null, null);

		vahvista.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Alert ilmoitus = null;
				Tila tila = new Tila();
				String nimiText = nimi.getText();
				boolean onnistui;

				tila.setHlomaara(hlömäärä.getValue());
				tila.setKaupunki(kaupunki.getText());
				tila.setKuvaus(kuvaus.getText());
				tila.setNakyvyys(true);
				tila.setNimi(nimiText);
				tila.setOsoite(osoite.getText());

				onnistui = kontrolleri.lisääTila(tila);

				if (onnistui) {
					ilmoitus = new Alert(AlertType.CONFIRMATION);
					ilmoitus.setHeaderText(nimiText + " on lisätty tietokantaan.");

					// palautetaan oletusarvot kenttiin
					hlömäärä.setValue(1);
					kaupunki.setText("");
					kuvaus.setText("");
					nimi.setText("");
					osoite.setText("");
				} else {
					ilmoitus = new Alert(AlertType.ERROR);
					ilmoitus.setHeaderText("Tilaa " + nimiText + " ei pystytty lisäämään.");
				}

				if (ilmoitus != null)
					ilmoitus.show();
			}

		});

		setPadding(new Insets(50));
		setSpacing(20);

		kolumni1.setSpacing(10);
		kolumni2.setSpacing(10);

		hlömäärä.setVisibleRowCount(12);
		hlömäärä.setValue(1);
		kuvaus.setPrefWidth(300);
		kuvaus.setPrefRowCount(5);
		kuvaus.setWrapText(true);

		nimiBox.getChildren().addAll(nimiLabel, nimi);
		osoiteBox.getChildren().addAll(osoiteLabel, osoite);
		kaupunkiBox.getChildren().addAll(kaupunkiLabel, kaupunki);
		hlömääräBox.getChildren().addAll(hlömääräLabel, hlömäärä);
		kuvausBox.getChildren().addAll(kuvausLabel, kuvaus);

		kolumni1.getChildren().addAll(nimiBox, osoiteBox, kaupunkiBox);
		kolumni2.getChildren().addAll(hlömääräBox, kuvausBox, vahvista);

		getChildren().addAll(kolumni1, kolumni2);
	}

}
