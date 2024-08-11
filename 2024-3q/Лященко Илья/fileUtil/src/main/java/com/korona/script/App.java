package com.korona.script;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.korona.script.constants.Constants.URL_DIRECTORY;
import static com.korona.script.util.SaverFiles.saveFiles;
import static com.korona.script.util.Statistics.getStatistic;


public class App {
    public static void main(String[] args) {
        boolean addedWithoutRewriting = false;
        String prefixFile = "";
        String prefixDerictory = "";
        List<String> listContentStrings = null;
        List<String> copiedlistContentStrings = new ArrayList<>();
        List<String> stringFileNamesInArgs = new ArrayList<>();
        List<String> listFloats = new ArrayList<>();
        List<String> listInteger = new ArrayList<>();
        List<String> listString = new ArrayList<>();
        boolean isShortStatistic = false;
        boolean isFullStatistic = false;

/*
 We determine which files we have in resources and save their names in List
*/
        File dir = new File(URL_DIRECTORY);
        List<String> stringFileNames = new ArrayList<>();
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile()) {
                String fileName = Paths.get(file.toURI()).getFileName().toString();
                stringFileNames.add(fileName);
            }
        }

/*
 Reading the contents of the parameters
 */
        for (int i = 0; i < args.length; i++) {
            if (stringFileNames.contains(args[i])) {
                stringFileNamesInArgs.add(args[i]);
            } else if (args[i].equals("-o")) {
                prefixDerictory = args[i + 1];
            } else if (args[i].equals("-p")) {
                prefixFile = args[i + 1];
            } else if (args[i].equals("-a")) {
                addedWithoutRewriting = true;
            } else if (args[i].equals("-s")) {
                isShortStatistic = true;
            } else if (args[i].equals("-f")) {
                isFullStatistic = true;
            }
        }

/*
Reads the contents of files
 */
        for (String str : stringFileNamesInArgs) {
            Path path = Path.of(URL_DIRECTORY + str);
            try {
                listContentStrings = Files.readAllLines(path);
                copiedlistContentStrings.addAll(listContentStrings);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

/*
Check if the data is a number
 */
        for (String str : copiedlistContentStrings) {
            if (NumberUtils.isCreatable(str)) {
                listFloats.add(str);
                try {
                    Integer x = Integer.parseInt(str);
                    if (x != null) {
                        listInteger.add(str);
                    }
                } catch (NumberFormatException e) {
                }
            } else {
                listString.add(str);
            }
        }

/*
Removes integer leaving only floating point
 */
        for (String str : listInteger) {
            if (listFloats.contains(str)) {
                listFloats.remove(str);
            }
        }

/*
Save output data to files
 */
        saveFiles(addedWithoutRewriting, listString, prefixFile + "strings.txt", prefixDerictory);
        saveFiles(addedWithoutRewriting, listFloats, prefixFile + "floats.txt", prefixDerictory);
        saveFiles(addedWithoutRewriting, listInteger, prefixFile + "integers.txt", prefixDerictory);
/*
We display statistics
 */
        getStatistic(listString, prefixFile + "strings.txt", isShortStatistic, isFullStatistic, "strings");
        getStatistic(listFloats, prefixFile + "floats.txt", isShortStatistic, isFullStatistic, "floats");
        getStatistic(listInteger, prefixFile + "integers.txt", isShortStatistic, isFullStatistic, "integers");

    }
}
