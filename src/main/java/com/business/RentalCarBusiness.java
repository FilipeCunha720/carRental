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
	public void obtainJsonFile() throws IOException;

	/**
	 * This method will print a list of all the cars, in ascending price order
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> printVehiclesOrderByPrice();

	/**
	 * Calculate the specification of the vehicles based on their SIPP.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> calculateSpecificationOfVehicles();

	/**
	 * Print out the highest rated supplier per car type, descending order.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> calculateHighestRatedSupplier();

	/**
	 * Give each vehicle a score based on the below breakdown, then combine this
	 * score with the suppliers rating.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> calculateVehicleScore();
}