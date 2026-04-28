package ru.rsreu.afonin0217;

public class ArgumentValidator {
	private static final int INDEX_NOT_FOUND = -1;

	private ArgumentValidator() {

	}

	public static boolean isNotNullAndNotEmptyStrings(String... strings) {
		for (String string : strings) {
			if (string == null || string.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static boolean isArrayNullOrEmpty(Object[] array) {
		return array == null || array.length == 0;
	}

	public static boolean isIndexValid(int index) {
		return index >= 0;
	}

	public static boolean isIndexNotFound(int position) {
		return position == ArgumentValidator.INDEX_NOT_FOUND;
	}
}