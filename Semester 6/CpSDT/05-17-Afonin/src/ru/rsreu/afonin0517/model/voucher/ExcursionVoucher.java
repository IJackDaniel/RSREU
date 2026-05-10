package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.voucher.features.ExcursionVoucherFeatures;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class ExcursionVoucher extends AbstractVoucher {

	private final int excursionsCount;
	private final ExcursionVoucherFeatures features;

	public ExcursionVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			int excursionsCount, ExcursionVoucherFeatures features) {
		super(title, days, price, transport, mealPlan);
		this.excursionsCount = excursionsCount;
		this.features = features;
	}

	public int getExcursionsCount() {
		return this.excursionsCount;
	}

	@Override
	public double calculateValue() {
		double baseValue = this.excursionsCount * this.features.excursionWeight() + this.getDays();

		if (this.getDays() < this.features.minimumDaysThreshold()) {
			baseValue -= this.features.shortTourPenalty();
		}

		return baseValue;
	}

}