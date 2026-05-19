package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.entity.Doctor;

public final class DoctorToTableConverter {

	private DoctorToTableConverter() {
	}

	public static String[][] convertDoctorsToTableRows(Doctor[] doctors) {

		String[][] table = new String[doctors.length][DoctorTableColumn.getColumnsCount()];

		for (int index = 0; index < doctors.length; index++) {

			Doctor doctor = doctors[index];

			table[index][DoctorTableColumn.DOCTOR_PERSONNEL_NUMBER.getIndex()] = String
					.valueOf(doctor.getDoctorPersonnelNumber());

			table[index][DoctorTableColumn.DOCTOR_SURNAME.getIndex()] = doctor.getDoctorSurname();

			table[index][DoctorTableColumn.DOCTOR_SPECIALIZATION.getIndex()] = doctor.getDoctorSpecialization();

			table[index][DoctorTableColumn.DOCTOR_OFFICE_NUMBER.getIndex()] = String
					.valueOf(doctor.getDoctorOfficeNumber());
		}

		return table;
	}
}