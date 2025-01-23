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

import java.sql.*;
import java.time.LocalDate;

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
            adminView.getUpdateButton().setOnAction(e -> {
                Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
                if (selectedReservering != null) {
                    handleUpdate(selectedReservering);
                } else {
                    showAlert("Fout", "Selecteer een reservering om bij te werken.");
                }
            });
            adminView.getDeleteButton().setOnAction(e -> {
                Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
                if (selectedReservering != null) {
                    handleDelete(selectedReservering);
                } else {
                    showAlert("Fout", "Selecteer een reservering om te verwijderen.");
                }
            });
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
        AdminView adminView = new AdminView(this); // Pass the controller to the AdminView
        this.adminView = adminView; // Ensure the controller uses this AdminView instance
        Scene scene = new Scene(adminView);
        stage.setScene(scene);
        stage.show();

        // Add button handlers
        adminView.getUpdateButton().setOnAction(e -> {
            Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
            if (selectedReservering != null) {
                handleUpdate(selectedReservering);
            } else {
                showAlert("Fout", "Selecteer een reservering om bij te werken.");
            }
        });
        adminView.getDeleteButton().setOnAction(e -> {
            Reservering selectedReservering = adminView.getTableView().getSelectionModel().getSelectedItem();
            if (selectedReservering != null) {
                handleDelete(selectedReservering);
            } else {
                showAlert("Fout", "Selecteer een reservering om te verwijderen.");
            }
        });

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

        // Convert geboortedatum to String for insertion
        String geboortedatumStr = geboortedatum.toString();

        // Perform the database operations in a transactional manner
        try (Connection conn = DBCPDataSource.getConnection()) {
            conn.setAutoCommit(false); // Start a transaction

            try {
                // Step 1: Insert into Naam_reserveerder (if not already present)
                String insertNaam = "INSERT IGNORE INTO Reserverder (naam_reserveerder) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertNaam)) {
                    stmt.setString(1, naam);
                    stmt.executeUpdate();
                }

                // Step 2: Insert into Geboortedatum_reserveerder (if not already present)
                String insertGeboortedatum = "INSERT IGNORE INTO Geboortedatum_reserveerder (geboortedatum) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertGeboortedatum)) {
                    stmt.setString(1, geboortedatumStr);
                    stmt.executeUpdate();
                }

                // Step 3: Insert into Locatie (if not already present)
                String insertLocatie = "INSERT IGNORE INTO Locatie (locatie) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertLocatie)) {
                    stmt.setString(1, locatie);
                    stmt.executeUpdate();
                }

                // Step 4: Insert into TypeReservering (if not already present)
                String insertTypeReservering = "INSERT IGNORE INTO TypeReservering (type_reservering) VALUES (?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertTypeReservering)) {
                    stmt.setString(1, typeReservering);
                    stmt.executeUpdate();
                }

                // Step 5: Ensure naam_reserveerder exists in Reserverder table
                String checkNaam = "SELECT COUNT(*) FROM Reserverder WHERE naam_reserveerder = ?";
                try (PreparedStatement stmt = conn.prepareStatement(checkNaam)) {
                    stmt.setString(1, naam);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            throw new Exception("naam_reserveerder does not exist in Reserverder table");
                        }
                    }
                }

                // Step 6: Insert into Reservering
                String insertReservering = """
                INSERT INTO Reservering (naam_reserveerder, geboortedatum_reserveerder, locatie, type_reservering)
                VALUES (?, ?, ?, ?)
            """;
                try (PreparedStatement stmt = conn.prepareStatement(insertReservering)) {
                    stmt.setString(1, naam); // Reference to Naam_reserveerder
                    stmt.setString(2, geboortedatumStr); // Reference to Geboortedatum_reserveerder
                    stmt.setString(3, locatie); // Reference to Locatie
                    stmt.setString(4, typeReservering); // Reference to TypeReservering
                    stmt.executeUpdate();
                }

                conn.commit(); // Commit transaction if all steps succeed
                showAlert("Succes", "De reservering is opgeslagen.");
            } catch (Exception e) {
                conn.rollback(); // Rollback transaction if any step fails
                e.printStackTrace();
                showAlert("Fout", "Er is een fout opgetreden bij het opslaan van de reservering.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Fout", "Er is een fout opgetreden bij het verbinden met de database.");
        }
    }

    public void handleUpdate(Reservering selectedReservering) {
        if (adminView == null) {
            showAlert("Fout", "Admin view is not initialized.");
            return;
        }

        Connection connection = null;
        try {
            connection = DBCPDataSource.getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Ensure 'naam_reserveerder' exists in 'Reserverder' table
            String checkNaamQuery = "SELECT COUNT(*) FROM Reserverder WHERE naam_reserveerder = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkNaamQuery)) {
                checkStmt.setString(1, selectedReservering.getNaam());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Insert if 'naam_reserveerder' does not exist
                        String insertReserverderQuery = "INSERT INTO Reserverder (naam_reserveerder) VALUES (?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertReserverderQuery)) {
                            insertStmt.setString(1, selectedReservering.getNaam());
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Step 2: Ensure 'geboortedatum_reserveerder' exists in 'Geboortedatum_reserveerder' table
            String checkGeboortedatumQuery = "SELECT COUNT(*) FROM Geboortedatum_reserveerder WHERE geboortedatum = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkGeboortedatumQuery)) {
                checkStmt.setDate(1, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Insert if 'geboortedatum_reserveerder' does not exist
                        String insertGeboortedatumQuery = "INSERT INTO Geboortedatum_reserveerder (geboortedatum) VALUES (?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertGeboortedatumQuery)) {
                            insertStmt.setDate(1, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Step 3: Ensure 'type_reservering' exists in 'TypeReservering' table
            String checkTypeReserveringQuery = "SELECT COUNT(*) FROM TypeReservering WHERE type_reservering = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkTypeReserveringQuery)) {
                checkStmt.setString(1, selectedReservering.getTypeReservering());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Insert if 'type_reservering' does not exist
                        String insertTypeReserveringQuery = "INSERT INTO TypeReservering (type_reservering) VALUES (?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertTypeReserveringQuery)) {
                            insertStmt.setString(1, selectedReservering.getTypeReservering());
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Step 4: Ensure 'locatie' exists in 'Locatie' table
            String checkLocatieQuery = "SELECT COUNT(*) FROM Locatie WHERE locatie = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkLocatieQuery)) {
                checkStmt.setString(1, selectedReservering.getLocatie());
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Insert if 'locatie' does not exist
                        String insertLocatieQuery = "INSERT INTO Locatie (locatie) VALUES (?)";
                        try (PreparedStatement insertStmt = connection.prepareStatement(insertLocatieQuery)) {
                            insertStmt.setString(1, selectedReservering.getLocatie());
                            insertStmt.executeUpdate();
                        }
                    }
                }
            }

            // Step 5: Update the 'Reservering' table
            String updateReserveringQuery = "UPDATE Reservering SET naam_reserveerder = ?, geboortedatum_reserveerder = ?, locatie = ?, type_reservering = ? " +
                    "WHERE naam_reserveerder = ? AND geboortedatum_reserveerder = ? AND locatie = ? AND type_reservering = ?";
            try (PreparedStatement updateReserveringStmt = connection.prepareStatement(updateReserveringQuery)) {
                updateReserveringStmt.setString(1, selectedReservering.getNaam());
                updateReserveringStmt.setDate(2, java.sql.Date.valueOf(selectedReservering.getGeboortedatum()));
                updateReserveringStmt.setString(3, selectedReservering.getLocatie());
                updateReserveringStmt.setString(4, selectedReservering.getTypeReservering());
                updateReserveringStmt.setString(5, selectedReservering.getOriginalNaam());
                updateReserveringStmt.setDate(6, java.sql.Date.valueOf(selectedReservering.getOriginalGeboortedatum()));
                updateReserveringStmt.setString(7, selectedReservering.getOriginalLocatie());
                updateReserveringStmt.setString(8, selectedReservering.getOriginalTypeReservering());
                updateReserveringStmt.executeUpdate();
            }

            // Commit the transaction
            connection.commit();

            showAlert("Succes", "De reservering is bijgewerkt.");
            loadTableData();  // Reload the data after the update

        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();  // Rollback if there's an error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            showAlert("Fout", "Er is een fout opgetreden bij het bijwerken van de reservering.");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }



    public void handleDelete(Reservering selectedReservering) {
        if (adminView == null) {
            showAlert("Fout", "Admin view is not initialized.");
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