package ru.rsreu.afonin0609.view;

public class TablePrinter {

	private TablePrinter() {

	}

	public static String formTable(String[] headers, String[][] data) {
		StringBuilder builder = new StringBuilder();

		int[] widths = TablePrinter.calculateWidths(headers, data);

		TablePrinter.appendRow(builder, headers, widths);
		TablePrinter.appendSeparator(builder, widths);
		TablePrinter.appendRows(builder, data, widths);

		return builder.toString();
	}

	private static int[] calculateWidths(String[] headers, String[][] data) {
		int[] widths = new int[headers.length];

		TablePrinter.initWidths(widths, headers);
		TablePrinter.updateWidths(widths, data);

		return widths;
	}

	private static void initWidths(int[] widths, String[] headers) {
		for (int i = 0; i < headers.length; i++) {
			widths[i] = headers[i].length();
		}
	}

	private static void updateWidths(int[] widths, String[][] data) {
		for (String[] row : data) {
			TablePrinter.updateWidthsByRow(widths, row);
		}
	}

	private static void updateWidthsByRow(int[] widths, String[] row) {
		for (int i = 0; i < row.length; i++) {
			widths[i] = Math.max(widths[i], row[i].length());
		}
	}

	private static void appendRows(StringBuilder builder, String[][] data, int[] widths) {
		for (String[] row : data) {
			TablePrinter.appendRow(builder, row, widths);
		}
	}

	private static void appendRow(StringBuilder builder, String[] row, int[] widths) {
		for (int i = 0; i < row.length; i++) {
			builder.append(String.format("%-" + widths[i] + "s", row[i])).append(" | ");
		}
		builder.append("\n");
	}

	private static void appendSeparator(StringBuilder builder, int[] widths) {
		for (int width : widths) {
			builder.append("-".repeat(width)).append("-+-");
		}
		builder.append("\n");
	}
}
