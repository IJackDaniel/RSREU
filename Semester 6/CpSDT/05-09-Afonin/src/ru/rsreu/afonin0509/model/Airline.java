package ru.rsreu.afonin0509.model;

import java.util.Arrays;

import ru.rsreu.afonin0509.model.aircraft.AbstractAircraft;
import ru.rsreu.afonin0509.model.aircraft.NullAircraft;

public class Airline {

	private final AbstractAircraft[] fleet;

	public Airline(AbstractAircraft[] fleet) {
		this.fleet = fleet;
	}

	public AbstractAircraft[] getFleet() {
		return this.fleet;
	}

	public int getTotalPassengersCapacity() {
		return Arrays.stream(this.fleet).mapToInt(AbstractAircraft::getPassengerCapacity).sum();
	}

	public double getTotalCargoCapacity() {
		return Arrays.stream(this.fleet).mapToDouble(AbstractAircraft::getCargoCapacity).sum();
	}

	public boolean canTransport(int passengers, double cargo) {
		int totalPassengerCapacity = this.getTotalPassengersCapacity();
		double totalCargoCapacity = this.getTotalCargoCapacity();
		return totalPassengerCapacity >= passengers && totalCargoCapacity >= cargo;
	}

	public void sortByFlightRange() {
		Arrays.sort(this.fleet);
	}

	public AbstractAircraft findByModel(String model) {
		AbstractAircraft result = Arrays.stream(this.fleet).filter(a -> a.getModel().equals(model)).findFirst()
				.orElse(null);

		if (result == null) {
			return new NullAircraft();
		}
		return result;
	}
}
