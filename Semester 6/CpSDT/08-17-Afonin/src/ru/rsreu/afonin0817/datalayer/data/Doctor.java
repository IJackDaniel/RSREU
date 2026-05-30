package ru.rsreu.afonin0817.datalayer.data;

public class Doctor {
	private final long doctorPersonnelNumber;
	private final String doctorSurname;
	private final String doctorSpecialization;
	private final int doctorOfficeNumber;

	public Doctor(long doctorPersonnelNumber, String doctorSurname, String doctorSpecialization,
			int doctorOfficeNumber) {
		this.doctorPersonnelNumber = doctorPersonnelNumber;
		this.doctorSurname = doctorSurname;
		this.doctorSpecialization = doctorSpecialization;
		this.doctorOfficeNumber = doctorOfficeNumber;
	}

	public long getDoctorPersonnelNumber() {
		return this.doctorPersonnelNumber;
	}

	public String getDoctorSurname() {
		return this.doctorSurname;
	}

	public String getDoctorSpecialization() {
		return this.doctorSpecialization;
	}

	public int getDoctorOfficeNumber() {
		return this.doctorOfficeNumber;
	}
}