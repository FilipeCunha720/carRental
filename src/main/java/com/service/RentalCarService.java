package com.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.RentalCarBusiness;
import com.dto.Vehicle;

@Service
public class RentalCarService {

	@Autowired
	private RentalCarBusiness storeBusiness;

	/**
	 * This method will print a list of all the cars, in ascending price order
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> printVehiclesOrderByPrice() {
		return storeBusiness.printVehiclesOrderByPrice();
	}

	/**
	 * Calculate the specification of the vehicles based on their SIPP.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> calculateSpecificationOfVehicles() {
		return storeBusiness.calculateSpecificationOfVehicles();
	}

	/**
	 * Print out the highest rated supplier per car type, descending order.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	public List<Vehicle> calculateHighestRatedSupplier() {
		return storeBusiness.calculateHighestRatedSupplier();
	}

	/**
	 * Give each vehicle a score based on the below breakdown, then combine this
	 * score with the suppliers rating.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return Map<String, String>
	 */
	public Map<String, String> calculateVehicleScore() {
		return storeBusiness.calculateVehicleScore();
	}

}
