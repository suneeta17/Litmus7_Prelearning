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

public class EmployeeManagerController {

    private EmployeeManagerService employeeManagerService = new EmployeeManagerService();

    public Response<String> importEmployeeData(String filePath)  {
        
        if (!ValidationUtility.isNotNullOrNotEmpty(filePath)) {
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.FILE_PATH_NULL);
        }

        if (!ValidationUtility.isCSVFile(filePath)) {
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_FILE_TYPE);
        }
        try {
        Map<String, List<String>> result = employeeManagerService.importEmployeeDataToDB(filePath);

        List<String> success = result.get("success");
        List<String> error = result.get("error");

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
    
    }catch (EmployeeServiceException e) {
    	return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, "Import Failed: " + e.getMessage());
    }catch (Exception e) {
        return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
    }
  }

    public Response<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeManagerService.getAllEmployees();
            if (employees.isEmpty()) {
                return new Response<>(StatusCodes.NO_CONTENT, MessageConstants.NO_EMPLOYEES_FOUND, employees);
            } else {
                return new Response<>(StatusCodes.OK, MessageConstants.EMPLOYEES_RETRIEVED_SUCCESS, employees);
            }
        } catch (EmployeeServiceException e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.EMPLOYEES_RETRIEVED_FAILURE+e.getMessage());
        }
        catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }
    
   public Response<Employee> getEmployeeById(String employeeId){
	   try {
		   Employee employee =  employeeManagerService.getEmployeebyId(employeeId);
		   if(employee == null) {
			   return new Response<>(StatusCodes.NOT_FOUND,MessageConstants.NOT_FOUND);
		   }else {
			   return new Response<>(StatusCodes.OK,MessageConstants.EMPLOYEES_RETRIEVED_SUCCESS,employee);
		   }
	   }catch(EmployeeServiceException e) {
		   return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR,MessageConstants.EMPLOYEES_RETRIEVED_FAILURE+ e.getMessage());
	   }
	   catch (Exception e) {
           return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
       }
   }
   
   
   public Response<String> addEmployee(List<String> data) {
	    try {
	        Status status = employeeManagerService.addEmployee(data);

	        switch (status) {
	            case SUCCESS:
	                return new Response<>(StatusCodes.CREATED, MessageConstants.ADDED); 
	            case ALREADY_EXIST:
	                return new Response<>(StatusCodes.CONFLICT, MessageConstants.ALREADY_EXISTS);
	            case FAILED:
	                return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA);
	            default:
	                return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR);
	        }

	    } catch (EmployeeServiceException e) {
	        return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
	    }
	    catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
	}

   
   public Response<String> updateEmployee(List<String> data) {
	    try {
	        Status status = employeeManagerService.updateEmployee(data);

	        switch (status) {
	            case SUCCESS:
	                return new Response<>(StatusCodes.OK, MessageConstants.UPDATED);
	            case NOT_FOUND:
	                return new Response<>(StatusCodes.NOT_FOUND, MessageConstants.NOT_FOUND);
	            case FAILED:
	                return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA); 
	            default:
	                return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR);
	        }
	    } catch (EmployeeServiceException e) {
	        return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
	    }catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
	
	}

    
    public Response<String> deleteEmployeeById(String employeeId){
    	try {
    	boolean result = employeeManagerService.deleteEmployeeById(employeeId);
    	if (result) {
    		return new Response<>(StatusCodes.OK,MessageConstants.EMPLOYEE_DELETED_SUCCESS);
    	}else {
    		return new Response<>(StatusCodes.NOT_FOUND,MessageConstants.EMPLOYEE_DELETED_FAILURE);
    		}
    	}catch (EmployeeServiceException e) {
    		return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR,MessageConstants.INTERNAL_SERVER_ERROR);
    	}catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }
    
    public Response<String> addEmployeeInBatches(List<String[]> employeeDataList) {
        if (employeeDataList == null || employeeDataList.isEmpty()) {
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_DATA);
        }

        try {
            Map<String, Object> result = employeeManagerService.addEmployeesInBatch(employeeDataList);

            int[] insertStatus = (int[]) result.get("insertStatus");
            List<String> errors = (List<String>) result.get("errors");

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
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        } catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    }
    
    public Response<String> transferEmployeesToDepartment(List<String> employeeIds,String newDepartment) {
    	try {
			int[] departmentUpdateStatus = employeeManagerService.transferEmployeesToDepartment(employeeIds,newDepartment);
			
			int successCount = 0;
			for (int status : departmentUpdateStatus) {
				if (status > 0) successCount ++;
			}
			if (successCount == 0) {
				return new Response<>(StatusCodes.NO_CONTENT,MessageConstants.DEPARTMENT_TRANSFER_FAILED);
			}
		return new Response<>(StatusCodes.OK,MessageConstants.DEPARTMENT_TRANSFER_SUCCESS + successCount+ " employees");
			
		} catch (EmployeeServiceException e) {
			return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
		}catch (Exception e) {
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.INTERNAL_SERVER_ERROR + ": " + e.getMessage());
        }
    	
    }

}
