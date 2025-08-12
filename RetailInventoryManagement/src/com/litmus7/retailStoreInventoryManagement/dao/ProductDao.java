package com.litmus7.retailStoreInventoryManagement.dao;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dto.Product;

public class ProductDao {
	
	public boolean addProduct(Product product) {
		//1.Append data to CSV file
		return true;
	}
	
	public List<Product> getAllProducts(){
		//1.fetch all products from the csv file
		//2..Return the list of products
		return null;
	}
	
	public List<Product> getProductsByCategory(String category){
		//1.Fetch the product based on the category
		//2.return the list of Products
		return null;
	}

}
