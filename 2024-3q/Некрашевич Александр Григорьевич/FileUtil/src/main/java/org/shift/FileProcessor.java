package org.shift;

import org.apache.commons.cli.CommandLine;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static org.shift.Constants.*;

public class FileProcessor {
    private List<String> files;
    private String prefix = "";
    private String pathToSave = "";
    private Boolean appendToExistingFiles = false;
    private Boolean isShortStatisticRequired = false;
    private Boolean isFullStatisticRequired = false;

    private Map<DataType, List<String>> sortedValuesMap;
    private Map<DataType, Statistic> statisticsMap;
    private Map<DataType, String> dataTypeToFileNameMap;

    public FileProcessor(CommandLine cmd) {
        if (cmd.hasOption("p")) {
            prefix = cmd.getOptionValue("p");
        }

        if (cmd.hasOption("o")) {
            pathToSave = cmd.getOptionValue("o");
        }

        if (cmd.hasOption("a")) {
            appendToExistingFiles = true;
        }

        if (cmd.hasOption("s")) {
            isShortStatisticRequired = true;
        }

        if (cmd.hasOption("f")) {
            isFullStatisticRequired = true;
        }

        files = cmd.getArgList();

        sortedValuesMap = new HashMap<DataType, List<String>>();
        statisticsMap = new HashMap<DataType, Statistic>();
        dataTypeToFileNameMap = new HashMap<DataType, String>();

        dataTypeToFileNameMap.put(DataType.INTEGER, prefix + INTEGERS_TXT);
        dataTypeToFileNameMap.put(DataType.FLOAT, prefix + FLOATS_TXT);
        dataTypeToFileNameMap.put(DataType.STRING, prefix + STRINGS_TXT);
    }

    public FileProcessor process() {
        files.forEach(file -> {
            try (Stream<String> lines = new Scanner(new File(file)).tokens()) {
                lines.forEach(line -> {
                    DataType dataType = DataType.fromString(line);

                    sortedValuesMap.computeIfAbsent(dataType, k -> new ArrayList<>()).add(line);
                    statisticsMap.computeIfAbsent(dataType, k -> new Statistic(dataType)).update(line);
                });
            } catch (FileNotFoundException e) {
                System.err.println(file + FILE_NOT_FOUND_MSG);
            }
        });

        if (isShortStatisticRequired || isFullStatisticRequired) {
            showStatistics(isFullStatisticRequired);
        }

        return this;
    }

    private void showStatistics(Boolean isFullStatisticRequired) {
        statisticsMap.forEach((dataType, stats) -> {
            String values = stats.getStatistic(isFullStatisticRequired);
            String fileName = dataTypeToFileNameMap.get(dataType);
            System.out.println(fileName + " " + values);
        });
    }

    public void writeSortedFiles() {
        sortedValuesMap.forEach((dataType, values) -> {
            try {
                writeToFile(
                    Paths.get(pathToSave, dataTypeToFileNameMap.get(dataType)).toString(),
                    String.join("\n", values),
                    appendToExistingFiles
                );
            } catch (IOException ioException) {
                System.err.println(FILE_WAS_NOT_WRITTEN_MSG + ioException.getMessage());
            }
        });
    }

    private void writeToFile(String filePath, String data, boolean append) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(data);
            writer.newLine();
        }
    }
}
