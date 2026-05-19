package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.entity.Patient;

public final class PatientToTableConverter {

	private PatientToTableConverter() {
	}

	public static String[][] convertPatientsToTableRows(Patient[] patients) {

		String[][] table = new String[patients.length][PatientTableColumn.getColumnsCount()];

		for (int index = 0; index < patients.length; index++) {

			Patient patient = patients[index];

			table[index][PatientTableColumn.PATIENT_INSURANCE_POLICY_NUMBER.getIndex()] = String
					.valueOf(patient.getPatientInsurancePolicyNumber());

			table[index][PatientTableColumn.PATIENT_SURNAME.getIndex()] = patient.getPatientSurname();

			table[index][PatientTableColumn.PATIENT_ADDRESS.getIndex()] = patient.getPatientAddress();

			table[index][PatientTableColumn.PATIENT_BIRTH_YEAR.getIndex()] = String
					.valueOf(patient.getPatientBirthYear());
		}

		return table;
	}
}