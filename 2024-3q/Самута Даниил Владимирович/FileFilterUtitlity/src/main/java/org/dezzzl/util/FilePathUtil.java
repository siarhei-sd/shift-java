package org.dezzzl.util;

import org.dezzzl.exception.PathIsNotValidException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FilePathUtil {
    private static final Set<String> INVALID_PREFIX_SYMBOLS = Set.of("/", "\\", "*", "?", "<", ">", "\"");
    public static final String DEFAULT_FILE_PREFIX = "";
    public static final String DEFAULT_FILE_SEPARATOR = "/";

    public static File parse(String pathStr) throws PathIsNotValidException {
        if (pathStr.isEmpty()) {
            throw new PathIsNotValidException("Path cannot be null or empty");
        }
        File file = createFile(pathStr);
        if (!file.exists()) throw new PathIsNotValidException(pathStr + " is not valid");
        return file;
    }

    public static File createFile(String pathStr) {
        pathStr = removeLeadingFileSeparator(pathStr);
        List<String> partsOfPath = List.of(pathStr.split("/+"));
        return new File(String.join(File.separator, partsOfPath));
    }

    public static List<File> getFilesListFromPaths(Set<String> sourceFilenames) {
        List<File> files = new ArrayList<>();
        for (String sourceFilename : sourceFilenames) {
            try {
                String path = removeLeadingFileSeparator(sourceFilename);
                files.add(FilePathUtil.parse(path));
            } catch (PathIsNotValidException e) {
                System.out.println(e.getMessage());
            }
        }
        return files;
    }

    public static String getRealPathAccordingToConfig(String prefix, String path, String filename) {
        prefix = validatePrefix(prefix);
        path = removeLeadingFileSeparator(path);
        if (path.isEmpty()) return path + prefix + filename;
        else return path + File.separator + prefix + filename;
    }

    private static String validatePrefix(String prefix) throws IllegalArgumentException {
        for (char c : prefix.toCharArray()) {
            if (INVALID_PREFIX_SYMBOLS.contains(String.valueOf(c))) {
                return DEFAULT_FILE_PREFIX;
            }
        }
        return prefix;
    }

    private static String removeLeadingFileSeparator(String path) {
        if (path.startsWith(DEFAULT_FILE_SEPARATOR)) {
            return path.substring(1);
        }
        return path;
    }

}
