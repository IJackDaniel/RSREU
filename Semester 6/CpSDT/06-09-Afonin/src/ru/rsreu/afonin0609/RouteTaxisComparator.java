package ru.rsreu.afonin0609;

import ru.rsreu.afonin0609.model.RouteTaxi;

public final class RouteTaxisComparator {

	private RouteTaxisComparator() {

	}

	public static boolean areArraysEqual(RouteTaxi[] first, RouteTaxi[] second) {
		if (first.length != second.length) {
			return false;
		}

		return RouteTaxisComparator.compareElements(first, second);
	}

	private static boolean compareElements(RouteTaxi[] first, RouteTaxi[] second) {
		for (int i = 0; i < first.length; i++) {
			if (!RouteTaxisComparator.areElementsEqual(first[i], second[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean areElementsEqual(RouteTaxi firstTaxi, RouteTaxi secondTaxi) {
		return firstTaxi.registrationNumber().equals(secondTaxi.registrationNumber())
				&& firstTaxi.owner().equals(secondTaxi.owner()) && firstTaxi.routeNumber() == secondTaxi.routeNumber();
	}
}
