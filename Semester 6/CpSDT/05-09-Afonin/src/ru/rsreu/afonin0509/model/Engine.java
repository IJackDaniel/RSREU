package ru.rsreu.afonin0509.model;

public class Engine {
	
	private final String type;
	private final int power;
	
	Engine(String type, int power) {
		this.type = type;
		this.power = power;
	}
	
	String getType() {
		return this.type;
	}
	
	int getPower() {
		return power;
	}
}
