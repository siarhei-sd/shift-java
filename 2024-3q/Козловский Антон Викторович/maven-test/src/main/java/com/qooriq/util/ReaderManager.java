package com.qooriq.util;

import java.io.*;
import java.net.URI;

public class ReaderManager {

    private ReaderManager() {
    }

    ;

    public static BufferedReader openReader(String fullPath, String prefix, String fileName, boolean append) {
        try {
            if (fullPath.isEmpty()) {
                File fullFileName = new File( prefix + fileName);
                return new BufferedReader(new InputStreamReader(fullFileName.toURL().openStream()));
            } else {
                File fullFileName = new File(fullPath + "/" + prefix + fileName);
                return new BufferedReader(new InputStreamReader(fullFileName.toURL().openStream()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
