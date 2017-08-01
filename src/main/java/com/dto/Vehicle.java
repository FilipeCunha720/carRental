package com.dto;

import java.math.BigDecimal;

public class Vehicle {

	private String SIPP;
	private String decodedSIPP;
	private String name;
	private BigDecimal price;
	private String supplier;
	private Double rating;
	private Integer score;

	public String getSIPP() {
		return SIPP;
	}

	public void setSIPP(String sIPP) {
		SIPP = sIPP;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDecodedSIPP() {
		return decodedSIPP;
	}

	public void setDecodedSIPP(String decodedSIPP) {
		this.decodedSIPP = decodedSIPP;
	}
}
