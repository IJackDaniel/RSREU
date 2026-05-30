package ru.rsreu.afonin0817.view.converter;

public final class AveragePatientsToTableConverter {

	private AveragePatientsToTableConverter() {
	}

	public static String[][] convertAveragePatientsToTableRows(Object[][] averageData) {
		String[][] table = new String[averageData.length][AveragePatientsTableColumn.getColumnsCount()];

		for (int index = 0; index < averageData.length; index++) {
			Object[] row = averageData[index];

			table[index][AveragePatientsTableColumn.DOCTOR_PERSONNEL_NUMBER.getIndex()] = String.valueOf((Long) row[0]);

			table[index][AveragePatientsTableColumn.DOCTOR_SURNAME.getIndex()] = (String) row[1];

			table[index][AveragePatientsTableColumn.PATIENT_COUNT.getIndex()] = String.valueOf((Integer) row[2]);
		}

		return table;
	}
}