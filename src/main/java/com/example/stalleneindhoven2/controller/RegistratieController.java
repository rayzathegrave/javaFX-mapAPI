package com.example.stalleneindhoven2.controller;

import com.example.stalleneindhoven2.model.Registratie;
import com.example.stalleneindhoven2.view.RegistratieView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.stalleneindhoven2.util.DBCPDataSource.getConnection;

public class RegistratieController {

    private final RegistratieView view;
    private Registratie registratie;

    public RegistratieController(RegistratieView view) {
        this.view = view;
        this.registratie = new Registratie("", null); // Lege registratie als startpunt
        initialize();
    }

    private void initialize() {
        view.getNaamVeld().setOnAction(event -> handleNaamInvoer());
        view.getGeboortedatumPicker().setOnAction(event -> {
            handleGeboortedatumInvoer();
            saveToDatabase();
        });
    }

    private void handleNaamInvoer() {
        String naam = view.getNaamVeld().getText();
        registratie.setNaam(naam);
        System.out.println("Naam ingevoerd: " + registratie.getNaam());
    }

    private void handleGeboortedatumInvoer() {
        if (view.getGeboortedatumPicker().getValue() != null) {
            registratie.setGeboortedatum(view.getGeboortedatumPicker().getValue());
            System.out.println("Geboortedatum geselecteerd: " + registratie.getGeboortedatum());
        }
    }

    public static void insertRegistratie(String naam, java.time.LocalDate geboortedatum) throws Exception {
        String sqlCheckNaam = "SELECT 1 FROM Naam WHERE naam = ?";
        String sqlInsertNaam = "INSERT INTO Naam (naam) VALUES (?)";

        String sqlCheckGeboortedatum = "SELECT 1 FROM Geboortedatum WHERE geboortedatum = ?";
        String sqlInsertGeboortedatum = "INSERT INTO Geboortedatum (geboortedatum) VALUES (?)";

        String sqlInsertRegistratie = "INSERT INTO Registratie (naam, geboortedatum) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmtCheckNaam = conn.prepareStatement(sqlCheckNaam);
                 PreparedStatement stmtInsertNaam = conn.prepareStatement(sqlInsertNaam);
                 PreparedStatement stmtCheckGeboortedatum = conn.prepareStatement(sqlCheckGeboortedatum);
                 PreparedStatement stmtInsertGeboortedatum = conn.prepareStatement(sqlInsertGeboortedatum);
                 PreparedStatement stmtInsertRegistratie = conn.prepareStatement(sqlInsertRegistratie)) {

                // Check and insert into Naam table
                stmtCheckNaam.setString(1, naam);
                try (ResultSet rs = stmtCheckNaam.executeQuery()) {
                    if (!rs.next()) { // If not found, insert
                        stmtInsertNaam.setString(1, naam);
                        stmtInsertNaam.executeUpdate();
                    }
                }

                // Check and insert into Geboortedatum table
                stmtCheckGeboortedatum.setDate(1, java.sql.Date.valueOf(geboortedatum));
                try (ResultSet rs = stmtCheckGeboortedatum.executeQuery()) {
                    if (!rs.next()) { // If not found, insert
                        stmtInsertGeboortedatum.setDate(1, java.sql.Date.valueOf(geboortedatum));
                        stmtInsertGeboortedatum.executeUpdate();
                    }
                }

                // Insert into Registratie table
                stmtInsertRegistratie.setString(1, naam);
                stmtInsertRegistratie.setDate(2, java.sql.Date.valueOf(geboortedatum));
                stmtInsertRegistratie.executeUpdate();

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw new Exception("Error inserting registratie: " + e.getMessage(), e);
            }
        } catch (SQLException e) {
            throw new Exception("Error connecting to database: " + e.getMessage(), e);
        }
    }


    private void saveToDatabase() {
        System.out.println("Opslaan van gegevens in database...");

        String naam = view.getNaamVeld().getText();
        java.time.LocalDate geboortedatum = view.getGeboortedatumPicker().getValue();

        if (naam.isEmpty() || geboortedatum == null) {
            System.out.println("Vul alstublieft zowel naam als geboortedatum in.");
            return;
        }

        try {
            RegistratieController.insertRegistratie(naam, geboortedatum);
            System.out.println("Invoer succesvol opgeslagen: " + naam + ", " + geboortedatum);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fout bij het opslaan van de gegevens.");
        }
    }



}
