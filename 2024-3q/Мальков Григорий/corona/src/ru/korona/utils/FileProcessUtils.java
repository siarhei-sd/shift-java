package ru.korona.utils;

import ru.korona.core.model.Arguments;
import ru.korona.core.model.Stats;
import ru.korona.enums.Types;

import java.io.*;
import java.util.*;

public class FileProcessUtils {
    public static Arguments parseArguments(String[] args, List<String> inputFiles) {
        Arguments arguments = new Arguments();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-o":
                    if (i + 1 < args.length) {
                        arguments.setOutputPath(args[++i]);
                    } else {
                        System.err.println("Option -o requires an argument");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        arguments.setFilePrefix(args[++i]);
                    } else {
                        System.err.println("Option -p requires an argument");
                    }
                    break;
                case "-a":
                    arguments.setAppendMode(true);
                    break;
                case "-s":
                    arguments.setFullStats(false);
                    break;
                case "-f":
                    arguments.setFullStats(true);
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }

        return arguments;
    }

    public static void parseFileContent(
            String fileName,
            Map<String, List<String>> buffers,
            Stats intStats,
            Stats floatStats,
            Stats strStats
    ) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.matches("-?\\d+")) {
                    buffers.get("integers").add(line);
                    intStats.update(Integer.parseInt(line));
                } else if (line.matches("-?\\d*\\.\\d+")) {
                    buffers.get("floats").add(line);
                    floatStats.update(Double.parseDouble(line));
                } else {
                    buffers.get("strings").add(line);
                    strStats.update(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file " + fileName + ": " + e.getMessage());
        }
    }

    public static void printStats(boolean fullStats, List<Stats> stats) {
        for (Stats stat : stats) {
            System.out.println(stat.getType().getType() + " Statistics:");
            System.out.println("Count: " + stat.getCount());
            if (fullStats) {
                if ("strings".equals(stat.getType().getType())) {
                    System.out.println("Min Length: " + stat.getMinLength());
                    System.out.println("Max Length: " + stat.getMaxLength());
                } else {
                    System.out.println("Min: " + stat.getMin());
                    System.out.println("Max: " + stat.getMax());
                    System.out.println("Sum: " + stat.getSum());
                    System.out.println("Average: " + (stat.getSum() / stat.getCount()));
                }
            }
            System.out.println();
        }
    }

    public static void writeBuffersToFile(Map<String, List<String>> buffers, Arguments arguments) throws IOException {
        String outputPath = arguments.getOutputPath();
        String filePrefix = arguments.getFilePrefix();
        boolean appendMode = arguments.isAppendMode();
        writeBufferToFile(
                outputPath,
                filePrefix != null ? filePrefix + "integers.txt" : "integers.txt",
                buffers.get(Types.INTEGERS.getType()),
                appendMode
        );
        writeBufferToFile(
                outputPath,
                filePrefix != null ? filePrefix + "floats.txt" : "floats.txt",
                buffers.get(Types.FLOATS.getType()),
                appendMode
        );
        writeBufferToFile(
                outputPath,
                filePrefix != null ? filePrefix + "strings.txt" : "strings.txt",
                buffers.get(Types.STRINGS.getType()),
                appendMode
        );
    }

    private static void writeBufferToFile(
            String path,
            String fileName,
            List<String> buffer,
            boolean append
    ) throws IOException {
        if (!buffer.isEmpty()) {
            File file = new File(path, fileName);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, append))) {
                for (String line : buffer) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
}
