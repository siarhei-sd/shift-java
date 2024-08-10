package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class StatsCollector {
    private Map<DataType, List<String>> dataMap;

    public StatsCollector() {
        this.dataMap = new HashMap<>();
        for (DataType type : DataType.values()) {
            dataMap.put(type, new ArrayList<>());
        }
    }

    public void collect(DataType type, String data) {
        dataMap.get(type).add(data);
    }

    public void printStatistics(String statisticsType) {
        for (DataType type : DataType.values()) {
            List<String> dataList = dataMap.get(type);
            int count = dataList.size();

            switch (statisticsType) {
                case "-s":
                    System.out.println(type.name().toLowerCase() + " short statistic: elements = " + count);
                    break;

                case "-f":
                    System.out.println(type.name().toLowerCase() + " full statistic:");

                    if (type == DataType.INTEGER) {
                        List<Integer> integers = dataList.stream()
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());

                        int min = integers.stream().min(Integer::compare).orElse(Integer.MAX_VALUE);
                        int max = integers.stream().max(Integer::compare).orElse(Integer.MIN_VALUE);
                        int sum = integers.stream().mapToInt(Integer::intValue).sum();
                        double average = integers.stream().mapToInt(Integer::intValue).average().orElse(Double.NaN);

                        System.out.println("  - elements = " + count);
                        System.out.println("  - minimum value = " + min);
                        System.out.println("  - maximum value = " + max);
                        System.out.println("  - sum = " + sum);
                        System.out.println("  - average = " + average);

                    } else if (type == DataType.FLOAT) {
                        List<Float> floats = dataList.stream()
                                .map(Float::parseFloat)
                                .collect(Collectors.toList());

                        float min = floats.stream().min(Float::compare).orElse(Float.MAX_VALUE);
                        float max = floats.stream().max(Float::compare).orElse(Float.MIN_VALUE);
                        float sum = (float) floats.stream().mapToDouble(Float::doubleValue).sum();
                        double average = floats.stream().mapToDouble(Float::doubleValue).average().orElse(Double.NaN);

                        System.out.println("  - elements = " + count);
                        System.out.println("  - minimum value = " + min);
                        System.out.println("  - maximum value = " + max);
                        System.out.println("  - sum = " + sum);
                        System.out.println("  - average = " + average);

                    } else if (type == DataType.STRING) {
                        List<String> strings = dataList;

                        int minLength = strings.stream().mapToInt(String::length).min().orElse(0);
                        int maxLength = strings.stream().mapToInt(String::length).max().orElse(0);
                        double averageLength = strings.stream().mapToInt(String::length).average().orElse(0);

                        System.out.println("  - elements = " + count);
                        System.out.println("  - shortest string length = " + minLength);
                        System.out.println("  - longest string length = " + maxLength);
                        System.out.println("  - average string length = " + averageLength);
                    }
                    break;

                default:
                    System.out.println(type.name().toLowerCase() + " statistic: elements = " + count);
                    break;
            }
        }
    }

}
