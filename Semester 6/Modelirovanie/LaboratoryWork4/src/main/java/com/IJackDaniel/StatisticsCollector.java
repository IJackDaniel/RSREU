package com.IJackDaniel;

public class StatisticsCollector {
    private int countOfElements;
    private double sumOfElements;
    private double sumOfSquareElements;
    private double sumOfCubedElements;

    public StatisticsCollector() {
        this.countOfElements = 0;
        this.sumOfElements = 0;
        this.sumOfSquareElements = 0;
        this.sumOfCubedElements = 0;
    }

    public void addValue(double value) {
        this.countOfElements++;
        this.sumOfElements += value;
        this.sumOfSquareElements += Math.pow(value, 2);
        this.sumOfCubedElements += Math.pow(value, 3);
    }

    public int getCountOfElements() {
        return this.countOfElements;
    }

    public double getExpectedValue() {
        return this.sumOfElements * (1.0 / this.countOfElements);
    }

    public double getDispersion() {
        return this.sumOfSquareElements * (1.0 / this.countOfElements) - Math.pow(getExpectedValue(), 2);
    }

    public double getSecondStartMoment() {
        return this.sumOfSquareElements * (1.0 / this.countOfElements);
    }

    public double getThirdStartMoment() {
        return this.sumOfCubedElements * (1.0 / this.countOfElements);
    }
}
