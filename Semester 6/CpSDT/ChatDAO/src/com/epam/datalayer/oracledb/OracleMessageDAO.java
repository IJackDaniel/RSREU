package com.epam.datalayer.oracledb;

import java.sql.Connection;
import java.util.List;

import com.epam.datalayer.MessageDAO;
import com.epam.datalayer.data.Message;

public class OracleMessageDAO implements MessageDAO {

	private Connection connection;

	public OracleMessageDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Message> getLast(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(Message message) {
		// TODO Auto-generated method stub
		
	}

	}
