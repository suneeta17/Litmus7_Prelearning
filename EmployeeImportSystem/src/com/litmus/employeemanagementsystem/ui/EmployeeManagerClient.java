package com.litmus.employeemanagementsystem.ui;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import com.litmus.employeemanagementsystem.controller.EmployeeManagerController;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.dto.Response;

public class EmployeeManagerClient {

    public static void main(String[] args) throws ParseException, IOException, SQLException {
        
		EmployeeManagerController controller = new EmployeeManagerController();
		String filePath = "C:\\Users\\sunee\\eclipse-workspace\\Litmus PreLearning\\data\\employee_data.csv";
	
	
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
        
        //Get Employee by Id
        Response<Employee> employeeResponse = controller.getEmployeeById("102");
        System.out.println("\n=== Get Employee Details by ID ===");
        System.out.println("Status Code: " + employeeResponse.getStatusCode());
        System.out.println(employeeResponse.getMessage());
        
        Employee employee = employeeResponse.getData();
        
        if (employee!= null) {
        	System.out.println("ID: " + employee.getEmployeeId());
            System.out.println("First Name: " + employee.getFirstName());
            System.out.println("Last Name: " + employee.getLastName());
            System.out.println("Email: " + employee.getEmail());
            System.out.println("Phone: " + employee.getPhone());
            System.out.println("Department: " + employee.getDepartment());
            System.out.println("Salary: " + employee.getSalary());
            System.out.println("Join Date: " + employee.getJoinDate());   	
        }
        
    	 //Delete Employee by ID
        Response<String> deleted = controller.deleteEmployeeById("210");
        System.out.println("\n=== Deletion Operation in Database ===");
        System.out.println("Status Code: " + deleted.getStatusCode());
        System.out.println(deleted.getMessage());
        
        
        // Update Employee Details 
    	 List<String> updatedEmployeeData = Arrays.asList(
    	     "105",                       
    	     "Alice",                    
    	     "Mathew",                    
    	     "alice.mathew@example.com",  
    	     "9123456789",                
    	     "HR",                       
    	     "60000",                     
    	     "24-05-2024"                
    	 );

    	 Response<String> updateResponse = controller.updateEmployee(updatedEmployeeData);

    	 System.out.println("\n=== Update Employee Operation ===");
    	 System.out.println("Status Code: " + updateResponse.getStatusCode());
    	 System.out.println(updateResponse.getMessage());
    	 
    	 
    	// Add New Employee 
    	 List<String> newEmployeeData = Arrays.asList(
    	     "210",                        
    	     "John",                       
    	     "Smith",                      
    	     "john.smith@example.com",     
    	     "9876543210",                 
    	     "Finance",                    
    	     "45000",                      
    	     "15-06-2024"                  
    	 );

    	 Response<String> addResponse = controller.addEmployee(newEmployeeData);

    	 System.out.println("\n=== Add Employee Operation ===");
    	 System.out.println("Status Code: " + addResponse.getStatusCode());
    	 System.out.println(addResponse.getMessage());
  
    }
}
