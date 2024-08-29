package Test;

import java.util.*;

public class Statistic {
    private int count;
    private Double min;
    private Double max;
    private Double sum;
    private Double average;
    private Integer minLength;
    private Integer maxLength;

    public Statistic() {
        this.count = 0;
        this.sum = 0.0;
    }

    public void addNumber(double value) {
        count++;
        if (min == null || value < min) min = value;
        if (max == null || value > max) max = value;
        sum += value;
        average = sum / count;
    }

    public void addString(String value) {
        count++;
        int length = value.length();
        if (minLength == null || length < minLength) minLength = length;
        if (maxLength == null || length > maxLength) maxLength = length;
    }

    public void printShortStatistic(String fileName) {
        System.out.println(fileName + " short statistic: elements = " + count);
    }

    public void printFullStatistic(String fileName, DataType type) {
        System.out.print(fileName + " full statistic: elements = " + count);
        if (type == DataType.INTEGER || type == DataType.FLOAT) {
            System.out.println("min = " + min + "; max = " + max + "; sum = " + sum + ", average = " + average);
        } else if (type == DataType.STRING) {
            System.out.println("min length = " + minLength + "; max length = " + maxLength);
        }
    }

    public int getCount() {
        return count;
    }
}