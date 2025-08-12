package com.litmus7.retailStoreInventoryManagement.dto;

public class Electronics extends Product {
	
	private String brand;
	private String status;

	public Electronics(String productId , String productName, double price , String status , String brand, int warrantyMonths) {
		super(productId, productName, price, status);
		this.brand = brand;
		this.status = status;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
