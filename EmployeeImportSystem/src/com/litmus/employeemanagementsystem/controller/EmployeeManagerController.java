package com.litmus.employeemanagementsystem.controller;

import com.litmus.employeemanagementsystem.service.EmployeeManagerService;
import com.litmus.employeemanagementsystem.dto.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public class EmployeeManagerController {

    private EmployeeManagerService employeeManagerService = new EmployeeManagerService();

   
	public Response<String> importEmployeeData(String filePath) throws ParseException, IOException, SQLException {
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

	    return new Response<>(error.isEmpty() ? 200 : 207, msg.toString());
	}

}
