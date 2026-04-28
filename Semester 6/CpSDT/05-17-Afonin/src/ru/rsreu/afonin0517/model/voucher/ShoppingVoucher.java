package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class ShoppingVoucher extends AbstractVoucher {

	private static final double DAYS_WEIGHT = 0.2;
	private static final double SPEED_WEIGHT = 0.01;
	private static final double COMFORT_WEIGHT = 0.2;

	private final double shoppingBudget;

	public ShoppingVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			double shoppingBudget) {
		super(title, days, price, transport, mealPlan);
		this.shoppingBudget = shoppingBudget;
	}

	public double getShoppingBudget() {
		return this.shoppingBudget;
	}

	@Override
	public double calculateValue() {
		double valueRatio = this.shoppingBudget / this.getPrice();
		double shoppingConvenience = this.getTransport().speed() * ShoppingVoucher.SPEED_WEIGHT
				+ this.getTransport().comfortLevel() * ShoppingVoucher.COMFORT_WEIGHT;

		return valueRatio + this.getDays() * ShoppingVoucher.DAYS_WEIGHT + shoppingConvenience;
	}

}