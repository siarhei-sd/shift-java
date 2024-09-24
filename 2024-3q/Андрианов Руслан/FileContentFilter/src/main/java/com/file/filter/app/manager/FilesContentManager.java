package com.file.filter.app.manager;

import com.file.filter.app.cli.CommandLineOptions;

import java.util.ArrayList;
import java.util.List;

public class FilesContentManager {

    private List<String> filesContent = new ArrayList<>();
    private final CommandLineOptions commandLineOptions;
    private final FileManager fileManager;

    public FilesContentManager(CommandLineOptions commandLineOptions, FileManager fileManager) {
        this.commandLineOptions = commandLineOptions;
        this.fileManager = fileManager;
        init();
    }

    public List<String> getFilesContent() {
        return filesContent;
    }

    private void init() {
        List<String> inputFiles = commandLineOptions.getFiles();
        if (!inputFiles.isEmpty()) {
            filesContent = fileManager.readFromFiles(inputFiles);
        }
    }
}







