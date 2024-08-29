package Test;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FilteringUtility {
    public static void main(String[] args) {
       if (args.length < 1) {
            System.err.println("Usage: java -jar util.jar [options] <input_files>");
            return;
        }

        Path outputPath = Paths.get(".");
        String prefix = "";
        boolean append = false;
        boolean fullStatistic = false;
        List<Path> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = Paths.get(args[++i]);
                    } else {
                        System.err.println("Missing value for -o option");
                        return;
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        prefix = args[++i];
                    } else {
                        System.err.println("Missing value for -p option");
                        return;
                    }
                    break;
                case "-a":
                    append = true;
                    break;
                case "-s":
                    fullStatistic = false;
                    break;
                case "-f":
                    fullStatistic = true;
                    break;
                default:
                    inputFiles.add(Paths.get(args[i]));
            }
        }

        FileProcessor processor = new FileProcessor(outputPath, prefix, append, fullStatistic);
        processor.processFiles(inputFiles);
    }
}
