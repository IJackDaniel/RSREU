package ru.rsreu.afonin0817.datalayer.oracledb;

import java.sql.Connection;
import ru.rsreu.afonin0817.datalayer.PatientDAO;

public class OraclePatientDAO implements PatientDAO {
	private Connection connection;

	public OraclePatientDAO(Connection connection) {
		this.connection = connection;
	}
}