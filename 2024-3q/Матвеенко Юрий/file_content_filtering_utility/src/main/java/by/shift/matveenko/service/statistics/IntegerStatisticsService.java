package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.DataStatistics;
import by.shift.matveenko.data.StatisticsTypes;

public class IntegerStatisticsService extends DataStatistics {
    private long maxValue = Long.MIN_VALUE;
    private long minValue = Long.MAX_VALUE;
    private double sum = 0;

    public IntegerStatisticsService(StatisticsTypes statisticsTypes) {
        super(statisticsTypes);
    }

    @Override
    public void addData(String data) {
        amount++;
        if (statisticsTypes == StatisticsTypes.SHORT) {
            return;
        }
        long number = Long.parseLong(data);
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

    public long getMaxValue() {
        return maxValue;
    }

    public long getMinValue() {
        return minValue;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        String result = "Integer numbers statistics:" + System.lineSeparator();
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