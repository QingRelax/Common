package com.common.persistence;


import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionFactory {

	private DataSource dataSource = null;
	private static ConnectionFactory connectionFactory = null;
	private static final Logger log = Logger.getLogger(ConnectionFactory.class);
	
	private ConnectionFactory(String connectionSource) {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup(connectionSource);
		} catch (NamingException e) {
			log.error("[Exception]: ", e);
		}
	}
	
	private ConnectionFactory(DataSource ds) {
		dataSource = ds;
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory("jdbc/SM2_DS");
		}
		return connectionFactory;
	}
	
	public static ConnectionFactory getInstance(DataSource ds) {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory(ds);
		}
		return connectionFactory;
	}
}