package com.example.stalleneindhoven2.util;

import com.example.stalleneindhoven2.model.EbikeStalling;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class DataFetcher {
    public static String fetchData(String endpoint) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();

        return content.toString();
    }
}

