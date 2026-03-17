package ru.rsreu.afonin0409;

import com.prutzkow.resourcer.ProjectResourcer;
import com.prutzkow.resourcer.Resourcer;

/**
 * Main class for running the application that demonstrates the functionality of
 * the ZerosToEndShiftionTwoDimArray class.
 */
public class ClientRunner {
	/**
	 * Resource manager for handling localized string resources. Used for obtaining
	 * messages in the appropriate language.
	 */
	private static final Resourcer RESOURCER = ProjectResourcer.getInstance();
	/**
	 * Array of integers defining the dimensions of the two dimensional array. Each
	 * element represents the size of a row in the array.
	 */
	private static final int[] SIZES = new int[] { 5, 4, 4, 3, 6, 5 };
	/**
	 * Lower bound for generating random integer values. Using when filling the
	 * array with random numbers.
	 */
	private static final int LOWER_BOUND = -5;
	/**
	 * Upper bound for generating random integer values. Used when filling the array
	 * with random numbers.
	 */
	private static final int UPPER_BOUND = 5;

	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private ClientRunner() {

	}

	/**
	 * Main entry point of the application.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		ZerosToEndShiftingTwoDimArray twoDimArray = new ZerosToEndShiftingTwoDimArray(ClientRunner.SIZES);
		twoDimArray.fillRandomValuesFromInterval(ClientRunner.LOWER_BOUND, ClientRunner.UPPER_BOUND);

		builder.append(RESOURCER.getString("message.output.originalTwoDimArray") + twoDimArray.toString());

		twoDimArray.moveZerosToEndForListsInTwoDimensionArray();

		builder.append(RESOURCER.getString("message.output.processedTwoDimArray") + twoDimArray.toString());

		System.out.println(builder.toString());
	}
}
