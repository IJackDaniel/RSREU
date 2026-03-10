package ru.rsreu.afonin0209;

public class CharacterInStringSorter {
	private CharacterInStringSorter() {

	}

	public static String[] sortCharactersInStringsFromArray(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			strings[i] = sortCharactersInString(strings[i]);
		}
		return strings;
	}

	private static String sortCharactersInString(String string) {
		int length = string.length();
		char[] alpha = new char[length];
		char[] digits = new char[length];
		char[] other = new char[length];

		for (int currentIndex = 0; currentIndex < length; currentIndex++) {
			char character = string.charAt(currentIndex);
			if (Character.isLetter(character)) {
				alpha[currentIndex] = character;
			} else if (Character.isDigit(character)) {
				digits[currentIndex] = character;
			} else {
				other[currentIndex] = character;
			}
		}

		return concatinateCharArrays(alpha, digits, other);
	}

	private static String concatinateCharArrays(char[]... charLists) {
		String resultString = "";
		for (char[] characters : charLists) {
			resultString = addCharactersFromArrayToString(resultString, characters);
		}
		return resultString;
	}

	private static String addCharactersFromArrayToString(String string, char[] characters) {
		for (char character : characters) {
			if (character != '\0') {
				string = string + character;
			}
		}
		return string;
	}
}
