package com.TestTask.statistic.statisticImpl;


import com.TestTask.statistic.Statistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class StatisticForInteger implements Statistic {
    private final boolean fullStat;
    private final boolean haseStat;
    private int max = -999999999;
    private int min = 999999999;
    private double sum = 0;
    private long count = 0;

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

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public StatisticForInteger(boolean haseStat, boolean fullStat){
        this.haseStat = haseStat;
        this.fullStat = fullStat;
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
                int lineValue = (int)Float.parseFloat(sc.nextLine());
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
                    printFullIntStatistic();
                }
            }

            setLineLength(0);
        } else {
            System.out.println("The file has not been created!!! " + file.getName());
        }
    }

    public void printFullIntStatistic() {

        System.out.println("Min = " + getMin());
        System.out.println("Max = " + getMax());
        System.out.println("Sum = " + (int)sum);
        System.out.println("Average = " + sum / getLineCount());
        System.out.println("─────────────────────────────────────");
    }
}
