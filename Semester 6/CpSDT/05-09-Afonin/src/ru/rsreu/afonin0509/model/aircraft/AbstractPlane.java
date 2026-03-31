package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

abstract class AbstractPlane extends AbstractAircraft {

	private final int wingspan;

	protected AbstractPlane(String model, int flightRange, Manufacturer manufacturer, int wingspan) {
		super(model, flightRange, manufacturer);
		this.wingspan = wingspan;
	}

	protected int getWingspan() {
		return this.wingspan;
	}
}
