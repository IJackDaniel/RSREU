package ru.rsreu.afonin0517.model.component;

public record MealPlan(String type, int mealsPerDay, double quality) {
	public double calculateNutritionScore() {
		return this.mealsPerDay * this.quality;
	}
}