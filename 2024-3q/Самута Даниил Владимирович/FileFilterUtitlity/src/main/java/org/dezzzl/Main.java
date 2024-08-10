package org.dezzzl;


public class Main {
    public static void main(String[] args) {
        CommandLineParser commandLineParser = new CommandLineParser();
        Configuration configuration = commandLineParser.parseArguments(args);
        FileFilterUtility fileFilterUtility = new FileFilterUtility(configuration);
        fileFilterUtility.execute();
    }
}