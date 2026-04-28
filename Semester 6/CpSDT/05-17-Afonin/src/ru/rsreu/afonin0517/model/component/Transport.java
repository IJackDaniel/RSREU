package ru.rsreu.afonin0517.model.component;

public record Transport(String type, double speed, double comfortLevel) {
	public double calculateEfficiency() {
		return this.speed * this.comfortLevel;
	}
}