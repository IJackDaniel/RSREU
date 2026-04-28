package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class ExcursionVoucher extends AbstractVoucher {

	private static final double EXCURSION_WEIGHT = 2.5;
	private static final int MIN_DAYS_THRESHOLD = 5;
	private static final double SHORT_TOUR_PENALTY = 5.0;

	private final int excursionsCount;

	public ExcursionVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			int excursionsCount) {
		super(title, days, price, transport, mealPlan);
		this.excursionsCount = excursionsCount;
	}

	public int getExcursionsCount() {
		return this.excursionsCount;
	}

	@Override
	public double calculateValue() {
		double baseValue = this.excursionsCount * ExcursionVoucher.EXCURSION_WEIGHT + this.getDays();

		if (this.getDays() < ExcursionVoucher.MIN_DAYS_THRESHOLD) {
			baseValue -= ExcursionVoucher.SHORT_TOUR_PENALTY;
		}

		return baseValue;
	}

}