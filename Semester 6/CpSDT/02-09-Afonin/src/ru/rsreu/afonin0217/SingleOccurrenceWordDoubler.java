package ru.rsreu.afonin0217;

public class SingleOccurrenceWordDoubler {

	private static final String WORD_SEPARATOR = " ";

	private SingleOccurrenceWordDoubler() {

	}

	public static String[] doubleSingleOccurrenceWordsInArray(String[] strings) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = SingleOccurrenceWordDoubler.doubleSingleOccurrenceWordsInString(strings[i]);
		}
		return result;
	}

	private static String doubleSingleOccurrenceWordsInString(String string) {
		String trimmedString = string.trim();
		if (trimmedString.isEmpty()) {
			return string;
		}

		String[] words = WordSplitter.splitIntoWords(trimmedString);
		boolean[] singleOccurrenceFlags = SingleOccurrenceWordDoubler.identifySingleOccurrenceWords(words);
		return SingleOccurrenceWordDoubler.assembleResultString(words, singleOccurrenceFlags);
	}

	private static boolean[] identifySingleOccurrenceWords(String[] words) {
		boolean[] flags = new boolean[words.length];
		for (int i = 0; i < words.length; i++) {
			flags[i] = SingleOccurrenceWordDoubler.countOccurrences(words, words[i]) == 1;
		}
		return flags;
	}

	private static int countOccurrences(String[] words, String targetWord) {
		int count = 0;
		for (String word : words) {
			if (word.equals(targetWord)) {
				count++;
			}
		}
		return count;
	}

	private static String assembleResultString(String[] words, boolean[] singleOccurrenceFlags) {
		String result = "";
		for (int i = 0; i < words.length; i++) {
			result += words[i];
			if (singleOccurrenceFlags[i]) {
				result += SingleOccurrenceWordDoubler.WORD_SEPARATOR + words[i];
			}
			if (i < words.length - 1) {
				result += SingleOccurrenceWordDoubler.WORD_SEPARATOR;
			}
		}
		return result;
	}
}