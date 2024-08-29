package com.example;

public class StatisticsCollector {
    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    private int minInteger = Integer.MAX_VALUE;
    private int maxInteger = Integer.MIN_VALUE;
    private long integerSum = 0;

    private double minFloat = Double.MAX_VALUE;
    private double maxFloat = Double.MIN_VALUE;
    private double floatSum = 0;

    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = 0;

    public void addInteger(int value) {
        integerCount++;
        integerSum += value;
        minInteger = Math.min(minInteger, value);
        maxInteger = Math.max(maxInteger, value);
    }

    public void addFloat(double value) {
        floatCount++;
        floatSum += value;
        minFloat = Math.min(minFloat, value);
        maxFloat = Math.max(maxFloat, value);
    }

    public void addString(String value) {
        stringCount++;
        int length = value.length();
        minStringLength = Math.min(minStringLength, length);
        maxStringLength = Math.max(maxStringLength, length);
    }

    public String getShortStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Short statistics:\n");
        sb.append("Integers: ").append(integerCount).append("\n");
        sb.append("Floats: ").append(floatCount).append("\n");
        sb.append("Strings: ").append(stringCount).append("\n");
        return sb.toString();
    }

    public String getFullStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("Full statistics:\n");
        
        sb.append("Integers: count=").append(integerCount);
        if (integerCount > 0) {
            sb.append(", min=").append(minInteger)
              .append(", max=").append(maxInteger)
              .append(", sum=").append(integerSum)
              .append(", average=").append((double) integerSum / integerCount);
        }
        sb.append("\n");

        sb.append("Floats: count=").append(floatCount);
        if (floatCount > 0) {
            sb.append(", min=").append(minFloat)
              .append(", max=").append(maxFloat)
              .append(", sum=").append(floatSum)
              .append(", average=").append(floatSum / floatCount);
        }
        sb.append("\n");

        sb.append("Strings: count=").append(stringCount);
        if (stringCount > 0) {
            sb.append(", shortest length=").append(minStringLength)
              .append(", longest length=").append(maxStringLength);
        }
        sb.append("\n");

        return sb.toString();
    }
}