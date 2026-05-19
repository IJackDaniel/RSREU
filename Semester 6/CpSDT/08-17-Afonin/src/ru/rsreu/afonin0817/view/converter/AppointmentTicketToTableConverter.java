package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.entity.AppointmentTicket;

public final class AppointmentTicketToTableConverter {

	private AppointmentTicketToTableConverter() {
	}

	public static String[][] convertAppointmentTicketsToTableRows(AppointmentTicket[] appointmentTickets) {

		String[][] table = new String[appointmentTickets.length][AppointmentTicketTableColumn.getColumnsCount()];

		for (int index = 0; index < appointmentTickets.length; index++) {

			AppointmentTicket appointmentTicket = appointmentTickets[index];

			table[index][AppointmentTicketTableColumn.APPOINTMENT_TICKET_NUMBER.getIndex()] = String
					.valueOf(appointmentTicket.getAppointmentTicketNumber());

			table[index][AppointmentTicketTableColumn.APPOINTMENT_DATETIME.getIndex()] = appointmentTicket
					.getAppointmentDatetime().toString();

			table[index][AppointmentTicketTableColumn.DOCTOR_SURNAME.getIndex()] = appointmentTicket.getDoctorSurname();

			table[index][AppointmentTicketTableColumn.PATIENT_SURNAME.getIndex()] = appointmentTicket
					.getPatientSurname();

			table[index][AppointmentTicketTableColumn.INSURANCE_PAYMENT_AMOUNT.getIndex()] = String
					.valueOf(appointmentTicket.getInsurancePaymentAmount());
		}

		return table;
	}
}