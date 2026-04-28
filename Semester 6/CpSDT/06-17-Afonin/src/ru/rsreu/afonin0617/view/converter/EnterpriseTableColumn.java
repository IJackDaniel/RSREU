package ru.rsreu.afonin0617.view.converter;

import ru.rsreu.afonin0617.view.table.TableAlignment;

public enum EnterpriseTableColumn {

	COMPANY_NAME(0, 20, TableAlignment.LEFT), REGISTRATION_CITY(1, 20, TableAlignment.LEFT),
	OWNERSHIP_FORM(2, 15, TableAlignment.LEFT);

	private final int index;
	private final int width;
	private final TableAlignment alignment;

	EnterpriseTableColumn(int index, int width, TableAlignment alignment) {
		this.index = index;
		this.width = width;
		this.alignment = alignment;
	}

	public int getIndex() {
		return this.index;
	}

	public int getWidth() {
		return this.width;
	}

	public TableAlignment getAlignment() {
		return this.alignment;
	}

	public static int getColumnsCount() {
		return EnterpriseTableColumn.values().length;
	}

	public static int[] getAllWidths() {

		EnterpriseTableColumn[] columns = EnterpriseTableColumn.values();
		int[] widths = new int[columns.length];

		for (int index = 0; index < columns.length; index++) {
			widths[index] = columns[index].getWidth();
		}

		return widths;
	}

	public static TableAlignment[] getAllAlignments() {

		EnterpriseTableColumn[] columns = EnterpriseTableColumn.values();
		TableAlignment[] alignments = new TableAlignment[columns.length];

		for (int index = 0; index < columns.length; index++) {
			alignments[index] = columns[index].getAlignment();
		}

		return alignments;
	}
}