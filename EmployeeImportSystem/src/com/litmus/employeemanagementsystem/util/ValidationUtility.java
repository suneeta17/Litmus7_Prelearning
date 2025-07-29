package com.litmus.employeemanagementsystem.util;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class ValidationUtility {
	
	
	//Checks whether parameter is empty or null
	public static boolean isNotNullOrNotEmpty(String value) {
		return value != null && !value.trim().isEmpty();
	}
	
	//Checks whether FileType is CSV
	public static boolean isCSVFile(String filePath) {
		return !isNotNullOrNotEmpty(filePath) && filePath.toLowerCase().endsWith(".csv");
		
	}
	
	//Checks whether Phone number is Valid
	public static boolean isPhoneNumberValid(String phoneNumber) {
		return (phoneNumber.matches("^[6-;9]\\\\d{9}$") && phoneNumber.length() == 10);
	}
	
	//Checks whether the email id is in valid format
	public static boolean isEmailValid(String email) {
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
	    return email.matches(emailRegex);
	}
	
	//Checks whether the value is in double format
	public static boolean isDoubleValid(String value) {
	    final String DOUBLE_REGEX = "^[0-9]+(\\.[0-9]+)?$";
	    return value != null && value.matches(DOUBLE_REGEX);
	}
	
	//Checks date is in expected format
	public static boolean isDateFormatValid(String date) {
	    // DD-MM-YYYY (1900–2099)
	    final String DATE_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d{2}$";
	    return date != null && date.matches(DATE_REGEX);
	}

	
	//Checks whether the data is valid
	public static java.sql.Date isDateValid(String date) throws ParseException {
		if(isDateFormatValid(date)) {
		
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		df.setLenient(false);
		java.util.Date joinDate = df.parse(date);
		return new java.sql.Date(joinDate.getTime());
		}
		return null;
		
	}
	
}
