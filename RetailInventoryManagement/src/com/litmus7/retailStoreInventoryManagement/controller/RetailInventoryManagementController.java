package com.litmus7.retailStoreInventoryManagement.controller;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.dto.Product;
import com.litmus7.retailStoreInventoryManagement.dto.Response;
import com.litmus7.retailStoreInventoryManagement.service.RetailInventoryManagementService;

public class RetailInventoryManagementController {
	
	RetailInventoryManagementService retailManagerService = new RetailInventoryManagementService();
	
	public Response<String> addProduct(Product product){
		
		retailManagerService.addProduct(product);
		return null;	
	}
	
	public Response<List<Product>> getAllProducts(){
			
		retailManagerService.getAllProducts();
		return null;
	}
	
	public Response<List<Product>> getProductsByCategory(String category){
		
		retailManagerService.getProductsByCategory(category);
		return null;	
	}
	
	public Response<List<Product>> sortProduct(String sortOption){
		
		retailManagerService.sortProducts(sortOption);
		return null;
	}

}
