package org.dezzzl;

import lombok.Data;

import java.util.*;

@Data
public class Configuration {
    public static final String DEFAULT_FILE_PREFIX = "";
    public static final String DEFAULT_FILE_PATH_TO_SAVE = "";
    private Set<String> sourceFilenames;
    private String filePrefix;
    private String pathToSave;
    private boolean appendToTheEndOption;
    private boolean shortStatisticsOption;
    private boolean fullStatisticsOption;
    private List<String> invalidArguments;

    public Configuration() {
        this.sourceFilenames = new LinkedHashSet<>();
        this.filePrefix = DEFAULT_FILE_PREFIX;
        this.pathToSave = DEFAULT_FILE_PATH_TO_SAVE;
        this.appendToTheEndOption = false;
        this.shortStatisticsOption = false;
        this.fullStatisticsOption = false;
        this.invalidArguments = new ArrayList<>();
    }

    public void addFilename(String filename) {
        this.sourceFilenames.add(filename);
    }

    public void addInvalidArguments(String argument) {
        this.invalidArguments.add(argument);
    }
}
