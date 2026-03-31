package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class CargoPlane extends Plane {

	private final double cargoCapacity;

	public CargoPlane(String model, int flightRange, Manufacturer engine, int wingspan, double cargoCapacity) {
		super(model, flightRange, engine, wingspan);
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
