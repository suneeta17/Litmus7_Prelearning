package com.Litmus7.EmployeeManagementSystem;

public class Response<T> {
	
	private boolean success = true;
	private int statusCode;
	private String message;
	private T data;
	
	public Response(boolean  success,int statusCode,String message , T data) {
		this.success = success;
		this.statusCode = statusCode;
		this.message = message;
		this.data = data;
	}
	public Response(boolean success,int statusCode , String message) {
		this(success,statusCode,message,null);
	}
	
	public boolean isSuccess()
	{
		return success;
	}
	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public T getData() {
		return data;
	}
}
