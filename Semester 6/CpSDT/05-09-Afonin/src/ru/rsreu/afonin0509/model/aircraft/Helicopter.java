package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Engine;

public abstract class Helicopter extends Aircraft {
	
	private final int rotorDiameter;
	
	protected Helicopter(String model, int flightRange, Engine engine, int rotorDiameter) {
		super(model, flightRange, engine);
		this.rotorDiameter = rotorDiameter;
	}
	
	protected int getRotorDiameter() {
		return rotorDiameter;
	}
}
