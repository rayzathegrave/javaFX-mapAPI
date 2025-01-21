package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ReserveringController {

    private final ReserveringView view;

    public ReserveringController(ReserveringView view) {
        this.view = view;
        initialize();
    }

    private void initialize() {
        Button submitButton = view.getSubmitButton();
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        String naam = view.getNaamVeld().getText();
        java.time.LocalDate geboortedatum = view.getGeboortedatumPicker().getValue();
        String locatie = view.getLocatieDropdown().getValue();
        String typeReservering = view.getTypeReserveringDropdown().getValue();

        if (naam.isEmpty() || geboortedatum == null || locatie == null || typeReservering == null) {
            System.out.println("Vul alstublieft alle velden in.");
            return;
        }

        System.out.println("Invoer ontvangen:");
        System.out.println("Naam: " + naam);
        System.out.println("Geboortedatum: " + geboortedatum);
        System.out.println("Locatie: " + locatie);
        System.out.println("Type Reservering: " + typeReservering);

        // Hier kan je de data opslaan in een database of andere verwerking uitvoeren
    }
}