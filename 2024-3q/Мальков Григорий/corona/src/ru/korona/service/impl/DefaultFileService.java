package ru.korona.service.impl;

import ru.korona.core.model.Arguments;
import ru.korona.core.model.Stats;
import ru.korona.enums.Types;
import ru.korona.service.FileService;
import ru.korona.utils.FileProcessUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultFileService implements FileService {

    @Override
    public void processFiles(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java corona.Dataseparator [options] <input_file1> <input_file2> ...");
            System.out.println("Options:");
            System.out.println("  -o <output_path>    Set output path");
            System.out.println("  -p <file_prefix>    Set prefix for output files");
            System.out.println("  -a                  Append mode");
            System.out.println("  -s                  Short statistics");
            System.out.println("  -f                  Full statistics");
            return;
        }

        List<String> inputFiles = new ArrayList<>();

        Arguments arguments = FileProcessUtils.parseArguments(args, inputFiles);

        Stats intStats = new Stats(Types.INTEGERS);
        Stats floatStats = new Stats(Types.FLOATS);
        Stats strStats = new Stats(Types.STRINGS);

        /**
         * Некие буфферы в которые мы складываем те или иные значения согласно типу данных в строке
         */
        Map<String, List<String>> buffers = initializeBuffers();

        for (String fileName : inputFiles) {
            FileProcessUtils.parseFileContent(fileName, buffers, intStats, floatStats, strStats);
        }

        try {
            FileProcessUtils.writeBuffersToFile(buffers, arguments);

            boolean fullStats = arguments.isFullStats();
            FileProcessUtils.printStats( fullStats, List.of(intStats, floatStats, strStats));

        } catch (IOException e) {
            System.err.println("Error writing output files: " + e.getMessage());
        }
    }

    private Map<String, List<String>> initializeBuffers() {
        return Arrays.stream(Types.values()).map(Types::getType)
                .collect(Collectors.toMap(s -> s, s -> new ArrayList<>()));
    }
}
