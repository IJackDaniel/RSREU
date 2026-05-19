package com.epam.datalayer;

import java.util.List;

import com.epam.datalayer.data.User;

public interface UserDAO {
	void logIn(User user);
	void logOut(User user);
	boolean isLogged(User user);

	void kick(User admin, User kickableUser);
	void unkick(User user);
	boolean isKicked(User user);
	
	List<User> getAllLogged();
}
