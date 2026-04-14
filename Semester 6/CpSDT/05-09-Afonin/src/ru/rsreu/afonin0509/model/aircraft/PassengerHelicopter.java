package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class PassengerHelicopter extends AbstractHelicopter {

	private final int passengerCapacity;
	private final boolean hasVipConfiguration;

	public PassengerHelicopter(String model, int flightRange, Manufacturer manufacturer, int rotorDiameter,
			int passengerCapacity, boolean hasVipConfiguration) {
		super(model, flightRange, manufacturer, rotorDiameter);
		this.passengerCapacity = passengerCapacity;
		this.hasVipConfiguration = hasVipConfiguration;
	}

	public boolean hasVipConfiguration() {
		return this.hasVipConfiguration;
	}

	@Override
	public boolean canTransportPassengers(int passengers) {
		return passengers <= this.passengerCapacity;
	}

	@Override
	public boolean canTransportCargo(double cargo) {
		return false;
	}

	@Override
	public int getPassengerCapacity() {
		return this.passengerCapacity;
	}

	@Override
	public double getCargoCapacity() {
		return 0;
	}

}
