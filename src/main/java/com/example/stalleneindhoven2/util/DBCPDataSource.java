package com.example.stalleneindhoven2.util;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.stalleneindhoven2.model.EbikeStalling;
import com.example.stalleneindhoven2.model.ScooterBerging;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/stallingenbergingen");
        ds.setUsername("AvansUser");
        ds.setPassword("password");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

   public static List<EbikeStalling> fetchEbikestallingen() throws Exception {
    List<EbikeStalling> ebikestallingen = new ArrayList<>();
    String sql = "SELECT stallingnaam, Longitude, Latitude FROM ebikestallingen";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String stallingNaam = rs.getString("stallingnaam");
            double longitude = rs.getDouble("longitude");
            double latitude = rs.getDouble("latitude");



            ebikestallingen.add(new EbikeStalling(stallingNaam, longitude, latitude));
        }
    } catch (Exception e) {
        throw new Exception("Error fetching ebikestallingen: " + e.getMessage());
    }

    return ebikestallingen;
}

    public static List<ScooterBerging> fetchScooterBergingen() throws Exception {
        List<ScooterBerging> scooterBergingen = new ArrayList<>();
        String sql = "SELECT bergingnaam, longitude, latitude FROM ScooterBerging"; // Ensure this matches your DB setup

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String bergingNaam = rs.getString("bergingNaam");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");

                scooterBergingen.add(new ScooterBerging(bergingNaam, longitude, latitude));
            }
        } catch (Exception e) {
            throw new Exception("Error fetching scooterbergings: " + e.getMessage());
        }

        return scooterBergingen;
    }

    public static void insertRegistratie(String naam, java.time.LocalDate geboortedatum) throws Exception {
        String sqlNaam = "INSERT INTO Naam (naam) VALUES (?)";
        String sqlGeboortedatum = "INSERT INTO Geboortedatum (geboortedatum) VALUES (?)";
        String sqlRegistratie = "INSERT INTO Registratie (naam, geboortedatum) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement stmtNaam = conn.prepareStatement(sqlNaam);
                 PreparedStatement stmtGeboortedatum = conn.prepareStatement(sqlGeboortedatum);
                 PreparedStatement stmtRegistratie = conn.prepareStatement(sqlRegistratie)) {

                // Insert into naam table
                stmtNaam.setString(1, naam);
                stmtNaam.executeUpdate();

                // Insert into geboortedatum table
                stmtGeboortedatum.setDate(1, java.sql.Date.valueOf(geboortedatum));
                stmtGeboortedatum.executeUpdate();

                // Insert into registratie table
                stmtRegistratie.setString(1, naam);
                stmtRegistratie.setDate(2, java.sql.Date.valueOf(geboortedatum));
                stmtRegistratie.executeUpdate();

                conn.commit(); // Commit transaction
            } catch (SQLException e) {
                conn.rollback(); // Rollback transaction on error
                throw new Exception("Error inserting registratie: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new Exception("Error connecting to database: " + e.getMessage());
        }
    }

   public static void saveData(String naam, String geboortedatum, String locatie, String typeReservering) throws Exception {
    try (Connection conn = getConnection()) {
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
                stmt.setString(1, geboortedatum);
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
                stmt.setString(2, geboortedatum); // Reference to Geboortedatum_reserveerder
                stmt.setString(3, locatie); // Reference to Locatie
                stmt.setString(4, typeReservering); // Reference to TypeReservering
                stmt.executeUpdate();
            }

            conn.commit(); // Commit transaction if all steps succeed
        } catch (Exception e) {
            conn.rollback(); // Rollback transaction if any step fails
            throw e; // Rethrow the exception to the caller
        }
    }
}
}