package org.example;

public class Main {
    public static void main(String[] args) {
        UtilCLI cli = new UtilCLI();
        cli.parseArguments(args);

        FileProcessor fileProcessor = new FileProcessor(cli);
        fileProcessor.processFiles();
    }
}