package com.litmus7.inventoryfeedphase2.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.litmus7.inventoryfeedphase2.dto.InventoryProcessResult;
import com.litmus7.inventoryfeedphase2.dto.Response;
import com.litmus7.inventoryfeedphase2.exception.InventoryServiceException;
import com.litmus7.inventoryfeedphase2.service.InventoryFeedService;

public class InventoryFeedController {
	
	private static final Logger logger = LogManager.getLogger(InventoryFeedController.class);
	
	private InventoryFeedService inventoryFeedService = new InventoryFeedService();
	
	public Response<InventoryProcessResult> processInventoryFeeds(){
		logger.debug("Enterring processInventory Feeds method");
		try{
		InventoryProcessResult result = inventoryFeedService.processInventoryFeeds();
		logger.info("Inventory feeds processed successfully: {}", result);
		
		return new Response<InventoryProcessResult>(200,"Feed processed successfullt", result);
		
	}catch (InventoryServiceException e) {
		logger.error("Error while processing inventory feed", e);
		return new Response<>(500, "Error processing feed: " + e.getMessage());
	}catch (Exception e) {
		logger.error("Unexpected server error while processing inventory feed", e);
		return new Response<>(500, "Server error: " + e.getMessage());
	} finally {
		logger.debug("Exiting processInventoryFeeds method");
	}
	}

}
	
