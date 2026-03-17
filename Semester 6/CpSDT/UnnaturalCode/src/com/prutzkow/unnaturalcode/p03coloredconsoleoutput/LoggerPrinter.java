package com.prutzkow.unnaturalcode.p03coloredconsoleoutput;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * Class allows you to log data and to print them on a console simultaneously
 */
public class LoggerPrinter {

	public static void logAndPrint(ColorKind colorKind, String text)
			throws InvocationTargetException, IllegalAccessException {
		Method[] declaredMethods = ColorTuner.class.getDeclaredMethods();
		Stream.of(declaredMethods).filter(
				method -> method.getName().equals(colorKind.getMethodName()))
				.findFirst().ifPresentOrElse(method -> {
					try {
						method.invoke(ColorTuner.class, text);
					} catch (IllegalAccessException
							| InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}, () -> {
					System.out.println(
							"[LoggerPrinter class] : Method not found");
				});
	}
}
