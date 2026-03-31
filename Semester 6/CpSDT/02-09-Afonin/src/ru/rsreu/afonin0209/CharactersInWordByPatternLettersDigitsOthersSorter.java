package ru.rsreu.afonin0209;

import java.util.function.IntPredicate;

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
		IntPredicate isLetterPredicate = Character::isLetter;
		IntPredicate isDigitPredicate = Character::isDigit;
		IntPredicate isOtherPredicate = isLetterPredicate.negate().and(isDigitPredicate.negate());

		String letters = extractOnlyCertainTypeCharactersFromString(string, isLetterPredicate);
		String digits = extractOnlyCertainTypeCharactersFromString(string, isDigitPredicate);
		String otherSymbols = extractOnlyCertainTypeCharactersFromString(string, isOtherPredicate);

		return letters + digits + otherSymbols;
	}

	private static String extractOnlyCertainTypeCharactersFromString(String string, IntPredicate condition) {
		String outcome = "";

		for (char character : string.toCharArray()) {
			if (condition.test(character)) {
				outcome += character;
			}
		}

		return outcome;
	}
}
