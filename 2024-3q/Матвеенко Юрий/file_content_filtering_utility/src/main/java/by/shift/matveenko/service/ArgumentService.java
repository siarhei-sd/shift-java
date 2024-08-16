package by.shift.matveenko.service;

import by.shift.matveenko.data.Arguments;
import com.beust.jcommander.JCommander;

import java.util.regex.Pattern;

public class ArgumentService {
    private static final Pattern PREFIX_PATTERN = Pattern.compile("^[^\\\\/:*?\"<>|]+$");

    public static Arguments parseArguments(String[] args) {
        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);
        return arguments;
    }

    public static boolean validateArguments(Arguments arguments) {
        if (arguments.getFiles() == null || arguments.getFiles().isEmpty()) {
            System.err.println("No input files");
            return false;
        }

        if (!PREFIX_PATTERN.matcher(arguments.getPrefix()).matches()) {
            System.err.println("Incorrect prefix");
            return false;
        }
        return true;
    }
}