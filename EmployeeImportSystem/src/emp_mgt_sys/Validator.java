package emp_mgt_sys;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Validator {
	
	final static String DATE_FORMAT = "dd-MM-yyyy";
	
	//Checks the validity of data inserted
	public static boolean recordValidator(String[] values , EmployeeDBService dbService) throws SQLException {
		
		if(values.length < 8) {
			System.out.println("Number of Fields Does not Match");
			System.out.println("Skipping the Record Entry");
			return false;
		}
		
		String empId = values[0].trim();
        String firstName = values[1].trim();
        String lastName = values[2].trim();
        String email = values[3].trim();
        String phone = values[4].trim();
        String department = values[5].trim();
        String salary = values[6].trim();
        String joinDate = values[7].trim();
        
        if (empId.isEmpty()) {
        	System.out.println("Employee ID is missing");
        	return false;
        }
        
        if (dbService.isEmployeeIDExist(empId)) {
        	System.out.println("Employee ID already exist : " + empId);
        	return false;
        }
        if (firstName.isEmpty()) {
        	System.out.println("First name Is Empty. Skipping Record");
        	return false;
        }
        if (lastName.isEmpty()) {
        	System.out.println("Last name Is Empty. Skipping Record");
        	return false;
        }
        
       if (!Validator.isValidEmail(email)) {
    	   System.out.println("Invalid Email id. Skipping Record");
    	   return false;
       }
       
       if (phone.length() < 10 || phone.length() >10) {
    	   System.out.println("Invalid Phone number. Skipping Record");
    	   return false;
       }
       if (Validator.isDateValid(joinDate) == null ) {
    	   System.out.println("Invalid Date. Skipping Record");
    	   return false;
       }
       return true;
		
		
	}
	
	//Checks whether the email id is in valid format
	public static boolean isValidEmail(String email) {
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
