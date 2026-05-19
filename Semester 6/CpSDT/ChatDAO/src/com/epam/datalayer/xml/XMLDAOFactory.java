package com.epam.datalayer.xml;

import com.epam.datalayer.DAOFactory;
import com.epam.datalayer.MessageDAO;
import com.epam.datalayer.UserDAO;

public class XMLDAOFactory extends DAOFactory {

	@Override
	public MessageDAO getMessageDAO() {
		return new XMLMessageDAO();
	}

	@Override
	public UserDAO getUserDAO() {
		return new XMLUserDAO();
	}

}
