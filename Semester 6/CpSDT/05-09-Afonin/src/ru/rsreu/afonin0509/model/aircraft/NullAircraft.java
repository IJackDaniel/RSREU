package ru.rsreu.afonin0509.model.aircraft;

import ru.rsreu.afonin0509.model.Manufacturer;

public class NullAircraft extends AbstractAircraft {

	public NullAircraft() {
		super("NONE", 0, new Manufacturer("NONE", "NONE"));
	}

	@Override
	public boolean canTransportPassengers(int passengers) {
		return false;
	}

	@Override
	public boolean canTransportCargo(double cargo) {
		return false;
	}

	@Override
	public int getPassengerCapacity() {
		return 0;
	}

	@Override
	public double getCargoCapacity() {
		return 0;
	}
}
