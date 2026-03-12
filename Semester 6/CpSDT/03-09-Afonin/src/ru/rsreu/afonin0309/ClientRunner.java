package ru.rsreu.afonin0309;

import java.util.Arrays;
import java.util.Locale;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class ClientRunner {
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();
	private static final int[] NUMBERS = new int[] { 1, 2, 3, 4, 5 };

	private ClientRunner() {

	}

	public static void main(String[] args) {
		StringBuilder stringBuilder = new StringBuilder();
		OddIndexNonZeroAvarageToIntegerEvenElementsSubstractor b = new OddIndexNonZeroAvarageToIntegerEvenElementsSubstractor(new NumbersList(ClientRunner.NUMBERS));
		b.method();

		ClientRunner.appendResultToBuilder(stringBuilder, b);

		Locale.setDefault(Locale.US);

		ClientRunner.appendResultToBuilder(stringBuilder, b);

		System.out.println(stringBuilder.toString());
	}

	private static void appendResultToBuilder(StringBuilder stringBuilder, OddIndexNonZeroAvarageToIntegerEvenElementsSubstractor b) {
		stringBuilder.append(RESOURCER.getString("message.output.originalArray"));
		stringBuilder.append(Arrays.toString(ClientRunner.NUMBERS));
		stringBuilder.append(RESOURCER.getString("message.output.processedArray"));
		stringBuilder.append(b.getData());
	}
}
