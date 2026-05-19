package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.view.table.TableAlignment;

public enum DoctorTableColumn implements TableColumnInterface {

	DOCTOR_PERSONNEL_NUMBER(0, 12, TableAlignment.RIGHT),

	DOCTOR_SURNAME(1, 20, TableAlignment.LEFT),

	DOCTOR_SPECIALIZATION(2, 20, TableAlignment.LEFT),

	DOCTOR_OFFICE_NUMBER(3, 10, TableAlignment.RIGHT);

	private final int index;

	private final int width;

	private final TableAlignment alignment;

	DoctorTableColumn(int index, int width, TableAlignment alignment) {
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
		return TableColumnInterface.getColumnsCount(DoctorTableColumn.class);
	}

	public static int[] getAllWidths() {
		return TableColumnInterface.getAllWidths(DoctorTableColumn.values());
	}

	public static TableAlignment[] getAllAlignments() {
		return TableColumnInterface.getAllAlignments(DoctorTableColumn.values());
	}
}