package com.litmus.employeemanagementsystem.dto;

import java.sql.Date;
import java.text.ParseException;

import com.litmus.employeemanagementsystem.util.ValidationUtility;


public class Employee {
	
	private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private double salary;
    private Date joinDate;
	
	public Employee(String[] empData) throws ParseException {
		
		 employeeId = empData[0];
		 firstName = empData[1];
		 lastName = empData[2];
		 email = empData[3];
		 phone = empData[4];
		 department = empData[5];
		 salary = ValidationUtility.isDoubleValid(empData[6]) ? Double.parseDouble(empData[6]) : null;
		 joinDate = ValidationUtility.isDateFormatValid(empData[7]) ? ValidationUtility.isDateValid(empData[7]) : null;

		
		
	}
	
	public String getEmpId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }

}
