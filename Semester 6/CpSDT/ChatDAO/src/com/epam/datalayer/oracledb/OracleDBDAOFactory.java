package com.epam.datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import com.epam.datalayer.DAOFactory;
import com.epam.datalayer.MessageDAO;
import com.epam.datalayer.UserDAO;

public class OracleDBDAOFactory extends DAOFactory {
	private static volatile OracleDBDAOFactory instance;
	private Connection connection;

	private OracleDBDAOFactory() {
	}

	public static OracleDBDAOFactory getInstance()
			throws ClassNotFoundException, SQLException {
		OracleDBDAOFactory factory = instance;
		if (instance == null) {
			synchronized (OracleDBDAOFactory.class) {
				instance = factory = new OracleDBDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
		Locale.setDefault(Locale.ENGLISH);
		// Class.forName("oracle.jdbc.driver.OracleDriver");
		String url = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "SYSTEM";
		String password = "111";
		connection = DriverManager.getConnection(url, user, password);
		System.out.println("Connected to oracle DB!");
	}

	@Override
	public MessageDAO getMessageDAO() {
		return new OracleMessageDAO(connection);
	}

	@Override
	public UserDAO getUserDAO() {
		return new OracleUserDAO(connection);
	}

}
