package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.view.table.TableAlignment;

public enum AppointmentTicketTableColumn implements TableColumnInterface {

	TICKET_NUMBER(0, 12, TableAlignment.RIGHT), APPOINTMENT_DATETIME(1, 25, TableAlignment.LEFT),
	DOCTOR_PERSONNEL_NUMBER(2, 12, TableAlignment.RIGHT), PATIENT_INSURANCE_POLICY_NUMBER(3, 20, TableAlignment.RIGHT),
	INSURANCE_PAYMENT_AMOUNT(4, 15, TableAlignment.RIGHT);

	private final int index;
	private final int width;
	private final TableAlignment alignment;

	AppointmentTicketTableColumn(int index, int width, TableAlignment alignment) {
		this.index = index;
		this.width = width;
		this.alignment = alignment;
	}

	@Override
	public int getIndex() {
		return this.index;
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public TableAlignment getAlignment() {
		return this.alignment;
	}

	public static int getColumnsCount() {
		return TableColumnInterface.getColumnsCount(AppointmentTicketTableColumn.class);
	}

	public static int[] getAllWidths() {
		return TableColumnInterface.getAllWidths(AppointmentTicketTableColumn.values());
	}

	public static TableAlignment[] getAllAlignments() {
		return TableColumnInterface.getAllAlignments(AppointmentTicketTableColumn.values());
	}
}