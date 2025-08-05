package com.litmus.employeemanagementsystem.dao;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus.employeemanagementsystem.constant.EmployeeTableConstants;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.exception.EmployeeDaoException;
import com.litmus.employeemanagementsystem.util.DBConnection;


public class EmployeeDAO  {
	
	//To add employee data to db
	public boolean saveEmployee(Employee emp) throws  EmployeeDaoException {
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(EmployeeTableConstants.INSERT_EMPLOYEE);){
		
		pst.setString(1, emp.getEmployeeId());
	    pst.setString(2, emp.getFirstName());
	    pst.setString(3, emp.getLastName());
	    pst.setString(4, emp.getEmail());
	    pst.setString(5, emp.getPhone());
	    pst.setString(6, emp.getDepartment());
	    pst.setDouble(7, emp.getSalary());
	    pst.setDate(8, emp.getJoinDate());
	    
	    int rowsInserted = pst.executeUpdate();
	    return rowsInserted > 0;
		}
		catch (SQLException e) {
			throw new EmployeeDaoException("Database error while saveing employee into Database", e);
		}
	    	
	}
	
	//To check Employee exist or not
	public boolean isEmployeeIDExist(String employeeId) throws  EmployeeDaoException  {
		
		try (Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(EmployeeTableConstants.CHECK_EMPLOYEE_ID_EXISTS);){
		
		pst.setString(1,employeeId);
		ResultSet rs = pst.executeQuery();
		
		return rs.next();}
		catch(SQLException e) {
			throw new EmployeeDaoException("Database error." ,e);

		}
		
		
	}
	
	//To get all employees
	public List<Employee> getAllEmployees() throws  EmployeeDaoException {
		List<Employee> employees = new ArrayList<>();
		

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(EmployeeTableConstants.GET_ALL_EMPLOYEE)) {
	    	ResultSet resultSet = preparedStatement.executeQuery();
	   
	        
            while (resultSet.next()) {
            	Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getString(EmployeeTableConstants.COLUMN_EMPLOYEE_ID));
                employee.setFirstName(resultSet.getString(EmployeeTableConstants.COLUMN_FIRST_NAME));
                employee.setLastName(resultSet.getString(EmployeeTableConstants.COLUMN_LAST_NAME));
                employee.setEmail(resultSet.getString(EmployeeTableConstants.COLUMN_EMAIL));
                employee.setPhone(resultSet.getString(EmployeeTableConstants.COLUMN_PHONE));
                employee.setDepartment(resultSet.getString(EmployeeTableConstants.COLUMN_DEPARTMENT));
                employee.setSalary(resultSet.getDouble(EmployeeTableConstants.COLUMN_SALARY));
                employee.setJoinDate(resultSet.getDate(EmployeeTableConstants.COLUMN_JOIN_DATE)); 
                employees.add(employee);
            
            }

	    } catch (SQLException e) {
	        throw new EmployeeDaoException("Database error while fetching employee data from Database",e);
	    }

	    return employees; 
	}

	
}	
  
	
