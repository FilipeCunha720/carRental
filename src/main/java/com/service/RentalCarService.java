package com.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.business.RentalCarBusiness;
import com.dto.Vehicle;

@Service
public class RentalCarService {


	private RentalCarBusiness storeBusiness;

	@Autowired
	public RentalCarService(RentalCarBusiness storeBusiness){
		this.storeBusiness=storeBusiness;

	}

	/**
	 * This method will print a list of all the cars, in ascending price order
	 *
	 * @return List<Vehicle>
	 */
	public List<Vehicle> printVehiclesOrderByPrice() {
		return storeBusiness.printVehiclesOrderByPrice();
	}

	/**
	 * Calculate the specification of the vehicles based on their SIPP.
	 *
	 * @return List<Vehicle>
	 */
	public List<Vehicle> calculateSpecificationOfVehicles() {
		return storeBusiness.calculateSpecificationOfVehicles();
	}

	/**
	 * Print out the highest rated supplier per car type, descending order.
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
	 * @return Map<String, String>
	 */
	public Map<String, Integer> calculateVehicleScore() {
		return storeBusiness.calculateVehicleScore();
	}

}
