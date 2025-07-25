package com.litmus.employeemanagementsystem.ui;

import com.litmus.employeemanagementsystem.controller.EmployeeManagerController;
import com.litmus.employeemanagementsystem.dto.Response;

public class Main {

    public static void main(String[] args) {
        
        EmployeeManagerController controller = new EmployeeManagerController();

        String filePath = "C:\\Users\\sunee\\OneDrive\\Documents\\LITMUS LEARNING\\Java\\employee_data.csv";
        
        Response<String> response = controller.importEmployeeDataToDB(filePath);

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Message: " + response.getMessage());

        
    }
}
