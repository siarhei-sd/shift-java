package com.javaded78.config;

import com.beust.jcommander.Parameter;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {

    @Parameter(names = {"-o"}, description = "Path for output files")
    private String pathOut;

    @Parameter(names = {"-p"}, description = "Prefix for output files")
    private String prefOut;

    @Parameter(names = {"-a"}, description = "Append option")
    private boolean append;

    @Parameter(names = {"-s"}, description = "Short statistics")
    private boolean isShortStats;

    @Parameter(names = {"-f"}, description = "Full statistics")
    private boolean isFullStats;

    @Parameter(description = "Input files", required = true)
    private List<String> files;

    public AppConfig() {
    }

    public String getPathOut() {
        return pathOut;
    }

    public String getPrefOut() {
        return prefOut;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isShortStats() {
        return isShortStats;
    }

    public boolean isFullStats() {
        return isFullStats;
    }

    public List<String> getFiles() {
        return files == null ? new ArrayList<>() : new ArrayList<>(this.files);
    }
}
