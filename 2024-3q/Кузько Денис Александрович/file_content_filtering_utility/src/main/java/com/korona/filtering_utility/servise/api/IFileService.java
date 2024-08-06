package com.korona.filtering_utility.servise.api;

import java.util.List;

public interface IFileService {
    void filterData(List<String> inputFiles, boolean append);
    void setFilePaths(String outputDir, String prefix);
}
