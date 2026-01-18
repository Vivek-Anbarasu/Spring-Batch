package com.springbatch.util;

import java.io.FileInputStream;
import java.util.Properties;

public class ApplicationUtil {

	static Properties properties = null;

	public static Properties getPropertyFile() {

		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(new FileInputStream("C:\\properties\\config.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	
	public static String getURL() {
		return getPropertyFile().getProperty("postgresURL");
	}
	
	public static String getUsername() {
		return getPropertyFile().getProperty("postgresUsername");
	}
	
	public static String getPasssword() {
		return getPropertyFile().getProperty("postgresPasssword");
	}
	
	public static String getMariaURL() {
		return getPropertyFile().getProperty("mariaDBURL");
	}
	
	public static String getMariaUsername() {
		return getPropertyFile().getProperty("mariaDBUsername");
	}
	
	public static String getMariaPasssword() {
		return getPropertyFile().getProperty("mariaDBPasssword");
	}

}
