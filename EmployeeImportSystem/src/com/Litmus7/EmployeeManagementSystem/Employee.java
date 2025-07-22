package com.Litmus7.EmployeeManagementSystem;

import java.sql.Date;


public class Employee {
	
	private String empId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String department;
    private double salary;
    private Date joinDate;
	
	public Employee(String[] empData) {
		
		 empId = empData[0];
		 firstName = empData[1];
		 lastName = empData[2];
		 email = empData[3];
		 phone = empData[4];
		 department = empData[5];
		 salary = Double.parseDouble(empData[6]);
		 joinDate = ValidationUtility.isDateValid(empData[7]);
		
		
	}
	
	public String getEmpId() { return empId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public Date getJoinDate() { return joinDate; }

}
