package ru.rsreu.afonin0609.converter;

import ru.rsreu.afonin0609.model.RouteTaxi;

public final class RouteTaxiToTableConverter {

	private enum ColumnIndex {
		REGISTRATION_NUMBER(0), OWNER(1), ROUTE_NUMBER(2);

		private final int index;

		ColumnIndex(int index) {
			this.index = index;
		}

		public static int getColumnCount() {
			return values().length;
		}

		public int getIndex() {
			return this.index;
		}
	}

	private RouteTaxiToTableConverter() {

	}

	public static String[][] convertRouteTaxiToTable(RouteTaxi[] taxis) {
		String[][] result = new String[taxis.length][ColumnIndex.getColumnCount()];

		for (int i = 0; i < taxis.length; i++) {
			result[i][ColumnIndex.REGISTRATION_NUMBER.getIndex()] = taxis[i].registrationNumber();
			result[i][ColumnIndex.OWNER.getIndex()] = taxis[i].owner();
			result[i][ColumnIndex.ROUTE_NUMBER.getIndex()] = taxis[i].routeNumber().name();
		}

		return result;
	}
}
