package com.litmus.employeemanagementsystem.controller;

import com.litmus.employeemanagementsystem.dto.Response;
import com.litmus.employeemanagementsystem.service.EmployeeManagerService;

public class EmployeeManagerController {

    private EmployeeManagerService service = new EmployeeManagerService();

    public Response<String> importEmployeeDataToDB(String filePath) {
        return service.importEmployeeDataToDB(filePath);
    }
}

