package com.business;

import com.dto.Vehicle;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
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

        jsonText = jsonText.substring(jsonText.indexOf('[') + 1, jsonText.indexOf(']'));

        List<Vehicle> listVehicles = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String vehicle = null;

        while (jsonText.length() > 10) {
            vehicle = jsonText.substring(jsonText.indexOf('{'), jsonText.indexOf('}') + 1);
            listVehicles.add(mapper.readValue(vehicle, Vehicle.class));
            jsonText = jsonText.substring(jsonText.indexOf('}') + 3);

        }

        return listVehicles;
    }

    public String decodeSIPP(String sipp) {

        String type = getCarType(sipp.charAt(0));

        String subTypeDoors = getCarSubType(sipp.charAt(1));

        if (subTypeDoors == null) {
            subTypeDoors = getNumberOfDoors(sipp.charAt(1));
        }

        String transmission = null;

        if (sipp.charAt(2) == 'M') {
            transmission = " - Manual";
        } else {
            transmission = " - Automatic";
        }

        String ac = null;

        if (sipp.charAt(3) == 'N') {
            ac = " - Petrol - no AC";
        } else {
            ac = " - Petrol - AC";
        }

        return type + subTypeDoors + transmission + ac;

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
            default:
                break;
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
            default:
                break;
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
            default:
                break;
        }
        return null;
    }

    public Integer calculateScore(String sipp) {

        Integer score = 0;

        if (sipp.charAt(2) == 'M') {
            score++;
        }
        else if (sipp.charAt(3) == 'R') {
            score += 2;
        }else {
            score += 5;
        }

        return score;
    }

}
