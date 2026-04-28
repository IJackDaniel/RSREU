package ru.rsreu.afonin0317;

import java.util.Arrays;
import java.util.Locale;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class ClientRunner {
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();
	private static final int[] NUMBERS = new int[] { 1, -2, 3, 4, -5, 6 };

	private ClientRunner() {

	}

	public static void main(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		ProductBetweenNegativesCalculator calculator = new ProductBetweenNegativesCalculator(
				new Numbers(ClientRunner.NUMBERS));

		ClientRunner.appendResultToBuilder(stringBuilder, calculator);

		Locale.setDefault(Locale.US);

		ClientRunner.appendResultToBuilder(stringBuilder, calculator);

		System.out.println(stringBuilder.toString());
	}

	private static void appendResultToBuilder(StringBuilder stringBuilder,
			ProductBetweenNegativesCalculator calculator) {
		stringBuilder.append(RESOURCER.getString("message.output.originalArray"));
		stringBuilder.append(Arrays.toString(ClientRunner.NUMBERS));
		stringBuilder.append(RESOURCER.getString("message.output.result"));

		if (!calculator.hasEnoughNegatives()) {
			stringBuilder.append(RESOURCER.getString("message.insufficient.negatives"));
		} else if (calculator.isDistanceBetweenFirstAndSecondNegativesMinimal()) {
			stringBuilder.append(RESOURCER.getString("message.adjacent.negatives"));
		} else {
			stringBuilder.append(calculator.calculateProduct());
		}
	}
}