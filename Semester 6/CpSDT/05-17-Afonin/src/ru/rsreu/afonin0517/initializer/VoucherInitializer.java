package ru.rsreu.afonin0517.initializer;

import ru.rsreu.afonin0517.model.component.*;
import ru.rsreu.afonin0517.model.voucher.*;
import ru.rsreu.afonin0517.model.voucher.features.CruiseVoucherFeatures;
import ru.rsreu.afonin0517.model.voucher.features.ExcursionVoucherFeatures;
import ru.rsreu.afonin0517.model.voucher.features.RestVoucherFeatures;
import ru.rsreu.afonin0517.model.voucher.features.ShoppingVoucherFeatures;

public final class VoucherInitializer {

	private VoucherInitializer() {
	}

	public static AbstractVoucher[] initializeVouchers() {

		Transport plane = new Transport("Plane", 850.0, 0.9);

		Transport bus = new Transport("Bus", 90.0, 0.6);

		Transport ship = new Transport("Ship", 40.0, 0.8);

		MealPlan allInclusive = new MealPlan("All Inclusive", 3, 0.9);

		MealPlan breakfast = new MealPlan("Breakfast Only", 1, 0.6);

		MealPlan diet = new MealPlan("Diet", 4, 0.95);

		RestVoucherFeatures restFeatures = new RestVoucherFeatures(1.2, 0.5);

		ExcursionVoucherFeatures excursionFeatures = new ExcursionVoucherFeatures(2.5, 5, 5.0);

		CruiseVoucherFeatures cruiseFeatures = new CruiseVoucherFeatures(3.0, 0.3);

		ShoppingVoucherFeatures shoppingFeatures = new ShoppingVoucherFeatures(0.2, 0.01, 0.2);

		return new AbstractVoucher[] {

				new RestVoucher("Sunny Beach", 7, 1200, plane, allInclusive, restFeatures),

				new ExcursionVoucher("Historic Europe", 5, 1500, plane, breakfast, 8, excursionFeatures),

				new CruiseVoucher("Mediterranean Cruise", 8, 3000, ship, allInclusive, 6, cruiseFeatures),

				new ShoppingVoucher("Dubai Shopping Festival", 4, 1800, plane, breakfast, 2500, shoppingFeatures),

				new TreatmentVoucher("Mountain Health Resort", 14, 2200, bus, diet, 1.8) };
	}
}