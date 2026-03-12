package ru.rsreu.afonin0209;

public class CharactersInWordByPatternLettersDigitsOthersSorter {
	private CharactersInWordByPatternLettersDigitsOthersSorter() {

	}

	public static String[] sortStringsByCharacterType(String[] strings) {
		for (int i = 0; i < strings.length; i++) {
			strings[i] = CharactersInWordByPatternLettersDigitsOthersSorter
					.sortCharactersByPatternLettersDigitsOther(strings[i]);
		}
		return strings;
	}

	private static String sortCharactersByPatternLettersDigitsOther(String string) {
		String letters = "";
		String digits = "";
		String otherSymbols = "";

		for (char character : string.toCharArray()) {
			if (Character.isLetter(character)) {
				letters += character;
			} else if (Character.isDigit(character)) {
				digits += character;
			} else {
				otherSymbols += character;
			}
		}

		return letters + digits + otherSymbols;
	}
}
