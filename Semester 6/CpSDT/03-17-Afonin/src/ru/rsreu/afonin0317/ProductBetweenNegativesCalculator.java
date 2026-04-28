package ru.rsreu.afonin0317;

public class ProductBetweenNegativesCalculator {
	private static final int MINIMUM_NEGATIVE_COUNT = 2;
	private static final int FIRST_NEGATIVE_INDEX = 1;
	private static final int SECOND_NEGATIVE_INDEX = 2;
	private static final int INITIAL_PRODUCT_VALUE = 1;

	private Numbers numbers;
	private int firstNegativePosition;
	private int secondNegativePosition;
	private int negativeCount;

	public ProductBetweenNegativesCalculator(Numbers numbers) {
		this.numbers = numbers;
		this.negativeCount = 0;
		this.findNegativePositions();
	}

	public int calculateProduct() {
		int product = ProductBetweenNegativesCalculator.INITIAL_PRODUCT_VALUE;

		for (int i = this.firstNegativePosition + 1; i < this.secondNegativePosition; i++) {
			product *= this.numbers.get(i);
		}

		return product;
	}

	public boolean hasEnoughNegatives() {
		return this.negativeCount >= ProductBetweenNegativesCalculator.MINIMUM_NEGATIVE_COUNT;
	}

	public boolean isDistanceBetweenFirstAndSecondNegativesMinimal() {
		return this.secondNegativePosition - this.firstNegativePosition <= 1;
	}

	private void findNegativePositions() {
		for (int i = 0; i < this.numbers.getLength(); i++) {
			if (this.isNegativeElement(i)) {
				this.negativeCount++;
				this.updateNegativePosition(i);
			}
		}
	}

	private boolean isNegativeElement(int index) {
		return this.numbers.get(index) < 0;
	}

	private void updateNegativePosition(int index) {
		if (this.negativeCount == ProductBetweenNegativesCalculator.FIRST_NEGATIVE_INDEX) {
			this.firstNegativePosition = index;
		} else if (this.negativeCount == ProductBetweenNegativesCalculator.SECOND_NEGATIVE_INDEX) {
			this.secondNegativePosition = index;
		}
	}
}