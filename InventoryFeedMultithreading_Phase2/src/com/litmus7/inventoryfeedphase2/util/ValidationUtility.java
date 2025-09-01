package com.litmus7.inventoryfeedphase2.util;

public class ValidationUtility {

	public static boolean isInventoryRecordValid(String[] record) {
		if (record == null || record.length != 4) return false;
		
		try {
			int sku = Integer.parseInt(record[0].trim());
            if (sku <= 0) return false;

            String productName = record[1].trim();
            if (productName == null || productName.isEmpty()) return false;

            int quantity = Integer.parseInt(record[2].trim());
            if (quantity < 0) return false;

            double price = Double.parseDouble(record[3].trim());
            if (price < 0) return false;
		}catch (Exception e) {
			return false;
		}
		return true;
	}
}
