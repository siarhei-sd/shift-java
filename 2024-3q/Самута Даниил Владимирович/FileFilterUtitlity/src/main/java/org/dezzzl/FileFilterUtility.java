package org.dezzzl;

import org.dezzzl.filewriter.FileWriterManager;
import org.dezzzl.statistics.StatisticsManager;
import org.dezzzl.util.FilePathUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileFilterUtility {
    private final Configuration configuration;
    private final FileWriterManager fileWriterManager;

    public FileFilterUtility(Configuration configuration) {
        this.configuration = configuration;
        this.fileWriterManager = new FileWriterManager(configuration);
    }

    public void execute() {
        this.printInvalidArguments();
        List<File> sourceFiles = FilePathUtil.getFilesListFromPaths(this.configuration.getSourceFilenames());
        if (sourceFiles.isEmpty()) {
            System.out.println("The utility needs to supply valid file paths");
        }

        StatisticsManager.setStatisticsToWriters(this.fileWriterManager.getFileWriters(), this.configuration);
        for (File file : sourceFiles) {
            try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
                String line = reader.readLine();
                while (line != null) {
                    this.fileWriterManager.writeLine(line);
                    line = reader.readLine();
                }
            } catch (IOException e) {
                this.fileWriterManager.closeConnections();
            }
        }
        StatisticsManager.printStatistics(this.fileWriterManager.getFileWriters());
        this.fileWriterManager.closeConnections();
    }

    private void printInvalidArguments() {
        if (!this.configuration.getInvalidArguments().isEmpty()) {
            String message = "Invalid arguments: " +
                             String.join(", ", this.configuration.getInvalidArguments());
            System.out.println(message);
        }
    }


}
