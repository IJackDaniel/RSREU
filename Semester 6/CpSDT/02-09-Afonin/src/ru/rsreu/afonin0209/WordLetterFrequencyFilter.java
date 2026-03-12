package ru.rsreu.afonin0209;

public class WordLetterFrequencyFilter {
	private static final int LETTER_COUNT_IS_ZERO = 0;
	private static final int UNICODE_CHARACTERS_COUNT = 65536;
	private static final int TARGET_FREQUENCY = 2;

	private WordLetterFrequencyFilter() {

	}

	public static String[] filterWordsInStringsFromListByLetterFrequency(String[] strings) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = WordLetterFrequencyFilter.filterWordsInStringByLetterFrequencies(strings[i]);
		}
		return result;
	}

	private static String filterWordsInStringByLetterFrequencies(String string) {
		string = string.trim();

		String resultString = "";
		String currentWord = "";
		int[] frequencies;
		for (int i = 0; i < string.length(); i++) {
			char character = string.charAt(i);

			if (character == ' ') {
				if (!currentWord.isEmpty()) {
					frequencies = WordLetterFrequencyFilter.countLetterFrequencies(currentWord);
					if (!WordLetterFrequencyFilter.hasUnifoemFrequency(frequencies)) {
						resultString += currentWord;
					}
					currentWord = "";
				}
				resultString += " ";
			} else {
				currentWord += character;
			}
		}

		frequencies = WordLetterFrequencyFilter.countLetterFrequencies(currentWord);
		if (!currentWord.isEmpty() && !WordLetterFrequencyFilter.hasUnifoemFrequency(frequencies)) {
			resultString += currentWord;
		}

		return resultString;
	}

	private static int[] countLetterFrequencies(String word) {
		int[] frequencies = new int[WordLetterFrequencyFilter.UNICODE_CHARACTERS_COUNT];

		for (char character : word.toCharArray()) {
			if (Character.isLetter(character)) {
				frequencies[character]++;
			}
		}
		return frequencies;
	}

	private static boolean hasUnifoemFrequency(int[] frequencies) {
		for (int frequency : frequencies) {
			if (frequency != WordLetterFrequencyFilter.LETTER_COUNT_IS_ZERO
					&& frequency != WordLetterFrequencyFilter.TARGET_FREQUENCY) {
				return false;
			}
		}
		return true;
	}
}
