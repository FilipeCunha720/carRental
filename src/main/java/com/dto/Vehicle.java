package com.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Vehicle {

	private String sipp;
	private String decodedSIPP;
	private String name;
	private BigDecimal price;
	private String supplier;
	private Double rating;
	private Integer score;
}
