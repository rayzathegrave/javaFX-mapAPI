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



}