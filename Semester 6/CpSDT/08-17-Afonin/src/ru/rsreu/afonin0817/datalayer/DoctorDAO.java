package ru.rsreu.afonin0817.datalayer;

import java.util.List;
import ru.rsreu.afonin0817.datalayer.data.Doctor;

public interface DoctorDAO {
	List<Doctor> getDoctorsByPatientLastName(String patientLastName);
}