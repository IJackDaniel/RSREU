package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public abstract class Aircraft implements Comparable<Aircraft> {
	
	private final String model;
	private final int flightRange;
	private final Manufacturer manufacturer;
	
	protected Aircraft(String model, int flightRange, Manufacturer manufacturer) {
		this.model = model;
		this.flightRange = flightRange;
		this.manufacturer = manufacturer;
	}
	
	public abstract boolean canTransportPassengers(int passengers);
	
	public abstract boolean canTransportCargo(double cargo);
	
	public abstract int getPassengerCapacity();
	
	public abstract double getCargoCapacity();
	
	// Getters
	public String getModel() {
		return this.model;
	}
	
	public int getFlightRange() {
		return this.flightRange;
	}
	
	public Manufacturer getManufacturer() {
		return this.manufacturer;
	}
	
	@Override
	public int compareTo(Aircraft other) {
		return Integer.compare(this.flightRange, other.flightRange);
	}
}
