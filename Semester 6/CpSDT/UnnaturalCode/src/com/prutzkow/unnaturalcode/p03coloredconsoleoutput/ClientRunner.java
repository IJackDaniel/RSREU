package com.prutzkow.unnaturalcode.p03coloredconsoleoutput;

import java.lang.reflect.InvocationTargetException;

public class ClientRunner {

	private ClientRunner() {
	}

	public static void main(String[] args) {
		try {
			LoggerPrinter.logAndPrint(ColorKind.BLACK_BG_YELLOW_TEXT, "Refactoring");
			LoggerPrinter.logAndPrint(ColorKind.BLUE_TEXT, "Java");
		} catch (InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
