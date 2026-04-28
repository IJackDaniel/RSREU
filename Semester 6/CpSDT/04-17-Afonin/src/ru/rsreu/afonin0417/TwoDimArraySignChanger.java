package ru.rsreu.afonin0417;

import com.prutzkow.twodimarray.TwoDimArray;

/**
 * Extension of TwoDimArray that provides functionality to find and change the
 * sign of a specific element in each row. The element to change is the one
 * (excluding the first and last) that has the minimal absolute difference
 * between the sum of elements on its left and the sum of elements on its right.
 * Also supports filling the array with random values.
 */
public class TwoDimArraySignChanger extends TwoDimArray {
	/**
	 * Minimal number of elements required in a row to perform the sign change
	 * operation. Rows with fewer elements than this count are skipped because the
	 * operation excludes the first and last elements, requiring at least three
	 * elements in a row.
	 */
	private static final int MINIMAL_ELEMENTS_COUNT = 3;

	/**
	 * Constructs a TwoDimArraySignChanger with the specified dimensions.
	 *
	 * @param sizes variable number of arguments defining the size of each dimension
	 * @throws IllegalArgumentException if the provided sizes are invalid
	 */
	public TwoDimArraySignChanger(int... sizes) throws IllegalArgumentException {
		super(sizes);
	}

	/**
	 * Iterates through each row of the two-dimensional array. For each row, finds
	 * the element (excluding the first and last) with the minimal absolute
	 * difference between the left sum and right sum, and changes its sign. Rows
	 * with less than 3 elements are skipped.
	 */
	public void changeSignElementsWithMinimalSideSumDifference() {
		for (int[] row : this.arrayBody) {
			this.changeSignElementWithMinimalSideSumDifferenceInRow(row);
		}
	}

	/**
	 * Fills all elements of the two-dimensional array with random integers from the
	 * interval from lowerBound to upperBound inclusive.
	 *
	 * @param lowerBound the lowest bound of the interval
	 * @param upperBound the upper bound of the interval
	 */
	public void fillRandomValuesFromInterval(int lowerBound, int upperBound) {
		for (int[] row : this.arrayBody) {
			this.fillRowWithRandomValues(row, lowerBound, upperBound);
		}
	}

	/**
	 * Finds the element with minimal absolute difference between left sum and right
	 * sum in the given row and changes its sign. Rows with length less than 3 are
	 * ignored.
	 *
	 * @param row the array representing a single row to change
	 */
	private void changeSignElementWithMinimalSideSumDifferenceInRow(int[] row) {
		if (row.length < TwoDimArraySignChanger.MINIMAL_ELEMENTS_COUNT) {
			return;
		}
		int targetIndex = this.findIndexWithMinimalSideSumDifference(row);
		row[targetIndex] = -row[targetIndex];
	}

	/**
	 * Finds the index of the element (excluding the first and last) that has the
	 * minimal absolute difference between the sum of elements on its left and the
	 * sum of elements on its right.
	 *
	 * @param row the integer array row to analyze
	 * @return the index of the element with the minimum left-right sum difference
	 */
	private int findIndexWithMinimalSideSumDifference(int[] row) {
		int minimalDifferenceIndex = 1;
		int minimalDifference = Integer.MAX_VALUE;

		for (int i = 1; i < row.length - 1; i++) {
			int difference = this.calculateAbsoluteDifferenceForIndex(row, i);
			if (difference < minimalDifference) {
				minimalDifference = difference;
				minimalDifferenceIndex = i;
			}
		}
		return minimalDifferenceIndex;
	}

	/**
	 * Calculates the absolute difference between the sum of elements to the left of
	 * the given index and the sum of elements to the right.
	 *
	 * @param row   the integer array
	 * @param index the index of the element to calculate the difference for
	 * @return the absolute difference between left sum and right sum
	 */
	private int calculateAbsoluteDifferenceForIndex(int[] row, int index) {
		int leftSum = this.calculateLeftSum(row, index);
		int rightSum = this.calculateRightSum(row, index);
		return Math.abs(leftSum - rightSum);
	}

	/**
	 * Calculates the sum of elements to the left of the specified index.
	 *
	 * @param row   the integer array
	 * @param index the index up to which elements are summed (exclusive)
	 * @return the sum of elements to the left of the index
	 */
	private int calculateLeftSum(int[] row, int index) {
		int sum = 0;
		for (int i = 0; i < index; i++) {
			sum += row[i];
		}
		return sum;
	}

	/**
	 * Calculates the sum of elements to the right of the specified index.
	 *
	 * @param row   the integer array
	 * @param index the index from which elements are summed (exclusive)
	 * @return the sum of elements to the right of the index
	 */
	private int calculateRightSum(int[] row, int index) {
		int sum = 0;
		for (int i = index + 1; i < row.length; i++) {
			sum += row[i];
		}
		return sum;
	}

	/**
	 * Fills a single row with random integers from the specified interval.
	 *
	 * @param row        the array row to fill
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 */
	private void fillRowWithRandomValues(int[] row, int lowerBound, int upperBound) {
		for (int i = 0; i < row.length; i++) {
			row[i] = TwoDimArraySignChanger.generateRandomInteger(lowerBound, upperBound);
		}
	}

	/**
	 * Generates a random integer within the specified interval from lowerBound to
	 * upperBound inclusive.
	 *
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 * @return a random integer in the specified range
	 */
	private static int generateRandomInteger(int lowerBound, int upperBound) {
		return (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
	}

	/**
	 * Calculates the sum of the first and last elements of the entire
	 * two-dimensional array.
	 *
	 * @return the sum of the first and last elements, or 0 if the array is empty
	 */
	private int calculateFirstAndLastElementSum() {
		if (this.arrayBody.length == 0) {
			return 0;
		}
		int[] firstRow = this.arrayBody[0];
		int[] lastRow = this.arrayBody[this.arrayBody.length - 1];
		return firstRow[0] + lastRow[lastRow.length - 1];
	}

	/**
	 * Appends a formatted row of integers to the provided StringBuilder.
	 *
	 * @param builder the StringBuilder to append to
	 * @param row     the integer array row to format and append
	 */
	private void appendFormattedRow(StringBuilder builder, int[] row) {
		for (int element : row) {
			builder.append(String.format(this.FORMAT, element));
		}
		builder.append("\n");
	}

	/**
	 * Returns a string representation of the two-dimensional array. The string
	 * contains the sum of the first and last elements of the array and all rows
	 * with formatted output of elements.
	 *
	 * @return a string representation of the two-dimensional array
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sum of first and last elements: " + this.calculateFirstAndLastElementSum() + "\n");

		for (int[] row : this.arrayBody) {
			this.appendFormattedRow(builder, row);
		}
		builder.append("\n");

		return builder.toString();
	}
}