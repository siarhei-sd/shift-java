package org.example;

public class StartApp {
    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        arguments.setListArg(args);
        Files files = new Files();
        files.readFiles(arguments.getFiles());
        files.createSortFiles(arguments.getPath(), arguments.getPrefix(), arguments.isArgA());
        Statistics statistics = new Statistics(files.getIntList(), files.getFloatList(), files.getStringList(),
                arguments.getPrefix());
        if (arguments.isArgS()) {
            statistics.getShortStatistics();
        }
        if (arguments.isArgF()) {
            statistics.getFullStatistics();
        }
    }
}