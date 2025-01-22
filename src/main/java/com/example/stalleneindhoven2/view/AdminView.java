package com.example.stalleneindhoven2.view;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class AdminView extends GridPane {

    private TextField naamVeld;
    private DatePicker geboortedatumPicker;
    private ComboBox<String> locatieDropdown;
    private ComboBox<String> typeReserveringDropdown;
    private Button updateButton;
    private Button deleteButton;

    public AdminView() {
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new javafx.geometry.Insets(10));

        // Naam veld
        Label naamLabel = new Label("Naam:");
        naamVeld = new TextField();

        // Geboortedatum picker
        Label geboortedatumLabel = new Label("Geboortedatum:");
        geboortedatumPicker = new DatePicker();

        // Locatie dropdown
        Label locatieLabel = new Label("Locatie:");
        locatieDropdown = new ComboBox<>();
        locatieDropdown.getItems().addAll(
                "Amsterdam", "Rotterdam", "Utrecht", "Den Haag",
                "Eindhoven", "Maastricht", "Groningen", "Leeuwarden"
        );

        // Type reservering dropdown
        Label typeReserveringLabel = new Label("Type Reservering:");
        typeReserveringDropdown = new ComboBox<>();
        typeReserveringDropdown.getItems().addAll("Privé", "Zakelijk", "Groepsreservering");

        // Update and Delete buttons
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        // Add components to the GridPane
        this.add(naamLabel, 0, 0);
        this.add(naamVeld, 1, 0);
        this.add(geboortedatumLabel, 0, 1);
        this.add(geboortedatumPicker, 1, 1);
        this.add(locatieLabel, 0, 2);
        this.add(locatieDropdown, 1, 2);
        this.add(typeReserveringLabel, 0, 3);
        this.add(typeReserveringDropdown, 1, 3);
        this.add(updateButton, 0, 4);
        this.add(deleteButton, 1, 4);
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

    public Button getUpdateButton() {
        return updateButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }
}
