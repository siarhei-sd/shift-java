package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final List<String> inputFiles = new ArrayList<>();
    private static final List<BigDecimal> floats = new ArrayList<>();
    private static final List<BigInteger> integers = new ArrayList<>();
    private static final List<String> strings = new ArrayList<>();
    private static final List<BigDecimal> copyFloats = new ArrayList<>();
    private static final List<BigInteger> copyIntegers = new ArrayList<>();
    private static final List<String> copyStrings = new ArrayList<>();
    private static String prefix = "";
    private static String outputPath = "";
    private static boolean isAppendToFile = false;
    private static boolean needShortStats = false;
    private static boolean needFullStats = false;
    private static boolean isManuallyChanged = false;

    public static void main(String[] args) {

        for (int i = 0; i < args.length; i++) {
            if (args[i].contains(".txt")) {
                inputFiles.add(args[i]);
            }
            switch (args[i]) {
                case "-o":
                    outputPath = args[i + 1] + File.separator;
                    break;
                case "-p":
                    prefix = args[i + 1];
                    break;
                case "-s":
                    needShortStats = true;
                    break;
                case "-f":
                    needFullStats = true;
                    break;
                case "-a":
                    isAppendToFile = true;
                    break;
            }
        }

        for (String file : inputFiles) {
            try (Scanner scan = new Scanner(new File(file))) {
                while (scan.hasNextLine()) {
                    String line = scan.nextLine();
                    if (isFloat(line)) {
                        String replacedDot = line;
                        if (line.contains(",")) {
                            replacedDot = line.replace(",", ".");
                        }
                        floats.add(new BigDecimal(replacedDot));
                    } else if (isInteger(line)) {
                        integers.add(new BigInteger(line));
                    } else {
                        strings.add(line);
                    }
                }
                copyFloats.addAll(floats);
                copyIntegers.addAll(integers);
                copyStrings.addAll(strings);
            } catch (IOException e) {
                System.out.println("Error reading input file: " + e.getMessage());
                return;
            }
        }
        if (isAppendToFile && (needShortStats || needFullStats)) {
            saveOldInfoForStats();
        }
        writeInFile(floats, "floats.txt");
        writeInFile(integers, "integers.txt");
        writeInFile(strings, "strings.txt");
        calculateStatistic();
    }

    private static boolean isFloat(String line) {
        return line.matches("[-+]?\\d+[.,]\\d+([eE][-+]?\\d+)?");
    }

    private static boolean isInteger(String line) {
        return line.matches("[-+]?\\d+");
    }

    private static void writeInFile(
            Collection<?> data,
            String file) {
        if (!data.isEmpty()) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(
                    outputPath + prefix + file,
                    isAppendToFile))) {
                for (Object item : data) {
                    writer.println(item);
                }
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    private static void saveOldInfoForStats() {
        List<String> oldFloats = getOldInfo(outputPath + prefix + "floats.txt");
        List<String> oldIntegers = getOldInfo(outputPath + prefix + "integers.txt");
        List<String> oldStrings = getOldInfo(outputPath + prefix + "strings.txt");
        if (!oldFloats.isEmpty()) {
            try {
                copyFloats.addAll(oldFloats
                        .stream()
                        .map(BigDecimal::new)
                        .collect(Collectors.toList()));
            } catch (NumberFormatException e) {
                isManuallyChanged = true;
                System.out.println("A line with an another data type was added to the "
                        + prefix
                        + "floats.txt. Check it and try again.");
            }
        }
        if (!oldIntegers.isEmpty()) {
            try {
                copyIntegers.addAll(oldIntegers
                        .stream()
                        .map(BigInteger::new)
                        .collect(Collectors.toList()));
            } catch (NumberFormatException e) {
                isManuallyChanged = true;
                System.out.println("A line with an another data type was added to the "
                        + prefix
                        + "integers.txt. Check it and try again!");
            }
        }
        if (!oldStrings.isEmpty()) {
            copyStrings.addAll(oldStrings);
        }
    }

    private static List<String> getOldInfo(String file) {
        List<String> lines = new ArrayList<>();
        try (Scanner scan = new Scanner(new File(file))) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                lines.add(line);
            }
        } catch (IOException ignored) {
        }
        return lines;
    }

    private static void calculateStatistic() {
        if (!isManuallyChanged) {
            if (needShortStats) {
                System.out.println(prefix
                        + "floats.txt short statistic: elements = "
                        + copyFloats.size());
                System.out.println(prefix
                        + "integers.txt short statistic: elements = "
                        + copyIntegers.size());
                System.out.println(prefix
                        + "strings.txt short statistic: elements = "
                        + copyStrings.size());
            }
            if (needFullStats) {
                if (!copyFloats.isEmpty()) {
                    BigDecimal min = copyFloats
                            .stream()
                            .min(BigDecimal::compareTo)
                            .orElse(new BigDecimal("0.0"));
                    BigDecimal max = copyFloats
                            .stream()
                            .max(BigDecimal::compareTo)
                            .orElse(new BigDecimal("0.0"));
                    BigDecimal sum = copyFloats.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal average = sum.divide(
                            BigDecimal.valueOf(copyFloats.size()),
                            RoundingMode.HALF_UP);
                    System.out.printf(
                            "%sfloats.txt full statistic: elements = %d; min = %s; max = %s; sum = %s; average = %s\n",
                            prefix,
                            copyFloats.size(),
                            min.toPlainString(),
                            max.toPlainString(),
                            sum.toPlainString(),
                            average.toPlainString());
                } else {
                    System.out.println(prefix + "floats.txt is empty.");
                }
                if (!copyIntegers.isEmpty()) {
                    BigInteger min = Collections.min(copyIntegers);
                    BigInteger max = Collections.max(copyIntegers);
                    BigInteger sum = copyIntegers.stream().reduce(BigInteger.ZERO, BigInteger::add);
                    BigDecimal average = new BigDecimal(sum).divide(
                            BigDecimal.valueOf(copyIntegers.size()),
                            RoundingMode.HALF_UP
                    );
                    System.out.printf(
                            "%sintegers.txt full statistic: elements = %d; min = %d; max = %d; sum = %d; average = %s\n",
                            prefix,
                            copyIntegers.size(),
                            min,
                            max,
                            sum,
                            average.toPlainString());
                } else {
                    System.out.println(prefix + "integers.txt is empty.");
                }
                if (!copyStrings.isEmpty()) {
                    int shortestLength = copyStrings
                            .stream()
                            .mapToInt(String::length)
                            .min()
                            .orElse(0);
                    int longestLength = copyStrings
                            .stream()
                            .mapToInt(String::length)
                            .max()
                            .orElse(0);
                    System.out.printf(
                            "%sstrings.txt full statistic: elements = %d; shortest string length = %d; longest string length = %d\n",
                            prefix,
                            copyStrings.size(),
                            shortestLength,
                            longestLength);
                } else {
                    System.out.println(prefix + "strings.txt is empty.");
                }
            }
        } else {
            System.out.println(
                    "Can`t show statistic because data of a different type was manually added to one of the results files.");
        }
    }
}