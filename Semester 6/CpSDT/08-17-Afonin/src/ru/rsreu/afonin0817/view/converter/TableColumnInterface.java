package ru.rsreu.afonin0817.view.converter;

import ru.rsreu.afonin0817.view.table.TableAlignment;

public interface TableColumnInterface {

	int getIndex();

	int getWidth();

	TableAlignment getAlignment();

	static int getColumnsCount(Class<? extends Enum<? extends TableColumnInterface>> enumClass) {
		return enumClass.getEnumConstants().length;
	}

	static int[] getAllWidths(TableColumnInterface[] columns) {
		int[] widths = new int[columns.length];
		for (int i = 0; i < columns.length; i++) {
			widths[i] = columns[i].getWidth();
		}
		return widths;
	}

	static TableAlignment[] getAllAlignments(TableColumnInterface[] columns) {
		TableAlignment[] alignments = new TableAlignment[columns.length];
		for (int i = 0; i < columns.length; i++) {
			alignments[i] = columns[i].getAlignment();
		}
		return alignments;
	}
}