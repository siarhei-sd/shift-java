package org.dezzzl.statistics;

public class NumberFullStatistics<T extends Number> extends ShortStatistics {
    private T minNumber;
    private T maxNumber;
    private double sumOfNumbers;
    private double averageNumber;


    @Override
    public void updateStatistics(Object data) {
        if (data == null) return;
        if (data instanceof Number) {
            @SuppressWarnings("unchecked")
            T number = (T) data;
            updateMinNumber(number);
            updateMaxNumber(number);
            updateSumOfNumbers(number);
        }
        this.numberOfElements++;
    }

    @Override
    public void printStatistics() {
        updateAverageNumber();
        String fullStatistic = String.format(
                "%s full statistic: elements = %d; min = %s; max = %s; sum = %s; average = %.2f",
                this.getPathToFile(),
                this.numberOfElements,
                this.minNumber,
                this.maxNumber,
                this.sumOfNumbers,
                this.averageNumber
        );
        System.out.println(fullStatistic);
    }

    private void updateMinNumber(T number) {
        if (this.minNumber == null || number.doubleValue() < this.minNumber.doubleValue()) this.minNumber = number;
    }

    private void updateMaxNumber(T number) {
        if (this.maxNumber == null || number.doubleValue() > this.maxNumber.doubleValue()) this.maxNumber = number;
    }

    private void updateSumOfNumbers(T number) {
        this.sumOfNumbers += number.doubleValue();
    }

    private void updateAverageNumber() {
        this.averageNumber = this.sumOfNumbers / this.numberOfElements;
    }


}
