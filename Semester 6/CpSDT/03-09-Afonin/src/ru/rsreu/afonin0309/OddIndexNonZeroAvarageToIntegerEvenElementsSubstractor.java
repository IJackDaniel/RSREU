package ru.rsreu.afonin0309;

public class OddIndexNonZeroAvarageToIntegerEvenElementsSubstractor {
	private NumbersList numbers;
	
	public OddIndexNonZeroAvarageToIntegerEvenElementsSubstractor(NumbersList numbers) {
		this.numbers = numbers;
	}
	
	public void subtrackEvenIndexAverageFromOddIndex(){
		int avg = this.averageOfNonZeroValuesWithEvenIndex();
		this.substractValueFromOddIndexes(avg);
	}
	
	public String getData() {
		return this.numbers.toString();
	}
	
	private int averageOfNonZeroValuesWithEvenIndex() {
		int count = 0;
		int sum = 0;
		for (int i = 0; i < numbers.getLength(); i += 2) {
			int value = numbers.get(i);
			if (value != 0) {
				count++;
				sum += value;
			}
		}
		
		return (int)(sum / count);
	}
	
	private void substractValueFromOddIndexes(int number) {
		for (int i = 1; i < numbers.getLength(); i += 2) {
			numbers.set(i, numbers.get(i) - number);
		}
	}
}
