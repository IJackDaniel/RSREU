package ru.rsreu.afonin0409;

import com.prutzkow.twodimarray.TwoDimArray;

public class ZerosToEndShiftingTwoDimArray extends TwoDimArray {
	
	public ZerosToEndShiftingTwoDimArray(int... sizes) throws IllegalArgumentException{
		super(sizes);
	}
	
	public void moveZerosToEndForListsInTwoDimensionArray() {
		for (int[] row : this.arrayBody) {
			this.moveZerosToEndInIntegerList(row);
		}
	}
	
	public void fillRandomValuesFromInterval(int lowerBound, int upperBound) {
		for (int[] row : this.arrayBody) {
			this.fillRowRandomValuesFromInterval(row, lowerBound, upperBound);
		}
	}
	
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
	
	private void fillIntegerListByZeroStartsFromIndex(int startIndex, int[] integers) {
		int currentIndex = startIndex;
		while (currentIndex < integers.length) {
			integers[currentIndex] = 0;
			currentIndex++;
		}
	}
	
	private void fillRowRandomValuesFromInterval(int[] row, int lowerBound, int upperBound) {
		for (int i = 0; i < row.length; i++) {
			row[i] = getRandomIntegerFromIntercal(lowerBound, upperBound);
		}
	}
	
	private int getRandomIntegerFromIntercal(int lowerBound, int upperBound) {
		return (int) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
	}
	
	private void addArrayToStringBuilderWithFormat(StringBuilder builder, int[] integers) {
		for (int element : integers) {
			builder.append(String.format(this.FORMAT, element));
		}
		builder.append("\n");
	}
	
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
