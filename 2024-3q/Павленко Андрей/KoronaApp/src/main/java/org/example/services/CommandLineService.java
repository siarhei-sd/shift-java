package org.example.services;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.core.exeptions.KoronaIncorrectCommandLineArgumentsException;
import org.example.configs.Constants;

public final class CommandLineService {

    private CommandLineService() {}

    private static final Logger logger = LogManager.getLogger("KoronaParser");

    private static final Option OUTPUT_OPT = new Option(
            Constants.OUTPUT_CMD_PARAM_SHORT,
            Constants.OUTPUT_CMD_PARAM_LONG,
            true,
            "Specify custom output folder path (optional)"
    );

    private static final Option PREFIX_OPT = new Option(
            Constants.PREFIX_CMD_PARAM_SHORT,
            Constants.PREFIX_CMD_PARAM_LONG,
            true,
            "Add prefix to file name (optional)"
    );

    private static final Option ADD_OPT = new Option(
            Constants.ADD_CMD_PARAM_SHORT,
            Constants.ADD_CMD_PARAM_LONG,
            false,
            "Add current progress to existing files (optional)"
    );

    private static final Option SHORT_STAT_OPT = new Option(
            Constants.SHORT_STAT_CMD_PARAM_SHORT,
            Constants.SHORT_STAT_CMD_PARAM_LONG,
            false,
            "Short statistic view (optional)"
    );

    private static final Option FULL_STAT_OPT = new Option(
            Constants.FULL_STAT_CMD_PARAM_SHORT,
            Constants.FULL_STAT_CMD_PARAM_LONG,
            false,
            "Full statistic view (optional)"
    );

    public static CommandLine getParams(final String[] args) {
        final var parser  = new DefaultParser();
        final var options = new Options();

        options.addOption(OUTPUT_OPT)
                .addOption(PREFIX_OPT)
                .addOption(ADD_OPT)
                .addOption(SHORT_STAT_OPT)
                .addOption(FULL_STAT_OPT);

        try {
            return parser.parse(options, args, false);
        } catch (final ParseException ex) {
            logger.error("Incorrect command line arguments", ex);
            throw new KoronaIncorrectCommandLineArgumentsException("Incorrect command line arguments : ");
        }
    }
}
