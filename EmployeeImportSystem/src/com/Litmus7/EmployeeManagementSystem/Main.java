package com.Litmus7.EmployeeManagementSystem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeManagerController controller = new EmployeeManagerController();
        String filePath = "C:\\Users\\sunee\\OneDrive\\Documents\\LITMUS LEARNING\\Java\\employee_data.csv"; // Change this to your actual file path

        List<Response<String>> results = controller.readCSV(filePath);

        System.out.println("\n--- Import Summary ---");
        for (Response<String> res : results) {
            System.out.println("Status: " + res.getStatusCode() + " | Success: " + res.isSuccess() + " | Message: " + res.getMessage());
        }
    }
}
