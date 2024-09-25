package com.nadzeya;

public class Main {
    public static void main(String[] args) {
        ArgumentsAnalyser argumentsAnalyser = new ArgumentsAnalyser();
        argumentsAnalyser.analyseArgs(args);
        FileInformation fileInformation = new FileInformation(argumentsAnalyser);
        fileInformation.analyseFiles();
        fileInformation.outputResult();
    }
}
