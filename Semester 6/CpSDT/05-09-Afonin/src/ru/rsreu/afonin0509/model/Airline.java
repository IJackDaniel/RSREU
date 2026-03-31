package ru.rsreu.afonin0509.model;

import java.util.Arrays;

import ru.rsreu.afonin0509.model.aircraft.Aircraft;
import ru.rsreu.afonin0509.model.aircraft.NullAircraft;

public class Airline {

	private final Aircraft[] fleet;

	public Airline(Aircraft[] fleet) {
		this.fleet = fleet;
	}

	public Aircraft[] getFleet() {
		return this.fleet;
	}

	public int getTotalPassengersCapacity() {
		return Arrays.stream(this.fleet).mapToInt(Aircraft::getPassengerCapacity).sum();
	}

	public double getTotalCargoCapacity() {
		return Arrays.stream(this.fleet).mapToDouble(Aircraft::getCargoCapacity).sum();
	}

	public boolean canTransport(int passengers, double cargo) {
		int totalPassengerCapacity = this.getTotalPassengersCapacity();
		double totalCargoCapacity = this.getTotalCargoCapacity();
		return totalPassengerCapacity >= passengers && totalCargoCapacity >= cargo;
	}

	public void sortByFlightRange() {
		Arrays.sort(this.fleet);
	}

	public Aircraft findByModel(String model) {
		Aircraft result = Arrays.stream(this.fleet).filter(a -> a.getModel().equals(model)).findFirst().orElse(null);

		if (result == null) {
			return new NullAircraft();
		}
		return result;
	}
}
