package ru.rsreu.afonin0509.view;

import ru.rsreu.afonin0509.model.aircraft.Aircraft;

class TableDataConverter {
	
	static String[][] convertAircraftsToStrings(Aircraft[] fleet) {
		String[][] data = new String[fleet.length][4];
		
		for (int i = 0; i < fleet.length; i++) {
			data[i][0] = fleet[i].getClass().getSimpleName();
			data[i][1] = fleet[i].getModel();
			data[i][2] = fleet[i].getManufacturer().name();
			data[i][3] = String.valueOf(fleet[i].getFlightRange());
		}
		
		return data;
	}
}
