package org.shift;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import static org.shift.Constants.*;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("o", "o", true, O_OPTION_DESCRIPTION);
        options.addOption("p", "p", true, P_OPTION_DESCRIPTION);
        options.addOption("a", "a", false, A_OPTION_DESCRIPTION);
        options.addOption("s", "s", false, S_OPTION_DESCRIPTION);
        options.addOption("f", "f", false, F_OPTION_DESCRIPTION);

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            new FileProcessor(cmd).process()
                .writeSortedFiles();
        } catch (ParseException e) {
            System.err.println(PARSING_CMD_OPTION_FAILED_MSG + e.getMessage());
        }
    }
}