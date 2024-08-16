package by.shift.matveenko;

import by.shift.matveenko.data.Arguments;
import by.shift.matveenko.data.StatisticsTypes;
import by.shift.matveenko.service.FileConsumerService;

import static by.shift.matveenko.service.ArgumentService.parseArguments;
import static by.shift.matveenko.service.ArgumentService.validateArguments;
import static by.shift.matveenko.service.DirectoryService.createDirectory;
import static by.shift.matveenko.service.DirectoryService.deleteDirectory;

public class FileContentFilteringUtility {

    public static void main(String[] args) {
        Arguments arguments = parseArguments(args);

        if (!validateArguments(arguments)) {
            return;
        }

        if (arguments.getPath() != null) {
            createDirectory(arguments.getPath());
        }

        FileConsumerService fileConsumerService = new FileConsumerService(
                arguments.isFullStatistics() ? StatisticsTypes.FULL : StatisticsTypes.SHORT,
                arguments.getPath(),
                arguments.getPrefix(),
                arguments.isAddedResults());

        try {
            for (String path : arguments.getFiles()) {
                fileConsumerService.readFile(path);
            }

            fileConsumerService.closeFiles();
            fileConsumerService.printStatistics();
        } catch (Exception e) {
            System.err.println("An error occurred while processing files: " + e.getMessage());
            e.printStackTrace();
        }

        if (arguments.getPath() != null) {
            deleteDirectory(arguments.getPath());
        }
    }
}