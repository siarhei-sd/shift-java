package com.TestTask.statistic.statisticImpl;

import com.TestTask.statistic.Statistic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StatisticForString implements Statistic {
    private final boolean haseStat;
    private final boolean fullStat;
    private double maxLength = -999999999;
    private double minLength = 999999999;
    private long strCount = 0;

    public long getCount() {
        return strCount;
    }

    public long getStrCount() {
        return strCount;
    }

    public void setLineLength(long count) {
        this.strCount = count;
    }

    public double getMinLength() {
        return minLength;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMinLength(double minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public StatisticForString(boolean haseStat, boolean fullStat) {
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
                setLineLength(getCount() + 1);
                String lineValue = String.valueOf(sc.nextLine());
                if (lineValue.length() > getMaxLength()) {
                    setMaxLength(lineValue.length());
                }
                if (lineValue.length() < getMinLength()) {
                    setMinLength(lineValue.length());
                }
            }
            if (haseStat) {
                System.out.println("Statistics " + file.getName());
                System.out.println("elements = " + getStrCount());
                if (fullStat) {
                    printFullStringStatistic();
                }
            }
            setLineLength(0);

        } else {
            System.out.println("File not exist: " + file.getName());
        }
    }

    public void printFullStringStatistic() {
        System.out.println("Max_length = " + getMaxLength());
        System.out.println("Min_length = " + getMinLength());
        System.out.println("─────────────────────────────────────");
        System.out.println();
    }
}
