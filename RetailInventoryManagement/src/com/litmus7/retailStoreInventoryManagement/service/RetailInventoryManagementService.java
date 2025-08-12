package com.litmus7.retailStoreInventoryManagement.service;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dao.ProductDao;
import com.litmus7.retailStoreInventoryManagement.dto.Product;

public class RetailInventoryManagementService {
	
	ProductDao productDao = new ProductDao();
	
	public boolean addProduct(Product product) {
		//1.validate the product details
		//2.If all details are correct then call addProduct() from Dao
		return productDao.addProduct(product);    
	}
	
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}
	
	public List<Product> getProductsByCategory(String category){
		return productDao.getProductsByCategory(category);
	}
	
	public List<Product> sortProducts(String sortingOption){
		List<Product> products = productDao.getAllProducts();
		//1. based on the sorting option call the appropriate comparator
		return null;
	}

}
