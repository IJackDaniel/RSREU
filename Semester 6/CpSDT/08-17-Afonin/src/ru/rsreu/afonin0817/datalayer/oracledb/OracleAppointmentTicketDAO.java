package ru.rsreu.afonin0817.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.afonin0817.datalayer.AppointmentTicketDAO;
import ru.rsreu.afonin0817.datalayer.data.AppointmentTicket;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class OracleAppointmentTicketDAO implements AppointmentTicketDAO {
	private static final String QUERY_TICKETS_AFTER_DATE_KEY = "query.tickets.after.date";
	private static final String QUERY_AVERAGE_PATIENTS_PER_DOCTOR_KEY = "query.average.patients.per.doctor";
	private static final int COLUMN_TICKET_IDENTIFIER = 1;
	private static final int COLUMN_APPOINTMENT_DATE = 2;
	private static final int COLUMN_DOCTOR_IDENTIFIER = 3;
	private static final int COLUMN_INSURANCE_POLICY_NUMBER = 4;
	private static final int COLUMN_INSURANCE_PAYMENT_AMOUNT = 5;
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private Connection connection;

	public OracleAppointmentTicketDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<AppointmentTicket> getTicketsAfterDate(Timestamp targetDate) {
		List<AppointmentTicket> tickets = new ArrayList<>();
		String sql = OracleAppointmentTicketDAO.RESOURCER
				.getString(OracleAppointmentTicketDAO.QUERY_TICKETS_AFTER_DATE_KEY);

		try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
			preparedStatement.setTimestamp(1, targetDate);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					AppointmentTicket ticket = OracleAppointmentTicketDAO.createTicketFromResultSet(resultSet);
					tickets.add(ticket);
				}
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return tickets;
	}

	@Override
	public List<Object[]> getAveragePatientsPerDoctor() {
		List<Object[]> averages = new ArrayList<>();
		String sql = OracleAppointmentTicketDAO.RESOURCER
				.getString(OracleAppointmentTicketDAO.QUERY_AVERAGE_PATIENTS_PER_DOCTOR_KEY);

		try (Statement statement = this.connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				long doctorIdentifier = resultSet.getLong(1);
				String lastName = resultSet.getString(2);
				int patientCount = resultSet.getInt(3);
				Object[] row = new Object[] { doctorIdentifier, lastName, patientCount };
				averages.add(row);
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return averages;
	}

	private static AppointmentTicket createTicketFromResultSet(ResultSet resultSet) throws SQLException {
		long ticketIdentifier = resultSet.getLong(OracleAppointmentTicketDAO.COLUMN_TICKET_IDENTIFIER);
		Timestamp appointmentDate = resultSet.getTimestamp(OracleAppointmentTicketDAO.COLUMN_APPOINTMENT_DATE);
		long doctorIdentifier = resultSet.getLong(OracleAppointmentTicketDAO.COLUMN_DOCTOR_IDENTIFIER);
		long insurancePolicyNumber = resultSet.getLong(OracleAppointmentTicketDAO.COLUMN_INSURANCE_POLICY_NUMBER);
		double insurancePaymentAmount = resultSet.getDouble(OracleAppointmentTicketDAO.COLUMN_INSURANCE_PAYMENT_AMOUNT);
		AppointmentTicket ticket = new AppointmentTicket(ticketIdentifier, appointmentDate, doctorIdentifier,
				insurancePolicyNumber, insurancePaymentAmount);
		return ticket;
	}
}