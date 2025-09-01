package com.litmus7.inventoryfeedphase3.constants;

public class SQLConstants {
	
	public static final String COLUMN_SKU = "sku";
	public static final String COLUMN_PRODUCT_NAME = "product_name";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_PRICE = "price";
	
	// SQL Query to Insert feed
	public static final String INSERT_PRODUCT =
		    "INSERT INTO inventory_feed (" +
		    COLUMN_SKU + ", " +
		    COLUMN_PRODUCT_NAME + ", " +
		    COLUMN_QUANTITY + ", " +
		    COLUMN_PRICE + ") " +   
		    "VALUES (?, ?, ?, ?)";

}
