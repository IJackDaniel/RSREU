package ru.rsreu.afonin0209;

public class WordsInStringProcesser {
	private WordsInStringProcesser() {
		
	}
	
	public static String[] processArray(String[] strings) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = processString(strings[i]);
		}
		return result;
	}
	
	private static String processString(String string) {
		string = string.trim();
		
		String resultString = "";
		String currentWord = "";
		for (int i = 0; i < string.length(); i++) {
			char character = string.charAt(i);
			
			if (character == ' ') {
				if (!currentWord.isEmpty()) {
					if (!isContainOnlyMask(countSymbolsInWord(string), new int[]{0, 2})) {
						resultString += currentWord;
					}
					currentWord = "";
				}
				resultString += " ";
			} else {
				currentWord += character;
			}
		}
		return resultString;
	}
	
	private static int[] countSymbolsInWord(String word) {
		int[] counter = new int[128];
		for (char character : word.toCharArray()) {
			counter[character] = counter[character] + 1;
		}
		return counter;
	}
	
	private static boolean contain(int number, int[] maska) {
		for (int elem : maska) {
			if (number == elem) {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isContainOnlyMask(int[] integers, int[] maska) {
		for (int num : integers) {
			if (contain(num, maska)) {
				return false;
			}
		}
		return true;
	}
}
