package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.voucher.features.ShoppingVoucherFeatures;
import ru.rsreu.afonin0517.model.component.MealPlan;

public class ShoppingVoucher extends AbstractVoucher {

	private final ShoppingVoucherFeatures features;
	private final double shoppingBudget;

	public ShoppingVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan,
			double shoppingBudget, ShoppingVoucherFeatures features) {
		super(title, days, price, transport, mealPlan);
		this.shoppingBudget = shoppingBudget;
		this.features = features;
	}

	public double getShoppingBudget() {
		return this.shoppingBudget;
	}

	@Override
	public double calculateValue() {
		double valueRatio = this.shoppingBudget / this.getPrice();
		double shoppingConvenience = this.getTransport().speed() * this.features.speedWeight()
				+ this.getTransport().comfortLevel() * this.features.comfortWeight();

		return valueRatio + this.getDays() * this.features.daysWeight() + shoppingConvenience;
	}

}