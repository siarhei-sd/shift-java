package com.shift_by;

import java.util.Collections;
import java.util.List;

public class Statistics {

    public static void printShortStats(String prefix, List<Integer> integers, List<Double> floats, List<String> strings) {
        // Вывод краткой статистики для каждого типа данных
        System.out.println(prefix + "integers.txt short statistic: elements = " + integers.size());
        System.out.println(prefix + "floats.txt short statistic: elements = " + floats.size());
        System.out.println(prefix + "strings.txt short statistic: elements = " + strings.size());
    }

    public static void printFullStats(String prefix, List<Integer> integers, List<Double> floats, List<String> strings) {
        // Вывод полной статистики для каждого типа данных
        System.out.print(prefix + "integers.txt full statistic: elements = " + integers.size() + "; ");

        if (!integers.isEmpty()) {
            int min = Collections.min(integers);
            int max = Collections.max(integers);
            long sum = integers.stream().mapToInt(Integer::intValue).sum();
            double avg = integers.stream().mapToInt(Integer::intValue).average().orElse(0);
            System.out.printf("min = %d; max = %d; sum = %d; average = %.0f;%n", min, max, sum, avg);
        } else {
            System.out.println();
        }

        System.out.print(prefix + "floats.txt full statistic: elements = " + floats.size() + "; ");

        if (!floats.isEmpty()) {
            double min = Collections.min(floats);
            double max = Collections.max(floats);
            double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
            double avg = floats.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            System.out.printf("min = %.2f; max = %.2f; sum = %.2f; average = %.2f;%n", min, max, sum, avg);
        } else {
            System.out.println();
        }

        System.out.print(prefix + "strings.txt full statistic: elements = " + strings.size() + "; ");

        if (!strings.isEmpty()) {
            int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);
            System.out.printf("min length = %d; max length = %d;%n", minLength, maxLength);
        } else {
            System.out.println();
        }
    }
}
