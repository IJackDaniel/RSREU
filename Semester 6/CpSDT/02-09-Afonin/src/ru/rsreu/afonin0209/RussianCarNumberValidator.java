package ru.rsreu.afonin0209;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RussianCarNumberValidator {
	private static final String VALID_LETTERS = "АВЕКМНОРСТУХ";
	private static final String CAR_NUMBER_REGEX = "^[" + VALID_LETTERS + "]\\d{3}[" + VALID_LETTERS + "]{2}" + "(?:"
			// 01-99
			+ "0[1-9]|[1-9][0-9]|"
			// 101-199
			+ "101|102|103|105|109|111|113|115|116|118|121|122|123|125|126|127|130|131|134|136|"
			+ "138|139|142|147|150|152|154|155|156|158|159|161|163|164|169|172|173|174|177|178|"
			+ "180|181|182|184|185|186|188|190|192|193|196|197|198|199|"
			// additional codes
			+ "250|252|323|550|702|716|725|750|754|761|763|774|777|790|797|799|977" + ")rus$";
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

		Matcher matcher = RussianCarNumberValidator.CAR_NUMBER_PATTERN.matcher(string);
		return matcher.matches();
	}
}
