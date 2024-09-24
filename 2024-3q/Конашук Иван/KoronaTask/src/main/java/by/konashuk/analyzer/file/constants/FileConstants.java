package by.konashuk.analyzer.file.constants;

import java.util.regex.Pattern;

public final class FileConstants {
    private FileConstants() {
    }

    public static class Files {

        private Files() {
        }

        public static final String FILE_NAME_PATTERN = "%s%s";
        public static final String INTEGER_FILE_NAME_PATTERN = FILE_NAME_PATTERN + "integer.txt";
        public static final String FLOAT_FILE_NAME_PATTERN = FILE_NAME_PATTERN + "float.txt";
        public static final String STRING_FILE_NAME_PATTERN = FILE_NAME_PATTERN + "strings.txt";
    }

    public static final class Patterns {

        private Patterns() {
        }

        public static final Pattern FLOAT_PATTERN = Pattern.compile("^[-+]?[0-9]*[.,]+[0-9]+(?:[eE][-+]?[0-9]+)?$");
        public static final Pattern INTEGER_PATTERN = Pattern.compile("(?<!\\d[-+])(?<=^|\\s)[\\-+]*[0-9]+(?:$|\\s)");
        public static final Pattern STRINGS_PATTERN = Pattern.compile("(?<!\\d[-+])(?<=^|\\s)[\\-+]*[a-zA-Zа-яА-Я]+(?:$|\\s)");
        public static final Pattern FILE_NAME_PATTERN = Pattern.compile("[\\w\\s]+\\.\\w+");
        public static final Pattern FILE_PATH_PATTERN = Pattern.compile("[a-zA-z]:(\\?([^*|\\\\:\"<>]*))*+");
    }
}
