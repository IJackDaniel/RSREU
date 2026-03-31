package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class PassengerHelicopter extends Helicopter {

	private final int passengerCapacity;

	public PassengerHelicopter(String model, int flightRange, Manufacturer engine, int rotorDiameter,
			int passengerCapacity) {
		super(model, flightRange, engine, rotorDiameter);
		this.passengerCapacity = passengerCapacity;
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
