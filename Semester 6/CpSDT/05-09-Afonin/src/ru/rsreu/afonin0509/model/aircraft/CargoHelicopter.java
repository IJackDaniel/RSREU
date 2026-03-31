package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class CargoHelicopter extends AbstractHelicopter {

	private final double cargoCapacity;

	public CargoHelicopter(String model, int flightRange, Manufacturer manufacturer, int rotorDiameter,
			double cargoCapacity) {
		super(model, flightRange, manufacturer, rotorDiameter);
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
