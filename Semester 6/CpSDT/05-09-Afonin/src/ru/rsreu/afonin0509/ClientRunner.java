package ru.rsreu.afonin0509;

import ru.rsreu.afonin0509.initializer.DataInitializer;
import ru.rsreu.afonin0509.model.Airline;
import ru.rsreu.afonin0509.model.aircraft.Aircraft;
import ru.rsreu.afonin0509.view.*;

public class ClientRunner {

	public static void main(String[] args) {
		
		StringBuilder output = new StringBuilder();
		
		Aircraft[] fleet = DataInitializer.initializeFleet();
		Airline airline = new Airline(fleet);
		
		airline.sortByFlightRange();
		
		String[] headers = {"Type", "Model", "Manufacturer", "Range"};
		String[][] data = TableDataConverter.convertAircraftsToStrings(fleet);
		
		output.append("Fleet:\n");
		output.append(TablePrinter.formTable(headers, data));
		
		boolean catTransport = airline.canTransport(200, 50000);
		output.append("\nCan transport: ").append(catTransport).append("\n");
		
		Aircraft found = airline.findByModel("Boeing 777-300ER");
		output.append("\nSearch result: ").append(found.getModel());
		
		System.out.println(output);

	}

}
