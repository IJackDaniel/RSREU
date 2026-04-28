package ru.rsreu.afonin0217;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexadecimalColorValidator {
	private static final String HEXADECIMAL_COLOR_REGEX = "^#[0-9A-Fa-f]{6}$";
	private static final Pattern HEXADECIMAL_COLOR_PATTERN = Pattern
			.compile(HexadecimalColorValidator.HEXADECIMAL_COLOR_REGEX);

	private HexadecimalColorValidator() {

	}

	public static Boolean[] validateHexadecimalColors(String[] strings) {
		Boolean[] result = new Boolean[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = HexadecimalColorValidator.isHexadecimalColor(strings[i]);
		}
		return result;
	}

	private static boolean isHexadecimalColor(String string) {
		if (!ArgumentValidator.isNotNullAndNotEmptyStrings(string)) {
			return false;
		}

		Matcher matcher = HexadecimalColorValidator.HEXADECIMAL_COLOR_PATTERN.matcher(string);
		return matcher.matches();
	}
}