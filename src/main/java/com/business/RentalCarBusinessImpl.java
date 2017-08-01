package com.business;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.dto.Vehicle;

@Component
public class RentalCarBusinessImpl implements RentalCarBusiness {

	private List<Vehicle> listVehicles;

	/**
	 * This method will access an URL and will obtain a json file. That json
	 * file will then be converted to a string.
	 * 
	 * @throws IOException
	 */
	@Override
	public void obtainJsonFile() throws IOException {
		InputStream is = new URL("http://www.rentalcars.com/js/vehicles.json").openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = buildStringJson(rd);
			listVehicles = obtainInfoFromTheJson(jsonText);
			// printVehiclesOrderByPrice();
			// System.out.println("////////////////////////////////");
			// calculateSpecificationOfVehicles();
			// System.out.println("////////////////////////////////");
			// calculateHighestRatedSupplier();
			// System.out.println("////////////////////////////////");
			// calculateVehicleScore();
		} finally {
			is.close();
		}
	}

	/**
	 * This method will build a string from a Reader. The Reader contains the
	 * content of the Json file.
	 * 
	 * @param rd
	 *            Reader
	 * 
	 * @return String
	 * 
	 * @throws IOException
	 */
	private String buildStringJson(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * This function will process the String with the content of the file and
	 * obtain the list Vehicle
	 * 
	 * @param jsonText
	 *            String
	 * 
	 * @return List<Vehicle>
	 * 
	 * @throws IOException
	 */
	private List<Vehicle> obtainInfoFromTheJson(String jsonText) throws IOException {

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

	@Override
	public List<Vehicle> printVehiclesOrderByPrice() {
		listVehicles.sort((v1, v2) -> v1.getPrice().compareTo(v2.getPrice()));
		listVehicles.forEach(vehicle -> System.out.println(vehicle.getName() + " - " + vehicle.getPrice()));
		return listVehicles;
	}

	@Override
	public List<Vehicle> calculateSpecificationOfVehicles() {
		listVehicles.forEach(vehicle -> {
			String vehicleSIPP = vehicle.getSIPP();
			vehicle.setDecodedSIPP(decodeSIPP(vehicleSIPP));
			System.out.println(vehicle.getName() + " - " + vehicle.getDecodedSIPP());
		});

		return listVehicles;
	}

	@Override
	public List<Vehicle> calculateHighestRatedSupplier() {
		// sort by rating
		listVehicles.sort((v1, v2) -> v1.getRating().compareTo(v1.getRating()));

		// translate the SIPP
		listVehicles.forEach(vehicle -> vehicle.setDecodedSIPP(getCarTypeAndSubType(vehicle.getSIPP())));

		// remove duplicated SIPP
		List<Vehicle> newlistVehicles = listVehicles.stream().collect(collectingAndThen(
				toCollection(() -> new TreeSet<>(comparing(Vehicle::getDecodedSIPP))), ArrayList::new));

		// sort by rating
		Collections.sort(newlistVehicles, new Comparator() {
			public int compare(Object v1, Object v2) {
				Double x1 = ((Vehicle) v1).getRating();
				Double x2 = ((Vehicle) v2).getRating();
				return x2.compareTo(x1);
			}
		});

		newlistVehicles.forEach(vehicle -> System.out.println(vehicle.getName() + " - " + vehicle.getDecodedSIPP()
				+ " - " + vehicle.getSupplier() + " - " + vehicle.getRating()));

		return newlistVehicles;

	}

	@Override
	public Map<String, String> calculateVehicleScore() {
		Map<String, Integer> totalScores = new HashMap<String, Integer>();

		// set a score to each vehicle and add the total cost for each supplier
		listVehicles.forEach(vehicle -> {

			Integer score = calculateScore(vehicle.getSIPP());

			vehicle.setScore(score);

			if (totalScores.get(vehicle.getSupplier()) != null) {
				totalScores.put(vehicle.getSupplier(), totalScores.get(vehicle.getSupplier()) + score);
			} else {
				totalScores.put(vehicle.getSupplier(), score);
			}

		});

		Map<String, String> finalTable = new HashMap<String, String>();

		// add to a HashMap the info of the vehicle and the sum of the total
		// score for it's supplier
		listVehicles.forEach(vehicle -> finalTable.put(
				vehicle.getName() + " - " + vehicle.getScore() + " - " + vehicle.getRating() + " - ",
				String.valueOf(totalScores.get(vehicle.getSupplier()))));

		// print the result ordered by the sum of the scores in descending order
		Map<String, String> tableSorted = finalTable.entrySet().stream()
				.sorted(Map.Entry.<String, String>comparingByValue().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		tableSorted.forEach((key, val) -> {
			System.out.println(key + val);
		});

		return tableSorted;

	}

	/**
	 * Decode the meaning of each letter in the SIPP
	 * 
	 * @param SIPP
	 *            String
	 * 
	 * @return String
	 */
	private String decodeSIPP(String SIPP) {

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

	/**
	 * Decode the type of Car
	 * 
	 * @param charAt
	 *            char
	 * 
	 * @return String
	 */
	private String getCarType(char charAt) {
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

	/**
	 * Decode the car subtype
	 * 
	 * @param charAt
	 *            char
	 * 
	 * @return String
	 */
	private String getCarSubType(char charAt) {
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

	/**
	 * Obtain the car type and sub type
	 * 
	 * @param SIPP
	 *            String
	 * 
	 * @return String
	 */
	private String getCarTypeAndSubType(String SIPP) {

		String type = getCarType(SIPP.charAt(0));

		String subtype_doors = getCarSubType(SIPP.charAt(1));

		if (subtype_doors != null) {
			return type + subtype_doors;
		}

		return type;

	}

	/**
	 * Decode the number of doors
	 * 
	 * @param charAt
	 *            char
	 * 
	 * @return String
	 */
	private String getNumberOfDoors(char charAt) {
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

	/**
	 * Calculate the score of the vehicle based on the SIPP
	 * 
	 * @param SIPP
	 *            String
	 * 
	 * @return Integer
	 */
	private Integer calculateScore(String SIPP) {

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
