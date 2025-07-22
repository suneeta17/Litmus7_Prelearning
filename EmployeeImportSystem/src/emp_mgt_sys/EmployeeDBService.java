package emp_mgt_sys;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class EmployeeDBService  {

	private static String url = "jdbc:mysql://localhost:3306/emp_mgt_sys";
	private static String username = "root";
        private static String pwd = "Your Password";
	
	private Connection conn;
	private Statement st;
	private PreparedStatement pstmt;
	
	//Constructor that implements db connection
	public EmployeeDBService() throws Exception{
		
	
		//register driver
		Class.forName("com.mysql.cj.jdbc.Driver");	
	
		//Establish a connection
		conn = DriverManager.getConnection(url,username,pwd);
		st = conn.createStatement();
	
		
		String query = "CREATE TABLE IF NOT EXISTS employees (\r\n"
				+ "    emp_id VARCHAR(20) PRIMARY KEY,\r\n"
				+ "    first_name VARCHAR(50),\r\n"
				+ "    last_name VARCHAR(50),\r\n"
				+ "    email VARCHAR(100) UNIQUE,\r\n"
				+ "    phone VARCHAR(20) UNIQUE,\r\n"
				+ "    department VARCHAR(50),\r\n"
				+ "    salary DECIMAL(10, 2),\r\n"
				+ "    join_date DATE\r\n"
				+ ");\r\n"
				+ "";
		st.executeUpdate(query);
		
	}
	
	//To check Employee exist or not
	public boolean isEmployeeIDExist(String empId) throws SQLException {
		boolean exists = false;
		
		
		PreparedStatement pst = conn.prepareStatement("Select emp_id from employees where emp_id = ?");
		pst.setString(1,empId);
		ResultSet rs = pst.executeQuery();
		
		exists = rs.next();
		
		return exists;
		
		
	}
	
	//To insert Employee data into database
	public void insertEmployee(String[] values) throws SQLException {
		
		String empId = values[0].trim();
        String firstName = values[1].trim();
        String lastName = values[2].trim();
        String email = values[3].trim();
        String phone = values[4].trim();
        String department = values[5].trim();
        String salary = values[6].trim();
        String joinDate = values[7].trim();
		
        Date date =Validator.isDateValid(joinDate);
        
        String sql = "INSERT INTO employees (emp_id, first_name, last_name, email, phone, department, salary, join_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        pstmt = conn.prepareStatement(sql);
	
	    pstmt.setString(1, empId);
	    pstmt.setString(2, firstName);
	    pstmt.setString(3, lastName);
	    pstmt.setString(4, email);
	    pstmt.setString(5, phone);
	    pstmt.setString(6, department);
	    pstmt.setDouble(7, Double.parseDouble(salary));
	    pstmt.setDate(8, date);
	
	    int rowsInserted = pstmt.executeUpdate();
	    if (rowsInserted > 0) {
	       System.out.println("Employee inserted: " + empId);
	    }
	
	}	
  }
	
