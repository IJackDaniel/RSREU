package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class PassengerPlane extends AbstractPlane {

	private final int passengerCapacity;

	public PassengerPlane(String model, int flightRange, Manufacturer manufacturer, int wingspan, int passengerCapacity) {
		super(model, flightRange, manufacturer, wingspan);
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
