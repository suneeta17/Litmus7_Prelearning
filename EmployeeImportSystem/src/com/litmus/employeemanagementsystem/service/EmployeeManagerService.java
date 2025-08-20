package com.litmus.employeemanagementsystem.service;

import com.litmus.employeemanagementsystem.dao.EmployeeDAO;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.exception.EmployeeDaoException;
import com.litmus.employeemanagementsystem.exception.EmployeeServiceException;
import com.litmus.employeemanagementsystem.util.CSVReader;
import com.litmus.employeemanagementsystem.util.ErrorCodeUtil;
import com.litmus.employeemanagementsystem.util.ValidationUtility;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EmployeeManagerService {
	
	private static final Logger logger = LogManager.getLogger(EmployeeManagerService.class);
	
	public enum Status {SUCCESS,NOT_FOUND,FAILED,ALREADY_EXIST}
	private EmployeeDAO employeeDAO = new EmployeeDAO();
	
	//Method to import employee data to database
    public Map<String, List<String>> importEmployeeDataToDB(String filePath) throws  EmployeeServiceException {
    	logger.debug("Entering importEmployeeDataToDB with filePath={}", filePath);
    	
    	Map<String, List<String>> result = new HashMap<>();
        result.put("success", new ArrayList<>());
        result.put("error", new ArrayList<>());
        List<String[]> rows;
        

        try {
        rows = CSVReader.loadData(filePath);
        logger.info("Loaded {} rows from CSV file", rows.size());
        }catch(IOException e) {
        	logger.error("Error reading CSV file: {}", filePath, e);
        	throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-100", filePath),e);
        	
        }
        List<Employee> validEmployees = new ArrayList<>();

        try {
		for (String[] row : rows) {
			logger.debug("Validating row: {}", Arrays.toString(row));
            String error = validate(row);
            if (error != null) {
            	logger.warn("Validation failed for row {}: {}", Arrays.toString(row), error);
                result.get("error").add(ErrorCodeUtil.getErrorMessage("EMP-SERV-101", Arrays.toString(row), error));
            } else {
                validEmployees.add(new Employee(row));
            }
		}}catch (ParseException e) {
			logger.error("Parsing error during validation", e);
			throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-102"),e);
			
		}
        

        for (Employee emp : validEmployees) {
            String employeeId = emp.getEmployeeId();
            
            try {
            	logger.debug("Checking if employee ID {} exists", employeeId);
            	if (employeeDAO.isEmployeeIDExist(employeeId)) {
                    logger.info("Duplicate Employee ID found: {}", employeeId);
                    result.get("error").add( ErrorCodeUtil.getErrorMessage("EMP-SERV-103", employeeId));
                } else if (employeeDAO.saveEmployee(emp)) {
                    logger.info("Inserted employee: {}", employeeId);
                    result.get("success").add( ErrorCodeUtil.getErrorMessage("EMP-SERV-104", employeeId));
                } else {
                    logger.warn("Failed to insert employee: {}", employeeId);
                    result.get("error").add( ErrorCodeUtil.getErrorMessage("EMP-SERV-105", employeeId));
                }
            }catch (EmployeeDaoException e) {
            	logger.error("DAO error while saving employee {}", employeeId, e);
            	throw new EmployeeServiceException( ErrorCodeUtil.getErrorMessage("EMP-SERV-106", employeeId),e);
            }
        }

        return result;
    }
    
    
    //Method to fetch all Employees from database
    public List<Employee> getAllEmployees() throws EmployeeServiceException{
    	logger.debug("Fetching all employees");
    	try{
        	return employeeDAO.getAllEmployees();
        }catch (EmployeeDaoException e) {
        	logger.error("Error fetching all employees", e);
        	throw new EmployeeServiceException( ErrorCodeUtil.getErrorMessage("EMP-SERV-200"),e);
        }
    }
    
    //Method to fetch employee record by ID
    public Employee getEmployeebyId(String employeeId) throws EmployeeServiceException {
    	logger.debug("Fetching employee by ID: {}", employeeId);	
    	try {
				return EmployeeDAO.getEmployeeById(employeeId);
			} catch  (EmployeeDaoException e) {
				logger.error("Error fetching employee with ID {}", employeeId, e);
	    		throw new EmployeeServiceException( ErrorCodeUtil.getErrorMessage("EMP-SERV-201", employeeId), e);
			}
    }
    
    //Method to Add a new Employee
    public Status addEmployee(List<String> data) throws EmployeeServiceException {
    	logger.debug("Adding employee with data: {}", data);
    	try {
            String[] employeeData = data.toArray(new String[0]);
            String validationError = validate(employeeData);
            if (validationError != null) {
            	logger.warn("Validation failed: {}", validationError);
                return Status.FAILED; 
            }
            
            Employee employee = new Employee(employeeData);

            // Check if Employee exists
            if (employeeDAO.isEmployeeIDExist(employee.getEmployeeId())) {
            	logger.info("Employee already exists: {}", employee.getEmployeeId());
            	return Status.ALREADY_EXIST;
            }

            // Save new Employee
            boolean added = employeeDAO.saveEmployee(employee);
            logger.info("Employee {} added status: {}", employee.getEmployeeId(), added);
            return added ? Status.SUCCESS : Status.FAILED;

        } catch (EmployeeDaoException e) {
        	logger.error("Error adding new employee", e);
            throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-202"), e);
        } catch (ParseException e) {
        	logger.error("Error while parsing employee data", e);
            throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-203"), e);
        }
    }
       
    
    //Method to update Employee
    public Status updateEmployee(List<String> data) throws EmployeeServiceException {
    	logger.debug("Updating employee with data: {}", data);
    	try {
            String[] employeeData = data.toArray(new String[0]);

            // Validate Employee object
            String validationError = validate(employeeData);
            if (validationError != null) {
            	logger.warn("Validation failed: {}", validationError);
                return Status.FAILED; 
            }
            
            Employee employee = new Employee(employeeData);
            // Check if Employee exists
            if (!employeeDAO.isEmployeeIDExist(employee.getEmployeeId())) {
            	 logger.info("Employee not found: {}", employee.getEmployeeId());
            	return Status.NOT_FOUND;
            }

            boolean updated = employeeDAO.updateEmployee(employee);
            logger.info("Employee {} update status: {}", employee.getEmployeeId(), updated);
            return updated ? Status.SUCCESS : Status.FAILED;

        } catch (EmployeeDaoException e) {
        	logger.error("Error updating employee", e);
            throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-204"), e);
        } catch (ParseException e) {
        	logger.error("Error parsing employee data", e);
            throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-205"), e);
        }
    }
    
    //Method to delete  employee by ID from database
    public boolean deleteEmployeeById(String employeeId) throws EmployeeServiceException{
    	 logger.debug("Deleting employee by ID: {}", employeeId);
         try {
             boolean deleted = employeeDAO.deleteEmployeeById(employeeId);
             logger.info("Employee {} deletion status: {}", employeeId, deleted);
             return deleted;
         } catch (EmployeeDaoException e) {
             logger.error("Error deleting employee with ID {}", employeeId, e);
             throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-206", employeeId), e);
         }
     }
    
    //Method to add Employees in batches
    public Map<String, Object> addEmployeesInBatch(List<String[]> employees) throws EmployeeServiceException {
    	logger.info("Starting batch addition of {} employees", employees.size());
    	List<Employee> validEmployees = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (String[] employee : employees) {
        	String employeeId = employee[0];
        	logger.debug("Processing employee with ID: {}", employeeId);
        	try {
				if (!employeeDAO.isEmployeeIDExist(employeeId)) {
				    try {
				        String error = validate(employee);
				        if (error != null) {
				        	logger.warn("Validation failed for employee ID {}: {}", employeeId, error);
				            errors.add(ErrorCodeUtil.getErrorMessage("EMP-SERV-207", employeeId, error));
				        } else {
				        	logger.debug("Validation passed for employee ID: {}", employeeId);
				            validEmployees.add(new Employee(employee));
				        }
				    } catch (ParseException e) {
				    	logger.error("Error parsing data for employee ID {}: {}", employeeId, e.getMessage(), e);
				        throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-2078", employeeId), e); 
				}}
				else {
	                logger.warn("Duplicate employee ID found: {}", employeeId);
	            }
			} catch (EmployeeDaoException e) {
				logger.error("DAO error while checking employee ID {}: {}", employeeId, e.getMessage(), e);
				throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-209", employeeId), e); 
			}
        }

        int[] insertStatus = new int[0];
        if (!validEmployees.isEmpty()) {
            logger.info("Inserting {} valid employees into database", validEmployees.size());
            try {
                insertStatus = employeeDAO.addEmployeesInBatch(validEmployees);
                logger.debug("Batch insert status: {}", Arrays.toString(insertStatus));
            } catch (EmployeeDaoException e) {
                logger.error("DAO error during batch insert: {}", e.getMessage(), e);
                throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-210"), e);
            }
        } else {
            logger.warn("No valid employees found for batch insert");
        }

        Map<String, Object> result = new HashMap<>();
        result.put("insertStatus", insertStatus);
        result.put("errors", errors);

        logger.info("Batch addition completed. Success count: {}, Error count: {}",
                validEmployees.size(), errors.size());
        return result;
    }

 // Method to update the department of employees given the id
    public int[] transferEmployeesToDepartment(List<String> employeeIds, String newDepartment) throws EmployeeServiceException {
        logger.info("Starting department transfer for {} employees to department '{}'", employeeIds.size(), newDepartment);
        List<String> validEmployeeIds = new ArrayList<>();
        int[] updateDepartmentStatus;

        for (String employeeId : employeeIds) {
            logger.debug("Checking employee ID for transfer: {}", employeeId);
            if (ValidationUtility.isNotNullOrNotEmpty(employeeId)) {
                try {
                    if (employeeDAO.isEmployeeIDExist(employeeId)) {
                        logger.debug("Employee ID {} exists, adding to transfer list", employeeId);
                        validEmployeeIds.add(employeeId);
                    } else {
                        logger.warn("Employee ID {} does not exist, skipping transfer", employeeId);
                    }
                } catch (EmployeeDaoException e) {
                    logger.error("DAO error while checking employee ID {}: {}", employeeId, e.getMessage(), e);
                    throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-211",employeeId), e);
                }
            } else {
                logger.warn("Invalid employee ID format: {}", employeeId);
            }
        }

        try {
            logger.info("Transferring {} employees to department '{}'", validEmployeeIds.size(), newDepartment);
            updateDepartmentStatus = employeeDAO.transferEmployeesToDepartment(validEmployeeIds, newDepartment);
            logger.debug("Department transfer status: {}", Arrays.toString(updateDepartmentStatus));
        } catch (EmployeeDaoException e) {
            logger.error("DAO error during department transfer: {}", e.getMessage(), e);
            throw new EmployeeServiceException(ErrorCodeUtil.getErrorMessage("EMP-SERV-212", newDepartment), e);
        }

        logger.info("Department transfer completed for {} employees", validEmployeeIds.size());
        return updateDepartmentStatus;
    }


    //Method to validate employee record
    private String validate(String[] data) throws ParseException  {
    	logger.debug("Validating employee data: {}", Arrays.toString(data));
        String[] fields = {"Employee ID", "First Name", "Last Name", "Email", "Phone", "Department", "Salary", "Join Date"};
        if (data.length != fields.length) return   ErrorCodeUtil.getErrorMessage("EMP-SERV-300", data.length);

        for (int i = 0; i < data.length; i++) {
            if (!ValidationUtility.isNotNullOrNotEmpty(data[i])) {
            	return ErrorCodeUtil.getErrorMessage("EMP-SERV-301", fields[i]);
            }
        }

        String email = data[3], phone = data[4], salary = data[6], date = data[7];
        if (!ValidationUtility.isEmailValid(email)) return ErrorCodeUtil.getErrorMessage("EMP-SERV-302", email);
        if (!ValidationUtility.isPhoneNumberValid(phone)) return ErrorCodeUtil.getErrorMessage("EMP-SERV-303", phone);
        if (!ValidationUtility.isDoubleValid(salary)) return ErrorCodeUtil.getErrorMessage("EMP-SERV-304", salary);
        if (ValidationUtility.isDateValid(date) == null) return ErrorCodeUtil.getErrorMessage("EMP-SERV-305", date);
        return null;
    }
    
    
}
