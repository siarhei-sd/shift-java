package com.sheremet.shift.statistics;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics {
    private static final Logger logger = LogManager.getLogger(Statistics.class);

    public static void printStatistics(List<String> integers,
                                       List<String> floats,
                                       List<String> strings,
                                       boolean fullStats,
                                       boolean shortStats) {

        if (fullStats) {
            printFullStatistics(integers, "integers");
            printFullStatistics(floats, "floats");
            printFullStatistics(strings, "strings");
        } else if (shortStats) {
            printShortStatistics(integers, "integers");
            printShortStatistics(floats, "floats");
            printShortStatistics(strings, "strings");
        }
    }

    private static void printShortStatistics(List<String> data, String type) {
        logger.info(type + " short stat: elements = " + data.size());
        System.out.println(type + " short stat: elements = " + data.size());
    }

    private static void printFullStatistics(List<String> data, String type) {
        if (data.isEmpty()) {
            logger.info(type + " full stat: elements = 0");
            System.out.println(type + " full stat: elements = 0");
            return;
        }

        if (type.equals("integers") || type.equals("floats")) {
            List<Double> numbers = new ArrayList<>();
            for (String s : data) {
                numbers.add(Double.parseDouble(s));
            }
            double min = Collections.min(numbers);
            double max = Collections.max(numbers);
            double sum = numbers.stream().mapToDouble(Double::doubleValue).sum();

            double average = sum / numbers.size();

            logger.info(type + " full stat: \nelements = " + numbers.size() + "\n" +
                    "min = " + min + "\n" +
                    "max = " + max + "\n" +
                    "sum = " + sum + "\n" +
                    "average = " + average);
            System.out.println(type + " full stat: \nelements = " + numbers.size() + "\n" +
                    "min = " + min + "\n" +
                    "max = " + max + "\n" +
                    "sum = " + sum + "\n" +
                    "average = " + average);
        } else {
            int minLength = data.stream().mapToInt(String::length).min().orElse(0);
            int maxLength = data.stream().mapToInt(String::length).max().orElse(0);
            logger.info(type + " full stat: \nelements = " + data.size() + "\n" +
                    "min length = " + minLength + "\n" +
                    "max length = " + maxLength + "\n");

            System.out.println(type + " full stat: \nelements = " + data.size() + "\n" +
                    "min length = " + minLength + "\n" +
                    "max length = " + maxLength + "\n");
        }
    }
}
