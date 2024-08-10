package org.dezzzl;

import java.util.Set;

public class CommandLineParser {
    public static final Set<String> OPTIONS = Set.of("-a", "-o", "-p", "-s", "-f");
    public static final String TXT_EXTENSION = ".txt";

    public Configuration parseArguments(String[] args) {
        Configuration configuration = new Configuration();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (OPTIONS.contains(arg)) {
                setOptionField(configuration, arg, getNextArg(args, i));
            } else if (arg.endsWith(TXT_EXTENSION)) {
                setSourceFilename(configuration, arg, getPreviousArg(args, i));
            } else {
                setInvalidArguments(configuration, arg, getPreviousArg(args, i));
            }
        }
        return configuration;
    }

    private void setOptionField(Configuration configuration, String flag, String nextArg) {
        if (!isNextArgForOptionValid(flag, nextArg)) return;
        Option option = Option.fromStringToOption(flag);
        switch (option) {
            case APPEND_TO_THE_END -> configuration.setAppendToTheEndOption(true);
            case PATH_FOR_RESULT -> configuration.setPathToSave(nextArg);
            case PREFIX_FOR_NAMES -> configuration.setFilePrefix(nextArg);
            case SHORT_STATISTICS -> configuration.setShortStatisticsOption(true);
            case FULL_STATISTICS -> configuration.setFullStatisticsOption(true);
        }
    }

    private void setSourceFilename(Configuration configuration, String filename, String previousArg) {
        if (OPTIONS.contains(previousArg)) {
            Option option = Option.fromStringToOption(previousArg);
            if (option == Option.PATH_FOR_RESULT) configuration.addInvalidArguments(filename);
            else if (option == Option.PREFIX_FOR_NAMES) configuration.setFilePrefix(filename);
            else configuration.addFilename(filename);
        } else configuration.addFilename(filename);
    }

    private void setInvalidArguments(Configuration configuration, String arg, String previousArg) {
        if (!OPTIONS.contains(previousArg)) configuration.addInvalidArguments(arg);
    }

    private boolean isNextArgForOptionValid(String optionStr, String nextArg) {
        Option option = Option.fromStringToOption(optionStr);
        if (option == Option.PATH_FOR_RESULT || option == Option.PREFIX_FOR_NAMES) {
            if (nextArg.endsWith(TXT_EXTENSION)) return false;
            return !OPTIONS.contains(nextArg);
        }
        return true;
    }

    private String getPreviousArg(String[] args, int i) {
        if (i == 0) return "";
        return args[i - 1];
    }

    private String getNextArg(String[] args, int i) {
        if (i == args.length - 1) return "";
        return args[i + 1];
    }

}
