package ru.rsreu.afonin0609.initializer;

import ru.rsreu.afonin0609.model.RouteNumber;
import ru.rsreu.afonin0609.model.RouteTaxi;

public final class RouteTaxiInitializer {

	private RouteTaxiInitializer() {

	}

	public static RouteTaxi[] initializeRouteTaxies() {
		return new RouteTaxi[] { new RouteTaxi("A432TK", "Ivanov", RouteNumber.ROUTE_16),
				new RouteTaxi("B329OA", "Petrov", RouteNumber.ROUTE_85),
				new RouteTaxi("C879TK", "Petrov", RouteNumber.ROUTE_85),
				new RouteTaxi("B651MX", "OOO LetsGo", RouteNumber.ROUTE_33),
				new RouteTaxi("T914OK", "OOO LetsGo", RouteNumber.ROUTE_17),
				new RouteTaxi("P988YC", "Baranova", RouteNumber.ROUTE_33),
				new RouteTaxi("B491TY", "Ivanov", RouteNumber.ROUTE_32) };
	}
}
