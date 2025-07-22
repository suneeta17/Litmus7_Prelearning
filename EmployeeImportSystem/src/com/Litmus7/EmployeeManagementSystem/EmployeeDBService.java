package com.Litmus7.EmployeeManagementSystem;


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
		
<<<<<<< HEAD:EmployeeImportSystem/src/emp_mgt_sys/EmployeeDBService.java
	
=======
		
		
>>>>>>> f8192d0 (updated code):EmployeeImportSystem/src/com/Litmus7/EmployeeManagementSystem/EmployeeDBService.java
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
	
	//getter
	public Connection getConn() {
		return conn;
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
	
	
	}	
  
	
