package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.datalayer.data.AppointmentTicket;

public final class AppointmentTicketToTableConverter {

	private AppointmentTicketToTableConverter() {
	}

	public static String[][] convertAppointmentTicketsToTableRows(AppointmentTicket[] tickets) {
		String[][] table = new String[tickets.length][AppointmentTicketTableColumn.getColumnsCount()];

		for (int index = 0; index < tickets.length; index++) {
			AppointmentTicket ticket = tickets[index];

			table[index][AppointmentTicketTableColumn.TICKET_NUMBER.getIndex()] = String
					.valueOf(ticket.getAppointmentTicketNumber());

			table[index][AppointmentTicketTableColumn.APPOINTMENT_DATETIME.getIndex()] = ticket.getAppointmentDatetime()
					.toString();

			table[index][AppointmentTicketTableColumn.DOCTOR_PERSONNEL_NUMBER.getIndex()] = String
					.valueOf(ticket.getDoctorPersonnelNumber());

			table[index][AppointmentTicketTableColumn.PATIENT_INSURANCE_POLICY_NUMBER.getIndex()] = String
					.valueOf(ticket.getPatientInsurancePolicyNumber());

			table[index][AppointmentTicketTableColumn.INSURANCE_PAYMENT_AMOUNT.getIndex()] = String
					.valueOf(ticket.getInsurancePaymentAmount());
		}

		return table;
	}
}