package com.litmus7.retailStoreInventoryManagement.controller;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dto.Product;
import com.litmus7.retailStoreInventoryManagement.dto.Response;
import com.litmus7.retailStoreInventoryManagement.exceptions.ProductServiceException;
import com.litmus7.retailStoreInventoryManagement.service.RetailInventoryManagementService;

public class RetailInventoryManagementController {
	
	private RetailInventoryManagementService retailManagerService = new RetailInventoryManagementService();
	
	public Response<String> addProduct(Product product){
		
		try {
			retailManagerService.addProduct(product);
			return new Response<>(200,"Product added successfully");
		} catch (ProductServiceException e) {
			return new Response<>(500,"Failed to add Products");	
		}
			
	}
	
	public Response<List<Product>> getAllProducts(){
			
		try {
			List<Product> products=retailManagerService.getAllProducts();
			return new Response<>(200,"Product fetched successfully",products);
		} catch (ProductServiceException e) {
			return new Response<>(500,"Failed to fetch products ");
		}
		
	}
	
	public Response<List<Product>> getProductsByCategory(String category){
		
		try {
			List<Product> products=retailManagerService.getProductsByCategory(category);
			return new Response<>(200,"Product fetched successfully",products);
		} catch (ProductServiceException e) {
			return new Response<>(500,"Faled to fetch products from : " + category);
		}
	}
	
	public Response<List<Product>> sortProduct(String sortOption){
		
		try {
			List<Product> sortedProducts =retailManagerService.sortProducts(sortOption);
			return new Response<>(200,"Product Sorted successfully",sortedProducts);
		} catch (ProductServiceException e) {
			return new Response<>(500,"Sort Operation Failed");
		}
		
	}

}
