package com.litmus.employeemanagementsystem.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.litmus.employeemanagementsystem.constant.RegexConstants;

public class ValidationUtility {
	
    // Checks whether parameter is not null and not empty
    public static boolean isNotNullOrNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Checks whether file type is CSV
    public static boolean isCSVFile(String filePath) {
        return isNotNullOrNotEmpty(filePath) && filePath.toLowerCase().endsWith(".csv");
    }

    // Checks whether phone number is valid
    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches(RegexConstants.PHONE_REGEX);
    }

    // Checks whether email is in valid format
    public static boolean isEmailValid(String email) {
        return email != null && email.matches(RegexConstants.EMAIL_REGEX);
    }

    // Checks whether value is a valid double
    public static boolean isDoubleValid(String value) {
        return value != null && value.matches(RegexConstants.DOUBLE_REGEX);
    }

    // Checks if date string is in expected format
    public static boolean isDateFormatValid(String date) {
        return date != null && date.matches(RegexConstants.DATE_FORMAT_REGEX);
    }

    // Parses and returns valid SQL Date, or null if invalid
    public static java.sql.Date isDateValid(String date) throws ParseException {
        if (isDateFormatValid(date)) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df.setLenient(false);
            java.util.Date joinDate = df.parse(date);
            return new java.sql.Date(joinDate.getTime());
        }
        return null;
    }
    
 
}
