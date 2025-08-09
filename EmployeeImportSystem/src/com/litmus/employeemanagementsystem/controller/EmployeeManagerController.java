package com.litmus.employeemanagementsystem.controller;

import com.litmus.employeemanagementsystem.service.EmployeeManagerService;
import com.litmus.employeemanagementsystem.service.EmployeeManagerService.Status;
import com.litmus.employeemanagementsystem.util.ValidationUtility;
import com.litmus.employeemanagementsystem.constant.MessageConstants;
import com.litmus.employeemanagementsystem.constant.StatusCodes;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.dto.Response;
import com.litmus.employeemanagementsystem.exception.EmployeeServiceException;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeManagerController {
	
	private static final Logger logger = LogManager.getLogger(EmployeeManagerController.class);

    private EmployeeManagerService employeeManagerService = new EmployeeManagerService();

    public Response<String> importEmployeeData(String filePath)  {
        logger.debug("Received request to import employee data from file: {}", filePath);

        if (!ValidationUtility.isNotNullOrNotEmpty(filePath)) {
            logger.warn("File path is null or empty");
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.FILE_PATH_NULL);
        }

        if (!ValidationUtility.isCSVFile(filePath)) {
            logger.warn("Invalid file type for path: {}", filePath);
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_FILE_TYPE);
        }
        try {
            logger.info("Starting employee data import for file: {}", filePath);
            Map<String, List<String>> result = employeeManagerService.importEmployeeDataToDB(filePath);

            List<String> success = result.get("success");
            List<String> error = result.get("error");

            logger.debug("Import complete. Success: {}, Errors: {}", success.size(), error.size());

            StringBuilder msg = new StringBuilder();
            msg.append("Import Summary:\n");
            msg.append("Inserted: ").append(success.size()).append("\n");
            msg.append("Errors: ").append(error.size()).append("\n\n");

            if (!success.isEmpty()) {
                msg.append("Success:\n");
                for (String s : success) msg.append("- ").append(s).append("\n");
            }

            if (!error.isEmpty()) {
                msg.append("\nErrors:\n");
                for (String e : error) msg.append("- ").append(e).append("\n");
            }

            return new Response<>(error.isEmpty() ? StatusCodes.OK : StatusCodes.PARTIAL_SUCCESS, msg.toString());
    
        } catch (EmployeeServiceException e) {
            logger.error("EmployeeServiceException during import: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, "Import Failed: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during import: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }

    public Response<List<Employee>> getAllEmployees() {
        logger.debug("Fetching all employees");
        try {
            List<Employee> employees = employeeManagerService.getAllEmployees();
            if (employees.isEmpty()) {
                logger.info("No employees found");
                return new Response<>(StatusCodes.NO_CONTENT, MessageConstants.NO_EMPLOYEES_FOUND, employees);
            } else {
                logger.info("Retrieved {} employees", employees.size());
                return new Response<>(StatusCodes.OK, MessageConstants.EMPLOYEES_RETRIEVED_SUCCESS, employees);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Error retrieving employees: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.EMPLOYEES_RETRIEVED_FAILURE+e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving employees: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }
    
    public Response<Employee> getEmployeeById(String employeeId){
        logger.debug("Fetching employee with ID: {}", employeeId);
        try {
            Employee employee =  employeeManagerService.getEmployeebyId(employeeId);
            if(employee == null) {
                logger.info("Employee with ID {} not found", employeeId);
                return new Response<>(StatusCodes.NOT_FOUND,MessageConstants.NOT_FOUND);
            } else {
                logger.info("Employee with ID {} retrieved successfully", employeeId);
                return new Response<>(StatusCodes.OK,MessageConstants.EMPLOYEES_RETRIEVED_SUCCESS,employee);
            }
        } catch(EmployeeServiceException e) {
            logger.error("Error retrieving employee {}: {}", employeeId, e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR,MessageConstants.EMPLOYEES_RETRIEVED_FAILURE+ e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error retrieving employee {}: {}", employeeId, e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }
   
    public Response<String> addEmployee(List<String> data) {
        logger.debug("Adding employee with data: {}", data);
        try {
            Status status = employeeManagerService.addEmployee(data);

            switch (status) {
                case SUCCESS:
                    logger.info("Employee added successfully: {}", data);
                    return new Response<>(StatusCodes.CREATED, MessageConstants.ADDED); 
                case ALREADY_EXIST:
                    logger.warn("Employee already exists: {}", data);
                    return new Response<>(StatusCodes.CONFLICT, MessageConstants.ALREADY_EXISTS);
                case FAILED:
                    logger.warn("Failed to add employee due to invalid data: {}", data);
                    return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA);
                default:
                    logger.error("Unknown status while adding employee: {}", data);
                    return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Service error while adding employee: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding employee: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }

   
    public Response<String> updateEmployee(List<String> data) {
        logger.debug("Updating employee with data: {}", data);
        try {
            Status status = employeeManagerService.updateEmployee(data);

            switch (status) {
                case SUCCESS:
                    logger.info("Employee updated successfully: {}", data);
                    return new Response<>(StatusCodes.OK, MessageConstants.UPDATED);
                case NOT_FOUND:
                    logger.warn("Employee not found for update: {}", data);
                    return new Response<>(StatusCodes.NOT_FOUND, MessageConstants.NOT_FOUND);
                case FAILED:
                    logger.warn("Failed to update employee due to invalid data: {}", data);
                    return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA);
                default:
                    logger.error("Unknown status while updating employee: {}", data);
                    return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Service error while updating employee: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while updating employee: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }

    public Response<String> deleteEmployeeById(String employeeId) {
        logger.debug("Deleting employee with ID: {}", employeeId);
        try {
            boolean result = employeeManagerService.deleteEmployeeById(employeeId);
            if (result) {
                logger.info("Employee deleted successfully: {}", employeeId);
                return new Response<>(StatusCodes.OK, MessageConstants.EMPLOYEE_DELETED_SUCCESS);
            } else {
                logger.warn("Employee delete failed, not found: {}", employeeId);
                return new Response<>(StatusCodes.NOT_FOUND, MessageConstants.EMPLOYEE_DELETED_FAILURE);
            }
        } catch (EmployeeServiceException e) {
            logger.error("Service error while deleting employee {}: {}", employeeId, e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while deleting employee {}: {}", employeeId, e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }

    public Response<String> addEmployeeInBatches(List<String[]> employeeDataList) {
        logger.debug("Adding employees in batch, total count: {}", 
                     employeeDataList == null ? 0 : employeeDataList.size());

        if (employeeDataList == null || employeeDataList.isEmpty()) {
            logger.warn("Empty or null employee batch data");
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA);
        }

        try {
            Map<String, Object> result = employeeManagerService.addEmployeesInBatch(employeeDataList);

            int[] insertStatus = (int[]) result.get("insertStatus");
            List<String> errors = (List<String>) result.get("errors");

            logger.info("Batch insert complete. Inserted: {}, Errors: {}", insertStatus.length, errors.size());

            StringBuilder msg = new StringBuilder("Batch Insert Summary:\n");
            msg.append("Inserted: ").append(insertStatus.length).append("\n");
            msg.append("Errors: ").append(errors.size()).append("\n\n");

            if (!errors.isEmpty()) {
                msg.append("Error Details:\n");
                for (String err : errors) {
                    msg.append("- ").append(err).append("\n");
                }
            }

            return new Response<>(
                errors.isEmpty() ? StatusCodes.CREATED : StatusCodes.PARTIAL_SUCCESS,
                msg.toString()
            );

        } catch (EmployeeServiceException e) {
            logger.error("Service error while adding employees in batch: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding employees in batch: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }

    public Response<String> transferEmployeesToDepartment(List<String> employeeIds, String newDepartment) {
        logger.debug("Transferring employees {} to department: {}", employeeIds, newDepartment);
        try {
            int[] departmentUpdateStatus = employeeManagerService.transferEmployeesToDepartment(employeeIds, newDepartment);

            int successCount = 0;
            for (int status : departmentUpdateStatus) {
                if (status > 0) successCount++;
            }

            if (successCount == 0) {
                logger.warn("No employees transferred to department: {}", newDepartment);
                return new Response<>(StatusCodes.NO_CONTENT, MessageConstants.DEPARTMENT_TRANSFER_FAILED);
            }

            logger.info("Transferred {} employees to department {}", successCount, newDepartment);
            return new Response<>(StatusCodes.OK, MessageConstants.DEPARTMENT_TRANSFER_SUCCESS + successCount + " employees");

        } catch (EmployeeServiceException e) {
            logger.error("Service error during department transfer: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during department transfer: {}", e.getMessage(), e);
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }


}
