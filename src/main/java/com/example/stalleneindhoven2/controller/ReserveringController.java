package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.util.DBCPDataSource;
import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
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
            showAlert("Fout", "Vul alstublieft alle velden in.");
            return;
        }

        try {
            // Save data to the database
            DBCPDataSource.saveData(
                    naam,
                    geboortedatum.toString(),
                    locatie,
                    typeReservering
            );
            showAlert("Succes", "De reservering is opgeslagen.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het opslaan van de reservering.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}