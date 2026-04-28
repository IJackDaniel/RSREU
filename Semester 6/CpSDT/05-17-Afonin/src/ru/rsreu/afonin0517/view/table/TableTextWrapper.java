package ru.rsreu.afonin0517.view.table;

public final class TableTextWrapper {

	private TableTextWrapper() {
	}

	public static String[] wrapTextToLines(String text, int width) {

		int partsCount = (text.length() + width - 1) / width;
		String[] parts = new String[partsCount];

		int start = 0;

		for (int i = 0; i < partsCount; i++) {
			int end = Math.min(start + width, text.length());
			parts[i] = text.substring(start, end);
			start = end;
		}

		return parts;
	}
}