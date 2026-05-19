package com.epam.datalayer;

import java.util.List;

import com.epam.datalayer.data.Message;

public interface MessageDAO {
	List<Message> getLast(int count);

	void sendMessage(Message message);
}
