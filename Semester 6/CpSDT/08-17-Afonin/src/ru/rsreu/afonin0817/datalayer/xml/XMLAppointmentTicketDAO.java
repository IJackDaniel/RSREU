package ru.rsreu.afonin0817.datalayer.xml;

import java.sql.Timestamp;
import java.util.List;
import ru.rsreu.afonin0817.datalayer.AppointmentTicketDAO;
import ru.rsreu.afonin0817.datalayer.data.AppointmentTicket;

public class XMLAppointmentTicketDAO implements AppointmentTicketDAO {
	@Override
	public List<AppointmentTicket> getTicketsAfterDate(Timestamp targetDate) {
		return null;
	}

	@Override
	public List<Object[]> getAveragePatientsPerDoctor() {
		return null;
	}
}