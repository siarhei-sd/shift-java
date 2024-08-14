package com.filterutil;

public class Main {
    public static void main(String[] args) {
        OptionsParser optionsParser = new OptionsParser(args);
        FileProcessor fileProcessor = new FileProcessor(optionsParser);
        fileProcessor.processFiles();
    }
}