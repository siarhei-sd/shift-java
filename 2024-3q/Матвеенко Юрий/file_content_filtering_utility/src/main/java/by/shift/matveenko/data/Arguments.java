package by.shift.matveenko.data;

import com.beust.jcommander.Parameter;

import java.util.List;

public class Arguments {
    @Parameter(names = {"-o"}, description = "Path for results")
    private String path;

    @Parameter(names = {"-p"}, description = "Output filename prefix")
    private String prefix;

    @Parameter(names = {"-a"}, description = "New results are added to files with previous ones")
    private boolean addedResults;

    @Parameter(names = {"-s"}, description = "Short statistics")
    private boolean shortStatistics;

    @Parameter(names = {"-f"}, description = "Full statistics")
    private boolean fullStatistics;

    @Parameter(description = "Input files")
    private List<String> files;

    public String getPath() {
        return path;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAddedResults() {
        return addedResults;
    }

    public boolean isShortStatistics() {
        return shortStatistics;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public List<String> getFiles() {
        return files;
    }
}