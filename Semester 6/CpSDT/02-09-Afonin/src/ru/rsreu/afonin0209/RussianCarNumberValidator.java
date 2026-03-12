package ru.rsreu.afonin0209;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RussianCarNumberValidator {
	private static final String CAR_NUMBER_REGEX = "^[а-яё]\\d{3}[а-яё]{2}\\d{2}rus$";
	private static final Pattern CAR_NUMBER_PATTERN = Pattern.compile(RussianCarNumberValidator.CAR_NUMBER_REGEX);

	private RussianCarNumberValidator() {

	}

	public static Boolean[] validateRussianCarNumbers(String[] strings) {
		Boolean[] result = new Boolean[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = RussianCarNumberValidator.isCarNumber(strings[i]);
		}
		return result;
	}

	private static boolean isCarNumber(String string) {
		if (string == null || string.isEmpty()) {
			return false;
		}

		Matcher matcher = RussianCarNumberValidator.CAR_NUMBER_PATTERN.matcher(string.toLowerCase());
		return matcher.matches();
	}
}
