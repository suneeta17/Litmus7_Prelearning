package com.litmus7.retailStoreInventoryManagement.dto;

public class Clothing extends Product {
	
	private String size;
	private String material;

	public Clothing(String productId , String productName, double price , String status,String size , String material) {
		super(productId, productName, price, status);
		this.size = size;
		this.material = material;	
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

}
