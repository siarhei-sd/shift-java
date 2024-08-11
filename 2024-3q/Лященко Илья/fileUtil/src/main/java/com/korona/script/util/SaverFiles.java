package com.korona.script.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static com.korona.script.constants.Constants.URL_DIRECTORY;

public class SaverFiles {

    public static void saveFiles(boolean addedWithoutRewriting, List<String> list, String prefixFile, String prefixDerictory) {

        String fileNameWithPrefix = URL_DIRECTORY + prefixDerictory;
        Path path = Paths.get(fileNameWithPrefix);

/*
Create directories if they are not created upon user request
 */
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

/*
Check if there is such a file and delete it, if not -a
 */
        Path pathStrings = Path.of(URL_DIRECTORY + prefixDerictory + "/" + prefixFile);
        if (addedWithoutRewriting == false) {
            if (Files.exists(pathStrings)) {
                try {
                    Files.delete(pathStrings);
                } catch (IOException e) {
                }
            }
        }
/*
If List is not zero, create a file and write data or write additional data
 */
        if (list != null) {
            for (String str : list) {
                try {
                    Files.writeString(pathStrings, str + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND
                    );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
