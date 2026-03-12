package ru.rsreu.afonin0209;

public class WordsInStringProcesser {
	private static final int LETTER_COUNT_IS_ZERO = 0;
	
	private WordsInStringProcesser() {
		
	}
	
	public static String[] processArray(String[] strings, int neededLetterCount) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = processString(strings[i], neededLetterCount);
		}
		return result;
	}
	
	private static String processString(String string, int neededLetterCount) {
		string = string.trim();
		
		String resultString = "";
		String currentWord = "";
		
		for (int i = 0; i < string.length(); i++) {
			char character = string.charAt(i);
			
			if (character == ' ') {
				if (!currentWord.isEmpty()) {
					if (!isCounterContainOnlyDetermineCount(countSymbolsInWord(currentWord), neededLetterCount)) {
						resultString += currentWord;
					}
					currentWord = "";
				}
				resultString += " ";
			} else {
				currentWord += character;
			}
		}

		if (!currentWord.isEmpty() && !isCounterContainOnlyDetermineCount(countSymbolsInWord(currentWord), neededLetterCount)) {
			resultString += currentWord;
		}
		
		return resultString;
	}
	
	private static int[] countSymbolsInWord(String word) {
		int[] counter = new int[256];
		for (char character : word.toCharArray()) {
			if (Character.isLetter(character)) {
				counter[character] = counter[character] + 1;
			}
		}
		return counter;
	}

	
	private static boolean isCounterContainOnlyDetermineCount(int[] counter, int determineCount) {
		for (int number : counter) {
			if (number != WordsInStringProcesser.LETTER_COUNT_IS_ZERO && number != determineCount) {
				return false;
			}
		}
		return true;
		
	}
}
