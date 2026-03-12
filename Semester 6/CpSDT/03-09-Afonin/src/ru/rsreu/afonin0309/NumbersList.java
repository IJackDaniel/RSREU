package ru.rsreu.afonin0309;

public class NumbersList {
	private int[] numbers;
	
	public NumbersList(int... numbers) {
		this.numbers = numbers.clone();
	}
	
	public int getLength() {
		return this.numbers.length;
	}
	
	public int get(int index) {
		if (this.checkIndexOutOfBounds(index)) {
			throw new IndexOutOfBoundsException();
		}
		return this.numbers[index];
	}
	
	public void set(int index, int value) {
		if (this.checkIndexOutOfBounds(index)) {
			throw new IndexOutOfBoundsException();
		}
		this.numbers[index] = value;
	}
	
	private boolean checkIndexOutOfBounds(int index) {
		return index < 0 || index > this.numbers.length;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		for (int i = 0; i < this.numbers.length; i++) {
			stringBuilder.append(this.get(i));
			if (i < this.numbers.length - 1) {
				stringBuilder.append(", ");
			}
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}
}
