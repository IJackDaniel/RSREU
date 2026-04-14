package ru.rsreu.afonin0609.model;

public record RouteTaxi(String registrationNumber, String owner, RouteNumber routeNumber) {
	@Override
	public String toString() {
		return this.registrationNumber + ";" + this.owner + ";" + this.routeNumber;
	}
}
