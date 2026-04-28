package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public abstract class AbstractVoucher implements Comparable<AbstractVoucher> {

	private final String title;
	private final int days;
	private final double price;
	private final Transport transport;
	private final MealPlan mealPlan;

	protected AbstractVoucher(String title, int days, double price, Transport transport, MealPlan mealPlan) {
		this.title = title;
		this.days = days;
		this.price = price;
		this.transport = transport;
		this.mealPlan = mealPlan;
	}

	public String getTitle() {
		return this.title;
	}

	public int getDays() {
		return this.days;
	}

	public double getPrice() {
		return this.price;
	}

	public Transport getTransport() {
		return this.transport;
	}

	public MealPlan getMealPlan() {
		return this.mealPlan;
	}

	public double calculatePricePerDay() {
		return this.price / this.days;
	}

	public abstract double calculateValue();

	@Override
	public int compareTo(AbstractVoucher other) {
		return Double.compare(this.calculateValue(), other.calculateValue());
	}
}