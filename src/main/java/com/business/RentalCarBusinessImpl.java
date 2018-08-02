package com.business;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dto.Vehicle;

@Component
public class RentalCarBusinessImpl implements RentalCarBusiness {

	private List<Vehicle> listVehicles;
	private Utils utils;

	@Autowired
	public RentalCarBusinessImpl(Utils utils) {
		this.utils = utils;
	}

	@Override
	public void obtainJsonFile() throws IOException {
		try (InputStream is = new URL("http://www.rentalcars.com/js/vehicles.json").openStream()) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = utils.buildStringJson(rd);
			listVehicles = utils.obtainInfoFromTheJson(jsonText);
		}
	}

	@Override
	public List<Vehicle> printVehiclesOrderByPrice() {
		listVehicles.sort(comparing(Vehicle::getPrice));
		return listVehicles;
	}

	@Override
	public List<Vehicle> calculateSpecificationOfVehicles() {
		listVehicles.forEach(vehicle -> vehicle.setDecodedSIPP(utils.decodeSIPP(vehicle.getSipp())));
		return listVehicles;
	}

	@Override
	public List<Vehicle> calculateHighestRatedSupplier() {

		// translate the SIPP
		listVehicles.forEach(vehicle -> vehicle.setDecodedSIPP(utils.decodeSIPP(vehicle.getSipp())));

		// remove duplicated SIPP
		List<Vehicle> newListVehicles = listVehicles.stream().collect(collectingAndThen(
				toCollection(() -> new TreeSet<>(comparing(Vehicle::getDecodedSIPP))), ArrayList::new));

		// sort by rating
		newListVehicles.sort(comparing(Vehicle::getRating));

		return newListVehicles;

	}

	@Override
	public Map<String, String> calculateVehicleScore() {
		Map<String, Integer> totalScores = new HashMap<>();

		// set a score to each vehicle and add the total cost for each supplier
		listVehicles.forEach(vehicle -> {

			Integer score = utils.calculateScore(vehicle.getSipp());

			vehicle.setScore(score);

			totalScores.merge(vehicle.getSupplier(), score, (a, b) -> a + b);

		});

		Map<String, String> finalTable = new HashMap<>();

		// add to a HashMap the info of the vehicle and the sum of the total
		// score for it's supplier
		listVehicles.forEach(vehicle -> finalTable.put(
				vehicle.getName() + " - " + vehicle.getScore() + " - " + vehicle.getRating() + " - ",
				String.valueOf(totalScores.get(vehicle.getSupplier()))));

		// print the result ordered by the sum of the scores in descending order
		return finalTable.entrySet().stream()
				.sorted(Map.Entry.<String, String>comparingByValue().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

	}

}
