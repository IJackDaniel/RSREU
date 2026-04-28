package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class CruiseVoucher extends AbstractVoucher {

	private static final double PORT_WEIGHT = 3.0;
	private static final double TRANSPORT_WEIGHT = 0.3;

	private final int portsCount;

	public CruiseVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan, int portsCount) {
		super(title, days, price, transport, mealPlan);
		this.portsCount = portsCount;
	}

	public int getPortsCount() {
		return this.portsCount;
	}

	@Override
	public double calculateValue() {
		double transportEfficiency = this.getTransport().calculateEfficiency();

		return this.portsCount * CruiseVoucher.PORT_WEIGHT + transportEfficiency * CruiseVoucher.TRANSPORT_WEIGHT;
	}

}