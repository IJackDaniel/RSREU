package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class PassengerPlane extends Plane{
	
	private final int passengerCapacity;

	public PassengerPlane(String model, int flightRange, Manufacturer engine, int wingspan, int passengerCapacity) {
		super(model, flightRange, engine, wingspan);
		this.passengerCapacity = passengerCapacity;
	}

	@Override
	public boolean canTransportPassengers(int passengers) {
		return this.passengerCapacity <= passengerCapacity;
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
