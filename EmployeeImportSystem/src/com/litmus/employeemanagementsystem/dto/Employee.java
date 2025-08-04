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
    
    public Employee() {}
	
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
	
	//getters
	public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }
    
    //setters
    public void setEmployeeId(String employeeId) {this.employeeId = employeeId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
    
    
    

}
