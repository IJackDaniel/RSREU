package ru.rsreu.afonin0209;

public class WordLetterFrequencyFilter {
	private static final int LETTER_COUNT_IS_ZERO = 0;
	private static final int UNICODE_CHARACTERS_COUNT = 65536;
	
	private WordLetterFrequencyFilter() {
		
	}
	
	public static String[] filterWordsInStringsFromListByLetterFrequency(String[] strings, int targetFrequency) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = filterWordsInStringByLetterFrequencies(strings[i], targetFrequency);
		}
		return result;
	}
	
	private static String filterWordsInStringByLetterFrequencies(String string, int targetFrequency) {
		string = string.trim();
		
		String resultString = "";
		String currentWord = "";
		
		for (int i = 0; i < string.length(); i++) {
			char character = string.charAt(i);
			
			if (character == ' ') {
				if (!currentWord.isEmpty()) {
					if (!hasUnifoemFrequency(countLetterFrequencies(currentWord), targetFrequency)) {
						resultString += currentWord;
					}
					currentWord = "";
				}
				resultString += " ";
			} else {
				currentWord += character;
			}
		}

		if (!currentWord.isEmpty() && !hasUnifoemFrequency(countLetterFrequencies(currentWord), targetFrequency)) {
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

	
	private static boolean hasUnifoemFrequency(int[] frequencies, int targetFrequency) {
		for (int frequency : frequencies) {
			if (frequency != WordLetterFrequencyFilter.LETTER_COUNT_IS_ZERO && frequency != targetFrequency) {
				return false;
			}
		}
		return true;
		
	}
}
