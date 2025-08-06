package com.litmus.employeemanagementsystem.constant;

public class RegexConstants {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    public static final String PHONE_REGEX = "^[6-9]\\d{9}$";
    public static final String DOUBLE_REGEX = "^[0-9]+(\\.[0-9]+)?$";
    public static final String DATE_FORMAT_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d{2}$";
    
    private RegexConstants() {
 
    }
}
