package com.litmus.employeemanagementsystem.constant;

public class SQLConstants {
	
	// Column Name Constants
	public static final String COLUMN_EMPLOYEE_ID = "emp_id";
	public static final String COLUMN_FIRST_NAME = "first_name";
	public static final String COLUMN_LAST_NAME = "last_name";
	public static final String COLUMN_EMAIL = "email";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_DEPARTMENT = "department";
	public static final String COLUMN_SALARY = "salary";
	public static final String COLUMN_JOIN_DATE = "join_date";

	// SQL Query to Insert Employee
	public static final String INSERT_EMPLOYEE =
	    "INSERT INTO employees (" +
	    COLUMN_EMPLOYEE_ID + ", " +
	    COLUMN_FIRST_NAME + ", " +
	    COLUMN_LAST_NAME + ", " +
	    COLUMN_EMAIL + ", " +
	    COLUMN_PHONE + ", " +
	    COLUMN_DEPARTMENT + ", " +
	    COLUMN_SALARY + ", " +
	    COLUMN_JOIN_DATE + ") " +
	    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	// SQL Query to Check if Employee ID Exists
	public static final String CHECK_EMPLOYEE_ID_EXISTS =
	    "SELECT " + COLUMN_EMPLOYEE_ID +
	    " FROM employees WHERE " + COLUMN_EMPLOYEE_ID + " = ?";

    
    //SQL Query to get all employee by ID
    public static final String GET_ALL_EMPLOYEE =
    	    "SELECT " +
    	    COLUMN_EMPLOYEE_ID + ", " +
    	    COLUMN_FIRST_NAME + ", " +
    	    COLUMN_LAST_NAME + ", " +
    	    COLUMN_EMAIL + ", " +
    	    COLUMN_PHONE + ", " +
    	    COLUMN_DEPARTMENT + ", " +
    	    COLUMN_SALARY + ", " +
    	    COLUMN_JOIN_DATE +
    	    " FROM employees";
    
    //SQL Query to delete an employee by ID
    public static final String DELETE_EMPLOYEE_BY_ID =
    		"DELETE FROM employees  WHERE " + COLUMN_EMPLOYEE_ID + "=?";
    
    //SQL Query to fetch Employee by ID
    public static final String GET_EMPLOYEE_BY_ID =
    		"SELECT "+
			COLUMN_EMPLOYEE_ID + ", " +
    	    COLUMN_FIRST_NAME + ", " +
    	    COLUMN_LAST_NAME + ", " +
    	    COLUMN_EMAIL + ", " +
    	    COLUMN_PHONE + ", " +
    	    COLUMN_DEPARTMENT + ", " +
    	    COLUMN_SALARY + ", " +
    	    COLUMN_JOIN_DATE +
    	    " FROM employees WHERE " +
    	    COLUMN_EMPLOYEE_ID + "=?";
    
    //SQL Query to Update Employee Details
    public static final String UPDATE_EMPLOYEE =
    "UPDATE employees SET " +
    COLUMN_FIRST_NAME + " = ?, " +
    COLUMN_LAST_NAME + " = ?, " +
    COLUMN_EMAIL + " = ?, " +
    COLUMN_PHONE + " = ?, " +
    COLUMN_DEPARTMENT + " = ?, " +
    COLUMN_SALARY + " = ?, " +
    COLUMN_JOIN_DATE + " = ? " +
    "WHERE " + COLUMN_EMPLOYEE_ID + " =?";
    
    //SQL Query to Update Department given EmployeeID
    public static final String UPDATE_DEPARTMENT_GIVEN_ID =
    "UPDATE employees SET " +
    COLUMN_DEPARTMENT + "= ?" +
    "WHERE " + COLUMN_EMPLOYEE_ID + " =?";
    


}
