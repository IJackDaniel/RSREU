package ru.rsreu.afonin0817;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0817.datalayer.AppointmentTicketDAO;
import ru.rsreu.afonin0817.datalayer.DAOFactory;
import ru.rsreu.afonin0817.datalayer.DBType;
import ru.rsreu.afonin0817.datalayer.DoctorDAO;
import ru.rsreu.afonin0817.datalayer.data.AppointmentTicket;
import ru.rsreu.afonin0817.datalayer.data.Doctor;
import ru.rsreu.afonin0817.view.converter.AppointmentTicketTableColumn;
import ru.rsreu.afonin0817.view.converter.AppointmentTicketToTableConverter;
import ru.rsreu.afonin0817.view.converter.AveragePatientsTableColumn;
import ru.rsreu.afonin0817.view.converter.AveragePatientsToTableConverter;
import ru.rsreu.afonin0817.view.converter.DoctorTableColumn;
import ru.rsreu.afonin0817.view.converter.DoctorToTableConverter;
import ru.rsreu.afonin0817.view.table.SmartTableDrawer;

public class ClientRunner {

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
	private static final String TARGET_DATE_STRING = "2026-05-27";
	private static final String TARGET_PATIENT_LAST_NAME = "Смирнов";
	private static final String MESSAGE_TICKET_HEADER_FORMAT_KEY = "message.ticket.header.format";
	private static final String MESSAGE_DOCTORS_HEADER_FORMAT_KEY = "message.doctors.header.format";
	private static final String MESSAGE_AVERAGE_HEADER_KEY = "message.average.header";
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {
	}

	public static void main(String[] arguments) {
		StringBuilder outputBuilder = new StringBuilder();

		try {
			DAOFactory factory = DAOFactory.getInstance(DBType.ORACLE);
			DoctorDAO doctorDAO = factory.getDoctorDAO();
			AppointmentTicketDAO appointmentTicketDAO = factory.getAppointmentTicketDAO();

			String ticketsResult = ClientRunner.getTicketsAfterDateResult(appointmentTicketDAO);
			outputBuilder.append(ticketsResult);

			String doctorsResult = ClientRunner.getDoctorsByPatientResult(doctorDAO);
			outputBuilder.append(doctorsResult);

			String averageResult = ClientRunner.getAveragePatientsPerDoctorResult(appointmentTicketDAO);
			outputBuilder.append(averageResult);

			System.out.println(outputBuilder.toString());

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private static String getTicketsAfterDateResult(AppointmentTicketDAO appointmentTicketDAO) {
		StringBuilder resultBuilder = new StringBuilder();

		Timestamp targetDate = ClientRunner.getTargetDate();
		List<AppointmentTicket> tickets = appointmentTicketDAO.getTicketsAfterDate(targetDate);
		AppointmentTicket[] ticketsArray = tickets.toArray(new AppointmentTicket[0]);

		String[][] tableData = AppointmentTicketToTableConverter.convertAppointmentTicketsToTableRows(ticketsArray);
		String[] headers = { ClientRunner.RESOURCER.getString("header.ticket.number"),
				ClientRunner.RESOURCER.getString("header.appointment.datetime"),
				ClientRunner.RESOURCER.getString("header.doctor.personnel.number"),
				ClientRunner.RESOURCER.getString("header.patient.insurance.policy.number"),
				ClientRunner.RESOURCER.getString("header.insurance.payment.amount") };

		String table = SmartTableDrawer.drawTable(headers, tableData, AppointmentTicketTableColumn.class);

		SimpleDateFormat dateFormat = new SimpleDateFormat(ClientRunner.DATE_FORMAT_PATTERN);
		String formattedDate = dateFormat.format(targetDate);

		String header = String.format(ClientRunner.RESOURCER.getString(ClientRunner.MESSAGE_TICKET_HEADER_FORMAT_KEY),
				formattedDate);

		resultBuilder.append(header);
		resultBuilder.append("\n");
		resultBuilder.append(table);
		resultBuilder.append("\n");

		return resultBuilder.toString();
	}

	private static String getDoctorsByPatientResult(DoctorDAO doctorDAO) {
		StringBuilder resultBuilder = new StringBuilder();

		List<Doctor> doctors = doctorDAO.getDoctorsByPatientLastName(ClientRunner.TARGET_PATIENT_LAST_NAME);
		Doctor[] doctorsArray = doctors.toArray(new Doctor[0]);

		String[][] tableData = DoctorToTableConverter.convertDoctorsToTableRows(doctorsArray);
		String[] headers = { ClientRunner.RESOURCER.getString("header.doctor.personnel.number"),
				ClientRunner.RESOURCER.getString("header.doctor.surname"),
				ClientRunner.RESOURCER.getString("header.doctor.specialization"),
				ClientRunner.RESOURCER.getString("header.doctor.office.number") };

		String table = SmartTableDrawer.drawTable(headers, tableData, DoctorTableColumn.class);

		String header = String.format(ClientRunner.RESOURCER.getString(ClientRunner.MESSAGE_DOCTORS_HEADER_FORMAT_KEY),
				ClientRunner.TARGET_PATIENT_LAST_NAME);

		resultBuilder.append(header);
		resultBuilder.append("\n");
		resultBuilder.append(table);
		resultBuilder.append("\n");

		return resultBuilder.toString();
	}

	private static String getAveragePatientsPerDoctorResult(AppointmentTicketDAO appointmentTicketDAO) {
		StringBuilder resultBuilder = new StringBuilder();

		List<Object[]> averages = appointmentTicketDAO.getAveragePatientsPerDoctor();
		Object[][] averagesArray = averages.toArray(new Object[0][]);

		String[][] tableData = AveragePatientsToTableConverter.convertAveragePatientsToTableRows(averagesArray);
		String[] headers = { ClientRunner.RESOURCER.getString("header.average.doctor.personnel.number"),
				ClientRunner.RESOURCER.getString("header.average.doctor.surname"),
				ClientRunner.RESOURCER.getString("header.patient.count") };

		String table = SmartTableDrawer.drawTable(headers, tableData, AveragePatientsTableColumn.class);

		resultBuilder.append(ClientRunner.RESOURCER.getString(ClientRunner.MESSAGE_AVERAGE_HEADER_KEY));
		resultBuilder.append("\n");
		resultBuilder.append(table);
		resultBuilder.append("\n");

		return resultBuilder.toString();
	}

	private static Timestamp getTargetDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(ClientRunner.DATE_FORMAT_PATTERN);
		try {
			Date parsedDate = dateFormat.parse(ClientRunner.TARGET_DATE_STRING);
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException exception) {
			exception.printStackTrace();
			return null;
		}
	}
}