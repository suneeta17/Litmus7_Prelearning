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
import com.litmus.employeemanagementsystem.util.ErrorCodeUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class EmployeeDAO  {
	
	private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

	
	//To add employee data to db
	public boolean saveEmployee(Employee emp) throws  EmployeeDaoException {
		logger.debug("Attempting to save employee : {},",emp);
		
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
	    logger.info("Employee {} saved successfully. Rows inserted: {}", emp.getEmployeeId(), rowsInserted);
	    return rowsInserted > 0;
		}
		catch (SQLException e) {
			logger.error("Error saving employee {}: {}", emp.getEmployeeId(), e.getMessage(), e);
			throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-500"), e);
		}
	    	
	}
	
	//To check Employee exist or not
	public boolean isEmployeeIDExist(String employeeId) throws  EmployeeDaoException  {
		logger.debug("Cheching if employee id exist : {}", employeeId);
		try (Connection conn = DBConnection.getConnection();
		PreparedStatement pst = conn.prepareStatement(SQLConstants.CHECK_EMPLOYEE_ID_EXISTS);){
		
		pst.setString(1,employeeId);
		ResultSet rs = pst.executeQuery();
		boolean exists= rs.next();
		logger.info("Employee ID {} exist ? {}",employeeId , exists);
		return exists; }
		
		catch(SQLException e) {
			logger.error("Error checking employee ID{}", employeeId,e);
			throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-501", employeeId),e);

		}
	}
	
	//To get all employees
	public List<Employee> getAllEmployees() throws  EmployeeDaoException {
		logger.debug("Fetching all employees from database.");
		List<Employee> employees = new ArrayList<>();
		

	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(SQLConstants.GET_ALL_EMPLOYEE);) {
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
            
            logger.info("Fetched {} employees from database", employees.size());

	    } catch (SQLException e) {
	    	logger.error("Database error while fetching employees from database",e);
	        throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-502"),e);
	    }

	    return employees; 
	}
	
	//DAO method to get Employee by ID
	public static Employee getEmployeeById(String employeeId) throws EmployeeDaoException {
		logger.debug("Fetching employee details by ID ", employeeId);
		
		Employee employee = null;
		try (Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.GET_EMPLOYEE_BY_ID);){
			
			pstmt.setString(1, employeeId);
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
                logger.info("Employee Found : {}" , employeeId);
			}else {
				logger.warn("No employee found with ID: {}",employeeId);
			}
			
			
		}catch(SQLException e) {
			logger.error("Database error fetching employee by ID {} ",employeeId,e);
			throw new EmployeeDaoException( ErrorCodeUtil.getErrorMessage("EMP-DB-503", employeeId),e);
		}return employee;
	}
	
	//Method to Update employee details
	public boolean updateEmployee(Employee employee) throws EmployeeDaoException {
		logger.debug("Updating employee: {}" , employee.getEmployeeId());
		
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_EMPLOYEE);){
			
		    pstmt.setString(1, employee.getFirstName());
		    pstmt.setString(2, employee.getLastName());
		    pstmt.setString(3, employee.getEmail());
		    pstmt.setString(4, employee.getPhone());
		    pstmt.setString(5, employee.getDepartment());
		    pstmt.setDouble(6, employee.getSalary());
		    pstmt.setDate(7, employee.getJoinDate());
		    pstmt.setString(8, employee.getEmployeeId());
		    
		    int rowsUpdated = pstmt.executeUpdate();
		    logger.info("Updated {} rows for employee {}", rowsUpdated, employee.getEmployeeId());
		    return rowsUpdated > 0;
			}
			catch (SQLException e) {
				logger.error("Error updating employee {}", employee.getEmployeeId(), e);
				throw new EmployeeDaoException( ErrorCodeUtil.getErrorMessage("EMP-DB-504", employee.getEmployeeId()), e);
			}
			
		}
	
	//DAO method to delete employee by ID
	public boolean deleteEmployeeById(String employeeId) throws EmployeeDaoException {
		logger.debug("Deleting employee with ID: {}", employeeId);
		int rowsDeleted;
		boolean result = false;
		
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.DELETE_EMPLOYEE_BY_ID);){
			pstmt.setString(1,employeeId);
				
			rowsDeleted = pstmt.executeUpdate();
			 
			if (rowsDeleted > 0) {
				logger.info("Deleted employee with ID: {}", employeeId);
                result = true;
            } else {
                logger.warn("No employee found to delete with ID: {}", employeeId);
            }
		}catch (SQLException e) {
			logger.error("Error deleting employee {}", employeeId, e);
			throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-505", employeeId), e);
		}
		return result;
		
	}
	
	//DAO method to add employees in batch
	public int[] addEmployeesInBatch(List<Employee> employees) throws EmployeeDaoException {
		logger.debug("Adding {} employees in batch", employees.size());
		
		try(Connection connection = DBConnection.getConnection();
			PreparedStatement pstmt = connection.prepareStatement(SQLConstants.INSERT_EMPLOYEE);){
			
			for(Employee employee : employees) {
				pstmt.setString(1, employee.getEmployeeId());
			    pstmt.setString(2, employee.getFirstName());
			    pstmt.setString(3, employee.getLastName());
			    pstmt.setString(4, employee.getEmail());
			    pstmt.setString(5, employee.getPhone());
			    pstmt.setString(6, employee.getDepartment());
			    pstmt.setDouble(7, employee.getSalary());
			    pstmt.setDate(8, employee.getJoinDate());
			    pstmt.addBatch();
			}
			
			int[] rowsAffected = pstmt.executeBatch();
			logger.info("Batch insert completed. {} employees added.", rowsAffected.length);
			return rowsAffected ;
			
		}catch(SQLException e) {
			logger.error("Error adding employees in batch", e);
			throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-506"),e);
		}
		
	}
	
	public int[] transferEmployeesToDepartment(List<String> employeeIds,String newDepartment) throws EmployeeDaoException {
		logger.debug("Transferring {} employees to department: {}", employeeIds.size(), newDepartment);
		
		try(Connection connection = DBConnection.getConnection();){
			connection.setAutoCommit(false);
			try(PreparedStatement pstmt = connection.prepareStatement(SQLConstants.UPDATE_DEPARTMENT_GIVEN_ID);){
			
			for(String employeeId : employeeIds) {
				pstmt.setString(1, newDepartment);
				pstmt.setString(2, employeeId);
				pstmt.addBatch();
			}	
			int[] rowsAffected = pstmt.executeBatch();
			connection.commit();
			logger.info("Transferred {} employees to department {}", rowsAffected.length, newDepartment);
			return rowsAffected ;
			
			}catch(SQLException e) {
				connection.rollback();
				logger.error("Transaction failed while transferring employees. Rolled back.", e);
				throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-507"),e);
			}finally { 
				connection.setAutoCommit(true);
			
			}
		}catch (SQLException e) {
			logger.error("DB connection error during employee transfer", e);
            throw new EmployeeDaoException(ErrorCodeUtil.getErrorMessage("EMP-DB-508"), e);
		}
	}
}	
  
	
