package com.litmus.employeemanagementsystem.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DBConnection {

		private static String url, username,pwd;
		

		public static Connection getConnection() throws SQLException, FileNotFoundException, IOException {
			
			Properties props = new Properties();
			props.load(new FileInputStream("config.properties"));
			
			url = props.getProperty("dburl");
		    username = props.getProperty("dbUser");
		    pwd = props.getProperty("dbPassword");
		
			return  DriverManager.getConnection(url,username,pwd);
		
		}

}
