package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class CargoPlane extends AbstractPlane {

	private final double cargoCapacity;
	private final boolean hasRefrigeration;

	public CargoPlane(String model, int flightRange, Manufacturer manufacturer, int wingspan, double cargoCapacity,
			boolean hasRefrigeration) {
		super(model, flightRange, manufacturer, wingspan);
		this.cargoCapacity = cargoCapacity;
		this.hasRefrigeration = hasRefrigeration;
	}

	public boolean canTransportPerishableCargo(double cargo) {
		return this.hasRefrigeration() && cargo <= this.cargoCapacity;
	}

	public boolean hasRefrigeration() {
		return this.hasRefrigeration;
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
