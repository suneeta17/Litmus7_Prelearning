package com.litmus.employeemanagementsystem.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.litmus.employeemanagementsystem.CONSTANTS.SQLConstants;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.util.DBConnection;


public class EmployeeDAO  {
	
	//To add employee data to db
	public boolean addEmployee(Employee emp) throws SQLException {
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE);
		
		pst.setString(1, emp.getEmpId());
	    pst.setString(2, emp.getFirstName());
	    pst.setString(3, emp.getLastName());
	    pst.setString(4, emp.getEmail());
	    pst.setString(5, emp.getPhone());
	    pst.setString(6, emp.getDepartment());
	    pst.setDouble(7, emp.getSalary());
	    pst.setDate(8, emp.getJoinDate());
	    
	    int rowsInserted = pst.executeUpdate();
	    if (rowsInserted > 0) 
	    	return true;
	    return false;
	}
	
	//To check Employee exist or not
	public boolean isEmployeeIDExist(String empId) throws SQLException {
		
		Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(SQLConstants.CHECK_EMPLOYEE_ID_EXISTS);
		pst.setString(1,empId);
		ResultSet rs = pst.executeQuery();
		
		return rs.next();
		
		
	}
	
}	
  
	
