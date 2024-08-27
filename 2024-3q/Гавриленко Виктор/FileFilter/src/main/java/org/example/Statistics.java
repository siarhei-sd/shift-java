package org.example;

import java.util.List;

import static org.example.Files.*;

public class Statistics {
    private final List<String> intList;
    private final List<String> floatList;
    private final List<String> stringList;
    private final String prefix;
    private static final String FULL_STATISTIC_ELEMENTS = " full statistic: elements = ";
    private static final String SHORT_STATISTIC_ELEMENTS = " short statistic: elements = ";

    public Statistics(List<String> intList, List<String> floatList, List<String> stringList, String prefix) {
        this.intList = intList;
        this.floatList = floatList;
        this.stringList = stringList;
        this.prefix = prefix;
    }

    public void getShortStatistics() {
        if (!intList.isEmpty())
            getShortStatisticsData(INTEGER_FILE_NAME, intList);
        if (!floatList.isEmpty())
            getShortStatisticsData(FLOATS_FILE_NAME, floatList);
        if (!stringList.isEmpty())
            getShortStatisticsData(STRINGS_FILE_NAME, stringList);
    }

    private void getShortStatisticsData(String filename, List<String> list) {
        System.out.println(prefix + filename + SHORT_STATISTIC_ELEMENTS + list.size());
    }

    public void getFullStatistics() {
        if (!intList.isEmpty())
            getFullStatisticsInt(intList);
        if (!floatList.isEmpty())
            getFullStatisticsFloat(floatList);
        if (!stringList.isEmpty())
            getFullStatisticsStr(stringList);
    }

    private void getFullStatisticsInt(List<String> list) {
        List<Integer> statList = list.stream().map(Integer::parseInt).toList();
        int min = statList.get(0);
        int max = statList.get(0);
        long sumList = 0;
        for (int number : statList) {
            if (number > max) {
                max = number;
            }
            if (number < min) {
                min = number;
            }
            sumList += number;
        }
        float avr = (float) sumList / statList.size();
        System.out.println(prefix + Files.INTEGER_FILE_NAME + FULL_STATISTIC_ELEMENTS + list.size()
                + "; min = " + min
                + "; max = " + max
                + "; sum = " + sumList
                + ", average = " + avr + ";");
    }

    private void getFullStatisticsFloat(List<String> list) {
        List<Float> statList = list.stream().map(Float::parseFloat).toList();
        float min = statList.get(0);
        float max = statList.get(0);
        double sumList = 0;
        for (float number : statList) {
            if (number > max) {
                max = number;
            }
            if (number < min) {
                min = number;
            }
            sumList += number;
        }
        double avr = sumList / statList.size();
        System.out.println(prefix + Files.FLOATS_FILE_NAME + FULL_STATISTIC_ELEMENTS + list.size()
                + "; min = " + min
                + "; max = " + max
                + "; sum = " + sumList
                + ", average = " + avr + ";");
    }

    private void getFullStatisticsStr(List<String> list) {
        int min = list.get(0).length();
        int max = list.get(0).length();
        for (String str : list) {
            if (str.length() > max) {
                max = str.length();
            }
            if (str.length() < min) {
                min = str.length();
            }
        }
        System.out.println(prefix + Files.STRINGS_FILE_NAME + FULL_STATISTIC_ELEMENTS + list.size()
                + "; length of the longest string = " + max
                + "; length of the shortest string = " + min + ";");
    }
}