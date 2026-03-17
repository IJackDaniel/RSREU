package ru.rsreu.afonin0409;

import com.prutzkow.twodimarray.TwoDimArray;

/**
 * Extension of TwoDimArray class that provides functionality for shifting all
 * zero elements to the end of each row in a two dimensional array. The class
 * also support filling the array with random values from a specified interval.
 */
public class ZerosToEndShiftingTwoDimArray extends TwoDimArray {

	/**
	 * @param sizes variable number of arguments defining the size of each dimension
	 * @throws IllegalArgumentException if the provided sizes are invalid
	 */
	public ZerosToEndShiftingTwoDimArray(int... sizes) throws IllegalArgumentException {
		super(sizes);
	}

	/**
	 * Moves all zero elements to the end of each row in two dimensional array. For
	 * each row of array, the method moveZerosToEndInIntegerList() is called.
	 */
	public void moveZerosToEndForListsInTwoDimensionArray() {
		for (int[] row : this.arrayBody) {
			this.moveZerosToEndInIntegerList(row);
		}
	}

	/**
	 * Fills all elements of the two dimensional array with random integers from the
	 * interval [lowerBound, upperBound].
	 * 
	 * @param lowerBound the lowest bound of the interval
	 * @param upperBound the upper bound of the interval
	 */
	public void fillRandomValuesFromInterval(int lowerBound, int upperBound) {
		for (int[] row : this.arrayBody) {
			this.fillRowRandomValuesFromInterval(row, lowerBound, upperBound);
		}
	}

	/**
	 * Moves all zero elements to the end of a one dimensional integer array,
	 * preserving the relative order of non-zero elements.
	 * 
	 * @param integers the integer array to shifting
	 */
	private void moveZerosToEndInIntegerList(int[] integers) {
		int nonZeroLastIndex = 0;
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] != 0) {
				integers[nonZeroLastIndex] = integers[i];
				nonZeroLastIndex++;
			}
		}

		this.fillIntegerListByZeroStartsFromIndex(nonZeroLastIndex, integers);
	}

	/**
	 * Fills the specified integer array with zeros starting from the given index.
	 * 
	 * @param startIndex the index from which to start filling with zeros
	 * @param integers   the integer array to fill
	 */
	private void fillIntegerListByZeroStartsFromIndex(int startIndex, int[] integers) {
		int currentIndex = startIndex;
		while (currentIndex < integers.length) {
			integers[currentIndex] = 0;
			currentIndex++;
		}
	}

	/**
	 * Fills one row of the two dimensional array with random integers from the
	 * specified interval.
	 * 
	 * @param row        the array row to fill
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 */
	private void fillRowRandomValuesFromInterval(int[] row, int lowerBound, int upperBound) {
		for (int i = 0; i < row.length; i++) {
			row[i] = getRandomIntegerFromIntercal(lowerBound, upperBound);
		}
	}

	/**
	 * Generates a random integer within the specifies interval.
	 * 
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 * @return a random integer in the range [lowerBound, upperBound]
	 */
	private int getRandomIntegerFromIntercal(int lowerBound, int upperBound) {
		return (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
	}

	/**
	 * Adds the string representation of a one dimensional array to a StringBuilder
	 * using formatting for element alignment.
	 * 
	 * @param builder  the StringBuilder to which the formatted string is added
	 * @param integers the integer array to format and add
	 */
	private void addArrayToStringBuilderWithFormat(StringBuilder builder, int[] integers) {
		for (int element : integers) {
			builder.append(String.format(this.FORMAT, element));
		}
		builder.append("\n");
	}

	/**
	 * Returns a string representation of the two dimensional array. The string
	 * contains the count of elements in the first column and all rows of the array
	 * with formatted output of elements.
	 * 
	 * @return a string representation of the two dimensional array
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Count of elements of first column: " + super.getRowCount() + "\n");

		for (int[] row : this.arrayBody) {
			addArrayToStringBuilderWithFormat(builder, row);
		}
		builder.append("\n");

		return builder.toString();
	}
}
