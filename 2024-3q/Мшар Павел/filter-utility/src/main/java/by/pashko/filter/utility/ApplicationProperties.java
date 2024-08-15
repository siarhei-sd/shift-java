package by.pashko.filter.utility;

import java.util.ArrayList;
import java.util.List;

public class ApplicationProperties {
    private final List<String> inputFiles = new ArrayList<>();
    private String outputDirectory = "";
    private String fileNamePrefix = "";
    private boolean appendToEnd;
    private boolean showShortStatistics;
    private boolean showFullStatistics;

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getFileNamePrefix() {
        return fileNamePrefix;
    }

    public void setFileNamePrefix(String fileNamePrefix) {
        this.fileNamePrefix = fileNamePrefix;
    }

    public boolean isAppendToEnd() {
        return appendToEnd;
    }

    public void setAppendToEnd(boolean appendToEnd) {
        this.appendToEnd = appendToEnd;
    }

    public boolean isShowShortStatistics() {
        return showShortStatistics;
    }

    public void setShowShortStatistics(boolean showShortStatistics) {
        this.showShortStatistics = showShortStatistics;
    }

    public boolean isShowFullStatistics() {
        return showFullStatistics;
    }

    public void setShowFullStatistics(boolean showFullStatistics) {
        this.showFullStatistics = showFullStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public void addInputFiles(String inputFile) {
        inputFiles.add(inputFile);
    }

    @Override
    public String toString() {
        return "{"
                + "outputDirectory='" + outputDirectory + '\''
                + ", fileNamePrefix='" + fileNamePrefix + '\''
                + ", appendToEnd=" + appendToEnd
                + ", showShortStatistics=" + showShortStatistics
                + ", showFullStatistics=" + showFullStatistics
                + ", inputFiles=" + inputFiles
                + '}';
    }
}
