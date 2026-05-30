package ru.rsreu.afonin0817.datalayer.data;

public class Patient {
	private final long patientInsurancePolicyNumber;
	private final String patientSurname;
	private final String patientAddress;
	private final int patientBirthYear;

	public Patient(long patientInsurancePolicyNumber, String patientSurname, String patientAddress,
			int patientBirthYear) {
		this.patientInsurancePolicyNumber = patientInsurancePolicyNumber;
		this.patientSurname = patientSurname;
		this.patientAddress = patientAddress;
		this.patientBirthYear = patientBirthYear;
	}

	public long getPatientInsurancePolicyNumber() {
		return this.patientInsurancePolicyNumber;
	}

	public String getPatientSurname() {
		return this.patientSurname;
	}

	public String getPatientAddress() {
		return this.patientAddress;
	}

	public int getPatientBirthYear() {
		return this.patientBirthYear;
	}
}