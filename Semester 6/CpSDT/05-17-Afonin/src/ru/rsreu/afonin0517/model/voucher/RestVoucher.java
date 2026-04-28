package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class RestVoucher extends AbstractVoucher {

	private static final double NUTRITION_WEIGHT = 1.2;
	private static final double DAYS_WEIGHT = 0.5;

	public RestVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan) {
		super(title, days, price, transport, mealPlan);
	}

	@Override
	public double calculateValue() {
		double nutritionScore = this.getMealPlan().calculateNutritionScore();
		double comfort = this.getTransport().comfortLevel();

		return nutritionScore * RestVoucher.NUTRITION_WEIGHT + this.getDays() * RestVoucher.DAYS_WEIGHT + comfort;
	}

}