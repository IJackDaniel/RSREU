package ru.rsreu.afonin0217;

import java.util.Scanner;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class ClientRunner {
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();
	private static final String OLD_SUBSTRING = "abc";
	private static final String NEW_SUBSTRING = "XYZ";
	private static final int ENTRY_NUMBER = 2;

	private ClientRunner() {

	}

	public static void main(String[] args) {
		String[] strings = ClientRunner.inputStrings();

		if (ArgumentValidator.isArrayNullOrEmpty(strings)) {
			return;
		}

		Boolean[] colorResults = HexadecimalColorValidator.validateHexadecimalColors(strings);
		System.out.println(RESOURCER.getString("message.output.regex") + ClientRunner.formatResults(colorResults));

		String[] wordResults = SingleOccurrenceWordDoubler.doubleSingleOccurrenceWordsInArray(strings);
		System.out.println(RESOURCER.getString("message.output.word") + ClientRunner.formatResults(wordResults));

		String[] insertionResults = SubstringAfterEntryInserter.insertSubstringAfterEntryInArray(strings,
				ClientRunner.OLD_SUBSTRING, ClientRunner.NEW_SUBSTRING, ClientRunner.ENTRY_NUMBER);
		System.out.println(RESOURCER.getString("message.output.string") + ClientRunner.formatResults(insertionResults));
	}

	private static String[] inputStrings() {
		Scanner scanner = new Scanner(System.in);

		System.out.print(RESOURCER.getString("message.input.numberOfStrings"));
		int stringCount = Integer.parseInt(scanner.next());
		scanner.nextLine();

		String[] strings = new String[stringCount];
		for (int i = 0; i < strings.length; i++) {
			System.out.printf(RESOURCER.getString("message.input.string"), i + 1);
			strings[i] = scanner.nextLine();
		}

		scanner.close();
		return strings;
	}

	private static String formatResults(Object[] results) {
		String formatted = String.valueOf(results[0]);
		for (int i = 1; i < results.length; i++) {
			formatted += "\n" + results[i];
		}
		return formatted;
	}
}
