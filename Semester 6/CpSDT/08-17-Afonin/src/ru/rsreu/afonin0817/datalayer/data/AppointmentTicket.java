package ru.rsreu.afonin0817.datalayer.data;

import java.sql.Timestamp;

public class AppointmentTicket {
	private final long appointmentTicketNumber;
	private final Timestamp appointmentDatetime;
	private final long doctorPersonnelNumber;
	private final long patientInsurancePolicyNumber;
	private final double insurancePaymentAmount;

	public AppointmentTicket(long appointmentTicketNumber, Timestamp appointmentDatetime, long doctorPersonnelNumber,
			long patientInsurancePolicyNumber, double insurancePaymentAmount) {
		this.appointmentTicketNumber = appointmentTicketNumber;
		this.appointmentDatetime = appointmentDatetime;
		this.doctorPersonnelNumber = doctorPersonnelNumber;
		this.patientInsurancePolicyNumber = patientInsurancePolicyNumber;
		this.insurancePaymentAmount = insurancePaymentAmount;
	}

	public long getAppointmentTicketNumber() {
		return this.appointmentTicketNumber;
	}

	public Timestamp getAppointmentDatetime() {
		return this.appointmentDatetime;
	}

	public long getDoctorPersonnelNumber() {
		return this.doctorPersonnelNumber;
	}

	public long getPatientInsurancePolicyNumber() {
		return this.patientInsurancePolicyNumber;
	}

	public double getInsurancePaymentAmount() {
		return this.insurancePaymentAmount;
	}
}