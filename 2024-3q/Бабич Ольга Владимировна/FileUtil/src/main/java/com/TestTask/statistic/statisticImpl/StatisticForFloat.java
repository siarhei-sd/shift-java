package com.TestTask.statistic.statisticImpl;


import com.TestTask.statistic.Statistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class StatisticForFloat implements Statistic {
    private final boolean fullStat;
    private final boolean haseStat;
    private double max = -999999999;
    private double min = 999999999;
    private double sum = 0;
    private long count = 0;

    public StatisticForFloat(boolean haseStat, boolean fullStat) {
        this.haseStat = haseStat;
        this.fullStat = fullStat;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getLineCount() {
        return count;
    }

    public void setLineLength(long count) {
        this.count = count;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }


    public void getStatistic(File file) {
        if (file.exists()) {
            Scanner sc;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while (sc.hasNextLine()) {
                float lineValue = Float.parseFloat(sc.nextLine());
                setSum(getSum() + lineValue);
                setLineLength(getLineCount() + 1);
                if (lineValue > getMax()) {
                    setMax(lineValue);
                }
                if (lineValue < getMin()) {
                    setMin(lineValue);
                }
            }
            if (haseStat) {
                System.out.println("Statistics " + file.getName());
                System.out.println("elements = " + getLineCount());
                if (fullStat) {
                    printFullFloatStatistic();
                }
            }
            setLineLength(0);
        } else {
            System.out.println("The file has not been created!!! " + file.getName());
        }
    }

    public void printFullFloatStatistic() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##########");
        System.out.println("Min = " + decimalFormat.format(getMin()));
        System.out.println("Max = " + decimalFormat.format(getMax()));
        System.out.println("Sum = " + decimalFormat.format(sum));
        System.out.println("Average = " + decimalFormat.format(sum / getLineCount()));
        System.out.println("─────────────────────────────────────");
    }
}
