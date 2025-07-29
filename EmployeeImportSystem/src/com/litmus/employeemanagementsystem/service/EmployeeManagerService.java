package com.litmus.employeemanagementsystem.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.util.ValidationUtility;


public class EmployeeManagerService {

    //Method to import data from  csv to database
    public Map<String, List<String>> importEmployeeDataToDB(String filePath) throws ParseException, IOException, SQLException {
    	EmployeeDAO employeeDAO = new EmployeeDAO();
        Map<String, List<String>> result = new HashMap<>();
        result.put("success", new ArrayList<>());
        result.put("error", new ArrayList<>());

        if (ValidationUtility.isCSVFile(filePath)) {
            result.get("error").add("Invalid file type. Please provide a CSV file.");
            return result;
        }

        List<String[]> csvData;
        csvData = loadCSVData(filePath);
        

        List<Employee> validEmployees = new ArrayList<>();

        for (String[] empData : csvData) {
            String validationError = validateEmployeeData(empData);
            if (validationError != null) {
                result.get("error").add("Validation failed: " + String.join(",", empData) + " | Reason: " + validationError);
            } else {
                validEmployees.add(new Employee(empData));
            }
        }

        for (Employee emp : validEmployees) {
           
            if (!employeeDAO.isEmployeeIDExist(emp.getEmpId())) {
                if (employeeDAO.addEmployee(emp)) {
                    result.get("success").add("Inserted employee ID: " + emp.getEmpId());
                } else {
                    result.get("error").add("Failed to insert employee ID: " + emp.getEmpId());
                }
            } else {
                result.get("error").add("Duplicate Employee ID skipped: " + emp.getEmpId());
            	}
            
        }
        return result;
    }
        
    // Method to load the data from csv
    private List<String[]> loadCSVData(String filePath) throws IOException {
        List<String[]> records = new ArrayList<>();

        try (FileReader fr = new FileReader(filePath);
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip header
                }
                String[] fields = line.split(",");
                records.add(fields);
            }
        }
        return records;
    }

    // Method to  validate data
    private String validateEmployeeData(String[] empData) {
    	
    	String[] fieldNames = {
    		    "Employee ID", "First Name", "Last Name", "Email",
    		    "Phone", "Department", "Salary", "Join Date"
    		};
    	
        if (empData.length != 8) {
            return "Invalid number of fields (expected 8, got " + empData.length + ")";
        }
        
        for (int i = 0; i < empData.length; i++) {
            if (!ValidationUtility.isNotNullOrNotEmpty(empData[i])) {
                return  fieldNames[i] + " is empty or null.";
            }
        }


        try {
            String email = empData[3];
            String phone = empData[4];
            String salary = empData[6];
            String joinDate = empData[7];

            if (!ValidationUtility.isEmailValid(email)) {
                return "Invalid email: " + email;
            }
            if (ValidationUtility.isPhoneNumberValid(phone)) {
                return "Invalid phone number: " + phone;
            }
            if (!ValidationUtility.isDoubleValid(salary)) {
                return "Invalid salary: " + salary;
            }
            if (ValidationUtility.isDateValid(joinDate) == null) {
                return "Invalid join date: " + joinDate;
            }

            return null; // No error
        } catch (Exception e) {
            return "Validation exception: " + e.getMessage();
        }
    }
}
