package com.litmus7.retailStoreInventoryManagement.dto;

import java.util.Date;

public class Grocery extends Product {
	
	private Date expiryDate;
	private double weightKg;

	public Grocery(String productId , String productName, double price , String status , Date expiryDate , double weightKg) {
		super(productId,productName,price,status);
		this.expiryDate = expiryDate;
		this.weightKg = weightKg;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public double getWeightKg() {
		return weightKg;
	}

	public void setWeightKg(double weightKg) {
		this.weightKg = weightKg;
	}
	
	

}
