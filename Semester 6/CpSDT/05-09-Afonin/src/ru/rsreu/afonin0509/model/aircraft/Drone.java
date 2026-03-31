package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class Drone extends AbstractAircraft {

	private final double cargoCapacity;

	public Drone(String model, int flightRange, Manufacturer manufacturer, double cargoCapacity) {
		super(model, flightRange, manufacturer);
		this.cargoCapacity = cargoCapacity;
	}

	@Override
	public boolean canTransportPassengers(int passengers) {
		return false;
	}

	@Override
	public boolean canTransportCargo(double cargo) {
		return cargo <= this.cargoCapacity;
	}

	@Override
	public int getPassengerCapacity() {
		return 0;
	}

	@Override
	public double getCargoCapacity() {
		return this.cargoCapacity;
	}
}
