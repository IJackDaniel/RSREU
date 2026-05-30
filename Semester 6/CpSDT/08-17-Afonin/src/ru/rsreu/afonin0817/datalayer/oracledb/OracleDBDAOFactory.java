package ru.rsreu.afonin0817.datalayer.oracledb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ru.rsreu.afonin0817.datalayer.DAOFactory;
import ru.rsreu.afonin0817.datalayer.DoctorDAO;
import ru.rsreu.afonin0817.datalayer.PatientDAO;
import ru.rsreu.afonin0817.datalayer.AppointmentTicketDAO;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class OracleDBDAOFactory extends DAOFactory {
	private static final String DATABASE_URL_KEY = "database.url";
	private static final String DATABASE_USER_KEY = "database.user";
	private static final String DATABASE_PASSWORD_KEY = "database.password";
	private static final String DATABASE_DRIVER_KEY = "database.driver";
	private static final String MESSAGE_CONNECTED_KEY = "message.connected";
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private static volatile OracleDBDAOFactory instance;
	private Connection connection;

	private OracleDBDAOFactory() {
	}

	public static OracleDBDAOFactory getInstance() throws ClassNotFoundException, SQLException {
		OracleDBDAOFactory factory = instance;
		if (instance == null) {
			synchronized (OracleDBDAOFactory.class) {
				instance = factory = new OracleDBDAOFactory();
				factory.connected();
			}
		}
		return factory;
	}

	private void connected() throws ClassNotFoundException, SQLException {
		String driver = OracleDBDAOFactory.RESOURCER.getString(OracleDBDAOFactory.DATABASE_DRIVER_KEY);
		Class.forName(driver);

		String url = OracleDBDAOFactory.RESOURCER.getString(OracleDBDAOFactory.DATABASE_URL_KEY);
		String user = OracleDBDAOFactory.RESOURCER.getString(OracleDBDAOFactory.DATABASE_USER_KEY);
		String password = OracleDBDAOFactory.RESOURCER.getString(OracleDBDAOFactory.DATABASE_PASSWORD_KEY);

		this.connection = DriverManager.getConnection(url, user, password);

		String connectedMessage = OracleDBDAOFactory.RESOURCER.getString(OracleDBDAOFactory.MESSAGE_CONNECTED_KEY);
		System.out.println(connectedMessage);
	}

	@Override
	public DoctorDAO getDoctorDAO() {
		return new OracleDoctorDAO(this.connection);
	}

	@Override
	public PatientDAO getPatientDAO() {
		return new OraclePatientDAO(this.connection);
	}

	@Override
	public AppointmentTicketDAO getAppointmentTicketDAO() {
		return new OracleAppointmentTicketDAO(this.connection);
	}
}