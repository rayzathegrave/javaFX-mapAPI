package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.util.DBCPDataSource;
import com.example.stalleneindhoven2.view.AdminLoginView;
import com.example.stalleneindhoven2.view.AdminView;
import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ReserveringController {

    private static final String ADMIN_CODE = "admin123"; // Example admin code
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

    public void showAdminLogin(Stage stage) {
        AdminLoginView adminLoginView = new AdminLoginView();
        Scene scene = new Scene(adminLoginView);
        stage.setScene(scene);
        stage.show();

        adminLoginView.getLoginButton().setOnAction(e -> {
            String enteredCode = adminLoginView.getAdminCodeField().getText();
            if (ADMIN_CODE.equals(enteredCode)) {
                showAdminView(stage);
            } else {
                showAlert("Error", "Invalid admin code");
            }
        });
    }

    private void showAdminView(Stage stage) {
        AdminView adminView = new AdminView();
        Scene scene = new Scene(adminView);
        stage.setScene(scene);
        stage.show();

        adminView.getUpdateButton().setOnAction(e -> {
            // Implement update logic here
        });

        adminView.getDeleteButton().setOnAction(e -> {
            // Implement delete logic here
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}