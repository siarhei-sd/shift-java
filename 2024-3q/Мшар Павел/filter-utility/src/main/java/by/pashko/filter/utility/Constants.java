package by.pashko.filter.utility;

import java.util.Map;

public class Constants {
    public static final String DEFAULT_FILE_NAME_FOR_INTEGERS = "integers.txt";
    public static final String DEFAULT_FILE_NAME_FOR_STRINGS = "strings.txt";
    public static final String DEFAULT_FILE_NAME_FOR_FLOATS = "floats.txt";

    public static final Map<ValueType, String> VALUE_TYPE_TO_FILE_NAME = Map.of(
            ValueType.DOUBLE, DEFAULT_FILE_NAME_FOR_FLOATS,
            ValueType.STRING, DEFAULT_FILE_NAME_FOR_STRINGS,
            ValueType.INTEGER, DEFAULT_FILE_NAME_FOR_INTEGERS
    );

    public static final String SUPPORTED_FILE_EXTENSION = ".txt";
    public static final String OUTPUT_FLAG = "-o";
    public static final String PREFIX_FLAG = "-p";
    public static final String APPEND_FLAG = "-a";
    public static final String SHORT_STAT_FLAG = "-s";
    public static final String FULL_STAT_FLAG = "-f";
    public static final String EMPTY_ARGUMENT_VALUE_ERROR_MESSAGE = "Empty value for `%s` argument. Default values will be used.\n";
    public static final String UNSUPPORTED_ARGUMENT_ERROR_MESSAGE = "Unsupported argument `%s`.\n";

    private Constants() {
    }
}
