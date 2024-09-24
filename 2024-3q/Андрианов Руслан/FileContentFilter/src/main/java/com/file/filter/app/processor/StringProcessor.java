package com.file.filter.app.processor;

import com.file.filter.app.cli.CommandLineOptions;
import com.file.filter.app.manager.FileManager;
import com.file.filter.app.manager.FilesContentManager;
import com.file.filter.app.manager.PatternMatcher;

import java.util.List;
import java.util.OptionalInt;

public class StringProcessor extends AbstractDataProcessor {

    public StringProcessor(FilesContentManager optionManager, FileManager fileManager, CommandLineOptions commandLineOptions) {
        super(optionManager, fileManager, commandLineOptions,"strings");
    }

    @Override
    protected void processContent(List<String> filesContent) {
        for (String str : filesContent) {
            if (!str.matches(PatternMatcher.INTEGER_REGEX.getRegex()) &&
                    !str.matches(PatternMatcher.DOUBLE_REGEX.getRegex()) && !str.matches("^$")) {
                elements.add(str);
            }
        }
    }

    @Override
    protected void collectStatistics() {
        shortStatistic = fileName + " short statistic: elements = " + elements.size();
        OptionalInt minStringLength = elements.stream()
                .mapToInt(String::length)
                .min();
        OptionalInt maxStringLength = elements.stream()
                .mapToInt(String::length)
                .max();

        fullStatistic = fileName + " full statistic: elements = " + elements.size()
                + ", min string length = " + minStringLength.getAsInt()
                + ", max string length = " + maxStringLength.getAsInt();
    }
}
