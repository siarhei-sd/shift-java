package com.file.filter.app.processor;

import com.file.filter.app.cli.CommandLineOptions;
import com.file.filter.app.manager.FileManager;
import com.file.filter.app.manager.FilesContentManager;
import com.file.filter.app.manager.PatternMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DoubleProcessor extends AbstractDataProcessor {
    private final List<Double> doublesForStatistic = new ArrayList<>();

    public DoubleProcessor(FilesContentManager optionManager, FileManager fileManager, CommandLineOptions commandLineOptions) {
        super(optionManager, fileManager, commandLineOptions, "floats");
    }

    @Override
    protected void processContent(List<String> filesContent) {
        for (String str : filesContent) {
            if (str.matches(PatternMatcher.DOUBLE_REGEX.getRegex())) {
                elements.add(str);
                doublesForStatistic.add(Double.parseDouble(str.replace(",", ".")));
            }
        }
    }

    @Override
    protected void collectStatistics() {
        shortStatistic = fileName + " short statistic: elements = " + elements.size();
        double min = Collections.min(doublesForStatistic);
        double max = Collections.max(doublesForStatistic);
        double sum = doublesForStatistic.stream()
                .mapToDouble(Double::valueOf)
                .sum();
        double average = sum / elements.size();

        fullStatistic = fileName + " full statistic: elements = " + elements.size()
                + ", min = " + min + ", max = " + max
                + ", sum = " + sum + ", average = " + average;
    }
}
