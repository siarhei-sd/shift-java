package team.korona.shift.parser;

import java.util.ArrayList;
import java.util.List;

public class FilesCommandLineParser implements CommandLineParser {

    private boolean appendMode;
    private String outputPath;
    private String prefix;
    private final List<String> inputFiles;
    private StatisticsType statisticsType;

    public FilesCommandLineParser(String[] args) throws ParsingException {
        this.inputFiles = new ArrayList<>();
        this.outputPath = ".";
        this.prefix = "";
        parseArguments(args);
    }


    private void parseArguments(String[] args) throws ParsingException {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a" -> appendMode = true;
                case "-o" -> {
                    if (i + 1 < args.length) {
                        outputPath = args[i + 1];
                        i++;
                    } else {
                        throw new ParsingException("Missing value for -o");
                    }
                }
                case "-p" -> {
                    if (i + 1 < args.length) {
                        prefix = args[i + 1];
                        i++;
                    } else {
                        throw new ParsingException("Missing value for -p");
                    }
                }
                case "-s" -> statisticsType = StatisticsType.SHORT;
                case "-f" -> statisticsType = StatisticsType.FULL;
                default -> inputFiles.add(args[i]);
            }
        }
        validateOptions();
    }

    private void validateOptions() throws ParsingException {
        if (inputFiles.isEmpty()) {
            throw new ParsingException("No input files specified.");
        }
        if (statisticsType == null) {
            throw new ParsingException("Error: You must specify either -s (short statistics) or -f (full statistics).");
        }
    }

    @Override
    public boolean isAppendMode() {
        return appendMode;
    }

    @Override
    public String getOutputPath() {
        return outputPath;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public List<String> getInputFiles() {
        return inputFiles;
    }

    @Override
    public StatisticsType getStatisticsType() {
        return statisticsType;
    }
}
