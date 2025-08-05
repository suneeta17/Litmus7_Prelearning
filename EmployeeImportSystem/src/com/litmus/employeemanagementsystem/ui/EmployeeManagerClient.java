package com.litmus.employeemanagementsystem.ui;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.litmus.employeemanagementsystem.controller.EmployeeManagerController;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.dto.Response;

public class EmployeeManagerClient {

    public static void main(String[] args) throws ParseException, IOException, SQLException {
        
    	 EmployeeManagerController controller = new EmployeeManagerController();
    	 String filePath = "C:\\Users\\sunee\\eclipse-workspace\\Litmus PreLearning\\employee_data.csv";


    	 Response<String> response = controller.importEmployeeData(filePath);

    	 System.out.println("\n=== Import Result ===");
    	 System.out.println("Status Code: " + response.getStatusCode());
    	 System.out.println(response.getMessage());
        
        
    	 // Get all employee records 
        Response<List<Employee>> allEmployeesResponse = controller.getAllEmployees();
        System.out.println("\n=== All Employees in Database ===");
        System.out.println("Status Code: " + allEmployeesResponse.getStatusCode());
        System.out.println(allEmployeesResponse.getMessage());

        List<Employee> employees = allEmployeesResponse.getData();
        if (employees != null && !employees.isEmpty()) {
            for (Employee emp : employees) {
                System.out.println("---------------------------");
                System.out.println("ID: " + emp.getEmployeeId());
                System.out.println("First Name: " + emp.getFirstName());
                System.out.println("Last Name: " + emp.getLastName());
                System.out.println("Email: " + emp.getEmail());
                System.out.println("Phone: " + emp.getPhone());
                System.out.println("Department: " + emp.getDepartment());
                System.out.println("Salary: " + emp.getSalary());
                System.out.println("Join Date: " + emp.getJoinDate());
            }
        } 
        
    }
}
