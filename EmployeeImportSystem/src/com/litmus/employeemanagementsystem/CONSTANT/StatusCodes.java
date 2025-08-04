package com.litmus.employeemanagementsystem.CONSTANT;

public class StatusCodes {
	
    public static final int OK = 200;                     // Success
    public static final int CREATED = 201;                // Resource created
    public static final int PARTIAL_SUCCESS = 207;        // Some records failed
    public static final int NO_CONTENT = 204;             // No records found
    public static final int BAD_REQUEST = 400;            // Invalid input
    public static final int INTERNAL_SERVER_ERROR = 500;  // General server error

    private StatusCodes() {
        
    }
	

}
