package com.example.stalleneindhoven2.view;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RegistratieView extends GridPane {

    private TextField naamVeld;
    private DatePicker geboortedatumPicker;

    public RegistratieView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new javafx.geometry.Insets(10));

        // Maak de UI-componenten
        Label naamLabel = new Label("Naam:");
        naamVeld = new TextField();

        Label geboortedatumLabel = new Label("Geboortedatum:");
        geboortedatumPicker = new DatePicker();

        // Voeg componenten toe aan de GridPane
        this.add(naamLabel, 0, 0);
        this.add(naamVeld, 1, 0);
        this.add(geboortedatumLabel, 0, 1);
        this.add(geboortedatumPicker, 1, 1);
    }

    public TextField getNaamVeld() {
        return naamVeld;
    }

    public DatePicker getGeboortedatumPicker() {
        return geboortedatumPicker;
    }
}
