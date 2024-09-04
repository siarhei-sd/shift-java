package com.filefilterutility;

public class Statistics {
    private final DataType type;
    private final boolean fullStatistics;

    private int count;
    private int minInt, maxInt, sumInt;
    private double minDouble, maxDouble, sumDouble;
    private int minString, maxString;

    public Statistics(DataType type, boolean fullStatistics) {
        this.type = type;
        this.fullStatistics = fullStatistics;
        reset();
    }

    private void reset() {
        count = 0;
        minInt = Integer.MAX_VALUE;
        maxInt = Integer.MIN_VALUE;
        sumInt = 0;
        minDouble = Double.MAX_VALUE;
        maxDouble = Double.MIN_VALUE;
        sumDouble = 0.0;
        minString = Integer.MAX_VALUE;
        maxString = 0;
    }

    public void updateStatistics(int value) {
        count++;
        sumInt += value;
        if (value < minInt){
            minInt = value;
        }
        if (value > maxInt){
            maxInt = value;
        }
    }

    public void updateStatistics(double value) {
        count++;
        sumDouble += value;
        if (value < minDouble) {
            minDouble = value;
        }
        if (value > maxDouble){
            maxDouble = value;
        }
    }

    public void updateStatistics(String value) {
        count++;
        int length = value.length();
        if (length < minString){
            minString = length;
        }
        if (length > maxString){
            maxString = length;
        }
    }

    public void printStatistics(String fileName) {
        System.out.print(fileName + " ");
        System.out.print("short statistic: elements = " + count);
        if (fullStatistics) {
            if (type == DataType.INTEGER) {
                System.out.print("; min = " + minInt + "; max = " + maxInt + "; sum = " + sumInt);
                System.out.printf("; average = %.2f%n", count > 0 ? (double) sumInt / count : 0.0);
            } else if (type == DataType.FLOAT) {
                System.out.print("; min = " + minDouble + "; max = " + maxDouble + "; sum = " + sumDouble);
                System.out.printf("; average = %.2f%n", count > 0 ? sumDouble / count : 0.0);
            } else if (type == DataType.STRING) {
                System.out.print("; min length = " + minString + "; max length = " + maxString);
                System.out.println();
            }
        } else {
            System.out.println();
        }
    }
}
