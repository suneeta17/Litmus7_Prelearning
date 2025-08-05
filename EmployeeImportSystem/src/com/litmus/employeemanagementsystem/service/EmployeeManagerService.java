package com.litmus.employeemanagementsystem.service;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.exception.EmployeeDaoException;
import com.litmus.employeemanagementsystem.exception.EmployeeServiceException;
import com.litmus.employeemanagementsystem.util.CSVReader;
import com.litmus.employeemanagementsystem.util.ValidationUtility;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeManagerService {
	
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	//Method to import employee data to database
    public Map<String, List<String>> importEmployeeDataToDB(String filePath) throws  EmployeeServiceException {
        Map<String, List<String>> result = new HashMap<>();
        result.put("success", new ArrayList<>());
        result.put("error", new ArrayList<>());
        List<String[]> rows;
        

        try {
        rows = CSVReader.loadData(filePath);
        }catch(IOException e) {
        	throw new EmployeeServiceException("Service error while reading CSV file",e);
        }
        List<Employee> validEmployees = new ArrayList<>();

        try {
		for (String[] row : rows) {
            String error = validate(row);
            if (error != null) {
                result.get("error").add("Invalid: " + Arrays.toString(row) + " | Reason: " + error);
            } else {
                validEmployees.add(new Employee(row));
            }
		}}catch (ParseException e) {
			throw new EmployeeServiceException("Server error while parsing data",e);
			
		}
        

        for (Employee emp : validEmployees) {
            String employeeId = emp.getEmployeeId();
            
            try {
            if (employeeDAO.isEmployeeIDExist(employeeId)) {
                result.get("error").add("Duplicate Employee ID: " + employeeId);
            } else if (employeeDAO.saveEmployee(emp)) {
                result.get("success").add("Inserted: " + employeeId);
            } else {
                result.get("error").add("Failed to insert: " + employeeId);
            }
            }catch (EmployeeDaoException e) {
            	throw new EmployeeServiceException("DAO error while processing and importing employye data",e);
            }
        }

        return result;
    }
    
    //Method to fetch all Employees from DAO
    public List<Employee> getAllEmployees() throws EmployeeServiceException{
        try{
        	return employeeDAO.getAllEmployees();
        }catch (EmployeeDaoException e) {
        	throw new EmployeeServiceException("Server layer failed to fetch employees",e);
        }
    }
   

    //Method to validate employee record
    private String validate(String[] data) throws ParseException  {
        String[] fields = {"Employee ID", "First Name", "Last Name", "Email", "Phone", "Department", "Salary", "Join Date"};
        if (data.length != fields.length) return "Expected " + fields.length + " fields, got " + data.length;

        for (int i = 0; i < data.length; i++) {
            if (!ValidationUtility.isNotNullOrNotEmpty(data[i])) {
                return fields[i] + " is empty or null.";
            }
        }

        String email = data[3], phone = data[4], salary = data[6], date = data[7];
        if (!ValidationUtility.isEmailValid(email)) return "Invalid email: " + email;
        if (!ValidationUtility.isPhoneNumberValid(phone)) return "Invalid phone: " + phone;
        if (!ValidationUtility.isDoubleValid(salary)) return "Invalid salary: " + salary;
        if (ValidationUtility.isDateValid(date) == null) return "Invalid date: " + date;

        return null;
    }
}
