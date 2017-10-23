package com.common.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class EmailProperties {
	
	private static Logger log = Logger.getLogger(EmailProperties.class);
	private static Properties config = null;

	static {
		InputStream in = EmailProperties.class.getClassLoader()
				.getResourceAsStream("email.properties");
		config = new Properties();
		try {
			config.load(in);
			in.close();
		} catch (IOException e) {
			log.error("No email.properties defined error");
		}
	}

	public static String getProperties(String key) {
		// Properties props = new Properties();
		try {
			String value = config.getProperty(key);
			return value;
		} catch (Exception e) {
			log.error("[Exception]: ", e);
			return null;
		}
	}
}
