package com.litmus.employeemanagementsystem.ui;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import com.litmus.employeemanagementsystem.controller.EmployeeManagerController;
import com.litmus.employeemanagementsystem.dto.Response;

public class Main {

    public static void main(String[] args) throws ParseException, IOException, SQLException {
        
        String filePath = "C:\\Users\\sunee\\OneDrive\\Documents\\LITMUS LEARNING\\Java\\employee_data.csv";

        EmployeeManagerController controller = new EmployeeManagerController();
        Response<String> response = controller.importEmployeeData(filePath);

        System.out.println("\n=== Import Result ===");
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println(response.getMessage());
    }
}
