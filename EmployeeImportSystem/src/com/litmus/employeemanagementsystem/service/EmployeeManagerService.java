package com.litmus.employeemanagementsystem.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.dto.Response;
import com.litmus.employeemanagementsystem.util.ValidationUtility;

public class EmployeeManagerService {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    // Full flow: CSV check → read → validate → insert
    public Response<String> importEmployeeDataToDB(String filePath) {
        if (ValidationUtility.isCSVFile(filePath)) {
            return new Response<>(400, "Invalid file type. Please provide a CSV file.");
        }

        List<String[]> csvData;
        try {
            csvData = loadCSVData(filePath);
        } catch (IOException e) {
            return new Response<>(500, "Failed to read file: " + e.getMessage());
        }

        List<Employee> validEmployees = new ArrayList<>();
        int skippedCount = 0;

        for (String[] empData : csvData) {
            Response<Employee> validationResult = validateEmployeeData(empData);
            if (validationResult.getStatusCode() != 200) {
                System.out.println("Validation failed: " + String.join(",", empData) + " | Reason: " + validationResult.getMessage());
                skippedCount++;
            } else {
                validEmployees.add(validationResult.getData());
            }
        }

        int insertedCount = 0;
        for (Employee emp : validEmployees) {
            try {
                if (!employeeDAO.isEmployeeIDExist(emp.getEmpId())) {
                    if (employeeDAO.addEmployee(emp)) {
                        insertedCount++;
                    }
                } else {
                    System.out.println("Duplicate Employee ID skipped: " + emp.getEmpId());
                }
            } catch (SQLException e) {
                System.out.println("DB Error for employee " + emp.getEmpId() + ": " + e.getMessage());
            }
        }

        return new Response<>(200, "Import complete. Inserted: " + insertedCount + ", Skipped: " + skippedCount);
    }

    
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


    // Validates a single employee entry
    private Response<Employee> validateEmployeeData(String[] empData) {
        if (empData.length != 8) {
            return new Response<>(400, "Invalid number of fields (expected 8, got " + empData.length + ")");
        }

        try {
            String email = empData[3];
            String phone = empData[4];
            String salary = empData[6];
            String joinDate = empData[7];

            if (!ValidationUtility.isEmailValid(email)) {
                return new Response<>(400, "Invalid email: " + email);
            }
            if (ValidationUtility.isPhoneNumberValid(phone)) {
                return new Response<>(400, "Invalid phone number: " + phone);
            }
            if (ValidationUtility.isDoubleValid(salary)) {
                return new Response<>(400, "Invalid salary: " + salary);
            }
            if (ValidationUtility.isDateValid(joinDate) == null) {
                return new Response<>(400, "Invalid join date: " + joinDate);
            }

            Employee emp = new Employee(empData);
            return new Response<>(200, "Valid", emp);

        } catch (Exception e) {
            return new Response<>(500, "Validation exception: " + e.getMessage());
        }
    }
}
