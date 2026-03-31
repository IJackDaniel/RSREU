package ru.rsreu.afonin0509;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

import ru.rsreu.afonin0509.initializer.FleetInitializer;
import ru.rsreu.afonin0509.model.Airline;
import ru.rsreu.afonin0509.model.aircraft.AbstractAircraft;
import ru.rsreu.afonin0509.view.*;

public class ClientRunner {

	private static final int REQUIRED_PASSENGERS_COUNT = 185;
	private static final int REQUIRED_CARGO = 43000;
	private static final String REQUIRED_AIRCRAFT_MODEL = "Boeing 777-300ER";

	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {

	}

	public static void main(String[] args) {

		StringBuilder output = new StringBuilder();

		AbstractAircraft[] fleet = FleetInitializer.initializeFleet();
		Airline airline = new Airline(fleet);

		airline.sortByFlightRange();

		String[] headers = { RESOURCER.getString("message.tableColumn.type"),
				RESOURCER.getString("message.tableColumn.model"),
				RESOURCER.getString("message.tableColumn.manufacturer"),
				RESOURCER.getString("message.tableColumn.range") };
		String[][] data = AircraftToTableRowConverter.convertAircraftsToStrings(airline.getFleet());

		output.append(RESOURCER.getString("message.output.fleet"));
		output.append(TablePrinter.formTable(headers, data));

		boolean catTransport = airline.canTransport(ClientRunner.REQUIRED_PASSENGERS_COUNT,
				ClientRunner.REQUIRED_CARGO);
		output.append(RESOURCER.getString("message.output.canTransport")).append(catTransport);

		AbstractAircraft found = airline.findByModel(ClientRunner.REQUIRED_AIRCRAFT_MODEL);
		output.append(RESOURCER.getString("message.output.searchResult")).append(found.getModel());

		System.out.println(output);

	}

}
