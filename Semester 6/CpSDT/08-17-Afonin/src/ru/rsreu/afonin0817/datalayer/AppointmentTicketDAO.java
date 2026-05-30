package ru.rsreu.afonin0817.datalayer;

import java.sql.Timestamp;
import java.util.List;
import ru.rsreu.afonin0817.datalayer.data.AppointmentTicket;

public interface AppointmentTicketDAO {
	List<AppointmentTicket> getTicketsAfterDate(Timestamp targetDate);

	List<Object[]> getAveragePatientsPerDoctor();
}