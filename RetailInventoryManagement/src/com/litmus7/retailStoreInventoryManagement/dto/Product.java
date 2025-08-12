package com.litmus7.retailStoreInventoryManagement.dto;

public abstract class Product {
	
	private String productId;
	private String productName;
	private double price;
	private String status;
	
	
	public Product(String productId , String productName, double price , String status ) {
		
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.setStatus(status);
		
	}

	
	public String getProductId() {
		return productId;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	

}
