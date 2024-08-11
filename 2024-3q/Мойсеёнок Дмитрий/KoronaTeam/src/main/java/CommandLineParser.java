import java.util.*;

public class CommandLineParser {
    private String[] args;
    private List<String> inputFiles = new ArrayList<>();
    private String outputPath = ".";
    private String prefix = "";
    private boolean append = false;
    private boolean fullStatistics = false;

    public CommandLineParser(String[] args) {
        this.args = args;
        parse();
    }

    private void parse() {
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-o":
                    outputPath = args[++i];
                    break;
                case "-p":
                    prefix = args[++i];
                    break;
                case "-a":
                    append = true;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
                case "-s":
                    fullStatistics = false;
                    break;
                default:
                    inputFiles.add(args[i]);
                    break;
            }
        }
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean isAppend() {
        return append;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }
}

