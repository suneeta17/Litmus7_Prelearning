package com.litmus7.inventoryfeedphase3.dto;

public class Product {
	
	private int sku;
	private String productName;
	private int quantity;
	private double price;
	
	public Product(int sku , String productName , int quantity , double price) {
		this.sku = sku;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
