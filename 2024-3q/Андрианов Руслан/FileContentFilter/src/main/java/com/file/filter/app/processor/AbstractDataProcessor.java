package com.file.filter.app.processor;

import com.file.filter.app.cli.CommandLineOptions;
import com.file.filter.app.manager.FileManager;
import com.file.filter.app.manager.FilesContentManager;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDataProcessor implements DataProcessor {
    protected final List<String> elements = new ArrayList<>();
    protected final String fileName;
    protected final String path;
    protected String shortStatistic = "";
    protected String fullStatistic = "";
    protected final FilesContentManager optionManager;
    protected final FileManager fileManager;
    protected final CommandLineOptions commandLineOptions;

    protected AbstractDataProcessor(FilesContentManager optionManager,
                                    FileManager fileManager,
                                    CommandLineOptions commandLineOptions,
                                    String fileType) {
        this.optionManager = optionManager;
        this.commandLineOptions = commandLineOptions;
        this.fileName = commandLineOptions.getPrefix() + fileType + ".txt";
        this.path = commandLineOptions.getPath() + fileName;
        this.fileManager = fileManager;
    }

    protected abstract void processContent(List<String> filesContent);
    protected abstract void collectStatistics();

    @Override
    public void start() {
        processContent(optionManager.getFilesContent());
        collectStatistics();
        showStatistic();
        writeToFile();
    }

    private void showStatistic() {
        if (commandLineOptions.isShortStatistic()) {
            System.out.println(shortStatistic);
        }
        if (commandLineOptions.isFullStatistic()) {
            System.out.println(fullStatistic);
        }
    }

    private void writeToFile() {
        fileManager.writeToFile(elements, path, commandLineOptions.isAppend());
    }
}
