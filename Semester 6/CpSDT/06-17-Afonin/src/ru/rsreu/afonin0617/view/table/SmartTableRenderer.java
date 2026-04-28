package ru.rsreu.afonin0617.view.table;

import ru.rsreu.afonin0617.view.table.SmartTableRenderer;
import ru.rsreu.afonin0617.view.converter.EnterpriseTableColumn;

public final class SmartTableRenderer {

	private static final int PADDING = 1;

	private SmartTableRenderer() {
	}

	public static String drawTable(String[] headers, String[][] data) {

		int[] widths = EnterpriseTableColumn.getAllWidths();
		TableAlignment[] alignments = EnterpriseTableColumn.getAllAlignments();

		int totalWidth = SmartTableRenderer.calculateTotalWidth(widths);
		int totalHeight = SmartTableRenderer.calculateTotalHeight(headers, data, widths);

		char[][] canvas = new char[totalHeight][totalWidth];

		SmartTableRenderer.fillCanvas(canvas);

		int currentRow = 0;

		currentRow = SmartTableRenderer.drawBorder(canvas, currentRow, widths);
		currentRow = SmartTableRenderer.drawWrappedRow(canvas, currentRow, headers, widths, alignments);
		currentRow = SmartTableRenderer.drawBorder(canvas, currentRow, widths);

		for (int i = 0; i < data.length; i++) {
			currentRow = SmartTableRenderer.drawWrappedRow(canvas, currentRow, data[i], widths, alignments);
			currentRow = SmartTableRenderer.drawBorder(canvas, currentRow, widths);
		}

		return SmartTableRenderer.convertCanvasToString(canvas);
	}

	private static int calculateTotalWidth(int[] widths) {

		int width = 1;

		for (int i = 0; i < widths.length; i++) {
			width += widths[i] + SmartTableRenderer.PADDING * 2 + 1;
		}

		return width;
	}

	private static int calculateTotalHeight(String[] headers, String[][] data, int[] widths) {

		int height = 0;

		height += 1;
		height += SmartTableRenderer.calculateRowHeight(headers, widths);
		height += 1;

		for (int i = 0; i < data.length; i++) {
			height += SmartTableRenderer.calculateRowHeight(data[i], widths);
			height += 1;
		}

		return height;
	}

	private static int calculateRowHeight(String[] row, int[] widths) {

		int maxHeight = 1;

		for (int i = 0; i < row.length; i++) {
			int cellHeight = TableTextWrapper.wrapTextToLines(row[i], widths[i]).length;
			if (cellHeight > maxHeight) {
				maxHeight = cellHeight;
			}
		}

		return maxHeight;
	}

	private static void fillCanvas(char[][] canvas) {

		for (int i = 0; i < canvas.length; i++) {
			for (int j = 0; j < canvas[i].length; j++) {
				canvas[i][j] = TableSymbol.SPACE.getSymbol();
			}
		}
	}

	private static int drawBorder(char[][] canvas, int row, int[] widths) {

		int col = 0;
		canvas[row][col++] = TableSymbol.CORNER.getSymbol();

		for (int i = 0; i < widths.length; i++) {

			int length = widths[i] + SmartTableRenderer.PADDING * 2;

			for (int j = 0; j < length; j++) {
				canvas[row][col++] = TableSymbol.HORIZONTAL.getSymbol();
			}

			canvas[row][col++] = TableSymbol.CORNER.getSymbol();
		}

		return row + 1;
	}

	private static int drawWrappedRow(char[][] canvas, int row, String[] values, int[] widths,
			TableAlignment[] alignments) {

		String[][] wrappedCellTexts = SmartTableRenderer.wrapTextsToFitColumns(values, widths);
		int maxPartsPerCell = SmartTableRenderer.findMaxPartsPerCell(wrappedCellTexts);

		for (int line = 0; line < maxPartsPerCell; line++) {

			int col = 0;
			canvas[row][col++] = TableSymbol.VERTICAL.getSymbol();

			for (int i = 0; i < values.length; i++) {

				String text = "";
				if (line < wrappedCellTexts[i].length) {
					text = wrappedCellTexts[i][line];
				}

				col = SmartTableRenderer.drawCell(canvas, row, col, text, widths[i], alignments[i]);
			}

			row++;
		}

		return row;
	}

	private static String[][] wrapTextsToFitColumns(String[] values, int[] widths) {
		String[][] wrappedCellTexts = new String[values.length][];
		for (int i = 0; i < values.length; i++) {
			wrappedCellTexts[i] = TableTextWrapper.wrapTextToLines(values[i], widths[i]);
		}
		return wrappedCellTexts;
	}

	private static int findMaxPartsPerCell(String[][] wrappedCellTexts) {
		int maxPartsPerCell = 1;
		for (int i = 0; i < wrappedCellTexts.length; i++) {
			if (wrappedCellTexts[i].length > maxPartsPerCell) {
				maxPartsPerCell = wrappedCellTexts[i].length;
			}
		}
		return maxPartsPerCell;
	}

	private static int drawCell(char[][] canvas, int row, int col, String value, int width, TableAlignment alignment) {

		canvas[row][col++] = TableSymbol.SPACE.getSymbol();

		int start = SmartTableRenderer.calculateStartPosition(col, value, width, alignment);

		for (int i = 0; i < value.length(); i++) {
			canvas[row][start + i] = value.charAt(i);
		}

		col += width;

		canvas[row][col++] = TableSymbol.SPACE.getSymbol();
		canvas[row][col++] = TableSymbol.VERTICAL.getSymbol();

		return col;
	}

	private static int calculateStartPosition(int column, String value, int width, TableAlignment alignment) {

		int start;

		if (alignment == TableAlignment.RIGHT) {
			start = column + (width - value.length());
		} else if (alignment == TableAlignment.CENTER) {
			start = column + (width - value.length()) / 2;
		} else {
			start = column;
		}

		return start;
	}

	private static String convertCanvasToString(char[][] canvas) {

		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < canvas.length; i++) {
			builder.append(canvas[i]);
			builder.append("\n");
		}

		return builder.toString();
	}
}