package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class PassengerPlane extends AbstractPlane {

	private final int passengerCapacity;
	private final boolean hasBusinessClass;

	public PassengerPlane(String model, int flightRange, Manufacturer manufacturer, int wingspan, int passengerCapacity,
			boolean hasBusinessClass) {
		super(model, flightRange, manufacturer, wingspan);
		this.passengerCapacity = passengerCapacity;
		this.hasBusinessClass = hasBusinessClass;
	}

	public boolean hasBusinessClass() {
		return this.hasBusinessClass;
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
