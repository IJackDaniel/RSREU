package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.view.table.TableAlignment;

public enum AveragePatientsTableColumn implements TableColumnInterface {

	DOCTOR_PERSONNEL_NUMBER(0, 12, TableAlignment.RIGHT), DOCTOR_SURNAME(1, 20, TableAlignment.LEFT),
	PATIENT_COUNT(2, 15, TableAlignment.RIGHT);

	private final int index;
	private final int width;
	private final TableAlignment alignment;

	AveragePatientsTableColumn(int index, int width, TableAlignment alignment) {
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
		return TableColumnInterface.getColumnsCount(AveragePatientsTableColumn.class);
	}

	public static int[] getAllWidths() {
		return TableColumnInterface.getAllWidths(AveragePatientsTableColumn.values());
	}

	public static TableAlignment[] getAllAlignments() {
		return TableColumnInterface.getAllAlignments(AveragePatientsTableColumn.values());
	}
}