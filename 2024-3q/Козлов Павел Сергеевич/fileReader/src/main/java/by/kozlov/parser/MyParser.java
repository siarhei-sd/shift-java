package by.kozlov.parser;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

public class MyParser {

    private String savePath = ".";
    private String preName = "";
    private boolean isAppend = false;
    private boolean isMinStat = false;
    private boolean isFullStat = false;
    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();

    {
        options.addOption("o",true,"output path to save");
        options.addOption("p",true,"pre name to save");
        options.addOption("a",false,"save to file");
        options.addOption("s",false,"min stat");
        options.addOption("f",false,"full stat");
    }

    public List<String> parse(String[] args) {

        List<String> inputFiles = new ArrayList<>();
        try {
            CommandLine cmd = parser.parse(options,args);
            if (cmd.hasOption("o")) {
                savePath = savePath + cmd.getOptionValue("o");
            }

            if (cmd.hasOption("p")) {
                preName = cmd.getOptionValue("p");
            }

            if (cmd.hasOption("a")) {
                isAppend = true;
            }

            if (cmd.hasOption("s")) {
                isMinStat = true;
            }

            if (cmd.hasOption("f")) {
                isFullStat = true;
            }
        } catch (
                ParseException e) {
            System.out.println(e.getLocalizedMessage());
        }

        for (String s:args) {
            if (s.endsWith(".txt")) {
                inputFiles.add(s);
            }
        }

        if (inputFiles.isEmpty()) System.out.println("No files to read. Please restart utility and enter the files!");

        return inputFiles;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getPreName() {
        return preName;
    }

    public boolean isAppend() {
        return isAppend;
    }

    public boolean isMinStat() {
        return isMinStat;
    }

    public boolean isFullStat() {
        return isFullStat;
    }
}
