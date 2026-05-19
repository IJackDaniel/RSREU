package com.epam.datalayer.oracledb;

import java.sql.Connection;
import java.util.List;

import com.epam.datalayer.UserDAO;
import com.epam.datalayer.data.User;

public class OracleUserDAO implements UserDAO {
	private Connection connection;

	public OracleUserDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void logIn(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logOut(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLogged(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void kick(User admin, User kickableUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unkick(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isKicked(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getAllLogged() {
		// TODO Auto-generated method stub
		return null;
	}

	


}
