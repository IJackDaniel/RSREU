package ru.rsreu.afonin0217;

public class WordSplitter {
	private static final char SEPARATOR_CHARACTER = ' ';

	private WordSplitter() {

	}

	public static String[] splitIntoWords(String string) {
		String trimmedString = string.trim();
		if (trimmedString.isEmpty()) {
			return new String[0];
		}

		int wordCount = WordSplitter.countWords(trimmedString);
		String[] words = new String[wordCount];
		int wordIndex = 0;
		int position = 0;

		while (position < trimmedString.length()) {
			if (trimmedString.charAt(position) == WordSplitter.SEPARATOR_CHARACTER) {
				position++;
			} else {
				String word = WordSplitter.extractWord(trimmedString, position);
				words[wordIndex] = word;
				wordIndex++;
				position += word.length();
			}
		}
		return words;
	}

	private static String extractWord(String string, int startPosition) {
		String word = "";
		int position = startPosition;

		while (position < string.length() && string.charAt(position) != WordSplitter.SEPARATOR_CHARACTER) {
			word += string.charAt(position);
			position++;
		}
		return word;
	}

	private static int countWords(String string) {
		int count = 0;
		boolean insideWord = false;

		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) != WordSplitter.SEPARATOR_CHARACTER) {
				if (!insideWord) {
					count++;
					insideWord = true;
				}
			} else {
				insideWord = false;
			}
		}
		return count;
	}
}