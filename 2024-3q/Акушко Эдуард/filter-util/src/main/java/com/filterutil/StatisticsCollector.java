package com.filterutil;

import java.util.*;

public class StatisticsCollector {
    private Map<String, String> statistics = new HashMap<>();

    public void collect(List<Integer> integers, List<Double> floats, List<String> strings) {
        collectIntegerStats(integers);
        collectFloatStats(floats);
        collectStringStats(strings);
    }

    private void collectIntegerStats(List<Integer> integers) {
        if (integers.isEmpty()) return;

        int min = Collections.min(integers);
        int max = Collections.max(integers);
        int sum = integers.stream().mapToInt(Integer::intValue).sum();
        double average = integers.stream().mapToInt(Integer::intValue).average().orElse(0);

        statistics.put("Integers", String.format("elements = %d; min = %d; max = %d; sum = %d; average = %.2f",
                integers.size(), min, max, sum, average));
    }

    private void collectFloatStats(List<Double> floats) {
        if (floats.isEmpty()) return;

        double min = Collections.min(floats);
        double max = Collections.max(floats);
        double sum = floats.stream().mapToDouble(Double::doubleValue).sum();
        double average = floats.stream().mapToDouble(Double::doubleValue).average().orElse(0);

        statistics.put("Floats", String.format("elements = %d; min = %.6f; max = %.6f; sum = %.6f; average = %.6f",
                floats.size(), min, max, sum, average));
    }

    private void collectStringStats(List<String> strings) {
        if (strings.isEmpty()) return;

        int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
        int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);

        statistics.put("Strings", String.format("elements = %d; min length = %d; max length = %d",
                strings.size(), minLength, maxLength));
    }

    public String getFullStatistics() {
        StringBuilder sb = new StringBuilder();
        statistics.forEach((key, value) -> sb.append(key).append(": ").append(value).append("\n"));
        return sb.toString();
    }
}
