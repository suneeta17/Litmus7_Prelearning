package com.litmus.employeemanagementsystem.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

public class ErrorCodeUtil {
	
	private static final Properties props = new Properties();
	
	static {
		try{
			props.load(ErrorCodeUtil.class.getClassLoader()
				.getResourceAsStream("applicationErrorCodes.properties"));
		} catch (IOException e) {
	        throw new ExceptionInInitializerError("Could not load error codes: " + e.getMessage());
	    }
	}
	
	 public static String getErrorMessage(String code, Object... args) {
	        String template = props.getProperty(code, "Unknown error code: " + code);
	        return MessageFormat.format(template, args);
	    }

}
