package com.litmus7.retailStoreInventoryManagement.ui;

import java.util.List;

import com.litmus7.retailStoreInventoryManagement.controller.RetailInventoryManagementController;
import com.litmus7.retailStoreInventoryManagement.dto.Product;
import com.litmus7.retailStoreInventoryManagement.dto.Response;

public class Main {
	
     

	public static void main(String[] args) {
		
	RetailInventoryManagementController retailController = new RetailInventoryManagementController();
		
	//-----ADD PRODUCT--------//	
	// 1.Receive category
	// 2.Receive details based on category
	// 3.Display Response
	Response<String> addProductResponse = retailController.addProduct(null);
			
	
	//-----GET ALL PRODUCTS--------//	
	// 1.Display Response
	Response<List<Product>> getAllProductsResponse = retailController.getAllProducts();
	
	
	//-----Get Products By Category--------//	
	// 1.Receive category
	// 2.Display Response
	Response<List<Product>> getProductsByCategoryResponse = retailController.getProductsByCategory(null);
	
	//----Sort Products-------//	
	// 1.Receive sort option
	// 2.Display Response
	Response<List<Product>> productSortResponse = retailController.sortProduct(null);
		
	}
}
