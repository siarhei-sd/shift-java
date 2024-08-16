package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.DataStatistics;
import by.shift.matveenko.data.StatisticsTypes;

public class DoubleStatisticsService extends DataStatistics {
    private double maxValue = Double.MIN_VALUE;
    private double minValue = Double.MAX_VALUE;
    private double sum = 0;

    public DoubleStatisticsService(StatisticsTypes statisticsTypes) {
        super(statisticsTypes);
    }

    @Override
    public void addData(String data) {
        amount++;
        if (statisticsTypes == StatisticsTypes.SHORT) {
            return;
        }
        double number = Double.parseDouble(data);
        sum += number;
        if (number > maxValue) {
            maxValue = number;
        }
        if (number < minValue) {
            minValue = number;
        }
    }

    public double average() {
        if (amount == 0) {
            return 0;
        } else {
            return sum / amount;
        }
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        String result = "Floating-point numbers statistics:" + System.lineSeparator();
        if (statisticsTypes == StatisticsTypes.SHORT || amount == 0) {
            return result + "amount: " + amount;
        } else {
            return result +
                    "amount: " + amount + System.lineSeparator() +
                    "min value: " + minValue + System.lineSeparator() +
                    "max value: " + maxValue + System.lineSeparator() +
                    "sum: " + sum + System.lineSeparator() +
                    "average: " + average() + "\n";
        }
    }
}