package com.ui;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dto.Vehicle;
import com.service.RentalCarService;

@RestController
@RequestMapping("/rentalCar")
public class RentalCarServiceController {

	@Autowired
	private RentalCarService rentalCarService;

	/**
	 * This method will print a list of all the cars, in ascending price order
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	@RequestMapping(value = "/printVehiclesOrderByPrice", method = RequestMethod.GET)
	public List<Vehicle> printVehiclesOrderByPrice() {
		return rentalCarService.printVehiclesOrderByPrice();
	}

	/**
	 * Calculate the specification of the vehicles based on their SIPP.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	@RequestMapping(value = "/calculateSpecificationOfVehicles", method = RequestMethod.GET)
	public List<Vehicle> calculateSpecificationOfVehicles() {
		return rentalCarService.calculateSpecificationOfVehicles();
	}

	/**
	 * Print out the highest rated supplier per car type, descending order.
	 * 
	 * @param listVehicles
	 *            List<Vehicle>
	 * 
	 * @return List<Vehicle>
	 */
	@RequestMapping(value = "/calculateHighestRatedSupplier", method = RequestMethod.GET)
	public List<Vehicle> calculateHighestRatedSupplier() {
		return rentalCarService.calculateHighestRatedSupplier();
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
	@RequestMapping(value = "/calculateVehicleScore", method = RequestMethod.GET)
	public Map<String, String> calculateVehicleScore() {
		return rentalCarService.calculateVehicleScore();
	}

}
