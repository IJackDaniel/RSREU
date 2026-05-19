package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.view.table.TableAlignment;

public enum PatientTableColumn implements TableColumnInterface {

	PATIENT_INSURANCE_POLICY_NUMBER(0, 15, TableAlignment.RIGHT),

	PATIENT_SURNAME(1, 20, TableAlignment.LEFT),

	PATIENT_ADDRESS(2, 20, TableAlignment.LEFT),

	PATIENT_BIRTH_YEAR(3, 10, TableAlignment.RIGHT);

	private final int index;

	private final int width;

	private final TableAlignment alignment;

	PatientTableColumn(int index, int width, TableAlignment alignment) {
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
		return TableColumnInterface.getColumnsCount(PatientTableColumn.class);
	}

	public static int[] getAllWidths() {
		return TableColumnInterface.getAllWidths(PatientTableColumn.values());
	}

	public static TableAlignment[] getAllAlignments() {
		return TableColumnInterface.getAllAlignments(PatientTableColumn.values());
	}
}