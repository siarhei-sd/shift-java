package ru.shift;

public class StatisticsCollector {
    private int intCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;
    private int intSum = 0;
    private double floatSum = 0.0;
    private int intMin = Integer.MAX_VALUE;
    private int intMax = Integer.MIN_VALUE;
    private double floatMin = Double.MAX_VALUE;
    private double floatMax = Double.MIN_VALUE;
    private int stringMinLength = Integer.MAX_VALUE;
    private int stringMaxLength = Integer.MIN_VALUE;

    public void addInteger(int value) {
        intCount++;
        intSum += value;
        intMin = Math.min(intMin, value);
        intMax = Math.max(intMax, value);
    }

    public void addFloat(double value) {
        floatCount++;
        floatSum += value;
        floatMin = Math.min(floatMin, value);
        floatMax = Math.max(floatMax, value);
    }

    public void addString(String value) {
        stringCount++;
        stringMinLength = Math.min(stringMinLength, value.length());
        stringMaxLength = Math.max(stringMaxLength, value.length());
    }

    public void printShortStatistics(String prefix) {
        System.out.println(prefix + "integers.txt short statistic: elements = " + intCount);
        System.out.println(prefix + "floats.txt short statistic: elements = " + floatCount);
        System.out.println(prefix + "strings.txt short statistic: elements = " + stringCount);
    }

    public void printFullStatistics(String prefix) {
        System.out.println(prefix + "integers.txt full statistic: elements = " + intCount
                + "; min = " + intMin
                + "; max = " + intMax
                + "; sum = " + intSum
                + "; average=" + (intCount == 0 ? 0 : intSum / (double) intCount));
        System.out.println(prefix + "floats.txt full statistic: elements = " + floatCount
                + "; min = " + floatMin
                + "; max = " + floatMax
                + "; sum = " + floatSum
                + "; average=" + (floatCount == 0 ? 0 : floatSum / (double) floatCount));
        System.out.println(prefix + "strings.txt full statistic: elements = " + stringCount
                + "; min = " + stringMinLength
                + "; max = " + stringMaxLength);
    }
}
