package com.litmus.employeemanagementsystem.dto;

public class Response<T> {
	
	private int statusCode;
	private String errorMessage;
	private T data;
	
	public Response(int statusCode,String errorMessage , T data) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}
	public Response(int statusCode , String errorMessage) {
		this(statusCode,errorMessage,null);
	}
	

	public int getStatusCode() {
		return statusCode;
	}
	
	public String getMessage() {
		return errorMessage;
	}
	
	public T getData() {
		return data;
	}
}
