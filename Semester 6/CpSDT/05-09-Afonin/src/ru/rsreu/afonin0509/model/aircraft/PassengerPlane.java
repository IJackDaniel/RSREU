package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Engine;

public class PassengerPlane extends Plane{
	
	private final int passengerCapacity;

	public PassengerPlane(String model, int flightRange, Engine engine, int wingspan, int passengerCapacity) {
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
