package org.egorsemenovv.filter;

import lombok.Getter;
import org.egorsemenovv.statistics.StatisticsCollector;
import org.egorsemenovv.statistics.StatisticsType;
import org.egorsemenovv.writer.LazyFileWriter;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileContentFilter {

    private static final String INTEGERS_FILE_NAME = "integers.txt";
    private static final String FLOATS_FILE_NAME = "floats.txt";
    private static final String STRINGS_FILE_NAME = "strings.txt";
    @Getter
    private final StatisticsCollector statisticsCollector;
    private final String path;
    private final String prefix;
    private final boolean append;

    public FileContentFilter(String path, String prefix, boolean append, StatisticsType statisticsType) {
        this.path = path;
        this.prefix = prefix;
        this.append = append;
        this.statisticsCollector = new StatisticsCollector(prefix.concat(INTEGERS_FILE_NAME),
                prefix.concat(FLOATS_FILE_NAME),
                prefix.concat(STRINGS_FILE_NAME),
                statisticsType);
    }

    public void performFiltering(List<String> fileNames) throws IOException {
        Path integersWritePath = Path.of(path, prefix.concat(INTEGERS_FILE_NAME));
        Path floatsWritePath = Path.of(path, prefix.concat(FLOATS_FILE_NAME));
        Path stringsWritePath = Path.of(path, prefix.concat(STRINGS_FILE_NAME));

        try (LazyFileWriter longsWriter = new LazyFileWriter(integersWritePath, append);
             LazyFileWriter doublesWriter = new LazyFileWriter(floatsWritePath, append);
             LazyFileWriter stringsWriter = new LazyFileWriter(stringsWritePath, append)) {
            for (String fileName : fileNames) {
                Path readPath = Path.of(fileName);
                try {
                    filterFile(longsWriter, doublesWriter, stringsWriter, readPath);
                } catch (IOException e){
                    System.out.println("problems occurred while reading" + fileName);
                }
            }
        }
    }

    private void filterFile(LazyFileWriter longsWriter,
                            LazyFileWriter doublesWriter,
                            LazyFileWriter stringsWriter,
                            Path readPath) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(readPath)) {
            String line = bufferedReader.readLine();
            while (line != null) {
                if (checkForLong(line)) {
                    longsWriter.write(line);
                    statisticsCollector.addLong(Long.parseLong(line));
                } else if (checkForDouble(line)) {
                    doublesWriter.write(line);
                    statisticsCollector.addDouble(Double.parseDouble(line));
                } else {
                    stringsWriter.write(line);
                    statisticsCollector.addString(line);
                }
                line = bufferedReader.readLine();
            }
        }
    }

    private boolean checkForDouble(String line) {
        try {
            Double.parseDouble(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean checkForLong(String line) {
        try {
            Long.parseLong(line);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
