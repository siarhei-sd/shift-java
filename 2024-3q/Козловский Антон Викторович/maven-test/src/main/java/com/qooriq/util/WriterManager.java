package com.qooriq.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriterManager {

    private WriterManager() {
    }

    ;

    public static BufferedWriter openWriter(String fullPath, String prefix, String fileName, boolean append) {
        try {
            if (fullPath.isEmpty()) {
                String fullFileName = prefix + fileName;
                return new BufferedWriter(new FileWriter(fullFileName, append));
            } else {
                String fullFileName = fullPath + "/" + prefix + fileName;
                File file = new File(fullFileName);
                file.getParentFile().mkdirs();
                file.createNewFile();
                return new BufferedWriter(new FileWriter(fullFileName, append));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
