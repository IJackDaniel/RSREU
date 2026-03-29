package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Engine;

public class Aircraft implements Comparable<Aircraft> {
	
	private final String model;
	private final int flightRange;
	private final Engine engine;
	
	protected Aircraft(String model, int flightRange, Engine engine) {
		this.model = model;
		this.flightRange = flightRange;
		this.engine = engine;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public int getFlightRange() {
		return this.flightRange;
	}
	
	public Engine getEngine() {
		return this.engine;
	}
	
	@Override
	public int compareTo(Aircraft other) {
		return 0;
	}
}
