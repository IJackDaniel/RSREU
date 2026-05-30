package ru.rsreu.afonin0817.datalayer.oracledb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.rsreu.afonin0817.datalayer.DoctorDAO;
import ru.rsreu.afonin0817.datalayer.data.Doctor;
import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class OracleDoctorDAO implements DoctorDAO {
	private static final String QUERY_DOCTORS_BY_PATIENT_KEY = "query.doctors.by.patient";
	private static final int COLUMN_DOCTOR_IDENTIFIER = 1;
	private static final int COLUMN_LAST_NAME = 2;
	private static final int COLUMN_SPECIALIZATION = 3;
	private static final int COLUMN_CABINET_NUMBER = 4;
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private Connection connection;

	public OracleDoctorDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Doctor> getDoctorsByPatientLastName(String patientLastName) {
		List<Doctor> doctors = new ArrayList<>();
		String sql = OracleDoctorDAO.RESOURCER.getString(OracleDoctorDAO.QUERY_DOCTORS_BY_PATIENT_KEY);

		try (PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
			preparedStatement.setString(1, patientLastName);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Doctor doctor = OracleDoctorDAO.createDoctorFromResultSet(resultSet);
					doctors.add(doctor);
				}
			}
		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		return doctors;
	}

	private static Doctor createDoctorFromResultSet(ResultSet resultSet) throws SQLException {
		long doctorIdentifier = resultSet.getLong(OracleDoctorDAO.COLUMN_DOCTOR_IDENTIFIER);
		String lastName = resultSet.getString(OracleDoctorDAO.COLUMN_LAST_NAME);
		String specialization = resultSet.getString(OracleDoctorDAO.COLUMN_SPECIALIZATION);
		int cabinetNumber = resultSet.getInt(OracleDoctorDAO.COLUMN_CABINET_NUMBER);
		Doctor doctor = new Doctor(doctorIdentifier, lastName, specialization, cabinetNumber);
		return doctor;
	}
}