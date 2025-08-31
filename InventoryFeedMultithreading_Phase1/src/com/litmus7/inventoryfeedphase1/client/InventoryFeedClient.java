package com.litmus7.inventoryfeedphase1.client;


import java.io.FileNotFoundException;
import java.io.IOException;


import com.litmus7.inventoryfeedphase1.controller.InventoryFeedController;
import com.litmus7.inventoryfeedphase1.dto.InventoryProcessResult;
import com.litmus7.inventoryfeedphase1.dto.Response;

public class InventoryFeedClient {

	public static void main(String[] args) throws FileNotFoundException, IOException {
				
		InventoryFeedController controller = new InventoryFeedController();
		Response<InventoryProcessResult> response =controller.processInventoryFeeds();
		System.out.println(response.getStatusCode());
		System.out.println(response.getMessage());
	}

}
