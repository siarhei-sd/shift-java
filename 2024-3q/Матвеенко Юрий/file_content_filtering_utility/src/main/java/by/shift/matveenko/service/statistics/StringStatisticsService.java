package by.shift.matveenko.service.statistics;

import by.shift.matveenko.data.DataStatistics;
import by.shift.matveenko.data.StatisticsTypes;

public class StringStatisticsService extends DataStatistics {
    private long shortestString = Integer.MAX_VALUE;
    private long longestString;

    public StringStatisticsService(StatisticsTypes statisticsTypes) {
        super(statisticsTypes);
    }

    @Override
    public void addData(String data) {
        amount++;
        if (statisticsTypes == StatisticsTypes.SHORT) {
            return;
        }
        if (data.length() < shortestString) {
            shortestString = data.length();
        }
        if (data.length() > longestString) {
            longestString = data.length();
        }
    }

    public long getShortestString() {
        return shortestString;
    }

    public long getLongestString() {
        return longestString;
    }

    @Override
    public String toString() {
        String result = "Strings statistics:" + System.lineSeparator();
        if (statisticsTypes == StatisticsTypes.SHORT || amount == 0) {
            return result + "amount: " + amount;
        } else {
            return result +
                    "amount: " + amount + System.lineSeparator() +
                    "shortest string: " + shortestString + System.lineSeparator() +
                    "longest string: " + longestString + "\n";
        }
    }
}