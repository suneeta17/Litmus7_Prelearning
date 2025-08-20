package com.litmus7.retailStoreInventoryManagement.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dto.Product;
import com.litmus7.retailStoreInventoryManagement.exceptions.ProductDaoException;

public class ProductDao {
	
	public boolean addProduct(Product product) throws ProductDaoException {
		try {
			//Code to append data to CSV file
			return true;
		}catch (IOException e) {
			throw new ProductDaoException("failed to add prudct",e);
		}
	}
	
	public List<Product> getAllProducts() throws ProductDaoException{
		try {
            // Code to fetch all products from CSV file
            // return list of products read from file
            return new ArrayList<>();
        } catch (IOException e) {
            throw new ProductDaoException("Failed to read products from storage", e);
        }
	}
	
	public List<Product> getProductsByCategory(String category) throws ProductDaoException{
		 try {
	          // Code to fetch products filtered by category from CSV file
	          return new ArrayList<>();
	        } catch (IOException e) {
	            throw new ProductDaoException("Failed to read products by category", e);
	        }
	    }
	}


