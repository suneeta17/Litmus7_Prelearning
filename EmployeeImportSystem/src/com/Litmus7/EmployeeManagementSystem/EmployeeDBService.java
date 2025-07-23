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
        private static String pwd = "Your passoword";
	
    
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
  
	
