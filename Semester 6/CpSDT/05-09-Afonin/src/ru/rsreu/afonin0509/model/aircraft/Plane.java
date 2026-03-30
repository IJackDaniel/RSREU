package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Engine;

public abstract class Plane extends Aircraft {
	
	private final int wingspan;
	
	protected Plane(String model, int flightRange, Engine engine, int wingspan) {
		super(model, flightRange, engine);
		this.wingspan = wingspan;
	}
	
	protected int getWingspan() {
		return this.wingspan;
	}
}
