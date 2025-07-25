package com.Litmus7.EmployeeManagementSystem;


public class Main {
	public static void main(String[] args) {
	    EmployeeManagerController controller = new EmployeeManagerController();
	    
	    String filePath ="\\C:\\Users\\sunee\\OneDrive\\Documents\\LITMUS LEARNING\\\\Java\\employee_data.csv";
	    try {
	        controller.readCSV(filePath, response -> {
	            // Handle each response immediately as it's produced
	            System.out.println("Status: " + response.getStatusCode() + " | " + response.getMessage() + "- " + response.getData());
	        });
	    } catch (IllegalArgumentException e) {
	        System.out.println("Error: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Unexpected Error: " + e.getMessage());
	    }
	}
}
