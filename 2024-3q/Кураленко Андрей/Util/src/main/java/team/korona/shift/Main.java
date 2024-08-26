package team.korona.shift;

import team.korona.shift.parser.CommandLineParser;
import team.korona.shift.parser.FilesCommandLineParser;
import team.korona.shift.parser.ParsingException;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            CommandLineParser parser = new FilesCommandLineParser(args);

            List<File> inputFiles = parser.getInputFiles().stream()
                    .map(File::new)
                    .collect(Collectors.toList());

            checkInputFiles(inputFiles);

            FileFilterCoordinator fileCoordinator = new FileFilterCoordinator(
                    inputFiles,
                    parser.getOutputPath(),
                    parser.getPrefix(),
                    parser.isAppendMode(),
                    parser.getStatisticsType()
            );
            fileCoordinator.filterFiles();

        } catch (ParsingException e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void checkInputFiles(List<File> inputFiles) {
        for (File file : inputFiles) {
            if (!file.exists()) {
                System.err.println("Error while processing data: File " + file.getName() + " not found");
                System.exit(1);
            }
        }
    }
}