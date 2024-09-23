package com.filter.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Statistics {
    private final List<Double> numericData = new ArrayList<>();
    private int stringCount = 0;
    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void addNumericValue(double value) {
        numericData.add(value);
    }

    public void addStringValue(String value) {
        stringCount++;
        int length = value.length();
        if (length < minStringLength) {
            minStringLength = length;
        }
        if (length > maxStringLength) {
            maxStringLength = length;
        }
    }

    public int getStringCount() {
        return stringCount;
    }

    public int getMinStringLength() {
        return stringCount > 0 ? minStringLength : 0;
    }

    public int getMaxStringLength() {
        return stringCount > 0 ? maxStringLength : 0;
    }

    public int getNumericCount() {
        return numericData.size();
    }

    public OptionalDouble getMinValue() {
        return numericData.stream().mapToDouble(Double::doubleValue).min();
    }

    public OptionalDouble getMaxValue() {
        return numericData.stream().mapToDouble(Double::doubleValue).max();
    }

    public double getSum() {
        return numericData.stream().mapToDouble(Double::doubleValue).sum();
    }

    public OptionalDouble getAverage() {
        return numericData.stream().mapToDouble(Double::doubleValue).average();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numeric data count: ").append(getNumericCount()).append("\n");
        if (getNumericCount() > 0) {
            sb.append("Min value: ").append(getMinValue().orElse(Double.NaN)).append("\n");
            sb.append("Max value: ").append(getMaxValue().orElse(Double.NaN)).append("\n");
            sb.append("Sum: ").append(getSum()).append("\n");
            sb.append("Average: ").append(getAverage().orElse(Double.NaN)).append("\n");
        }
        sb.append("String data count: ").append(getStringCount()).append("\n");
        if (getStringCount() > 0) {
            sb.append("Min string length: ").append(getMinStringLength()).append("\n");
            sb.append("Max string length: ").append(getMaxStringLength()).append("\n");
        }
        return sb.toString();
    }
}
