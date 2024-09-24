package com.file.filter.app.processor;

import com.file.filter.app.cli.CommandLineOptions;
import com.file.filter.app.manager.FileManager;
import com.file.filter.app.manager.FilesContentManager;
import com.file.filter.app.manager.PatternMatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IntegersProcessor extends AbstractDataProcessor {
    private final List<Integer> integersForStatistic = new ArrayList<>();

    public IntegersProcessor(FilesContentManager optionManager, FileManager fileManager, CommandLineOptions commandLineOptions) {
        super(optionManager, fileManager, commandLineOptions, "integers");
    }

    @Override
    protected void processContent(List<String> filesContent) {
        for (String str : filesContent) {
            if (str.matches(PatternMatcher.INTEGER_REGEX.getRegex())) {
                elements.add(str);
                integersForStatistic.add(Integer.parseInt(str));
            }
        }
    }

    @Override
    protected void collectStatistics() {
        shortStatistic = fileName + " short statistic: elements = " + elements.size();
        int min = Collections.min(integersForStatistic);
        int max = Collections.max(integersForStatistic);
        int sum = integersForStatistic.stream().mapToInt(Integer::intValue).sum();
        double average = (double) sum / elements.size();

        fullStatistic = fileName + " full statistic: elements = " + elements.size()
                + ", min = " + min + ", max = " + max
                + ", sum = " + sum + ", average = " + average;
    }
}
