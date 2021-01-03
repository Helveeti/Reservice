package com.varausjarjestelma.käyttöliittymä;

import com.varausjarjestelma.käyttöliittymä.tilanlisäys.TilanLisäyslomake;
import com.varausjarjestelma.käyttöliittymä.tilojenselaus.TilojenSelaus;
import com.varausjarjestelma.käyttöliittymä.varauksetikkuna.VarausIkkuna;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main class where the UI is initialized and launched.
 * 
 * @author V. Ahlstén, E. Niemi, O. Närhi, S. Sarviala
 *
 */
public class TempMain extends Application {

	/**
	 * Overrides a method from Application.
	 * <a href="https://docs.oracle.com/javase/8/javafx/api/toc.htm">Information
	 * about JavaFX class Application.</a> The default scene is set up and made
	 * visible.
	 * 
	 * @param stage to set the scene on
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		BorderPane mainPane = new BorderPane();

		TabPane tabPane = new TabPane();

		TilojenSelaus tilojenselaus = new TilojenSelaus();
		SplitPane varausikkuna = new VarausIkkuna().getRoot();
		TilanLisäyslomake tilanlisäys = new TilanLisäyslomake();

		final Tab tab1 = new Tab();
		tab1.setContent(tilojenselaus);
		tab1.setText("Tilat");
		tab1.closableProperty().setValue(false);
		final Tab tab2 = new Tab();
		tab2.setContent(varausikkuna);
		tab2.setText("Varaukset");
		tab2.closableProperty().setValue(false);
		Tab tab3 = new Tab();
		tab3.setContent(tilanlisäys);
		tab3.setText("Lisää uusi tila");

		tabPane.getTabs().addAll(tab1, tab2, tab3);

		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
				System.out.println("Tabi vaihdettu");
				tab2.setContent(new VarausIkkuna().getRoot());
				tab1.setContent(new TilojenSelaus());
			}
		});

		mainPane.setTop(new TilojenSelaus().kieliboxi());
		mainPane.setCenter(tabPane);

		primaryStage.setScene(new Scene(mainPane, 1000, 600));
		primaryStage.show();
	}

	/**
	 * The method called when the application launches. Notifies the stage to launch
	 * as well.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
