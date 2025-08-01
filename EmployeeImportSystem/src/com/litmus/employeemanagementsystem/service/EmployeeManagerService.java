package com.litmus.employeemanagementsystem.service;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.util.ValidationUtility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EmployeeManagerService {
	
	//Method to import employee data to database
    public Map<String, List<String>> importEmployeeDataToDB(String filePath) throws ParseException, IOException, SQLException {
        EmployeeDAO dao = new EmployeeDAO();
        Map<String, List<String>> result = new HashMap<>();
        result.put("success", new ArrayList<>());
        result.put("error", new ArrayList<>());

        if (ValidationUtility.isCSVFile(filePath)) {
            result.get("error").add("Invalid file type. Please provide a CSV file.");
            return result;
        }

        List<String[]> rows = loadCSV(filePath);
        List<Employee> validEmployees = new ArrayList<>();

        for (String[] row : rows) {
            String error = validate(row);
            if (error != null) {
                result.get("error").add("Invalid: " + Arrays.toString(row) + " | Reason: " + error);
            } else {
                validEmployees.add(new Employee(row));
            }
        }

        for (Employee emp : validEmployees) {
            String employeeId = emp.getEmpId();
            if (dao.isEmployeeIDExist(employeeId)) {
                result.get("error").add("Duplicate Employee ID: " + employeeId);
            } else if (dao.addEmployee(emp)) {
                result.get("success").add("Inserted: " + employeeId);
            } else {
                result.get("error").add("Failed to insert: " + employeeId);
            }
        }

        return result;
    }

    //Method to load employee data from csv file
    private List<String[]> loadCSV(String path) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }

    //Method to validate employee record
    private String validate(String[] data) throws ParseException {
        String[] fields = {"Employee ID", "First Name", "Last Name", "Email", "Phone", "Department", "Salary", "Join Date"};
        if (data.length != fields.length) return "Expected " + fields.length + " fields, got " + data.length;

        for (int i = 0; i < data.length; i++) {
            if (!ValidationUtility.isNotNullOrNotEmpty(data[i])) {
                return fields[i] + " is empty or null.";
            }
        }

        String email = data[3], phone = data[4], salary = data[6], date = data[7];
        if (!ValidationUtility.isEmailValid(email)) return "Invalid email: " + email;
        if (ValidationUtility.isPhoneNumberValid(phone)) return "Invalid phone: " + phone;
        if (!ValidationUtility.isDoubleValid(salary)) return "Invalid salary: " + salary;
        if (ValidationUtility.isDateValid(date) == null) return "Invalid date: " + date;

        return null;
    }
}
