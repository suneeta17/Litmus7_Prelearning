package com.litmus.employeemanagementsystem.CONSTANTS;

public class SQLConstants {
    
    public static final String INSERT_EMPLOYEE = 
        "INSERT INTO employees (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String CHECK_EMPLOYEE_ID_EXISTS = 
        "SELECT emp_id FROM employees WHERE emp_id = ?";
}
