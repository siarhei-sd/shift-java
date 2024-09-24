package by.konashuk.analyzer;

import by.konashuk.analyzer.file.FileAnalyzer;
import by.konashuk.analyzer.parameter.AnalysisParameters;
import by.konashuk.analyzer.parameter.ParametersAnalyzer;

public final class Main {

    public static void main(String[] args) {
        try {
            AnalysisParameters analyzedParameters = ParametersAnalyzer.analyzeInputParameters(args);
            FileAnalyzer.analyzeFiles(analyzedParameters);
        } catch (RuntimeException exp) {
            System.err.println("Application execution failed");
            exp.printStackTrace();
        }
    }
}
