package org.srmzhk;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class CmdParser {
    private final Options options;
    private final CommandLineParser parser;
    private final RegexHelper regexHelper;

    private String[] inputFiles;
    private final String[] outputFiles = {"integers.txt", "floats.txt", "strings.txt"};
    private boolean isAppend = false;
    private char statType;

    public CmdParser() {
        this.options = new Options();
        options.addOption("a", "Add results to output file");
        options.addOption("s", "Show a short statistic");
        options.addOption("f", "Show a full statistic");
        options.addOption("p", true, "Set prefix for output files");
        options.addOption("o", true, "Set path for output files");
        this.parser = new DefaultParser();
        this.regexHelper = new RegexHelper();
    }

    public void parseCmd(String[] args) {
        CommandLine cmd;
        String outputFilesPrefix;
        String outputFilesPath;

        try {
            cmd = parser.parse(options, args);

            if (cmd.hasOption("a")) {
                isAppend = true;
            }

            if(cmd.hasOption("s") && cmd.hasOption("f"))
                throw new ParseException("You can't use both options(-s and -f)");

            if (cmd.hasOption("s"))
                statType = 's';
            else if (cmd.hasOption("f"))
                statType = 'f';

            if (cmd.hasOption("p")) {
                outputFilesPrefix = cmd.getOptionValue("p");
                for (int i = 0; i < 3; i++) {
                    outputFiles[i] = outputFilesPrefix + outputFiles[i];
                }
            }

            if (cmd.hasOption("o")) {
                outputFilesPath = cmd.getOptionValue("o");
                if (outputFilesPath.charAt(outputFilesPath.length() - 1) != '/')
                    outputFilesPath += "/";
                for (int i = 0; i < 3; i++) {
                    outputFiles[i] = outputFilesPath + outputFiles[i];
                }
            }

            inputFiles = cmd.getArgs();
            if (inputFiles.length == 0) {
                System.out.println("There is no any input file");
                System.exit(1);
            }
            for (String arg : inputFiles) {
                if(!regexHelper.isTxt(arg))
                    throw new ParseException("Wrong input file name!");
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public String[] getInputFiles() {
        return inputFiles;
    }

    public boolean getAppendState() {
        return isAppend;
    }

    public char getStatType() {
        return statType;
    }

    public String[] getOutputFiles() {
        return outputFiles;
    }
}
