package com.app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static boolean needShortStats = false, needFullStats = false, needAppend = false;
    static String resultsPrefix = "", resultsPath = "";

    private static String getType(String str) {
        String type;
        try {
            Integer.parseInt(str);
            type = "Integer";
        } catch (NumberFormatException e1) {
            try {
                Float.parseFloat(str);
                type = "Float";
            } catch (NumberFormatException e2) {
                type = "String";
            }
        }
        return type;
    }

    private static void printStatsForIntegers(ArrayList<Integer> integers) throws Exception{
        if (integers.size() > 0) {
            int min = integers.get(0), max = integers.get(0), sum = 0;
            for (int num : integers) {
                min = Math.min(num, min);
                max = Math.max(num, max);
                sum += num;
            }
            System.out.printf("%sintegers.txt elements = %d; min = %d; max = %d; " +
                            "sum = %d; average = %.3f \n",
                    resultsPrefix, integers.size(), min, max, sum, Double.valueOf(sum / integers.size()));
        }
    }

    private static void printStatsForFloats(ArrayList<Float> floats) {
        if (floats.size() > 0) {
            float min = floats.get(0), max = floats.get(0), sum = 0;
            for (float num : floats) {
                min = Math.min(num, min);
                max = Math.max(num, max);
                sum += num;
            }
            System.out.printf("%sfloats.txt elements = %d; min = %f; max = %f; " +
                            "sum = %f; average = %.3f \n",
                    resultsPrefix, floats.size(), min, max, sum, sum / floats.size());
        }
    }

    private static void printStatsForStrings(ArrayList<String> strings) {
        if (strings.size() > 0) {
            int minLength = strings.get(0).length(), maxLength = strings.get(0).length();
            for (String str : strings) {
                minLength = Math.min(str.length(), minLength);
                maxLength = Math.max(str.length(), maxLength);
            }
            System.out.printf("%sstrings.txt elements = %d; minLength = %d; maxLength = %d \n",
                    resultsPrefix, strings.size(), minLength, maxLength);
        }
    }

    private static void writeInFile(ArrayList values, String fileName) throws IOException {
        if (values.size() > 0) {
            File file;
            if (resultsPath == "") {
                file = new File(resultsPrefix + fileName);
            } else {
                File directory = new File(resultsPath);
                file = new File(directory, resultsPrefix + fileName);
            }
            FileWriter writer = new FileWriter(file, needAppend);
            for (var value : values) {
                writer.append(value + "\n");
            }
            writer.flush();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> fileNames = new ArrayList<>();
        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<Float> floats = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();

        try {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-o":
                        resultsPath = args[i + 1];
                        if (resultsPath.charAt(0) == '/') {
                            resultsPath = resultsPath.substring(1);
                        }
                        File directory = new File(resultsPath);
                        if (!directory.exists()) {
                            System.err.println("Results path " + resultsPath + " is not found.");
                            resultsPath = "";
                        }
                        i++;
                        break;
                    case "-a":
                        needAppend = true;
                        break;
                    case "-p":
                        resultsPrefix = args[i + 1];
                        i++;
                        break;
                    case "-s":
                        needShortStats = true;
                        break;
                    case "-f":
                        needFullStats = true;
                        break;
                    default:
                        fileNames.add(args[i]);
                }
            }


            for (String fileName : fileNames) {
                File file = new File(fileName);
                if (!file.exists()) {
                    System.out.println("File " + fileName + " is not found.");
                } else {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String value = scanner.nextLine();

                        switch (getType(value)) {
                            case "Integer":
                                integers.add(Integer.valueOf(value));
                                break;
                            case "Float":
                                floats.add(Float.valueOf(value));
                                break;
                            default:
                                strings.add(value);
                                break;
                        }
                    }
                }
            }

            writeInFile(integers, "integers.txt");
            writeInFile(floats, "floats.txt");
            writeInFile(strings, "strings.txt");

            if (integers.size() > 0 || floats.size() > 0 || strings.size() > 0) {
                if (needFullStats) {
                    System.out.println("Full statistics: ");
                    printStatsForIntegers(integers);
                    printStatsForFloats(floats);
                    printStatsForStrings(strings);
                } else if (needShortStats) {
                    System.out.println("Short statistics: ");
                    System.out.printf("%sintegers.txt elements = %d \n", resultsPrefix, integers.size());
                    System.out.printf("%sfloats.txt elements = %d \n", resultsPrefix, floats.size());
                    System.out.printf("%sstrings.txt elements = %d \n", resultsPrefix, strings.size());
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
