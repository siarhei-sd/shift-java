package by.konashuk.analyzer.parameter;

import by.konashuk.analyzer.file.constants.FileConstants;

import java.util.*;
import java.util.regex.Matcher;

import static by.konashuk.analyzer.file.constants.FileConstants.Patterns.FILE_PATH_PATTERN;
import static by.konashuk.analyzer.parameter.constants.ParameterConstants.*;


public final class ParametersAnalyzer {
    private ParametersAnalyzer() {
    }

    public static AnalysisParameters analyzeInputParameters(String[] args) throws RuntimeException {
        Set<String> lineParameters = new HashSet<>();
        Set<String> fileNames = new HashSet<>();
        String prefix = "";
        String path = "";

        if (args.length == 0) {
            throw new RuntimeException("No args in command line");
        }

        for (int i = 0; i < args.length; ++i) {

            Matcher fileNameMatcher = FileConstants.Patterns.FILE_NAME_PATTERN.matcher(args[i]);
            if (fileNameMatcher.find()) {
                fileNames.add(args[i]);
            }

            if (args[i].equals(PREFIX_PARAM)) {
                hasLineParameterArgument(i, args.length, PREFIX_STR);
                checkPrefixValue(args[i + 1]);
                prefix = args[i + INCREMENT_VALUE];
            }

            if (args[i].equals(PATH_PARAM)) {
                hasLineParameterArgument(i, args.length, PATH_STR);
                checkPathValue(args[i + INCREMENT_VALUE]);
                path = args[i + INCREMENT_VALUE];
            }

            lineParameters.add(args[i]);

            if (!PARAMS_VALUES.contains(args[i]) && !lineParameters.contains(args[i])) {
                throw new RuntimeException("Unknown parameter " + args[i] + " " + "found");
            }
        }

        if (fileNames.isEmpty()) {
            throw new RuntimeException("No file name(s) found.");
        }

        return new AnalysisParameters(
                path,
                prefix,
                lineParameters.contains(APPEND_PARAM),
                lineParameters.contains(SHORT_STATISTIC_PARAM),
                lineParameters.contains(FULL_STATISTIC_PARAM),
                fileNames);
    }

    private static void hasLineParameterArgument(Integer index, Integer arrSize, String parameterName) {
        if (index == arrSize - INCREMENT_VALUE) {
            throw new RuntimeException(parameterName + " parameter is missing.");
        }
    }

    private static void checkPrefixValue(String prefix) {
        if (prefix.length() < MIN_PREFIX_LENGTH) {
            throw new RuntimeException(PREFIX_STR + " must include a minimum of 3 characters");
        }
    }

    private static void checkPathValue(String filePath) {
        Matcher filePathMatcher = FILE_PATH_PATTERN.matcher(filePath);
        if (!filePathMatcher.find()) {
            throw new RuntimeException("Bad file path value.");
        }
    }
}
