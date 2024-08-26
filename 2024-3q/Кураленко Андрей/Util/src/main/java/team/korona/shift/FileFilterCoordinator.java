package team.korona.shift;

import team.korona.shift.parser.StatisticsType;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class FileFilterCoordinator {
    private final List<File> inputFiles;
    private final String outputPath;
    private final String prefix;
    private final boolean appendMode;
    private final StatisticsType statisticsType;

    public FileFilterCoordinator(List<File> inputFiles, String outputPath, String prefix, boolean appendMode, StatisticsType statisticsType) {
        this.inputFiles = inputFiles;
        this.outputPath = outputPath != null ? outputPath : ".";
        this.prefix = prefix != null ? prefix : "";
        this.appendMode = appendMode;
        this.statisticsType = statisticsType;
    }

    public void filterFiles()  {
        try (DataProcessor dataProcessor = new DataProcessor(outputPath, prefix, appendMode, getStatistics(statisticsType))) {
            for (File file : inputFiles) {
                processFile(file, dataProcessor);
            }

            dataProcessor.printStatistics();
        } catch (IOException e) {
            System.err.println("Error while handle data: " + e.getMessage());
        }
    }

    private Statistics getStatistics(StatisticsType statisticsType) {
        if (statisticsType == StatisticsType.SHORT) {
            return new ShortStatistics();
        } else {
            return new FullStatistics();
        }
    }

    private void processFile(File file, DataProcessor dataProcessor) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dataProcessor.processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file.getName() + " - " + e.getMessage());
        }
    }
}
