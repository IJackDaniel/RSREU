package ru.rsreu.afonin0209;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarNumberChecker {
	private CarNumberChecker() {
		
	}
	
	public static Boolean[] checkCarNumberInArray(String[] strings) {
		Boolean[] result = new Boolean[strings.length];
		for (int i = 0; i < strings.length; i++) {
			result[i] = isCarNumber(strings[i]);
		}
		return result;
	}
	
	private static boolean isCarNumber(String string) {
		String regex = "^[а-яА-ЯёЁ]\\d{3}[а-яА-ЯёЁ]{3}\\d{2}rus$";
		
		if (string == null || string.isEmpty()) {
			return false;
		}
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
}
