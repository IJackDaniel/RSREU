package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

abstract class AbstractHelicopter extends AbstractAircraft {

	private final int rotorDiameter;

	protected AbstractHelicopter(String model, int flightRange, Manufacturer engine, int rotorDiameter) {
		super(model, flightRange, engine);
		this.rotorDiameter = rotorDiameter;
	}

	protected int getRotorDiameter() {
		return rotorDiameter;
	}
}
