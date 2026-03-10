package ru.rsreu.afonin0209;

import java.util.Scanner;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

public class ClientRunner {
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();

	private ClientRunner() {

	}

	public static void main(String[] args) {
		 String[] strings = inputValues();
		
		if (strings == null || strings.length == 0) {
			return;
		}

		String[] resultOne = CharacterInStringSorter.sortCharactersInStringsFromArray(strings);
		System.out.println(RESOURCER.getString("message.output.string") + convertArrayToString(resultOne));
	}

	private static String[] inputValues() {
		Scanner in = new Scanner(System.in);

		System.out.print(RESOURCER.getString("message.input.numberOfStrings"));
		int stringCount = Integer.parseInt(in.next());
		in.nextLine();

		String[] strings = new String[stringCount];
		for (int i = 0; i < strings.length; i++) {
			System.out.printf(RESOURCER.getString("message.input.string"), i + 1);
			strings[i] = in.nextLine();
		}

		in.close();

		return strings;
	}

	private static String convertArrayToString(Object[] array) {
		String string = String.valueOf(array[0]);
		for (int i = 1; i < array.length; i++) {
			string = string + "\n" + array[i];
		}
		return string;
	}
}
