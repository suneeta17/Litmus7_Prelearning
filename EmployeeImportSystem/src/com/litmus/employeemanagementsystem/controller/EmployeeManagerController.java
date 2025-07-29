package com.litmus.employeemanagementsystem.controller;

import com.litmus.employeemanagementsystem.service.EmployeeManagerService;
import com.litmus.employeemanagementsystem.dto.Response;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class EmployeeManagerController {

    private EmployeeManagerService employeeManagerService = new EmployeeManagerService();

    public Response<String> importEmployeeData(String filePath) throws ParseException {
        Map<String, List<String>> result = employeeManagerService.importEmployeeDataToDB(filePath);

        List<String> successList = result.get("success");
        List<String> errorList = result.get("error");

        StringBuilder message = new StringBuilder();
        message.append("Import Summary:\n");
        message.append("Successfully inserted: ").append(successList.size()).append(" entries.\n");
        message.append("Skipped/Errors: ").append(errorList.size()).append(" entries.\n\n");

        if (!successList.isEmpty()) {
            message.append("Success Entries:\n");
            for (String msg : successList) {
                message.append(" - ").append(msg).append("\n");
            }
            message.append("\n");
        }

        if (!errorList.isEmpty()) {
            message.append("Error Entries:\n");
            for (String err : errorList) {
                message.append(" - ").append(err).append("\n");
            }
        }

        int statusCode = errorList.isEmpty() ? 200 : 207; 
        return new Response<>(statusCode, message.toString());
    }
}
