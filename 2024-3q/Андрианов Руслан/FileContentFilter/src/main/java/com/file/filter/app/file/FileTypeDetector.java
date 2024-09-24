package com.file.filter.app.file;

import com.file.filter.app.manager.PatternMatcher;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileTypeDetector {

    public Set<FileType> getFilesTypes(List<String> filesContent) {
        Set<FileType> filesTypes = new HashSet<>();
        for (String str : filesContent) {
            if (str.matches(PatternMatcher.INTEGER_REGEX.getRegex())) {
                filesTypes.add(FileType.INTEGERS);
            } else if (str.matches(PatternMatcher.DOUBLE_REGEX.getRegex())) {
                filesTypes.add(FileType.FLOATS);
            } else {
                filesTypes.add(FileType.STRINGS);
            }
        }
        return filesTypes;
    }
}
