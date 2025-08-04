package com.litmus.employeemanagementsystem.controller;

import com.litmus.employeemanagementsystem.service.EmployeeManagerService;
import com.litmus.employeemanagementsystem.util.ValidationUtility;
import com.litmus.employeemanagementsystem.constant.MessageConstants;
import com.litmus.employeemanagementsystem.constant.StatusCodes;
import com.litmus.employeemanagementsystem.dto.Employee;
import com.litmus.employeemanagementsystem.dto.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class EmployeeManagerController {

    private EmployeeManagerService employeeManagerService = new EmployeeManagerService();

    public Response<String> importEmployeeData(String filePath) throws ParseException, IOException, SQLException {
        
        if (!ValidationUtility.isNotNullOrNotEmpty(filePath)) {
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.FILE_PATH_NULL);
        }

        if (!ValidationUtility.isCSVFile(filePath)) {
            return new Response<>(StatusCodes.BAD_REQUEST, MessageConstants.INVALID_FILE_TYPE);
        }

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
    }

    public Response<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeManagerService.getAllEmployees();
            if (employees.isEmpty()) {
                return new Response<>(StatusCodes.NO_CONTENT, MessageConstants.NO_EMPLOYEES_FOUND, employees);
            } else {
                return new Response<>(StatusCodes.OK, MessageConstants.EMPLOYEES_RETRIEVED_SUCCESS, employees);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response<>(StatusCodes.INTERNAL_SERVER_ERROR, MessageConstants.EMPLOYEES_RETRIEVED_FAILURE, null);
        }
    }
}
