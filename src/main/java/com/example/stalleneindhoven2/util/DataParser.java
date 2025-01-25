package com.example.stalleneindhoven2.util;

import com.example.stalleneindhoven2.model.Fietsenstalling;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    public static List<Fietsenstalling> parseData(String jsonData) {
        List<Fietsenstalling> fietsenstallingen = new ArrayList<>();
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            JSONArray results = (JSONArray) jsonObject.get("results");

            for (Object resultObj : results) {
                JSONObject result = (JSONObject) resultObj;
//                JSONObject fields = (JSONObject) result.get("fields");

                if (result != null) {
                    Fietsenstalling fietsenstalling = new Fietsenstalling();
                    fietsenstalling.setNaam((String) result.get("naam_fietsenstalling"));
                    fietsenstalling.setLongitude((Double) result.get("longitude"));
                    fietsenstalling.setLatitude((Double) result.get("latitude"));

                    fietsenstallingen.add(fietsenstalling);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return fietsenstallingen;
    }
}