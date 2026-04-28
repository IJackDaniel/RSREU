package ru.rsreu.afonin0217;

public class SubstringAfterEntryInserter {

	private static final int ENTRY_NOT_FOUND_INDEX = -1;

	private SubstringAfterEntryInserter() {

	}

	public static String[] insertSubstringAfterEntryInArray(String[] strings, String oldSubstring, String newSubstring,
			int entry) {
		String[] result = new String[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = SubstringAfterEntryInserter.insertSubstringAfterEntryInString(strings[i], oldSubstring,
					newSubstring, entry);
		}
		return result;
	}

	private static String insertSubstringAfterEntryInString(String string, String oldSubstring, String newSubstring,
			int entry) {
		if (!ArgumentValidator.isNotNullAndNotEmptyStrings(string, oldSubstring, newSubstring)
				|| !ArgumentValidator.isIndexValid(entry)) {
			return string;
		}

		int entryPosition = SubstringAfterEntryInserter.findEntryPosition(string, oldSubstring, entry);
		if (ArgumentValidator.isIndexNotFound(entryPosition)) {
			return string;
		}

		return string.substring(0, entryPosition + oldSubstring.length()) + newSubstring
				+ string.substring(entryPosition + oldSubstring.length());
	}

	private static int findEntryPosition(String string, String oldSubstring, int entry) {
		int[] positions = SubstringAfterEntryInserter.findAllSubstringPositions(string, oldSubstring);
		if (positions.length < entry) {
			return SubstringAfterEntryInserter.ENTRY_NOT_FOUND_INDEX;
		}
		return positions[entry - 1];
	}

	private static int[] findAllSubstringPositions(String string, String substring) {
		int count = SubstringAfterEntryInserter.countSubstringOccurrences(string, substring);
		int[] positions = new int[count];
		int positionIndex = 0;

		for (int i = 0; i <= string.length() - substring.length(); i++) {
			if (string.substring(i, i + substring.length()).equals(substring)) {
				positions[positionIndex] = i;
				positionIndex++;
			}
		}
		return positions;
	}

	private static int countSubstringOccurrences(String string, String substring) {
		int count = 0;
		for (int i = 0; i <= string.length() - substring.length(); i++) {
			if (string.substring(i, i + substring.length()).equals(substring)) {
				count++;
			}
		}
		return count;
	}
}