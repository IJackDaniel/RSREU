package ru.rsreu.afonin0517.model.voucher;

import ru.rsreu.afonin0517.model.component.Transport;
import ru.rsreu.afonin0517.model.component.MealPlan;

public final class NullVoucher extends AbstractVoucher {

	private static final String DEFAULT_TITLE = "NOT FOUND";

	public NullVoucher() {
		super(NullVoucher.DEFAULT_TITLE, 0, 0.0, new Transport("NONE", 0.0, 0.0), new MealPlan("NONE", 0, 0.0));
	}

	@Override
	public double calculateValue() {
		return 0.0;
	}

}