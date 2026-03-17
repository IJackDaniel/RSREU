package ru.rsreu.afonin0409;

import com.prutzkow.twodimarray.TwoDimArray;

// Преобразовать элементы строки массива так, 
// чтобы сначала располагались элементы с ненулевыми значениями
// без изменения их порядке, а затем - с нулевыми
public class A extends TwoDimArray {
	
	public A(int... sizes) throws IllegalArgumentException{
		super(sizes);
	}
	
	public void method() {
		
	}
	
	public void fillRandomValuesFromInterval(int lowerBound, int upperBound) {
		for (int[] row : super.arrayBody) {
			this.fillRowRandomValuesFromInterval(row, lowerBound, upperBound);
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
	
	private int getCountElementsOfFirstColumn() {
		return super.arrayBody.length;
	}
	
	// Add count of elements for first column
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		return "";
	}
}
