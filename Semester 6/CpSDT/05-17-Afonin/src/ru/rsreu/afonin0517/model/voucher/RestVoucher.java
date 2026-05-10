package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.voucher.features.RestVoucherFeatures;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class RestVoucher extends AbstractVoucher {

	private final RestVoucherFeatures features;

	public RestVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			RestVoucherFeatures features) {
		super(title, days, price, transport, mealPlan);
		this.features = features;
	}

	@Override
	public double calculateValue() {
		double nutritionScore = this.getMealPlan().calculateNutritionScore();
		double comfort = this.getTransport().comfortLevel();

		return nutritionScore * this.features.nutritionWeight() + this.getDays() * this.features.daysWeight() + comfort;
	}

}