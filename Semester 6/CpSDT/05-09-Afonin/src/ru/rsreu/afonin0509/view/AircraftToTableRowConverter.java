package ru.rsreu.afonin0509.view;

import ru.rsreu.afonin0509.model.aircraft.Aircraft;

public class AircraftToTableRowConverter {
	
	private enum ColumnIndex {
		TYPE(0),
		MODEL(1),
		MANUFACTURER(2),
		RANGE(3);
		
		private final int index;
		
		ColumnIndex(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
	}
	
	private AircraftToTableRowConverter() {

	}

	public static String[][] convertAircraftsToStrings(Aircraft[] fleet) {
		String[][] data = new String[fleet.length][4];

		for (int i = 0; i < fleet.length; i++) {
			data[i][ColumnIndex.TYPE.getIndex()] = fleet[i].getClass().getSimpleName();
			data[i][ColumnIndex.MODEL.getIndex()] = fleet[i].getModel();
			data[i][ColumnIndex.MANUFACTURER.getIndex()] = fleet[i].getManufacturer().name();
			data[i][ColumnIndex.RANGE.getIndex()] = String.valueOf(fleet[i].getFlightRange());
		}

		return data;
	}
}
