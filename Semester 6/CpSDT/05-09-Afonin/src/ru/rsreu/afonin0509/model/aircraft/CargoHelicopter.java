package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class CargoHelicopter extends AbstractHelicopter {

	private final double cargoCapacity;
	private final boolean hasExternalSling;
	private final double externalSlingCapacity;

	public CargoHelicopter(String model, int flightRange, Manufacturer manufacturer, int rotorDiameter,
			double cargoCapacity, boolean hasExternalSling, double externalSlingCapacity) {
		super(model, flightRange, manufacturer, rotorDiameter);
		this.cargoCapacity = cargoCapacity;
		this.hasExternalSling = hasExternalSling;
		this.externalSlingCapacity = externalSlingCapacity;
	}

	public boolean canUseExternalSling() {
		return this.hasExternalSling;
	}

	@Override
	public boolean canTransportPassengers(int passengers) {
		return false;
	}

	@Override
	public boolean canTransportCargo(double cargo) {
		return cargo <= this.getCargoCapacity();
	}

	@Override
	public int getPassengerCapacity() {
		return 0;
	}

	@Override
	public double getCargoCapacity() {
		double totalCargoWeightCanTransport = this.cargoCapacity;
		if (this.canUseExternalSling()) {
			totalCargoWeightCanTransport += this.externalSlingCapacity;
		}
		return totalCargoWeightCanTransport;
	}

}
