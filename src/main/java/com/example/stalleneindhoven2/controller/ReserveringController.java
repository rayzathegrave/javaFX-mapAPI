package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.model.Reservering;
import com.example.stalleneindhoven2.util.DBCPDataSource;
import com.example.stalleneindhoven2.view.AdminLoginView;
import com.example.stalleneindhoven2.view.AdminView;
import com.example.stalleneindhoven2.view.ReserveringView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReserveringController {

    private static final String ADMIN_CODE = "admin123"; // Example admin code
    private final ReserveringView view;
    private AdminView adminView;

    public ReserveringController(ReserveringView view) {
        this.view = view;
        this.adminView = null;
        initialize();
    }

    public ReserveringController(AdminView adminView) {
        this.view = null;
        this.adminView = adminView;
        initializeAdmin();
    }

    private void initialize() {
        Button submitButton = view.getSubmitButton();
        submitButton.setOnAction(event -> handleSubmit());
    }

    private void initializeAdmin() {
        if (adminView != null) {
            adminView.getUpdateButton().setOnAction(e -> handleUpdate());
            adminView.getDeleteButton().setOnAction(e -> handleDelete());
            loadTableData();
        }
    }



    private void loadTableData() {
        if (adminView == null) {
            System.out.println("AdminView is null; cannot load data.");
            return;
        }

        ObservableList<Reservering> data = FXCollections.observableArrayList();

        try (Connection connection = DBCPDataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Reservering")) {

            System.out.println("Connected to the database. Executing query...");

            while (resultSet.next()) {
                Reservering reservering = new Reservering(
                        resultSet.getString("naam_reserveerder"),
                        resultSet.getDate("geboortedatum_reserveerder").toLocalDate(),
                        resultSet.getString("locatie"),
                        resultSet.getString("type_reservering")
                );
                System.out.println("Loaded row: " + reservering.getNaam());
                data.add(reservering);
            }

            System.out.println("Data loaded: " + data.size() + " rows");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading data: " + e.getMessage());
        }

        Platform.runLater(() -> adminView.setTableData(data));
    }

    private void showAdminView(Stage stage) {
        AdminView adminView = new AdminView();
        this.adminView = adminView; // Ensure the controller uses this AdminView instance
        Scene scene = new Scene(adminView);
        stage.setScene(scene);
        stage.show();

        // Add button handlers
        adminView.getUpdateButton().setOnAction(e -> handleUpdate());
        adminView.getDeleteButton().setOnAction(e -> handleDelete());

        // Load data into the table
        loadTableData();
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

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Reservering (naam_reserveerder, geboortedatum_reserveerder, locatie, type_reservering) VALUES (?, ?, ?, ?)")) {

            statement.setString(1, naam);
            statement.setDate(2, java.sql.Date.valueOf(geboortedatum));
            statement.setString(3, locatie);
            statement.setString(4, typeReservering);
            statement.executeUpdate();

            showAlert("Succes", "De reservering is opgeslagen.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het opslaan van de reservering.");
        }
    }

    private void handleUpdate() {
        if (adminView == null) {
            showAlert("Fout", "Admin view is not initialized.");
            return;
        }
        Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
        if (selectedReservering == null) {
            showAlert("Fout", "Selecteer een reservering om bij te werken.");
            return;
        }

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Reservering SET geboortedatum_reserveerder = ?, locatie = ?, type_reservering = ? " +
                             "WHERE naam_reserveerder = ? AND geboortedatum_reserveerder = ? AND type_reservering = ? AND locatie = ?")) {
            statement.setDate(1, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
            statement.setString(2, selectedReservering.getLocatie());
            statement.setString(3, selectedReservering.getTypeReservering());
            statement.setString(4, selectedReservering.getNaam());

            // Set the original primary key values for the WHERE clause
            statement.setDate(5, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
            statement.setString(6, selectedReservering.getTypeReservering());
            statement.setString(7, selectedReservering.getLocatie());

            statement.executeUpdate();
            showAlert("Succes", "De reservering is bijgewerkt.");
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het bijwerken van de reservering.");
        }
    }

    private void handleDelete() {
        if (adminView == null) {
            showAlert("Fout", "Admin view is not initialized.");
            return;
        }
        Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
        if (selectedReservering == null) {
            showAlert("Fout", "Selecteer een reservering om te verwijderen.");
            return;
        }

        try (Connection connection = DBCPDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Reservering WHERE naam_reserveerder = ? AND geboortedatum_reserveerder = ? AND type_reservering = ? AND locatie = ?")) {
            statement.setString(1, selectedReservering.getNaam());
            statement.setDate(2, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
            statement.setString(3, selectedReservering.getTypeReservering());
            statement.setString(4, selectedReservering.getLocatie());
            statement.executeUpdate();
            showAlert("Succes", "De reservering is verwijderd.");
            loadTableData();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het verwijderen van de reservering.");
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



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}