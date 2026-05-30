package ru.rsreu.afonin0817.datalayer.xml;

import ru.rsreu.afonin0817.datalayer.DAOFactory;
import ru.rsreu.afonin0817.datalayer.DoctorDAO;
import ru.rsreu.afonin0817.datalayer.PatientDAO;
import ru.rsreu.afonin0817.datalayer.AppointmentTicketDAO;

public class XMLDAOFactory extends DAOFactory {
	@Override
	public DoctorDAO getDoctorDAO() {
		return new XMLDoctorDAO();
	}

	@Override
	public PatientDAO getPatientDAO() {
		return new XMLPatientDAO();
	}

	@Override
	public AppointmentTicketDAO getAppointmentTicketDAO() {
		return new XMLAppointmentTicketDAO();
	}
}