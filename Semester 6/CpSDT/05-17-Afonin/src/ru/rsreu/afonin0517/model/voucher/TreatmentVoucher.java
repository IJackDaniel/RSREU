package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class TreatmentVoucher extends AbstractVoucher {

	private final double treatmentLevel;

	public TreatmentVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			double treatmentLevel) {
		super(title, days, price, transport, mealPlan);
		this.treatmentLevel = treatmentLevel;
	}

	public double getTreatmentLevel() {
		return this.treatmentLevel;
	}

	@Override
	public double calculateValue() {
		double nutritionScore = this.getMealPlan().calculateNutritionScore();
		return this.treatmentLevel * this.getDays() + nutritionScore;
	}

}