package com.litmus.employeemanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

		private static String url = "jdbc:mysql://localhost:3306/emp_mgt_sys";
		private static String username = "root";
	    private static String pwd = "Suneeta@123";
		
	   
	
		public static Connection getConnection() throws SQLException {
			return  DriverManager.getConnection(url,username,pwd);
		
		}

}
