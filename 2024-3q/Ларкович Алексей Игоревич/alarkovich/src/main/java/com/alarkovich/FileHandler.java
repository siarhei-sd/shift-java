package com.alarkovich;

import java.io.*;
import java.util.*;

public class FileHandler {
    private String      path;
    private String      fileName;

    private Boolean     append = false;

    public FileHandler(String path, String fileName, Boolean append) {
        this.path       = path;
        this.fileName   = fileName;
        this.append     = append;
    }

    public void writeToFile(ArrayList<String> dataList) {
        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(this.path, this.fileName), this.append), "UTF-8"));

            Iterator<String> it = dataList.iterator();

            while (it.hasNext()) {
                out.write(it.next());
                if (it.hasNext()) out.write("\n");
            }

            out.close();
        } catch (IOException e) {
            System.err.println("File write error");
            e.printStackTrace();
        }
    }
}
