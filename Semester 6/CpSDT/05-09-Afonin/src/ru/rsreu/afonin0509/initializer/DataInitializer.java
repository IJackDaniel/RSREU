package ru.rsreu.afonin0509.initializer;

import ru.rsreu.afonin0509.model.aircraft.*;
import ru.rsreu.afonin0509.model.Manufacturer;

public final class DataInitializer {
	
	private DataInitializer() {
		
	}
	
	public static Aircraft[] initializeFleet() {
		return new Aircraft[] {
				new PassengerPlane("Ilyushin II-96-300", 12100,
                        new Manufacturer("Ilyushin", "Russia"),
                        60, 235),
				
				new PassengerPlane("Boeing 777-300ER", 13650,
                        new Manufacturer("Boeing", "USA"),
                        64, 396),

                new CargoPlane("Ilyushin II-76TD-90A", 4000,
                        new Manufacturer("Ilyushin", "Russia"),
                        50, 48000),
                
                new CargoPlane("Boeing 747-8 Freighter", 8200,
                        new Manufacturer("Boeing", "USA"),
                        68, 140000),

                new PassengerHelicopter("Mil Mi-38", 880,
                        new Manufacturer("Moscow Mil Helicopter Plant", "Russia"),
                        21, 30),

                new CargoHelicopter("Mi-26", 800,
                        new Manufacturer("Moscow Mil Helicopter Plant", "Russia"),
                        32, 20000),

                new Drone("CargoDrone X1", 300,
                        new Manufacturer("DJI", "China"),
                        500)
		};
	}
}
