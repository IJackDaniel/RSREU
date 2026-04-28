package ru.rsreu.afonin0517.view.converter;

import ru.rsreu.afonin0517.view.table.TableAlignment;

public enum VoucherTableColumn {

	TYPE(0, 12, TableAlignment.LEFT), TITLE(1, 20, TableAlignment.LEFT), DAYS(2, 6, TableAlignment.LEFT),
	PRICE(3, 10, TableAlignment.LEFT), TRANSPORT(4, 12, TableAlignment.LEFT), MEAL(5, 15, TableAlignment.LEFT),
	VALUE(6, 10, TableAlignment.LEFT);

	private final int index;
	private final int width;
	private final TableAlignment alignment;

	VoucherTableColumn(int index, int width, TableAlignment alignment) {
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
		return VoucherTableColumn.values().length;
	}

	public static int[] getAllWidths() {
		VoucherTableColumn[] columns = VoucherTableColumn.values();
		int[] widths = new int[columns.length];

		for (int i = 0; i < columns.length; i++) {
			widths[i] = columns[i].getWidth();
		}

		return widths;
	}

	public static TableAlignment[] getAllAlignments() {
		VoucherTableColumn[] columns = VoucherTableColumn.values();
		TableAlignment[] alignments = new TableAlignment[columns.length];

		for (int i = 0; i < columns.length; i++) {
			alignments[i] = columns[i].getAlignment();
		}

		return alignments;
	}
}