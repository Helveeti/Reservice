package com.varausjarjestelma.käyttöliittymä.varauksetikkuna;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.varausjarjestelma.i18n.I18n;
import com.varausjarjestelma.kontrolleri.*;
import com.varausjarjestelma.malli.Varaukset;

/**
 * A view for browsing and selecting reservations (Varaukset).
 * 
 * @author V. Ahlstén, E. Niemi, O. Närhi 
 *
 */
public class VarausIkkuna extends BorderPane {
	
	private Kontrolleri kontrolleri;
	private static Varaukset[] varaukset;
	private static Varaukset[] menneetvaraukset;
	private SplitPane root;
	
	/**
	 * Constructor.
	 */
	public VarausIkkuna() {
		kontrolleri = Kontrolleri.haeInstanssi();
		
	}
	
	private void load() { // Hae tiedot kannasta
		varaukset = menneetvaraukset = kontrolleri.haeKaikkiVaraukset();
		root = new SplitPane();
	}
	
	/**
	 * Nullifies the member list varaukset and the member variable root.
	 */
	public void clear() {
		if(varaukset != null) varaukset = null;
		if(root != null) root = null;
	}
	
	private void build() { // Aja tiedot käyttöliittymään
		TilePane pastpane = new TilePane();
		TilePane pane = new TilePane();
		Label pastLabel = I18n.stringForLabel("menneetvaraukset");
		Label futureLabel = I18n.stringForLabel("tulevatvaraukset");
		futureLabel.setTextFill(Color.WHITE);
		pastLabel.setTextFill(Color.WHITE);
		pane.getChildren().add(futureLabel);
		pastpane.getChildren().add(pastLabel);
        pane.setHgap(10);
        pane.setVgap(10);
        pastpane.setHgap(10);
        pastpane.setVgap(10);
        varaukset = sort(varaukset);
        menneetvaraukset = pastsort(menneetvaraukset);
		
		for(final Varaukset v: varaukset) {
		    Button btn = new Button(v.getTila().getNimi());
		    
		    btn.setOnAction(new EventHandler<ActionEvent>() {
		    	
		    	public void handle(ActionEvent event) {
		    		VarausPopUp pop = new VarausPopUp(kontrolleri);
		    		pop.display(v);
		    	}
		    });
		    TilePane.setMargin(btn, new Insets(20,5,5,5));
		    pane.getChildren().add(btn);
		    
		}
		
		for(final Varaukset v: menneetvaraukset) {
		    Button btn = new Button(v.getTila().getNimi());
		    
		    btn.setOnAction(new EventHandler<ActionEvent>() {
		    	
		    	public void handle(ActionEvent event) {
		    		VarausPopUp pop = new VarausPopUp(kontrolleri);
		    		pop.display(v);
		    	}
		    });
		    TilePane.setMargin(btn, new Insets(20,5,5,5));
		    pastpane.getChildren().add(btn);
		    
		}
        
        root.getItems().addAll(pane, pastpane);
        root.setBackground(new Background(new BackgroundFill(Color.DARKOLIVEGREEN, null, null)));
	}
	
	private Varaukset[] sort(Varaukset[] varaukset) { // Lajittele varaukset päivämäärän mukaan
		ArrayList<Date> list = new ArrayList<Date>();
		
		for(Varaukset v : varaukset) {
			list.add(v.getAlkuAika());
		}
		
		Collections.sort(list);
		
		System.out.println(list);
		
		List<Varaukset> sorted = new ArrayList<Varaukset>();
		Varaukset varaus;
		
		for(Date d : list) {
			for(int i = 0; i < varaukset.length; i++) {
				varaus = varaukset[i];
				if(varaus.getAlkuAika() == d && varaus.getLoppuAika().toLocalDateTime().isAfter(LocalDateTime.now())) {
					sorted.add(varaus);
				}
			}
		}
		
		return sorted.toArray(new Varaukset[sorted.size()]);
	}
	
	private Varaukset[] pastsort(Varaukset[] menneetvaraukset) { // Lajittele varaukset päivämäärän mukaan
		ArrayList<Date> list = new ArrayList<Date>();
		
		for(Varaukset v : menneetvaraukset) {
			list.add(v.getAlkuAika());
		}
		
		Collections.sort(list);
		
		System.out.println(list);
		
		List<Varaukset> sorted = new ArrayList<Varaukset>();
		Varaukset varaus;
		
		for(Date d : list) {
			for(int i = 0; i < menneetvaraukset.length; i++) {
				varaus = menneetvaraukset[i];
				if(varaus.getAlkuAika() == d && varaus.getLoppuAika().toLocalDateTime().isBefore(LocalDateTime.now())) {
					sorted.add(varaus);
				}
			}
		}
		
		return sorted.toArray(new Varaukset[sorted.size()]);
	}
	
	/**
	 * Loads the reservations (Varaukset) from database and
	 * builds a view for browsing them.
	 * 
	 * @return split pane for browsing reservations
	 */
	public SplitPane getRoot() { // Palauta näkymä
		load();
		build();
		return root;
	}

}
