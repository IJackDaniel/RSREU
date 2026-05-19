package com.epam;

import com.epam.datalayer.DAOFactory;
import com.epam.datalayer.DBType;
import com.epam.datalayer.DBTypeException;
import com.epam.datalayer.MessageDAO;
import com.epam.datalayer.UserDAO;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
			MessageDAO messageDAO = factory.getMessageDAO();
			UserDAO userDAO = factory.getUserDAO();
		} catch (DBTypeException e) {
			e.printStackTrace();
		}
	}

}
