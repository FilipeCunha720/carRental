package com.business;

import com.dto.Vehicle;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;

public class Utils {

    public String buildStringJson(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public List<Vehicle> obtainInfoFromTheJson(String jsonText) throws IOException {

        jsonText = jsonText.substring(jsonText.indexOf("[") + 1, jsonText.indexOf("]"));

        List<Vehicle> listVehicles = new ArrayList<Vehicle>();
        ObjectMapper mapper = new ObjectMapper();
        String vehicle = null;

        while (jsonText.length() > 10) {
            vehicle = jsonText.substring(jsonText.indexOf("{"), jsonText.indexOf("}") + 1);
            listVehicles.add(mapper.readValue(vehicle, Vehicle.class));
            jsonText = jsonText.substring(jsonText.indexOf("}") + 3);

        }

        return listVehicles;
    }

    public String decodeSIPP(String SIPP) {

        String type = getCarType(SIPP.charAt(0));

        String subtype_doors = getCarSubType(SIPP.charAt(1));

        if (subtype_doors == null) {
            subtype_doors = getNumberOfDoors(SIPP.charAt(1));
        }

        String transmission = null;

        if (SIPP.charAt(2) == 'M') {
            transmission = " - Manual";
        } else {
            transmission = " - Automatic";
        }

        String AC = null;

        if (SIPP.charAt(3) == 'N') {
            AC = " - Petrol - no AC";
        } else {
            AC = " - Petrol - AC";
        }

        return type + subtype_doors + transmission + AC;

    }

    public String getCarType(char charAt) {
        switch (charAt) {
            case 'M':
                return "Mini";
            case 'E':
                return "Economy";
            case 'C':
                return "Compact";
            case 'I':
                return "Intermediate";
            case 'S':
                return "Standard";
            case 'F':
                return "Full size";
            case 'P':
                return "Premium";
            case 'L':
                return "Luxury";
        }
        return null;
    }

    public String getCarSubType(char charAt) {
        switch (charAt) {
            case 'W':
                return " Estate";
            case 'T':
                return " Convertible";
            case 'F':
                return " SUV";
            case 'P':
                return " Pick up";
            case 'V':
                return " Passenger Van";
            case 'X':
                return " Special";
        }
        return null;
    }

    public String getNumberOfDoors(char charAt) {
        switch (charAt) {
            case 'B':
                return " - 2 doors";
            case 'C':
                return " - 4 doors";
            case 'D':
                return " - 5 doors";
        }
        return null;
    }

    public Integer calculateScore(String SIPP) {

        Integer score = 0;

        if (SIPP.charAt(2) == 'M') {
            score++;
        } else {
            score += 5;
        }
        if (SIPP.charAt(3) == 'R') {
            score += 2;
        }
        return score;
    }

}
