package com.example.stalleneindhoven2.view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ReserveringView extends GridPane {

    private TextField naamVeld;
    private DatePicker geboortedatumPicker;
    private ComboBox<String> locatieDropdown;
    private ComboBox<String> typeReserveringDropdown;
    public Button submitButton;

    public ReserveringView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new javafx.geometry.Insets(10));

        // Naam veld
        Label naamLabel = new Label("Volledige naam:");
        naamVeld = new TextField();

        // Geboortedatum picker
        Label geboortedatumLabel = new Label("Geboortedatum:");
        geboortedatumPicker = new DatePicker();

        // Locatie dropdown
        Label locatieLabel = new Label("Locatie:");
        locatieDropdown = new ComboBox<>();
        locatieDropdown.getItems().addAll(
                "Fietsenstalling Heuvel", "Fietsenstalling 18 Septemberplein", "Fietsenstalling NS-station zuidzijde", "Pop-up fietsenstalling Mercado",
                "Fietsenstalling Winkelcentrum", "Fietsenstalling NS-station", "blaytstalling", "sukaberging"
        );

        // Type reservering dropdown
        Label typeReserveringLabel = new Label("Type Reservering:");
        typeReserveringDropdown = new ComboBox<>();
        typeReserveringDropdown.getItems().addAll("Normaal", "Oplaadplek", "Paddelacplek");

        // listener
        locatieDropdown.setOnAction(event -> {
            String selectedLocatie = locatieDropdown.getValue();
            typeReserveringDropdown.getItems().clear();
            if ("sukaberging".equals(selectedLocatie)) {
                typeReserveringDropdown.getItems().addAll("Normaal", "Paddelacplek");
            } else if ("blaytstalling".equals(selectedLocatie)) {
                typeReserveringDropdown.getItems().addAll("Normaal", "Oplaadplek");
            } else {
                typeReserveringDropdown.getItems().addAll("Normaal", "Oplaadplek", "Paddelacplek");
            }
        });

        // Submit button
        submitButton = new Button("Opslaan");

        // Add components to the GridPane
        this.add(naamLabel, 0, 0);
        this.add(naamVeld, 1, 0);
        this.add(geboortedatumLabel, 0, 1);
        this.add(geboortedatumPicker, 1, 1);
        this.add(locatieLabel, 0, 2);
        this.add(locatieDropdown, 1, 2);
        this.add(typeReserveringLabel, 0, 3);
        this.add(typeReserveringDropdown, 1, 3);
        this.add(submitButton, 1, 4);
    }

    public TextField getNaamVeld() {
        return naamVeld;
    }

    public DatePicker getGeboortedatumPicker() {
        return geboortedatumPicker;
    }

    public ComboBox<String> getLocatieDropdown() {
        return locatieDropdown;
    }

    public ComboBox<String> getTypeReserveringDropdown() {
        return typeReserveringDropdown;
    }

    public Button getSubmitButton() {
        return submitButton;
    }
}