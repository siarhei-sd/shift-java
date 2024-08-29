package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

@Getter
@Setter
class Options {
    private String outputPath = ".";
    private String prefix = "";
    private boolean append = false;
    private boolean shortStatistics = false;
    private boolean fullStatistics = false;
    private List<String> files;

    public String getFileName(DataType type) {
        String fileName = prefix + type.toString().toLowerCase() + "s.txt";
        return outputPath + File.separator + fileName;
    }
}
