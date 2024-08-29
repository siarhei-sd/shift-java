package com.example;

import java.util.ArrayList;
import java.util.List;

public class DataFilter {
    private final List<Integer> integers = new ArrayList<>();
    private final List<Double> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    private final StatisticsCollector statisticsCollector;

    public DataFilter(StatisticsCollector statisticsCollector) {
        this.statisticsCollector = statisticsCollector;
    }

    public void processLine(String line) {
        line = line.trim();
        if (isInteger(line)) {
            int value = Integer.parseInt(line);
            integers.add(value);
            statisticsCollector.addInteger(value);
        } else if (isFloat(line)) {
            double value = Double.parseDouble(line);
            floats.add(value);
            statisticsCollector.addFloat(value);
        } else {
            strings.add(line);
            statisticsCollector.addString(line);
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Integer> getIntegers() {
        return integers;
    }

    public List<Double> getFloats() {
        return floats;
    }

    public List<String> getStrings() {
        return strings;
    }
}