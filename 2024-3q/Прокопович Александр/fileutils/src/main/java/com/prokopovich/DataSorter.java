package com.prokopovich;

import java.io.*;
import java.util.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class DataSorter {
    public static void main(String[] args) {

        String jarDir = "";

        try {
            jarDir = Paths.get(DataSorter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent().toString();
        } catch (URISyntaxException e) {

        }

        String outputPath = jarDir;
        String prefix = "";
        boolean appendMode = false;
        boolean fullStats = false;
        boolean shortStats = false;

        List<String> inputFiles = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                    } else {
                        System.err.println("Опция -o требует аргумент");
                        return;
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.err.println("Опция -p требует аргумент");
                        return;
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    shortStats = true;
                    break;
                case "-f":
                    fullStats = true;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        String intFileName = outputPath + File.separator + prefix + "integers.txt";
        String floatFileName = outputPath + File.separator + prefix + "floats.txt";
        String stringFileName = outputPath + File.separator + prefix + "strings.txt";

        List<String> integers = new ArrayList<>();
        List<String> floats = new ArrayList<>();
        List<String> strings = new ArrayList<>();

        DataStats stats = new DataStats();


        for (String fileName : inputFiles) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "Cp1251"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    classifyData(line, integers, floats, strings, stats);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + fileName);
                //e.printStackTrace();
            }
        }

        FileWriter.writeToFile(intFileName, integers, appendMode, false);
        FileWriter.writeToFile(floatFileName, floats, appendMode, false);
        FileWriter.writeToFile(stringFileName, strings, appendMode, false);

        if (shortStats) {
            System.out.println(stats.getShortStats());
        }

        if (fullStats) {
            System.out.println(stats.getFullStats());
        }
    }

    private static void classifyData(String data, List<String> integers, List<String> floats, List<String> strings, DataStats stats) {
        if (isInteger(data)) {
            integers.add(data);
            stats.updateIntStats(data);
        } else if (isFloat(data)) {
            floats.add(data);
            stats.updateFloatStats(data);
        } else {
            strings.add(data);
            stats.updateStringStats(data);
        }
    }

    private static boolean isInteger(String data) {
        try {
            Integer.parseInt(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isFloat(String data) {
        try {
            Float.parseFloat(data);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}

