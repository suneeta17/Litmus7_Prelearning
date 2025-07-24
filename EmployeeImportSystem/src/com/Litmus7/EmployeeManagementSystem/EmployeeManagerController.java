package com.Litmus7.EmployeeManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EmployeeManagerController {

    //Read CSV line by line
    public void readCSV(String filePath, Consumer<Response<String>> responseHandler) throws Exception {
        // Fail fast for incorrect file type
        if (!ValidationUtility.isCSVFile(filePath)) {
            throw new IllegalArgumentException("Incorrect File Type: " + filePath);
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length != 8) {
                    responseHandler.accept(new Response<>(false, 422,
                            "Incorrect number of fields. Skipping line: " + line));
                    continue;
                }

                Employee emp = new Employee(values);

                // Validate employee data
                Response<String> validationResult = validateEmployeeData(emp);
                if (!validationResult.isSuccess()) {
                    responseHandler.accept(validationResult);
                    continue;
                }

                // Insert into DB
                Response<String> dbResult = writeEmployeeToDatabase(emp);
                responseHandler.accept(dbResult);
            }

        } catch (Exception e) {
            responseHandler.accept(new Response<>(false, 500, "Error reading CSV: " + e.getMessage()));
        }
    }

    // Validate employee data
    private Response<String> validateEmployeeData(Employee emp) throws Exception {
        List<String> missingFields = new ArrayList<>();
        EmployeeDBService db = new EmployeeDBService();

        if (!ValidationUtility.isNotNullorNotEmpty(emp.getEmpId())) missingFields.add("empId");
        if (!ValidationUtility.isNotNullorNotEmpty(emp.getLastName())) missingFields.add("lastName");
        if (!ValidationUtility.isNotNullorNotEmpty(emp.getEmail())) missingFields.add("email");
        if (!ValidationUtility.isNotNullorNotEmpty(emp.getPhone())) missingFields.add("phone");

        if (db.isEmployeeIDExist(emp.getEmpId())) {
            return new Response<>(false, 409, "Employee with ID already exists: " + emp.getEmpId());
        }

        if (!missingFields.isEmpty()) {
            return new Response<>(false, 422,
                    "Validation failed. Missing fields: " + String.join(", ", missingFields));
        }

        if (!ValidationUtility.isEmailValid(emp.getEmail())) {
            return new Response<>(false, 400, "Invalid email format for: " + emp.getEmail());
        }

        if (!ValidationUtility.isPhoneNumberValid(emp.getPhone())) {
            return new Response<>(false, 400, "Invalid phone number for: " + emp.getPhone());
        }

        return new Response<>(true, 200, "Validation passed for employee: " + emp.getEmpId());
    }

    // Insert employee into database
    private Response<String> writeEmployeeToDatabase(Employee emp) throws Exception {
        EmployeeDBService db = new EmployeeDBService();

        String sql = "INSERT INTO employees (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = db.getConn(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, emp.getEmpId());
            pstmt.setString(2, emp.getFirstName());
            pstmt.setString(3, emp.getLastName());
            pstmt.setString(4, emp.getEmail());
            pstmt.setString(5, emp.getPhone());
            pstmt.setString(6, emp.getDepartment());
            pstmt.setDouble(7, emp.getSalary());
            pstmt.setDate(8, emp.getJoinDate());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                return new Response<>(true, 200, "Employee inserted successfully: " + emp.getEmpId());
            } else {
                return new Response<>(false, 500, "Failed to insert employee: " + emp.getEmpId());
            }
        } catch (Exception e) {
            return new Response<>(false, 500, "Database error: " + e.getMessage() + " for employee: " + emp.getEmpId());
        }
    }
}
