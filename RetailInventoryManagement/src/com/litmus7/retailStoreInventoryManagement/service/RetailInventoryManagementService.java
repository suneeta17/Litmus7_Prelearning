package com.litmus7.retailStoreInventoryManagement.service;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dao.ProductDao;
import com.litmus7.retailStoreInventoryManagement.dto.Product;
import com.litmus7.retailStoreInventoryManagement.exceptions.ProductDaoException;
import com.litmus7.retailStoreInventoryManagement.exceptions.ProductServiceException;

public class RetailInventoryManagementService {
	
	ProductDao productDao = new ProductDao();
	
	public boolean addProduct(Product product) throws ProductServiceException {
		//Code to validate the product details
		try {
			return productDao.addProduct(product);
		} catch (ProductDaoException e) {
			throw new ProductServiceException("Error adding product", e);
		}    
	}
	
	public List<Product> getAllProducts() throws ProductServiceException{
		try {
			return productDao.getAllProducts();
		} catch (ProductDaoException e) {
			throw new ProductServiceException("Error retrieving all products", e);
		}
	}
	
	public List<Product> getProductsByCategory(String category) throws ProductServiceException{
		try {
			return productDao.getProductsByCategory(category);
		} catch (ProductDaoException e) {
			  throw new ProductServiceException("Error retrieving products by category", e);
		}
	}
	
	public List<Product> sortProducts(String sortingOption) throws ProductServiceException{
		try {
			List<Product> products = productDao.getAllProducts();
		} catch (ProductDaoException e) {
			throw new ProductServiceException("Error while fetching  products", e);
		}
		//Code to sort data based on the sorting option 
		//Based on the sorting option call call the appropriate comparator
		return null;
	}

}
