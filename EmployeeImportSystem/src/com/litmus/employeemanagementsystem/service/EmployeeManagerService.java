package com.litmus.employeemanagementsystem.service;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.exception.EmployeeDaoException;
import com.litmus.employeemanagementsystem.exception.EmployeeServiceException;
import com.litmus.employeemanagementsystem.util.CSVReader;
import com.litmus.employeemanagementsystem.util.ValidationUtility;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeManagerService {
	
	public enum Status {SUCCESS,NOT_FOUND,FAILED,ALREADY_EXIST}
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
    
    
    //Method to fetch all Employees from database
    public List<Employee> getAllEmployees() throws EmployeeServiceException{
        try{
        	return employeeDAO.getAllEmployees();
        }catch (EmployeeDaoException e) {
        	throw new EmployeeServiceException("Server failed to fetch employees",e);
        }
    }
    
    //Method to fetch employee record by ID
    public Employee getEmployeebyId(String employeeId) throws EmployeeServiceException {
    	try {
    		return EmployeeDAO.getEmployeeById(employeeId);
    	}catch (EmployeeDaoException e) {
    		throw new EmployeeServiceException("Server failed tp fetch employee with ID "+ employeeId, e);
    	}
    }
    
    //Method to Add a new Employee
    public Status addEmployee(List<String> data) throws EmployeeServiceException {
        try {
            // Convert list to array for Employee constructor
            String[] employeeData = data.toArray(new String[0]);

   
            // Validate Employee object
            String validationError = validate(employeeData);
            if (validationError != null) {
                return Status.FAILED; 
            }
            
            Employee employee = new Employee(employeeData);

            // Check if Employee exists
            if (employeeDAO.isEmployeeIDExist(employee.getEmployeeId())) {
                return Status.ALREADY_EXIST;
            }

            // Save new Employee
            boolean added = employeeDAO.saveEmployee(employee);
            return added ? Status.SUCCESS : Status.FAILED;

        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error adding  new employee: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new EmployeeServiceException("Error parsing employee data: " + e.getMessage(), e);
        }
    }
       
    
    //Method to update Employee
    public Status updateEmployee(List<String> data) throws EmployeeServiceException {
        try {
            // Convert list to array for Employee constructor
            String[] employeeData = data.toArray(new String[0]);

   
            // Validate Employee object
            String validationError = validate(employeeData);
            if (validationError != null) {
                System.out.println("Validation failed: " + validationError);
                return Status.FAILED; 
            }
            
            Employee employee = new Employee(employeeData);

            // Check if Employee exists
            if (!employeeDAO.isEmployeeIDExist(employee.getEmployeeId())) {
                return Status.NOT_FOUND;
            }

            // Perform update
            boolean updated = employeeDAO.updateEmployee(employee);
            return updated ? Status.SUCCESS : Status.FAILED;

        } catch (EmployeeDaoException e) {
            throw new EmployeeServiceException("Error updating employee: " + e.getMessage(), e);
        } catch (ParseException e) {
            throw new EmployeeServiceException("Error parsing employee data: " + e.getMessage(), e);
        }
    }
    
    //Method to delete  employee by ID from database
    public boolean deleteEmployeeById(String employeeId) throws EmployeeServiceException{
    try {
    	return employeeDAO.deleteEmployeeById(employeeId);
    }catch (EmployeeDaoException e) {
    	throw new EmployeeServiceException("Server Failed to delete Employee with ID " + employeeId,e);
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
