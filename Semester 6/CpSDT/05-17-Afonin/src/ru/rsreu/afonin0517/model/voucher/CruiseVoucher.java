package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.voucher.features.CruiseVoucherFeatures;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class CruiseVoucher extends AbstractVoucher {

	private final CruiseVoucherFeatures features;
	private final int portsCount;

	public CruiseVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan, int portsCount,
			CruiseVoucherFeatures features) {
		super(title, days, price, transport, mealPlan);
		this.portsCount = portsCount;
		this.features = features;
	}

	public int getPortsCount() {
		return this.portsCount;
	}

	@Override
	public double calculateValue() {
		double transportEfficiency = this.getTransport().calculateEfficiency();

		return this.portsCount * this.features.portWeight() + transportEfficiency * this.features.transportWeight();
	}

}