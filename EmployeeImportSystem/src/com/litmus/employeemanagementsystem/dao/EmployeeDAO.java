package com.litmus.employeemanagementsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.litmus.employeemanagementsystem.constant.SQLConstants;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.exception.EmployeeDaoException;
import com.litmus.employeemanagementsystem.util.DBConnection;


public class EmployeeDAO  {
	
	//To add employee data to db
	public boolean saveEmployee(Employee emp) throws  EmployeeDaoException {
		
		try(Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(SQLConstants.INSERT_EMPLOYEE);){
		
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
		PreparedStatement pst = conn.prepareStatement(SQLConstants.CHECK_EMPLOYEE_ID_EXISTS);){
		
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
	         PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.GET_ALL_EMPLOYEE)) {
	    	ResultSet resultSet = preparedStatement.executeQuery();
	   
	        
            while (resultSet.next()) {
            	Employee employee = new Employee();
                employee.setEmployeeId(resultSet.getString(SQLConstants.COLUMN_EMPLOYEE_ID));
                employee.setFirstName(resultSet.getString(SQLConstants.COLUMN_FIRST_NAME));
                employee.setLastName(resultSet.getString(SQLConstants.COLUMN_LAST_NAME));
                employee.setEmail(resultSet.getString(SQLConstants.COLUMN_EMAIL));
                employee.setPhone(resultSet.getString(SQLConstants.COLUMN_PHONE));
                employee.setDepartment(resultSet.getString(SQLConstants.COLUMN_DEPARTMENT));
                employee.setSalary(resultSet.getDouble(SQLConstants.COLUMN_SALARY));
                employee.setJoinDate(resultSet.getDate(SQLConstants.COLUMN_JOIN_DATE)); 
                employees.add(employee);
            
            }

	    } catch (SQLException e) {
	        throw new EmployeeDaoException("Database error while fetching employee data from Database",e);
	    }

	    return employees; 
	}
	
	//DAO method to get Employee by ID
	public static Employee getEmployeeById(String EmployeeId) throws EmployeeDaoException {
		Employee employee = null;
		try (Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_EMPLOYEE_BY_ID)){
			
			pstmt.setString(1, EmployeeId);
			ResultSet resultSet = pstmt.executeQuery();
			
			if (resultSet.next()){	
				employee = new Employee();
				employee.setEmployeeId(resultSet.getString(SQLConstants.COLUMN_EMPLOYEE_ID));
                employee.setFirstName(resultSet.getString(SQLConstants.COLUMN_FIRST_NAME));
                employee.setLastName(resultSet.getString(SQLConstants.COLUMN_LAST_NAME));
                employee.setEmail(resultSet.getString(SQLConstants.COLUMN_EMAIL));
                employee.setPhone(resultSet.getString(SQLConstants.COLUMN_PHONE));
                employee.setDepartment(resultSet.getString(SQLConstants.COLUMN_DEPARTMENT));
                employee.setSalary(resultSet.getDouble(SQLConstants.COLUMN_SALARY));
                employee.setJoinDate(resultSet.getDate(SQLConstants.COLUMN_JOIN_DATE)); 	
			}	
		}catch(SQLException e) {
			e.printStackTrace();
			throw new EmployeeDaoException("Database error while fetching employee data from Database",e);
		}return employee;
	}
	
	//Method to Update employee details
	public boolean updateEmployee(Employee employee) throws EmployeeDaoException {
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_EMPLOYEE)){
			
		    pstmt.setString(1, employee.getFirstName());
		    pstmt.setString(2, employee.getLastName());
		    pstmt.setString(3, employee.getEmail());
		    pstmt.setString(4, employee.getPhone());
		    pstmt.setString(5, employee.getDepartment());
		    pstmt.setDouble(6, employee.getSalary());
		    pstmt.setDate(7, employee.getJoinDate());
		    pstmt.setString(8, employee.getEmployeeId());
		    
		    int rowsUpdated = pstmt.executeUpdate();
		    return rowsUpdated > 0;
			}
			catch (SQLException e) {
				throw new EmployeeDaoException("Database error while saveing employee into Database", e);
			}
			
		}
	
	//DAO method to delete employee by ID
	public boolean deleteEmployeeById(String employeeId) throws EmployeeDaoException {
		int rowsDeleted;
		boolean result = false;
		
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_EMPLOYEE_BY_ID)){
			pstmt.setString(1,employeeId);
				
			rowsDeleted = pstmt.executeUpdate();
			 
			if (rowsDeleted > 0)  result = true;;
		}catch (SQLException e) {
			throw new EmployeeDaoException("Database error could not delete employee with  ID " + employeeId , e);
		}
		return result;
		
	}
}	
  
	
