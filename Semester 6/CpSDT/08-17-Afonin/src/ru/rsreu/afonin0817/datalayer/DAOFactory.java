package ru.rsreu.afonin0817.datalayer;

public abstract class DAOFactory {
	public static DAOFactory getInstance(DBType dbType) {
		DAOFactory result = dbType.getDAOFactory();
		return result;
	}

	public abstract DoctorDAO getDoctorDAO();

	public abstract PatientDAO getPatientDAO();

	public abstract AppointmentTicketDAO getAppointmentTicketDAO();
}