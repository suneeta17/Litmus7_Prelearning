package com.litmus7.inventoryfeedphase2.util;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {

		private static String url, username,pwd;
		
		static {
			Properties props = new Properties();
			try {
				props.load(new FileInputStream("config.properties"));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			url = props.getProperty("dburl");
		    username = props.getProperty("dbUser");
		    pwd = props.getProperty("dbPassword");
		}
		

		public static Connection getConnection() throws SQLException {
			
			return  DriverManager.getConnection(url,username,pwd);
		
		}

}

