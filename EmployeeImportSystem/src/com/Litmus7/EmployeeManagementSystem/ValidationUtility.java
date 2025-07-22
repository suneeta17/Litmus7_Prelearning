package com.Litmus7.EmployeeManagementSystem;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ValidationUtility {
	
	public static boolean valid = false;
	final static String DATE_FORMAT = "dd-MM-yyyy";
	
		
	
	
	//Checks whether parameter is empty or null
	public static boolean isNotNullorNotEmpty(String Value) {
		if (Value != null) {
			if(!(Value.trim().isEmpty())) {
				valid = true;
			}
		}
		return valid;
	}
	
	//Checks whether FileType is CSV
	public static boolean isCSVFile(String filePath) {
		if (filePath.endsWith(".csv")) {
			valid = true;
		}
		return valid;
		
	}
	
	//Checks whether Phone number is Valid
	public static boolean isPhoneNumberValid(String phoneNumber) {
		if (phoneNumber.matches("^[6-9]\\\\d{9}$") && phoneNumber.length() == 10){
		valid = true;
		}
		return valid;
	}
	
	//Checks whether the email id is in valid format
	public static boolean isEmailValid(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    return email.matches(emailRegex);
	}
	
	//Checks whether the data is valid
	public static java.sql.Date isDateValid(String date) {
		try {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT);
		df.setLenient(false);
		java.util.Date joinDate = df.parse(date);
		return new java.sql.Date(joinDate.getTime());
		}
		catch (ParseException e) {
			return null;
		}
		
	}
	
}
