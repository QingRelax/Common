package com.common.dao;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.PatternLayout;
import org.junit.BeforeClass;
import org.junit.Ignore;

import com.common.persistence.ConnectionFactory;

@Ignore("Runnable test is unnecessary for base test class")
public class BaseDaoTest {

	static OracleConnectionPoolDataSource oracleConnectionPoolDataSource = null;
	static Properties config = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try {

			InputStream inputStream = BaseDaoTest.class.getClassLoader().getResourceAsStream("unittest.properties");
			config = new Properties();
			try {
				config.load(inputStream);
			} catch (IOException e) {
				System.out.println("File 'unittest.properties' not in class path");
			} finally {
				inputStream.close();
			}

			oracleConnectionPoolDataSource = new OracleConnectionPoolDataSource();
			// oracleConnectionPoolDataSource.setURL(config.getProperty("jdbc.url"));
			// oracleConnectionPoolDataSource.setUser(config.getProperty("jdbc.user"));
			// oracleConnectionPoolDataSource.setPassword(config.getProperty("jdbc.password"));

			oracleConnectionPoolDataSource.setURL("jdbc:oracle:thin:@//192.168.116.84:1521/u01store");
			oracleConnectionPoolDataSource.setUser("sm2_online");
			oracleConnectionPoolDataSource.setPassword("storesman");

			ConnectionFactory.getInstance(oracleConnectionPoolDataSource);

			Appender a = new ConsoleAppender();
			a.setLayout(new PatternLayout());
			BasicConfigurator.configure();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws Exception {
		return oracleConnectionPoolDataSource.getConnection();
	}

	public void printSystemInfo() {
		/* Total number of processors or cores available to the JVM */
		System.out.println("Available processors (cores): " + Runtime.getRuntime().availableProcessors());

		/* Total amount of free memory available to the JVM */
		System.out.println("Free memory (MB): " + Runtime.getRuntime().freeMemory() / (1024 * 1024));

		/* This will return Long.MAX_VALUE if there is no preset limit */
		BigDecimal maxMemory = new BigDecimal(Runtime.getRuntime().maxMemory() / (1024 * 1024));
		/* Maximum amount of memory the JVM will attempt to use */
		System.out.println("Maximum memory (MB): " + maxMemory);

		/* Total memory currently available to the JVM */
		System.out.println("Total memory available to JVM (MB): " + Runtime.getRuntime().totalMemory() / (1024 * 1024));
	}

}
