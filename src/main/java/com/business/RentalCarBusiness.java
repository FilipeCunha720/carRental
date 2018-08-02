package com.business;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.dto.Vehicle;

public interface RentalCarBusiness {

	/**
	 * This method will access an URL and will obtain a json file. That json
	 * file will then be converted to a string.
	 * 
	 * @throws IOException
	 */
	void obtainJsonFile() throws IOException;

	/**
	 * This method will print a list of all the cars, in ascending price order
	 *
	 * @return List<Vehicle>
	 */
	 List<Vehicle> printVehiclesOrderByPrice();

	/**
	 * Calculate the specification of the vehicles based on their SIPP.
	 *
	 * @return List<Vehicle>
	 */
	 List<Vehicle> calculateSpecificationOfVehicles();

	/**
	 * Print out the highest rated supplier per car type, descending order.
	 *
	 * @return List<Vehicle>
	 */
	 List<Vehicle> calculateHighestRatedSupplier();

	/**
	 * Give each vehicle a score based on the below breakdown, then combine this
	 * score with the suppliers rating.
	 * 
	 * @return Map<String, String>
	 */
	 Map<String, String> calculateVehicleScore();
}